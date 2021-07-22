package geek.util;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @Author: ChenQing
 * @Desc:
 * @Since: 2021/3/5 15:15
 */
public class ReqKafkaProducer {

    public static void main(String[] args) {

        //TODO 生产者三个属性必须指定(broker地址清单、key和value的序列化器)
        Properties properties = new Properties();
        properties.put("bootstrap.servers","172.16.180.151:9092,172.16.180.152:9092,172.16.180.98:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String,String> producer = new KafkaProducer<String, String>(properties);

        try {
            ProducerRecord<String,String> record;
            try {
                //模拟发送广告请求
                for(int i=0 ; i < 10 ; i++){
//                    String jsonTest = "40012115020101012021020413495800100002000101202102041349580010000101202102041349580010";
//                    String jsonTest = "11 22";
//                    String jsonTest = "a:1;b:1";
                    String jsonTest = "a b b b b";

                    record = new ProducerRecord<String,String>("spark_streaming_test_window", jsonTest);
//                    record = new ProducerRecord<String,String>("monitor_raw_4", jsonTest);
                    System.out.println(i+"，message is senting----->");
                    producer.send(record);//发送并发忘记（重试会有）
                    System.out.println(i+"，message is sented----->");
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            producer.close();
        }
    }
}
