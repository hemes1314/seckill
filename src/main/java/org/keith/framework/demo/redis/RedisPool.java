package org.keith.framework.demo.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public final class RedisPool {

	// Redis服务器IP
	private static String ADDR = "192.168.75.128";
	// Redis的端口号
	private static int PORT = 6379;
	// 访问密码
	private static String AUTH = "123456";
	// ms
	private static int TIMEOUT = 10000;
	private static JedisPool jedisPool = null;

	/**
	 * 初始化Jedis连接池
	 */
	static {
		try {
			//详细参数可看AC-redis.xml
			JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
			jedisPool = new JedisPool(jedisPoolConfig, ADDR, PORT, TIMEOUT, AUTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取jedis实例
	 */
	public synchronized static Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
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
