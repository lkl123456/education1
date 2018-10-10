package com.adks.dbclient.redis;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RedisClient {
    Logger logger = LoggerFactory.getLogger(RedisClient.class);

    protected JedisPool jedisPool = null;
    protected String host;
    protected int port;
    protected int timeout;
    protected String password;

    public void init() {
        logger.info("redis init,host:{},port:{},timeout:{}", host, port, timeout);
        if (StringUtils.isNotBlank(password)) {
            jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout, password);
        } else {
            jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout);
        }

    }

    /**
     * 为有序集key的成员member的score值加上增量increment。如果key中不存在member，就在key中添加一个member，score是increment（就好像它之前的score是0.0）。如果key不存在，就创建一个只含有指定member成员的有序集合。
     * 当key不是有序集类型时，返回一个错误。
     * score值必须是字符串表示的整数值或双精度浮点数，并且能接受double精度的浮点数。也有可能给一个负数来减少score的值。
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Double zincrby(String key, double score, String member) {
        Jedis jedis = jedisPool.getResource();
        Double result = 0d;
        boolean operationSuccess = true;
        try {
            result = jedis.zincrby(key, score, member);
            logger.info("zincrby ,key:{},score:{},member:{}", key, score, member);
        } catch (JedisConnectionException e) {
            logger.warn("zincrby error,key:{},score:{},member:{},reason:{}", key, score, member, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
            	jedis.close();
            }
        }
        return result;
    }

    /**
     * 返回有序集key中，成员member的score值。如果member元素不是有序集key的成员，或key不存在，返回nil。
     * @param key
     * @param member
     * @return
     */
    public Double zscore(String key, String member) {
        Jedis jedis = jedisPool.getResource();
        Double result = 0d;
        boolean operationSuccess = true;
        try {
            result = jedis.zscore(key, member);
        } catch (JedisConnectionException e) {
            logger.warn("zscore error,key:{},member:{},reason:{}", key, member, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * 字符串值value关联到key
     * redis set
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String result = null;
        boolean operationSuccess = true;
        try {
            result = jedis.set(key, value);
            logger.debug("redis set,key:{},value:{}", key, value);
        } catch (JedisConnectionException e) {
            logger.warn("redis set error,key:{},value:{},reason:{}", key, value, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }
    
    /**
     * 检查给定key是否存在
     * redis exists
     * @param key
     * @return - whether the key is existed in redis
     */
    public boolean exists(String key) {
    	Jedis jedis = jedisPool.getResource();
        boolean result = false;
        boolean operationSuccess = true;
        try {
        	result = jedis.exists(key);
            logger.debug("redis exists,key:{},value:{}", key, result);
        } catch (JedisConnectionException e) {
            logger.warn("redis exists error,key:{},value:{},reason:{}", key, result, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * 将值value关联到key，并将key的生存时间设为seconds(秒)。
     * redis set
     * @param key
     * @param senconds
     * @param value
     * @return
     */
    public String setex(String key, int senconds, String value) {
        Jedis jedis = jedisPool.getResource();
        String result = null;
        boolean operationSuccess = true;
        try {
            result = jedis.setex(key, senconds, value);
            logger.debug("redis setex,key:{},value:{}", key, value);
        } catch (JedisConnectionException e) {
            logger.warn("redis setex error,key:{},value:{},reason:{}", key, value, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * 将值value关联到key， 如果 key 已经存在，返回 0， nx 是 not exist 的意思
     * redis set
     * @param key
     * @param value
     * @return
     */
    public long setnx(String key,String value) {
        Jedis jedis = jedisPool.getResource();
        long result = -1;
        boolean operationSuccess = true;
        try {
            result = jedis.setnx(key, value);
            logger.debug("redis setnx,key:{},value:{}", key, value);
        } catch (JedisConnectionException e) {
            logger.warn("redis setnx error,key:{},value:{},reason:{}", key, value, e.getMessage());
            operationSuccess = false;

            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }

        return result;
    }

    /**
     * 获取并更改数据  
     * redis setget
     * @param key
     * @param value
     * @return
     */
    public String getSet(String key,String value) {
        Jedis jedis = jedisPool.getResource();
        String result = null;
        boolean operationSuccess = true;
        try {
            result = jedis.getSet(key, value);
            logger.debug("redis getSet,key:{},value:{}", key, value);
        } catch (JedisConnectionException e) {
            logger.warn("redis getSet error,key:{},value:{},reason:{}", key, value, e.getMessage());
            operationSuccess = false;

            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }

        return result;
    }
    /**
     * 返回给定key的有效时间，如果是-1则表示永远有效
     * @param key
     * @return
     */
    public Long ttl(String key) {
        Jedis jedis = jedisPool.getResource();
        Long remainTime = Long.valueOf(-1);
        boolean operationSuccess = true;
        try {
            remainTime = jedis.ttl(key);
        } catch (JedisConnectionException e) {
            logger.warn("redis ttl error,key:{},reason:{}", key, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        }finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return remainTime;
    }


    /**
     * 根据Key 返回对应的Value值
     * redis get
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String result = null;
        boolean operationSuccess = true;
        try {
            result = jedis.get(key);
        } catch (JedisConnectionException e) {
            logger.warn("redis get error,key:{},reason:{}", key, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * 移除给定的一个或多个key,如果key不存在,则忽略该命令
     * redis delete
     * @param key
     * @return
     */
    public long delete(String key) {
        Jedis jedis = jedisPool.getResource();
        long result = 0;
        boolean operationSuccess = true;
        try {
            result = jedis.del(key);
            logger.debug("redis delete,key:{}", key);
        } catch (JedisConnectionException e) {
            logger.warn("redis delete error,key:{},reason:{}", key, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }
    
    /**
     * 批处理删除、移除给定的多个key
     * redis batch delete with pipeline
     * @param keySet
     */
    public void batchDelete(Set<String> keySet) {
        Jedis jedis = jedisPool.getResource();
        Pipeline pipeline = jedis.pipelined();
        boolean operationSuccess = true;
        try {
        	for (String key : keySet) {
        		pipeline.del(key);
        	}
        	pipeline.sync();
            logger.debug("redis delete with pipeline");
        } catch (JedisConnectionException e) {
            logger.warn("redis delete with pipeline error,reason:{}", e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
    }
    
    /**
     * 批量增加Key 对应有序集合
     * @param key
     * @param scoreMembers Map<String, Double>
     */
    public long batchZAdd(String key, Map<String, Double> scoreMembers) {
        Jedis jedis = jedisPool.getResource();
        boolean operationSuccess = true;
        long result = 0;
        try {
        	result = jedis.zadd(key, scoreMembers);
            logger.debug("redis batchZAdd, key:{}", key);
        } catch (JedisConnectionException e) {
            logger.warn("redis delete with pipeline error,reason:{}", e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }


    /**
     * 哈希表key中的域field的值设为value
     * redis het
     * @param key
     * @return
     */
    public long hset(String key, String field, String value) {
        Jedis jedis = jedisPool.getResource();
        long result = 0;
        boolean operationSuccess = true;
        try {
            result = jedis.hset(key, field, value);
            logger.debug("redis hset,key:{},field:{}", key, field);
        } catch (JedisConnectionException e) {
            logger.warn("redis get error,key:{},field:{},reason:{}", key, field, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }
    /**
     * 返回哈希表key中 给定域field的值 
     * redis hget
     * @param key
     * @return
     */
    public String hget(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        String result = null;
        boolean operationSuccess = true;
        try {
            result = jedis.hget(key, field);
            logger.debug("redis hget,key:{},field:{}", key, field);
        } catch (JedisConnectionException e) {
            logger.warn("redis get error,key:{},field:{},reason:{}", key, field, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }
    /**
     * 设置Key 对应的值为 哈希表 hashMap
     * redis hmset
     * @param key
     * @return
     */
    public String hmset(String key, Map<String, String> hash) {
        Jedis jedis = jedisPool.getResource();
        String result = "";
        boolean operationSuccess = true;
        try {
            result = jedis.hmset(key, hash);
            logger.debug("redis hset,key:{},hash:{}", key, hash);
        } catch (JedisConnectionException e) {
            logger.warn("redis get error,key:{},hash:{},reason:{}", key, hash, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * 返回哈希表key中所有域和值
     * redis het
     * @param key
     * @return
     */
    public Map<String, String> hgetAll(String key) {
        Jedis jedis = jedisPool.getResource();
        Map<String, String> result = new HashMap<String, String>();
        boolean operationSuccess = true;
        try {
            result = jedis.hgetAll(key);
        } catch (JedisConnectionException e) {
            logger.warn("redis get error,key:{},reason:{}", key, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * 根据key 删除指定fields中的值
     * redis hdel
     * @param key
     * @return
     */
    public long hdel(String key, String... fields) {
        Jedis jedis = jedisPool.getResource();
        long result = 0l;
        boolean operationSuccess = true;
        try {
            result = jedis.hdel(key, fields);
            logger.warn("redis hdel,key:{},fields:{},reason:{}", key,fields);
        } catch (JedisConnectionException e) {
            logger.warn("redis hdel error,key:{},fields:{},reason:{}", key, fields, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * 对指定key对应的value 进行递增操作 +1
     * redis incr
     * @param key
     * @return
     */
    public long incr(String key) {
        Jedis jedis = jedisPool.getResource();
        long result = 0;
        boolean operationSuccess = true;
        try {
            result = jedis.incr(key);
        } catch (JedisConnectionException e) {
            logger.warn("redis incr error,key:{},reason:{}", key, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * 对指定key对应的value 进行递减操作 -1
     * redis decr
     * @param key
     * @return
     */
    public long decr(String key) {
        Jedis jedis = jedisPool.getResource();
        long result = 0l;
        boolean operationSuccess = true;
        try {
            result = jedis.decr(key);
            logger.debug("redis decr,key:{},reason:{}", key);
        } catch (JedisConnectionException e) {
            logger.warn("redis decr error,key:{},reason:{}", key, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }
    
    /**
     * 将member元素加入到集合key当中
     * redis sadd
     * @param key
     * @param members
     * @return - Integer reply, specifically: 1 if the new element was added 0 if the element was already a member of the set
     */
    public long sadd(String key, String... members) {
        Jedis jedis = jedisPool.getResource();
        long result = 0l;
        boolean operationSuccess = true;
        try {
            result = jedis.sadd(key, members);
            logger.debug("redis sadd,key:{},reason:{}", key);
        } catch (JedisConnectionException e) {
            logger.warn("redis sadd error,key:{},reason:{}", key, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }
    
    /**
     * 返回集合key中的所有成员
     * redis smembers
     * @param key
     * @return - Return all the members (elements) of the set value stored at key. This is just syntax glue for SINTER.
     */
    public Set<String> smembers(String key) {
        Jedis jedis = jedisPool.getResource();
        Set<String> result = new HashSet<String>();
        boolean operationSuccess = true;
        try {
        	result = jedis.smembers(key);
            logger.debug("redis smembers,key:{},size:{}", key, result.size());
        } catch (JedisConnectionException e) {
            logger.warn("redis smembers error,key:{},reason:{}", key, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }
    
    /**
     * 判断元素是否是集合key的成员
     * redis smembers
     * @param key
     * @return - Return true if member is a member of the set stored at key, otherwise false is returned.
     */
    public boolean sismember(String key, String member) {
        Jedis jedis = jedisPool.getResource();
        boolean result = false;
        boolean operationSuccess = true;
        try {
        	result = jedis.sismember(key, member);
            logger.debug("redis sIsMember,key:{},result:{}", key, result);
        } catch (JedisConnectionException e) {
            logger.warn("redis sIsMember error,key:{},reason:{}", key, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }
    
    /**
     * 移除集合中的member元素
     * redis srem
     * @param key
     * @return - Integer reply, specifically: 1 if the new element was removed 0 if the new element was not a member of the set
     */
    public long srem(String key, String... members) {
        Jedis jedis = jedisPool.getResource();
        long result = 0l;
        boolean operationSuccess = true;
        try {
        	result = jedis.srem(key, members);
            logger.debug("redis srem,key:{},result:{}", key, result);
        } catch (JedisConnectionException e) {
            logger.warn("redis srem error,key:{},reason:{}", key, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }
    
    /**
     * 有序集合：根据“第二个参数 score”进行排序
     * redis zadd(SortedSet)
     * @param key
     * @param members
     * @return - Integer reply, specifically: 1 if the new element was added 0 if the element was already a member of the sorted set and the score was updated
     */
    public long zadd(String key, double score, String member) {
        Jedis jedis = jedisPool.getResource();
        long result = 0l;
        boolean operationSuccess = true;
        try {
            result = jedis.zadd(key, score, member);
            logger.debug("redis zadd,key:{},reason:{}", key);
        } catch (JedisConnectionException e) {
            logger.warn("redis zadd error,key:{},reason:{}", key, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }
    
    /**
     * key 在min-max之间的元素的个数
     * redis zcount(SortedSet), count how many members which score >= min && score <= max. 
     * @param key
     * @param min - minimum score inclusive
     * @param max - maximum score inclusive
     * @return - members count(score >= min && score <= max)
     */
    public long zcount(String key, double min, double max) {
        Jedis jedis = jedisPool.getResource();
        long result = 0l;
        boolean operationSuccess = true;
        try {
            result = jedis.zcount(key, min, max);
            logger.debug("redis zcount,key:{},reason:{}", key);
        } catch (JedisConnectionException e) {
            logger.warn("redis zcount error,key:{},reason:{}", key, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }
    
    /**
     * 返回有序集 key中，从start到end的所有成员
     * redis zrange(SortedSet), get the members which index is in the range[min, max], the index of the first member is 0.
     * @param key
     * @param start - start index inclusive
     * @param end - end index inclusive
     * @return - members in range[start, end]
     */
    public Set<String> zrange(String key, long start, long end) {
        Jedis jedis = jedisPool.getResource();
        Set<String> result = new HashSet<String>();
        boolean operationSuccess = true;
        try {
        	result = jedis.zrange(key, start, end);
            logger.debug("redis zrange,key:{},reason:{}", key);
        } catch (JedisConnectionException e) {
            logger.warn("redis zrange error,key:{},reason:{}", key, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }
    
    /**
     * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列。
     * 第一个成员的索引为0
     * redis zrangeWithScores(SortedSet), get the members with scores which index is in the range[min, max], the index of the first member is 0.
     * @param key
     * @param start - start index inclusive
     * @param end - end index inclusive
     * @return - members with scores in the range[start, end]
     */
    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        Jedis jedis = jedisPool.getResource();
        Set<Tuple> result = new HashSet<Tuple>();
        boolean operationSuccess = true;
        try {
        	result = jedis.zrangeWithScores(key, start, end);
            logger.debug("redis zrangeWithScores,key:{},reason:{}", key);
        } catch (JedisConnectionException e) {
            logger.warn("redis zrangeWithScores error,key:{},reason:{}", key, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }
    
    /**
     * 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员
     * redis zrangeByScore(SortedSet), get the members which score is in the range[min, max]
     * @param key
     * @param min - minimum score inclusive
     * @param max - maximum score inclusive
     * @return - members which score is in the range[min, max]
     */
    public Set<String> zrangeByScore(String key, double min, double max) {
        Jedis jedis = jedisPool.getResource();
        Set<String> result = new HashSet<String>();
        boolean operationSuccess = true;
        try {
        	result = jedis.zrangeByScore(key, min, max);
            logger.debug("redis zrangeByScore,key:{},reason:{}", key);
        } catch (JedisConnectionException e) {
            logger.warn("redis zrangeByScore error,key:{},reason:{}", key, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }
    
    /**
     * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列。
     * 
     * redis zrangeByScoreWithScores(SortedSet), get the members with scores which score is in the range[min, max]
     * @param key
     * @param min - minimum score inclusive
     * @param max - maximum score inclusive
     * @return - members with scores which score is in the range[min, max]
     */
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        Jedis jedis = jedisPool.getResource();
        Set<Tuple> result = new HashSet<Tuple>();
        boolean operationSuccess = true;
        try {
        	result = jedis.zrangeByScoreWithScores(key, min, max);
            logger.debug("redis zrangeByScoreWithScores,key:{},reason:{}", key);
        } catch (JedisConnectionException e) {
            logger.warn("redis zrangeByScoreWithScores error,key:{},reason:{}", key, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }
    
    /**
     *  移除有序集 key 中的一个或多个成员，不存在的成员将被忽略
     * redis zrem(SortedSet). Remove the specified member from the sorted set value stored at key. 
     * If member was not a member of the set no operation is performed. If key does not not hold a set value an error is returned.
     * @param key
     * @param members
     * @return - Integer reply, specifically: 1 if the new element was removed 0 if the new element was not a member of the set
     */
    public long zrem(String key, String... members) {
        Jedis jedis = jedisPool.getResource();
        long result = 0l;
        boolean operationSuccess = true;
        try {
        	result = jedis.zrem(key, members);
            logger.debug("redis zrem,key:{},reason:{}", key);
        } catch (JedisConnectionException e) {
            logger.warn("redis zrem error,key:{},reason:{}", key, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }
    
    /**
     * 移除有序集 key 中，指定排名(rank)区间内的所有成员。
     * 区间分别以下标参数 start 和 end 指出，包含 start 和 end 在内。
     * 下标参数 start 和 end 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推
     * 
     * redis zremrangeByRank(SortedSet). Remove all elements in the sorted set at key with rank between start and end. Start and end are 0-based 
     * with rank 0 being the element with the lowest score. Both start and end can be negative numbers, where they indicate offsets starting at 
     * the element with the highest rank. For example: -1 is the element with the highest score, -2 the element with the second highest score and so forth.
     * 
     * @param key
     * @param start
     * @param end
     * @return - Integer reply, specifically the number of elements removed.
     */
    public long zremrangeByRank(String key, int start, int end) {
        Jedis jedis = jedisPool.getResource();
        long result = 0l;
        boolean operationSuccess = true;
        try {
        	result = jedis.zremrangeByRank(key, start, end);
            logger.debug("redis zremrangeByRank,key:{},reason:{}", key);
        } catch (JedisConnectionException e) {
            logger.warn("redis zremrangeByRank error,key:{},reason:{}", key, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }
    
    /**
     * 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
     * redis zremrangeByScore(SortedSet). Remove all the elements in the sorted set at key with a score between min and max (including elements with score equal to min or max).
     * @param key
     * @param min
     * @param max
     * @return - Integer reply, specifically the number of elements removed.
     */
    public long zremrangeByScore(String key, double min, double max) {
        Jedis jedis = jedisPool.getResource();
        long result = 0l;
        boolean operationSuccess = true;
        try {
        	result = jedis.zremrangeByScore(key, min, max);
            logger.debug("redis zremrangeByScore,key:{},reason:{}", key);
        } catch (JedisConnectionException e) {
            logger.warn("redis zremrangeByScore error,key:{},reason:{}", key, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }



    /**
     * 对key的模糊查询
     * redis decr
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        Jedis jedis = jedisPool.getResource();
        Set<String> result = null;
        boolean operationSuccess = true;
        try {
            result = jedis.keys(pattern);
        } catch (JedisConnectionException e) {
            logger.warn("redis get keys error,key:{},reason:{}", pattern, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }


    /**
     * 设置key生存时间，当key过期时，它会被自动删除。
     * redis expire
     * @param key
     * @return
     */
    public long expire(String key, int second) {
        Jedis jedis = jedisPool.getResource();
        long result = 0;
        boolean operationSuccess = true;
        try {
            result = jedis.expire(key, second);
            logger.debug("redis expire,key:{}, second:{}", key, second);
        } catch (JedisConnectionException e) {
            logger.warn("redis delete error,key:{},second:{}, reason:{}", key, second, e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
        return result;
    }
    
    /**
     * 异步方式，一次发送多个指令，不同步等待其返回结果。这样可以取得非常好的执行效率。这就是管道
     * redis execute some commands in pipeline, don't do query in the pipeline.
     * @param pipelineCallback
     */
    public void doInPipeline(PipelineCallback pipelineCallback) {
        Jedis jedis = jedisPool.getResource();
        Pipeline pipeline = jedis.pipelined();
        boolean operationSuccess = true;
        try {
        	pipelineCallback.doInPipeline(pipeline);
        	pipeline.sync();
            logger.debug("redis doInPipeline");
        } catch (JedisConnectionException e) {
            logger.warn("redis doInPipeline error,reason:{}", e.getMessage());
            operationSuccess = false;
            if (null != jedis) {
                jedis.close();
            }
        } finally {
            if (operationSuccess) {
                jedis.close();
            }
        }
    }
    /**
     * 程序关闭时，需要调用关闭方法
     */
    public void closePool() {
        this.jedisPool.destroy();
    }

    @Autowired
    @Required
    public void setHost(String host) {
        this.host = host;
    }

    @Autowired
    @Required
    public void setPort(int port) {
        this.port = port;
    }

    @Autowired
    @Required
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Autowired
    @Required
    public void setPassword(String password) {
        this.password = password;
    }
}
