package sparkTest

import org.apache.spark.HashPartitioner
import org.apache.spark.sql.SparkSession

object DomainPartionTest {
  def main(args: Array[String]): Unit = {
    //创建实例
    val spark = SparkSession.builder().appName("wordCount")
      .master("local[*]")
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .getOrCreate()

    val df = spark.read.option("header", "true").option("inferSchema", "true")
      .csv("E://test")
    val rdd = df.coalesce(10).rdd

    val keyedRDD = rdd.keyBy(row => row(6).asInstanceOf[Int].toDouble)
    //哈希分区
    keyedRDD.partitionBy(new HashPartitioner(10)).take(10)

    //自定义分区
    import org.apache.spark.Partitioner
    class DomainPartitioner extends Partitioner {
      def numPartitions = 3
      def getPartition(key: Any): Int = {
        val customerId = key.asInstanceOf[Double].toInt
        if (customerId == 17850.0 || customerId == 12583.0) {
          return 0
        } else {
          return new java.util.Random().nextInt(2) + 1
        }
      }
    }
    keyedRDD
      .partitionBy(new DomainPartitioner).map(_._1).glom().map(_.toSet.toSeq.length)
      .take(5)
  }
}
