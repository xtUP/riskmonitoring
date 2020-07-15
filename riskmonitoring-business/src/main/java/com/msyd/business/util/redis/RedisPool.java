package com.msyd.business.util.redis;
//package com.msyd.business.util.redis;
//
//import org.apache.commons.lang3.StringUtils;
//
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
///**
// * 
// * @author MinSheng-Hao
// *
// */
//public class RedisPool {
//	public static final int DEFAULT_PORT_NUM = 6379;
//	public static final int DEFAULT_TIMEOUT = 3000;
//	public static final int DEFAULT_POOL_SIZE = 15;
//	private String host;
//	private int port;
//	private int timeout;
//	private String password;
//	private int poolSize;
//
//	private int maxIdle;
//	private int maxActive;
//	private long maxWait;
//	private JedisPool jedisPool = null;
//
//	public RedisPool() {
//		this.port = 6379;
//		this.timeout = 3000;
//		this.poolSize = 15;
//	}
//
//	public RedisPool(String host, int port, String password, int poolSize, int timeout, int maxIdle, int maxActive,
//			long maxWait) {
//		this.host = host;
//		this.port = port;
//		this.password = password;
//		this.poolSize = poolSize;
//		this.timeout = timeout;
//		this.maxIdle = maxIdle;
//		this.maxActive = maxActive;
//		this.maxWait = maxWait;
//	}
//
//	public RedisPool(String host, int port, String password, int poolSize, int timeout) {
//		this.host = host;
//		this.port = port;
//		this.password = password;
//		this.timeout = timeout;
//		this.poolSize = poolSize;
//	}
//
//	public RedisPool(String host, int port, String password, int poolSize) {
//		this(host, port, password, poolSize, 3000);
//	}
//
//	public RedisPool(String host, int port, String password) {
//		this(host, port, password, 15);
//	}
//
//	public RedisPool(String host, String password) {
//		this(host, 6379, password);
//	}
//
//	public Jedis getRedisFromPool() {
//		Jedis jedis = null;
//		if (null != this.jedisPool) {
//			jedis = (Jedis) this.jedisPool.getResource();
//		}
//		return jedis;
//	}
//
//	public Jedis getRedisFromPool(int database) {
//		Jedis jedis = getRedisFromPool();
//		if ((null != jedis) && (database > 0)) {
//			jedis.select(database);
//		}
//		return jedis;
//	}
//
//	public void returnToPool(Jedis redis) {
//		if (null != redis)
//			redis.close();
//	}
//
//	public void init() {
//		if (null != this.jedisPool) {
//			return;
//		}
//		JedisPoolConfig config = new JedisPoolConfig();
//		config.setMaxTotal(this.poolSize);
//		config.setMinIdle(5);
//		int maxIdle = config.getMinIdle() + 5;
//		if (maxIdle > config.getMaxTotal()) {
//			maxIdle = config.getMaxTotal();
//		}
//		config.setMaxIdle(this.maxIdle);
//		config.setMaxWaitMillis(this.maxWait);
//		config.setBlockWhenExhausted(true);
//		config.setTestOnBorrow(false);
//		config.setTestOnReturn(false);
//		config.setTestWhileIdle(true);
//		config.setMinEvictableIdleTimeMillis(600000L);
//		config.setTimeBetweenEvictionRunsMillis(30000L);
//		config.setNumTestsPerEvictionRun(-1);
//		if (StringUtils.isBlank(this.password))
//			this.jedisPool = new JedisPool(config, this.host, this.port, this.timeout);
//		else
//			this.jedisPool = new JedisPool(config, this.host, this.port, this.timeout, this.password);
//	}
//
//	public void destroy() {
//		if (null != this.jedisPool) {
//			JedisPool _pool = this.jedisPool;
//			this.jedisPool = null;
//
//			_pool.destroy();
//		}
//	}
//
//	public String getHost() {
//		return this.host;
//	}
//
//	public void setHost(String host) {
//		this.host = host;
//	}
//
//	public int getPort() {
//		return this.port;
//	}
//
//	public void setPort(int port) {
//		this.port = port;
//	}
//
//	public int getTimeout() {
//		return this.timeout;
//	}
//
//	public void setTimeout(int timeout) {
//		this.timeout = timeout;
//	}
//
//	public String getPassword() {
//		return this.password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public int getPoolSize() {
//		return this.poolSize;
//	}
//
//	public void setPoolSize(int poolSize) {
//		this.poolSize = poolSize;
//	}
//
//	public int getMaxIdle() {
//		return maxIdle;
//	}
//
//	public void setMaxIdle(int maxIdle) {
//		this.maxIdle = maxIdle;
//	}
//
//	public int getMaxActive() {
//		return maxActive;
//	}
//
//	public void setMaxActive(int maxActive) {
//		this.maxActive = maxActive;
//	}
//
//	public long getMaxWait() {
//		return maxWait;
//	}
//
//	public void setMaxWait(long maxWait) {
//		this.maxWait = maxWait;
//	}
//
//	public static void main(String[] args) {
//		Jedis jedis = Redises.getPooledRedis();
//		jedis.publish("logChannel", "pubtest1111111");
//	}
//}