package flink.redis;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;

/**
 * @author shenss
 * @create 2021-05-24 16:04
 **/
//指定Redis key并将flink数据类型映射到Redis数据类型
public final class RedisExampleMapper implements RedisMapper<Tuple2<String, Integer>> {
    public RedisCommandDescription getCommandDescription() {
        return new RedisCommandDescription(RedisCommand.HSET, "flinkToRedis");
    }

    public String getKeyFromData(Tuple2<String, Integer> data) {
        return data.f0;
    }

    public String getValueFromData(Tuple2<String, Integer> data) {
        return data.f1.toString();
    }
}
