package sparkTest

import org.apache.spark.sql.SparkSession


object sparktest51 {
  def main(args: Array[String]): Unit = {

    //创建实例
    val spark = SparkSession.builder().appName("wordCount")
      .master("local[*]")
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .getOrCreate()

    //一个简单的wordcount
    //(jquery,1)
    //(java,3)
    //(hadoop,1)
    //(js,2)
    //(html,1)
      spark.sparkContext.textFile("E:\\gitproject\\wordCount.txt").flatMap(_.split("~")).map((_, 1)).reduceByKey(_ + _).collect
      .foreach(e => println(e))

    //
    val flightData2015 = spark
      .read
      .option("inferSchema", "true")
      .option("header", "true")
      .csv("E:\\gitproject\\2015-summary.csv")
    flightData2015.createOrReplaceTempView("flight_data_2015")
    flightData2015.take(3).foreach(e => println(e))

    //== Physical Plan ==
    //*(1) Sort [count#18 ASC NULLS FIRST], true, 0
    //+- Exchange rangepartitioning(count#18 ASC NULLS FIRST, 200), ENSURE_REQUIREMENTS, [id=#32]
    //   +- FileScan csv [dis#16,ogrin#17,count#18] Batched: false, DataFilters: [], Format: CSV, Location: InMemoryFileIndex[file:/E:/gitproject/2015-summary.csv], PartitionFilters: [], PushedFilters: [], ReadSchema: struct<dis:string,ogrin:string,count:int>
    flightData2015.sort("count").explain()
    //函数式编程

    //
    val sqlWay = spark.sql("""
          SELECT DEST_COUNTRY_NAME, count(1)
          FROM flight_data_2015
          GROUP BY DEST_COUNTRY_NAME
          """)
    val dataFrameWay = flightData2015.groupBy("DEST_COUNTRY_NAME").count()
    println(dataFrameWay)

    import org.apache.spark.sql.functions.max
//    [Lorg.apache.spark.sql.Row;@64a7ad02
    //[344]
   flightData2015.select(max("count")).take(1).foreach(e => println(e))

    flightData2015
      .groupBy("DEST_COUNTRY_NAME")
      .sum("count")
      .withColumnRenamed("sum(count)", "destination_total")
      .orderBy("destination_total")
//      .sort(desc("destination_total"))
      .limit(5)
      .show()

    case class Flight(DEST_COUNTRY_NAME: String,
                      ORIGIN_COUNTRY_NAME: String,
                      count: BigInt)
    val flightsDF = spark.read
//      .parquet("E:\\gitproject\\2010-summary.parquet/")
//    val flights = flightsDF.as[Flight]


    //8 连接操作
//    val person = Seq(
//      (0, "Bill Chambers", 0, Seq(100)),
//      (1, "Matei Zaharia", 1, Seq(500, 250, 100)),
//      (2, "Michael Armbrust", 1, Seq(250, 100)))
//      .toDF("id", "name", "graduate_program", "spark_status")
//    val graduateProgram = Seq(
//      (0, "Masters", "School of Information", "UC Berkeley"),
//      (2, "Masters", "EECS", "UC Berkeley"),
//      (1, "Ph.D.", "EECS", "UC Berkeley"))
//      .toDF("id", "degree", "department", "school")
//    val sparkStatus = Seq(
//      (500, "Vice President"),
//      (250, "PMC Member"),
//      (100, "Contributor"))
//      .toDF("id", "status")

    //9 数据源


//    case class Flight(DEST_COUNTRY_NAME: String,
//                      ORIGIN_COUNTRY_NAME: String,
//                      count: BigInt)


    //13 高级RDD
    println("----------13 高级RDD")
    // in Scala
    val myCollection = "Spark The Definitive Guide : Big Data Processing Made Simple"
      .split(" ")
    //只要在方法名称中看到 ByKey，就意味着只能以 PairRDD 类型执行
    //此操作
    //RDD 的每个记录中都有两个值
    //List打印
    val words = spark.sparkContext.parallelize(myCollection, 2)
    words.map(word => (word.toLowerCase, 1))
//    words.foreach(word => println("word !!!!!" + word))
    println( " word getClass : " + words.getClass)

    //将单词中的第一个字母作为key，然后Spark将该单词
    //记录保存为RDD 的value
    val keyword = words.keyBy(word => word.toLowerCase.toSeq(0).toString)
//    keyword.foreach(e => println(e._1 + "@@@@@" + e._2))

    // in Scala
    // mapValues
    //Spark The Definitive Guide : Big Data Processing Made Simple
    keyword.mapValues(word => word.toUpperCase).collect()
      .foreach(e => println(e._1 + "---" + e._2))
    //s---SPARK
    //t---THE
    //d---DEFINITIVE
    //g---GUIDE
    //:---:
    //b---BIG
    //d---DATA
    //p---PROCESSING
    //m---MADE
    //s---SIMPLE

    // in Scala
    //flatMapValues
    keyword.flatMapValues(word => word.toUpperCase).collect()
      .foreach(e => println(e._1 + "+++" + e._2))
    //s+++S
    //s+++P
    //s+++A
    //s+++R
    //s+++K
    //t+++T
    //t+++H
    //t+++E


    // in Scala
    //提取key
    keyword.keys.collect().foreach(e => println(e))
    //提取value
    keyword.values.collect().foreach(e => println(e))

    //查找keyword中 k 是 s的，闭关打印出来
    //Spark
    //Simple
    keyword.lookup("s").foreach(e => println(e.toString))


//    val distinctChars = words.flatMap(word => word.toLowerCase.toSeq).distinct.collect()
//    import scala.util.Random
//    val sampleMap = distinctChars.map(c => (c, new Random().nextDouble())).toMap
//    words.map(word => (word.toLowerCase.toSeq(0), word))
//      .sampleByKey(true, sampleMap, 6L)
//      .collect()

    // in Scala
    val chars = words.flatMap(word => word.toLowerCase.toSeq)
    val kvcharacters = chars.map(letter => (letter, 1))
    def maxFunc(left:Int, right:Int) = math.max(left, right)
    def addFunc(left:Int, right:Int) = left + right
    val nums = spark.sparkContext.parallelize(1 to 30, 5)
    val timeout = 1000L //毫秒
    val confidence = 0.95
    kvcharacters.countByKey()
    kvcharacters.countByKeyApprox(timeout, confidence)

    //PairRDDFunctions。每个字母计数
    kvcharacters.reduceByKey(addFunc).collect().foreach(e => println(e._1 + "-----" + e._2))
    kvcharacters.reduceByKey(addFunc).collect().toString

    //第一个函数执行分区内聚合
    nums.aggregate(0)(maxFunc,addFunc)
    val depth = 3
    //第二个函数执行分区间聚合
    nums.treeAggregate(0)(maxFunc, addFunc, depth)

    // in Scala
    kvcharacters.foldByKey(0)(addFunc).collect()

    //Co Group
    // in Scala
    import scala.util.Random
    val distinctChars = words.flatMap(word => word.toLowerCase.toSeq).distinct
    val charRDD = distinctChars.map(c => (c,new Random().nextDouble()))
    val charRDD2 = distinctChars.map(c => (c,new Random().nextDouble()))
    val charRDD3 = distinctChars.map(c => (c,new Random().nextDouble()))
    //CoGroups在Scala中允许将三个key-value RDD一起分组
    charRDD.cogroup(charRDD2,charRDD3).take(5)

    //自定义分区
    // in Scala
    val df = spark.read.option("header", "true").option("inferSchema", "true")
      .csv("/data/retail-data/all/")
    val rdd = df.coalesce(10).rdd

    import org.apache.spark.HashPartitioner
    rdd.map(r => r(6)).take(5).foreach(println)
    val keyedRDD = rdd.keyBy(row => row(6).asInstanceOf[Int].toDouble)
    //RangePartition按范围分区
    //哈希分区
    keyedRDD.partitionBy(new HashPartitioner(10)).take(10)

    //
  }

}
