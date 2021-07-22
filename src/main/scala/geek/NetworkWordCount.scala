package geek

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext};

object NetworkWordCount {
  def main(args: Array[String]): Unit = {
    // 第一种创建方式
      //创建SparkConf对象,local[2]表示程序在运行的时候会占用2个CPU的内核，也就是在运行的过程中会使用到两个线程
      //如果要把程序打包成jar包在集群上运行，setMaster可以省略
//      val sess = SparkSession.builder().appName("ncwordcount")
//        .master("local[1]") //使用standalone模式需要注释掉,local[*]/yarn
//        .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
//        //        .config("spark.shuffle.service.enabled", false)
//        .getOrCreate()
//      val sc = sess.sparkContext

    // 第二种创建方式
    // SparkSession sparkContext  SparkConf 之间的区别
//      val sc = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
//
//      //1 创建streaming
//      val ssc = new StreamingContext(sc, Seconds(5))
//      ssc.checkpoint("E:\\gitproject\\ScalaTest\\checkpoint")
//
//    //创建输入DStream，这里的端口号跟nc要监控的一致，这样就可以从socket中获取数据
//    // connection refused。需要先启动nc -l 6666,再启动scala文件
//    val lines = ssc.socketTextStream("172.16.54.201", 6666)

    // 例子1 wordCount
    //对socketInputDStream进行flatMap算子操作
//    val wordsDStream = lines.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
//    wordsDStream.cache()
//    wordsDStream.checkpoint(Duration(5000))
//
//    //将结果打印输出到Console
//    wordsDStream.print()

//    //启动Spark Streaming
//    ssc.start()
//    //等待应用程序终止
//    ssc.awaitTermination()

    // 第三种创建方式
    val spark = SparkSession
      .builder
      .appName("StructuredNetworkWordCount")
      .master("local[1]")
      .getOrCreate()
    val lines = spark.readStream
      .format("socket")
      .option("host", "172.16.54.201")
      .option("port", 6666)
      .load()
    lines.isStreaming
    lines.printSchema()
    //+-----+-----+
    //|value|count|
    //+-----+-----+
    //|    c|    1|
    //|    1|    8|
    //|    b|    1|
    //|    a|    1|
    //|     |    1|
    //|    2|    5|
    //+-----+-----+

    import spark.implicits._
    val words = lines.as[String].flatMap(_.split(" "))

    // Generate running word count
    val wordCounts = words.groupBy("value").count()

    val query = wordCounts.writeStream
      .outputMode("complete")
      .format("console")
      .start()

    query.awaitTermination()


      //例子2 滑动窗口
//      val words = lines.flatMap(_.split(" "))
//      val pairs = words.map(word => (word, 1))
////      val wordCounts = pairs.reduceByKey(_ + _)
////      println("---- reduceByKey 计算最近5s的计数-----")
////      wordCounts.print()
//      val windowedWordCounts = pairs.reduceByKeyAndWindow((a:Int,b:Int) => (a + b), Seconds(15), Seconds(5))
//      println("---- reduceByKeyAndWindow 滑动窗口计数每5s滑动一次,计算最近15s的统计-----")
//      windowedWordCounts.print()

//      val sparkConf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
//
//      val ssc = new StreamingContext(sparkConf,Seconds(5))
//
//      val lines = ssc.socketTextStream("172.16.54.201", 6666)
//
//      val result = lines.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)
//      result.print()
//
//      ssc.start()
//      ssc.awaitTermination()
  }
}
