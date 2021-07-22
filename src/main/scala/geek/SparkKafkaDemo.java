package geek;

/**
 * @author shenss
 * @create 2021-04-07 11:27
 **/
import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.*;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import scala.Tuple2;
import java.util.*;

public class SparkKafkaDemo {
    public static void main(String[] args) throws InterruptedException {
        SparkConf conf = new SparkConf();
        conf.setAppName("SparkKafkaDemo");
        conf.setMaster("local[1]");
        //创建Spark流应用上下文，batch设为1min
        JavaStreamingContext streamingContext = new JavaStreamingContext(conf,new Duration(1*60*1000));
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "172.16.180.151:9092,172.16.180.152:9092,172.16.180.98:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "GROUP_SPARK_SUM");
        kafkaParams.put("auto.offset.reset", "latest");
        Collection<String> topics = Arrays.asList("spark_streaming_test_window");


        final JavaInputDStream<ConsumerRecord<String, String>> stream =
                KafkaUtils.createDirectStream(streamingContext,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams));

        // a b b b
        JavaDStream<String> wordsDS = stream.flatMap(new FlatMapFunction<ConsumerRecord<String,String>, String>() {
            public Iterator<String> call(ConsumerRecord<String, String> r) throws Exception {
                String value = r.value();
                List<String> list = new ArrayList<String>();
                String[] arr = value.split(" ");
                for (String s : arr) {
                    list.add(s);
                }
                return list.iterator();
            }
        });
        //映射成元组，（KEY，近1min VAL）
        JavaPairDStream<String,Integer> pairDS = wordsDS.mapToPair(new PairFunction<String, String, Integer>() {
            public Tuple2<String, Integer> call(String s) throws Exception {
                //KEY：VAL
                return new Tuple2<String,Integer>(s, 1);
            }
        }) ;

        //聚合reduceByKeyAndWindow（KEY，近1minVAL），窗口长度设为1min，滑动间隔设为1min
        JavaPairDStream<String,Integer> countDS = pairDS.reduceByKeyAndWindow(new Function2<Integer, Integer, Integer>() {
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        },new Duration(120000),new Duration(60000));//windowDuration窗口大小, slideDuration滑动频率


//        // a:1;b:1
//        final JavaInputDStream<ConsumerRecord<String, String>> stream =
//                KafkaUtils.createDirectStream(streamingContext,
//                        LocationStrategies.PreferConsistent(),
//                        ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams));
//
//        JavaDStream<String> wordsDS = stream.flatMap(new FlatMapFunction<ConsumerRecord<String,String>, String>() {
//            public Iterator<String> call(ConsumerRecord<String, String> r) throws Exception {
//                String value = r.value();
//                List<String> list = new ArrayList<String>();
//                String[] arr = value.split(";");
//                for (String s : arr) {
//                    list.add(s);
//                }
//                return list.iterator();
//            }
//        });
//        //映射成元组，（KEY，近1min VAL）
//        JavaPairDStream<String,Integer> pairDS = wordsDS.mapToPair(new PairFunction<String, String, Integer>() {
//            public Tuple2<String, Integer> call(String s) throws Exception {
//                //KEY：VAL
//                String[] arr=s.split(":");
//                return new Tuple2<String,Integer>(arr[0],Integer.parseInt(arr[1]));
//            }        }) ;
//        //聚合reduceByKeyAndWindow（KEY，近1minVAL），窗口长度设为1min，滑动间隔设为1min
//        JavaPairDStream<String,Integer> countDS = pairDS.reduceByKeyAndWindow(new Function2<Integer, Integer, Integer>() {
//            public Integer call(Integer v1, Integer v2) throws Exception {
//                return v1 + v2;
//            }
//        },new Duration(120000),new Duration(60000));//windowDuration窗口大小, slideDuration滑动频率
//        // 必须是1min的整数倍
       //打印
        countDS.print();
        streamingContext.start();
        streamingContext.awaitTermination();
        streamingContext.stop();
    }}

