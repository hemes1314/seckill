package org.keith.framework.demo.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public final class ShardedRedisPool {

	// ms
	private static int TIMEOUT = 2000;
	private static ShardedJedisPool jedisPool = null;

	/**
	 * 初始化Jedis连接池
	 */
	static {
		try {
			//详细参数可看AC-redis.xml
			JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
			
			List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
			
			JedisShardInfo shardInfo1 = new JedisShardInfo("redis://:123456@192.168.75.128:6379/0");
			shards.add(shardInfo1);
			
			JedisShardInfo shardInfo2 = new JedisShardInfo("redis://:123456@192.168.75.130:6379/0");
			shards.add(shardInfo2);
			
			jedisPool = new ShardedJedisPool(jedisPoolConfig, shards);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取jedis实例
	 */
	public synchronized static ShardedJedis getJedis() {
		try {
			if (jedisPool != null) {
				ShardedJedis resource = jedisPool.getResource();
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
