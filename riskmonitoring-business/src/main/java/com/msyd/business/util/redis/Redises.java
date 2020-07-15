package com.msyd.business.util.redis;
//package com.msyd.business.util.redis;
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.alibaba.fastjson.JSONObject;
//
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.commands.JedisCommands;
//
///**
// * 
// * @author MinSheng-Hao
// *
// */
//public class Redises implements InvocationHandler {
//	private static Logger log = LoggerFactory.getLogger(Redises.class);
//
//	private static Map<String, RedisPool> pools = null;
//
//	private static Map<String, InvocationHandler> handlers = null;
//
//	private String poolName;
//
//	private static final String LOCK_SUCCESS = "OK";
//
//	private static final Long RELEASE_SUCCESS = 1L;
//
//	/** NX key不存在的时候才set，存在不set */
//	private static final String NX_XX = "NX";
//
//	/** 超时时间单位 EX秒，PX毫秒 */
//	private static final String EX_PX = "PX";
//
//	private Redises(String poolName) {
//		this.poolName = poolName;
//	}
//
//	public static RedisPool getRedisPool(String poolName) {
//		if (null == pools) {
//			synchronized (Redises.class) {
//				if (null == pools) {
//					pools = new ConcurrentHashMap<String, RedisPool>();
//				}
//			}
//		}
//		RedisPool redisPool = (RedisPool) pools.get(poolName);
//		if (null == redisPool) {
//			synchronized (pools) {
//				redisPool = (RedisPool) pools.get(poolName);
//				if (null == redisPool) {
//					PropertiesUtil util = new PropertiesUtil("redis.properties");
//					int timeout;
//					String host;
//					int port;
//					String password;
//					int poolSize;
//					int maxIdle;
//					int maxActive;
//					long maxWait;
//					host = util.readProperty("redis.host").trim();
//					port = Integer.parseInt(util.readProperty("redis.port").trim());
//					password = util.readProperty("redis.password").trim();
//					poolSize = Integer.parseInt(util.readProperty("redis.poolSize").trim());
//					timeout = Integer.parseInt(util.readProperty("redis.timeout").trim());
//					maxIdle = Integer.parseInt(util.readProperty("redis.maxIdle").trim());
//					maxActive = Integer.parseInt(util.readProperty("redis.maxActive").trim());
//					maxWait = Long.parseLong(util.readProperty("redis.maxWait").trim());
//					redisPool = new RedisPool(host, port, password, poolSize, timeout, maxIdle, maxActive, maxWait);
//					redisPool.init();
//					pools.put(poolName, redisPool);
//				}
//			}
//		}
//		return (RedisPool) redisPool;
//	}
//
//	/**
//	 * 尝试获取分布式锁
//	 * 
//	 * @param jedis
//	 *            Redis客户端
//	 * @param lockKey
//	 *            锁
//	 * @param requestId
//	 *            请求标识
//	 * @param expireTime
//	 *            超期时间 这里单位是毫秒。查过这个时间锁就释放了。
//	 * @return 是否获取成功
//	 * 
//	 *         可以看到，我们加锁就一行代码：jedis.set(String key, String value, String nxxx,
//	 *         String expx, int time)， 这个set()方法一共有五个形参：
//	 *         第一个为key，我们使用key来当锁，因为key是唯一的。
//	 *         第二个为value，我们传的是requestId，很多童鞋可能不明白，有key作为锁不就够了吗，为什么还要用到value？
//	 *         原因就是我们在上面讲到可靠性时，分布式锁要满足第四个条件解铃还须系铃人，通过给value赋值为requestId，
//	 *         我们就知道这把锁是哪个请求加的了，在解锁的时候就可以有依据。requestId可以使用UUID.randomUUID().
//	 *         toString()方法生成。 第三个为nxxx，这个参数我们填的是NX，意思是SET IF NOT
//	 *         EXIST，即当key不存在时，我们进行set操作；若key已经存在，则不做任何操作；
//	 *         只能取NX或者XX，如果取NX，则只有当key不存在是才进行set，如果取XX，则只有当key已经存在时才进行set
//	 *         第四个为expx，这个参数我们传的是PX 毫秒，意思是我们要给这个key加一个过期的设置，具体时间由第五个参数决定。
//	 *         只能取EX或者PX，代表数据过期时间的单位，EX代表秒，PX代表毫秒。
//	 *         第五个为time，与第四个参数相呼应，代表key的过期时间。 总的来说，执行上面的set()方法就只会导致两种结果：1.
//	 *         当前没有锁（key不存在），那么就进行加锁操作，并对锁设置个有效期，同时value表示加锁的客户端。2.
//	 *         已有锁存在，不做任何操作。
//	 *         心细的童鞋就会发现了，我们的加锁代码满足我们可靠性里描述的三个条件。首先，set()加入了NX参数，可以保证如果已有key存在，
//	 *         则函数不会调用成功，也就是只有一个客户端能持有锁，满足互斥性。其次，由于我们对锁设置了过期时间，
//	 *         即使锁的持有者后续发生崩溃而没有解锁，锁也会因为到了过期时间而自动解锁（即key被删除），不会发生死锁。最后，
//	 *         因为我们将value赋值为requestId，代表加锁的客户端请求标识，那么在客户端在解锁的时候就可以进行校验是否是同一个客户端。
//	 *         由于我们只考虑Redis单机部署的场景，所以容错性我们暂不考虑。 set key value [expiration EX
//	 *         seconds|PX milliseconds] [NX|XX]
//	 */
//	public static boolean tryLock(String lockKey, String requestId, Integer expireMilliTime) {
//		String script = "return redis.call('set',KEYS[1],ARGV[1],'px',ARGV[2],'nx') ";
//		Jedis jedis = null;
//		try {
//			jedis = getPooledRedis();
//			// 成功返回OK，失败返回NULL
//			log.info("===============尝试获取锁================");
//			// String result = jedis.set(lockKey, requestId, NX_XX, EX_PX,
//			// expireMilliTime);
//			Object result = jedis.eval(script, 1, lockKey, requestId, String.valueOf(expireMilliTime));
//			if (LOCK_SUCCESS.equals(result)) {
//				log.info("====获取锁成功====lockKey[" + lockKey + "] requestId[" + requestId + "] result[" + result
//						+ "]===========");
//				// 加锁成功，返回true
//				return true;
//			} else {
//				log.info("====获取锁失败====lockKey[" + lockKey + "] requestId[" + requestId + "] result[" + result
//						+ "]===========");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		} finally {
//			Redises.returnToPool(jedis);
//		}
//		return false;
//	}
//
//	/**
//	 * 释放分布式锁
//	 * 
//	 * @param jedis
//	 *            Redis客户端
//	 * @param lockKey
//	 *            锁
//	 * @param requestId
//	 *            请求标识
//	 * @return 是否释放成功
//	 * 
//	 *         可以看到，我们解锁只需要两行代码就搞定了！ 第一行代码，我们写了一个简单的Lua脚本代码。
//	 *         第二行代码，我们将Lua代码传到jedis.eval()方法里，并使参数KEYS[1]赋值为lockKey，ARGV[1]
//	 *         赋值为requestId。 eval()方法是将Lua代码交给Redis服务端执行。
//	 *         那么这段Lua代码的功能是什么呢？其实很简单，首先获取锁对应的value值，检查是否与requestId相等，如果相等则删除锁（
//	 *         解锁）。 那么为什么要使用Lua语言来实现呢？因为要确保上述操作是原子性的 。
//	 *         那么为什么执行eval()方法可以确保原子性，源于Redis的特性，下面是官网对eval命令的部分解释：
//	 *         简单来说，就是在eval命令执行Lua代码的时候，Lua代码将被当成一个命令去执行，并且直到eval命令执行完成，
//	 *         Redis才会执行其他命令。
//	 */
//	public static boolean releaseLock(String lockKey, String requestId) {
//		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
//		Jedis jedis = null;
//		try {
//			jedis = getPooledRedis();
//			log.info("===============尝试释放锁================");
//			Object result = jedis.eval(script, Collections.singletonList(lockKey),
//					Collections.singletonList(requestId));
//			if (RELEASE_SUCCESS.equals(result)) {
//				log.info("===============释放锁成功 lockKey[" + lockKey + "] requestId[" + requestId + "] result[" + result
//						+ "]================");
//				return true;
//			} else {
//				log.info("===============释放锁失败 lockKey[" + lockKey + "] requestId[" + requestId + "] result[" + result
//						+ "]================");
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		} finally {
//			Redises.returnToPool(jedis);
//		}
//		return false;
//
//	}
//
//	@SuppressWarnings("unused")
//	private static String extract(String str, String key) {
//		int keyIndex = str.indexOf(key);
//		int colonIdx = keyIndex + key.length();
//		if ((keyIndex >= 0) && (colonIdx < str.length())) {
//			int commaIdx = str.indexOf(',', colonIdx);
//			if (commaIdx > colonIdx) {
//				return str.substring(colonIdx + 1, commaIdx);
//			}
//			return str.substring(colonIdx + 1);
//		}
//		return null;
//	}
//
//	public static RedisPool getRedisPool() {
//		return getRedisPool("X_REDIS_CS_COMMON");
//	}
//
//	public static Jedis getPooledRedis(String poolName) {
//		return getRedisPool(poolName).getRedisFromPool();
//	}
//
//	public static Jedis getPooledRedis() {
//		return getPooledRedis("X_REDIS_CS_COMMON");
//	}
//
//	public static void returnToPool(Jedis redis) {
//		if (null == redis) {
//			return;
//		}
//		getRedisPool().returnToPool(redis);
//	}
//
//	public static void returnToPool(String poolName, Jedis redis) {
//		getRedisPool(poolName).returnToPool(redis);
//	}
//
//	public static JedisCommands getPooledJedisCommands(String poolName) {
//		if (null == handlers) {
//			synchronized (Redises.class) {
//				if (null == handlers) {
//					handlers = new ConcurrentHashMap<String, InvocationHandler>();
//				}
//			}
//		}
//		InvocationHandler invocationHandler = (InvocationHandler) handlers.get(poolName);
//		if (null == invocationHandler) {
//			synchronized (handlers) {
//				invocationHandler = (InvocationHandler) handlers.get(poolName);
//				if (null == invocationHandler) {
//					invocationHandler = new Redises(poolName);
//					handlers.put(poolName, invocationHandler);
//				}
//			}
//		}
//
//		return (JedisCommands) (JedisCommands) Proxy.newProxyInstance(JedisCommands.class.getClassLoader(),
//				new Class[] { JedisCommands.class }, invocationHandler);
//	}
//
//	public static JedisCommands getPooledJedisCommands() {
//		return getPooledJedisCommands("X_REDIS_CS_COMMON");
//	}
//
//	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//		long start = System.currentTimeMillis();
//
//		if (log.isDebugEnabled()) {
//			log.debug("proxy instance of JedisCommands invoked (methodName:" + method.getName() + ", arguments:"
//					+ Arrays.toString(args) + ")");
//		}
//
//		Jedis redis = getPooledRedis(this.poolName);
//
//		Object rtnRslt = null;
//		try {
//			rtnRslt = method.invoke(redis, args);
//		} finally {
//			returnToPool(this.poolName, redis);
//		}
//
//		if (log.isInfoEnabled()) {
//			log.info("excecute JedisCommands " + method.getName() + "(" + Arrays.toString(args) + ") elapsed time:"
//					+ (System.currentTimeMillis() - start) + " ms!");
//		}
//
//		return rtnRslt;
//	}
//
//	public static void expireAt(String key, long timestamp) {
//		Jedis jedis = null;
//		try {
//			jedis = getPooledRedis();
//			jedis.expireAt(key, timestamp);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			Redises.returnToPool(jedis);
//		}
//	}
//
//	public static void expire(String key, EnumExpireTimeUnit timeUnit, Long expireTime) {
//		Jedis jedis = null;
//		try {
//			Long time = 0L;
//			if (expireTime == null) {
//				expireTime = 0L;
//			}
//			jedis = getPooledRedis();
//			if (timeUnit != null) {
//				if (timeUnit == EnumExpireTimeUnit.MILLISECOND) {
//					jedis.pexpire(key, expireTime);
//				} else {
//					switch (timeUnit) {
//					case SECOND:// 秒，redis expire的单位就是秒
//						time = expireTime;
//						break;
//					case MINUTE:
//						time = expireTime * 60;
//						break;
//					case HOUR:
//						time = expireTime * 3600;
//						break;
//					case DAY:
//						time = expireTime * 24 * 3600;
//						break;
//					default:
//						throw new RuntimeException("时间单位不存在");
//					}
//					jedis.expire(key, time.intValue());
//				}
//			} else {
//				throw new RuntimeException("时间单位不能为空");
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			Redises.returnToPool(jedis);
//		}
//	}
//
//	public static Set<String> getAllKeys() {
//		Jedis jedis = null;
//		Set<String> set = null;
//		try {
//			jedis = getPooledRedis();
//			set = jedis.keys("*");
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			Redises.returnToPool(jedis);
//		}
//		return set;
//
//	}
//
//	public static Set<String> getKeysByRegular(String regular) {
//		Jedis jedis = null;
//		Set<String> set = null;
//		try {
//			jedis = getPooledRedis();
//			set = jedis.keys(regular);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			Redises.returnToPool(jedis);
//		}
//		return set;
//	}
//
//	public static long getTtl(String key) {
//		long value = 0;
//		Jedis jedis = null;
//		try {
//			jedis = getPooledRedis();
//			value = jedis.ttl(key);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			Redises.returnToPool(jedis);
//		}
//		return value;
//	}
//
//	public static String get(String key) {
//		String value = null;
//		Jedis jedis = null;
//		try {
//			jedis = getPooledRedis();
//			value = jedis.get(key);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			Redises.returnToPool(jedis);
//		}
//		return value;
//	}
//	public static String hget(String key,String filed) {
//		String value = null;
//		Jedis jedis = null;
//		try {
//			jedis = getPooledRedis();
//			value =jedis.hget(key, filed);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			Redises.returnToPool(jedis);
//		}
//		return value;
//	}
//	public static Map<String,String> hgetAll(String key) {
//		Map<String,String> value = null;
//		Jedis jedis = null;
//		try {
//			jedis = getPooledRedis();
//			value = jedis.hgetAll(key);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			Redises.returnToPool(jedis);
//		}
//		return value;
//	}
//	public static Map<String, Object> getMap(String key) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		boolean success = true;
//		String value = null;
//		Jedis jedis = null;
//		try {
//			jedis = getPooledRedis();
//			value = jedis.get(key);
//		} catch (Exception e) {
//			success = false;
//			e.printStackTrace();
//		} finally {
//			Redises.returnToPool(jedis);
//		}
//		map.put("success", success);
//		map.put("result", value);
//		return map;
//	}
//
//	public static void del(String key) {
//		Jedis jedis = null;
//		try {
//			jedis = getPooledRedis();
//			jedis.del(key);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			Redises.returnToPool(jedis);
//		}
//	}
//
//	/**
//	 * 设置普通的key-value，无有效期
//	 * 
//	 * @param key
//	 * @param value
//	 */
//	public static void set(String key, String value) {
//		Jedis jedis = null;
//		try {
//			jedis = getPooledRedis();
//			jedis.set(key, value);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			Redises.returnToPool(jedis);
//		}
//	}
//
//	/**
//	 * 设置普通key-value 添加有效期
//	 * 
//	 * @param key
//	 * @param value
//	 * @param timeUnit
//	 *            时间单位
//	 * @param expireTime
//	 *            超时时间 set key value [expiration EX seconds|PX milliseconds]
//	 *            [NX|XX] EVAL script numkeys key [key ...] arg [arg ...]
//	 */
//	public static String set(String key, String value, EnumExpireTimeUnit timeUnit, Long expireTime) {
//		String script = "return redis.call('set',KEYS[1],ARGV[1],'ex',ARGV[2]) ";
//		Jedis jedis = null;
//		try {
//			Long time = 30 * 60L;
//			jedis = getPooledRedis();
//			if (expireTime != null && expireTime > 0 && timeUnit != null) {
//				switch (timeUnit) {
//				case SECOND:// 秒，redis expire的单位就是秒
//					time = expireTime;
//					break;
//				case MINUTE:
//					time = expireTime * 60;
//					break;
//				case HOUR:
//					time = expireTime * 3600;
//					break;
//				case DAY:
//					time = expireTime * 24 * 3600;
//					break;
//				default:
//					throw new RuntimeException("时间单位不存在");
//				}
//			}
//
//			Object result = jedis.eval(script, 1, key, value, String.valueOf(time));
//			return String.valueOf(result);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			Redises.returnToPool(jedis);
//		}
//		return null;
//	}
//
//	public static Long incrBy(String key, long num) {
//		Jedis jedis = null;
//		try {
//			jedis = getPooledRedis();
//			Long incrBy = jedis.incrBy(key, num);
//			return incrBy;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			Redises.returnToPool(jedis);
//		}
//		return null;
//	}
//
//	public static Long incr(String key) {
//		Jedis jedis = null;
//		try {
//			jedis = getPooledRedis();
//			Long incr = jedis.incr(key);
//			return incr;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			Redises.returnToPool(jedis);
//		}
//		return null;
//	}
//
//	public static Long decrBy(String key, long num) {
//		Jedis jedis = null;
//		try {
//			jedis = getPooledRedis();
//			Long decrBy = jedis.decrBy(key, num);
//			return decrBy;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			Redises.returnToPool(jedis);
//		}
//		return null;
//	}
//
//	public static Long decr(String key) {
//		Jedis jedis = null;
//		try {
//			jedis = getPooledRedis();
//			Long decr = jedis.decr(key);
//			return decr;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			Redises.returnToPool(jedis);
//		}
//		return null;
//	}
//
//	public static Boolean hasKey(String key) {
//		Jedis jedis = null;
//		try {
//			jedis = getPooledRedis();
//			return jedis.exists(key);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			Redises.returnToPool(jedis);
//		}
//		return null;
//	}
//
//	/**
//	 * setnx 这里使用lua脚本，使用原生的set命令 set key value [expiration EX seconds|PX
//	 * milliseconds] [NX|XX]
//	 * 
//	 * @param key
//	 * @param value
//	 * @return String 设置成功返回OK，失败返回null
//	 */
//	public static String setnx(String key, String value) {
//		String script = "return redis.call('set',KEYS[1],ARGV[1],'nx') ";
//		Jedis jedis = null;
//		try {
//			jedis = getPooledRedis();
//			Object result = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(value));
//			return String.valueOf(result);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			Redises.returnToPool(jedis);
//		}
//		return null;
//	}
//
//	public static void main(String[] args) {
//
//		// String reqId = UUID.randomUUID().toString().replace("-", "");
//		// boolean ret = tryLock("key3", reqId, 20000);
//		// System.out.println(ret);
//		// expire("key3", EnumExpireTimeUnit.DAY, 1L);
//		 String setnx = setnx("key3", "333");
//		 System.out.println(setnx);
////		String set = set("key3", "3", EnumExpireTimeUnit.MINUTE, 1L);
////		try {
////			Thread.sleep(2000);
////		} catch (InterruptedException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		String string = get("key3");
////		String orig_prcp_daily=get("orig_prcp_daily");
//		Set<String> hasht=getAllKeys();
//		Iterator<String> it = hasht.iterator();
//		while (it.hasNext()) {
//		  String str = it.next();
//		  System.out.println(str);
//		}
////System.out.println(get("loan_statistics_4group_2019-12-28"));
////System.out.println(get("loan_statistics_4group_2019-12-30"));
////System.out.println(get("loan_statistics_total_2019-12-28"));
////System.out.println(get("loan_statistics_total_2019-12-30"));
////System.out.println(get("loan_historical_data_2019-12-27"));
////System.out.println(get("loan_historical_data_2019-12-29"));
//		System.out.println("-----------------累计数据计算统计存量值----------------------");
//		System.out.println("当天累计放款------------"+hget("loan_statistics_total_2020-01-03", "orig_prcp_daily"));
//		System.out.println("当天累计还款------------"+hget("loan_statistics_total_2020-01-03", "deductionPrincipal_daily"));
//		System.out.println("当天的贷款余额----------"+hget("loan_statistics_total_2020-01-03", "loan_os_prcp_daily"));
//		System.out.println("历史累计放款------------"+hget("loan_statistics_total_2020-01-03", "release_totalamt"));
//		System.out.println("历史累计余额------------"+hget("loan_statistics_total_2020-01-03", "loan_balance_amt"));
//		System.out.println("-----------------每日放款分产品统计----------------------");
//		String str=hget("loan_statistics_4group_2020-01-07", "TEST");
//		JSONObject jo=JSONObject.parseObject(str);
//		System.out.println(str);
////		System.out.println(jo.get("statistics_date"));
//		System.out.println("当天累计放款------------"+hget("loan_statistics_4group_2020-01-07", "TEST"));
////		System.out.println(set);
////		System.out.println(string);
////		System.out.println(orig_prcp_daily);
//		// long ttl = getTtl("name");
//		// System.out.println(ttl);
//		
////		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
////        Map<String, Object> map1 = new HashMap<String, Object>();
////        map1.put("name", "p");
////        map1.put("cj", "5");
////        Map<String, Object> map2 = new HashMap<String, Object>();
////        map2.put("name", "h");
////        map2.put("cj", "12");
////        Map<String, Object> map3 = new HashMap<String, Object>();
////        map3.put("name", "f");
////        map3.put("cj", "31");
////        list.add(map1);
////        list.add(map3);
////        list.add(map2);
////		System.out.println(list);
////		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
////		String strs=df.format(new Date());
////		System.out.println(strs);
////		char []c=new char[]{'1','2','3'};
////		for(int i=0;i<c.length;i++){
////			System.out.println(c[i]);
////		}
//	}
//
//}