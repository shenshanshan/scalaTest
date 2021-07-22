package flink.redis;

import org.apache.flink.streaming.connectors.redis.common.mapper.RedisDataType;

/**
 * @author shenss
 * @create 2021-05-24 19:17
 **/
public  enum MyRedisCommand {
    HGET(RedisDataType.HASH),

    HGETVAL(RedisDataType.HASH);

    private RedisDataType redisDataType;

    private MyRedisCommand(RedisDataType redisDataType) {
        this.redisDataType = redisDataType;
    }

    public RedisDataType getRedisDataType() {
        return this.redisDataType;
    }
}
