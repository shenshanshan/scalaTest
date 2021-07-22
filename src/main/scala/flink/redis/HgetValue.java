package flink.redis;

import breeze.linalg.max;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;

/**
 * @author shenss
 * @create 2021-05-24 19:18
 **/
public class HgetValue {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();

        FlinkJedisPoolConfig conf =
                new FlinkJedisPoolConfig.Builder().setHost("172.16.180.215").setPort(6379)
                        .setPassword("xmrbi").build();

//        DataStreamSource<MyRedisRecord> source = executionEnvironment.addSource(
//                new RedisSource(conf,new MyRedisCommandDescription(
//                        MyRedisCommand.HGET,"flinkToRedis")));
        DataStreamSource<MyRedisRecord> source = executionEnvironment.addSource(
                new RedisSource(conf,new MyRedisCommandDescription(
                        MyRedisCommand.HGETVAL,"flinkToRedis" ,"a")));

        source.print();
        System.out.println(source.getId());
        //8> flink.redis.MyRedisRecord@18050d7
//        source.print();
//        DataStream<Tuple2<String, Integer>> val =
//                source.flatMap(new MyMapRedisRecordSplitter())
//                .windowAll(SlidingProcessingTimeWindows.of(Time.seconds(10),Time.seconds(5)))
//                .maxBy(1);
//        val.print().setParallelism(1);
//        val.print();

        executionEnvironment.execute();
    }
}
