package flink.redis;

import org.apache.flink.streaming.connectors.redis.common.mapper.RedisDataType;
import org.apache.flink.util.Preconditions;

import java.io.Serializable;

/**
 * @author shenss
 * @create 2021-05-24 19:17
 **/
public class MyRedisCommandDescription  implements Serializable {
    private static final long serialVersionUID = 1L;
    private MyRedisCommand redisCommand;
    private String additionalKey;
    private String additionalField;


    public MyRedisCommandDescription(MyRedisCommand redisCommand, String additionalKey) {
        Preconditions.checkNotNull(redisCommand, "Redis command type can not be null");
        this.redisCommand = redisCommand;
        this.additionalKey = additionalKey;
        if ((redisCommand.getRedisDataType() == RedisDataType.HASH || redisCommand.getRedisDataType() == RedisDataType.SORTED_SET) && additionalKey == null) {
            throw new IllegalArgumentException("Hash and Sorted Set should have additional key");
        }
    }

    public MyRedisCommandDescription(MyRedisCommand redisCommand, String additionalKey, String additionalField) {
        Preconditions.checkNotNull(redisCommand, "Redis command type can not be null");
        this.redisCommand = redisCommand;
        this.additionalKey = additionalKey;
        this.additionalField = additionalField;
        if ((redisCommand.getRedisDataType() == RedisDataType.HASH ||
                redisCommand.getRedisDataType() == RedisDataType.SORTED_SET) &&
                additionalKey == null &&
                additionalField == null) {
            throw new IllegalArgumentException("Hash and Sorted Set should have additional key field");
        }
    }

    public MyRedisCommandDescription(MyRedisCommand redisCommand) {
        this(redisCommand, (String)null);
    }

    public MyRedisCommand getCommand() {
        return this.redisCommand;
    }

    public String getAdditionalKey() {
        return this.additionalKey;
    }
}
