package flink;

import flink.redis.RedisExampleMapper;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.redis.RedisSink;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;

/**
 * @author shenss
 * @create 2021-05-24 15:54
 **/
public class FlinkToRedis {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();

        Tuple2<String, Integer> t1 = new Tuple2<>("a", 1);
        Tuple2<String, Integer> t2 = new Tuple2<>("b", 2);
        Tuple2<String, Integer> t3 = new Tuple2<>("c", 3);
        DataStream<Tuple2<String, Integer>> flintstones = env.fromElements(t1,t2,t3);

        FlinkJedisPoolConfig conf = new FlinkJedisPoolConfig.Builder().setHost("172.16.180.215").setPassword("xmrbi").build();
        flintstones.addSink(new RedisSink<Tuple2<String, Integer>>(conf, new RedisExampleMapper()));

        env.execute("flink to redis");
    }
}
