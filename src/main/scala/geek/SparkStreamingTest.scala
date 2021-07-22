package geek

import org.apache.commons.codec.StringDecoder
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable

object SparkStreamingTest {
  def main(args: Array[String]): Unit = {
    //1 创建streaming
    val conf = new SparkConf().setMaster("local[1]")
      .setAppName("SparkStreamingTest")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    val ssc = new StreamingContext(conf, Seconds(5))
//    ssc.checkpoint("E:\\gitproject\\ScalaTest\\checkpoint")
    val topic = "spark_streaming_test_window"
    val key="window"

    //2 获取数据流
    val kafkaParam = mutable.Map(
      "bootstrap.servers" -> "172.16.180.151:9092,172.16.180.152:9092,172.16.180.98:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "auto.offset.reset" -> "latest",
      "group.id"-> "ds_raw4_notice",
      "enable.auto.commit" -> (true: java.lang.Boolean)
    )
    val consumerStrategy = ConsumerStrategies.Subscribe[String,String](Array(topic),kafkaParam)
    val kafkaDStream = KafkaUtils.createDirectStream[String,String](
      ssc,
      LocationStrategies.PreferConsistent,
      consumerStrategy)

    kafkaDStream.print()

//    val words = lines.flatMap(_.split(" "))
//    val pairs = words.map(word => (word, 1))
//     3 获取kafka数据流 : DStream[KafkaMessageNew]
//    val messageDStream = kafkaDStream.map(record => {
//      val message: String = record.value()
//      message.split(" ").flatMap(word => (word, 1).reduceByKey(_ + _))
//    })

    ssc.start()
    ssc.awaitTermination()
  }

  //定义一个case class ，用来组装切分后的消息。
  case class MsgBean(upflow:Int,downFlow:Int)
}
