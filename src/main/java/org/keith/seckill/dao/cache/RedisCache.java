package org.keith.seckill.dao.cache;

import javax.annotation.Resource;

import org.keith.seckill.model.bo.Seckill;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class RedisCache {

	@Resource
	private RedisTemplate redisTemplate;
	
	@Resource
	private StringRedisTemplate stringRedisTemplate; 

	/**
	 * 写数据
	 */
    public boolean set(final byte[] key, final Object value,final long liveTime) {
    	RuntimeSchema<Object> schema = (RuntimeSchema<Object>) RuntimeSchema.createFrom(value.getClass());
    	final byte[] bytes = ProtostuffIOUtil.toByteArray(value, schema,
				LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
	    return (boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
	       public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
	          connection.set(key,bytes);
//	          if (liveTime > 0) {
//	              connection.expire(key,liveTime);
//	          }
	          return true;
	       }
	    });
    }

	/**
	 * 读数据
	 */
	public <T> T get(final byte[] key, final Class<T> classType) {
	    return (T) redisTemplate.execute(new RedisCallback<T>() {
	       public T doInRedis(RedisConnection connection) throws DataAccessException {
	    	    RuntimeSchema<T> schema = RuntimeSchema.createFrom(classType);
	    	    byte[] bytes = connection.get(key);
	    	    if (bytes != null) {
					T t = schema.newMessage();
					ProtostuffIOUtil.mergeFrom(bytes, t, schema);
					// seckill被反序列化
					return t;
				}
				return null;
	       }
	    });
	}

	/**
	 * 删数据
	 */
	public long del(final String... keys) {
	    return (Long)redisTemplate.execute(new RedisCallback() {
	       public Long doInRedis(RedisConnection connection) throws DataAccessException {
	          long result = 0;
	          for (int i = 0; i < keys.length; i++) {
	              result = connection.del(keys[i].getBytes());
	          }
	          return result;
	       }
	    });
	}
	
}
