package geek

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object MemoryTest {
  def main(args: Array[String]): Unit = {
    val sess = SparkSession.builder().appName("Memory_test")
      .master("local[*]")//使用standalone模式需要注释掉,/yarn
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .getOrCreate()
    val sc = sess.sparkContext

    // 定义了 dict 字典，这个字典在 Driver 端生成，
    // 它在后续的 RDD 调用中会随着任务一起分发到 Executor 端
    val dict: List[String] = List("spark", "scala")
    // 读取 words.csv 文件并生成 RDD words
    val words: RDD[String] = sc.textFile("./words.txt")
    // 用 dict 字典对 words 进行过滤，
    // 此时 dict 已分发到 Executor 端，Executor 将其存储在堆内存中，
    // 用于对 words 数据分片中的字符串进行过滤
    // Dict 字典属于开发者自定义数据结构，因此，Executor 将其存储在 User Memory 区域
    val keywords: RDD[String] = words.filter(word => dict.contains(word))
    // 用 cache 和 count 对 keywords RDD 进行缓存，
    // 以备后续频繁访问，分布式数据集的缓存占用的正是 Storage Memory 内存区域
    keywords.cache
    keywords.count
    //reduceByKey返回的是RDD[(K, V)]
    // 在 keywords 上调用 reduceByKey 对单词分别计数
    keywords.map((_, 1)).reduceByKey(_ + _).collect().foreach(e => println("-----------" + e.toString()))

//    sc.textFile("./words.txt").flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).collect

    val textFile = sc.textFile("./words.txt")
    val counts = textFile.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)
//    counts.saveAsTextFile("./1.txt")
    counts.foreach(e => println(e._1 + " " + e._2 ))
  }
}
