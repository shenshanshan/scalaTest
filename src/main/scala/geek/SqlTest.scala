package geek

import org.apache.spark.sql.{SaveMode, SparkSession}

object SqlTest {
  def main(args: Array[String]): Unit = {
    val sess = SparkSession.builder().appName("SQL_test")
      .master("local[*]")//使用standalone模式需要注释掉,/yarn
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
//      .config("spark.sql.warehouse.dir", "E:\\gitproject\\ScalaTest\\spark-warehouse\\mysql-connector-java-5.1.39.jar")
      .getOrCreate()
    val sc = sess.sqlContext

    // &useUnicode=true&characterEncoding=utf-8&useSSL=false
    val url =
    "jdbc:mysql://172.16.181.35:3306/xm_bus_lifecycle?user=xmbuslife&password=buslife^2020&useSSL=false"
//    val url =
//    "jdbc:mysql://172.16.180.235:3306/kafka_manager?user=kafka;password=kafka"
    val df = sc
      .read
      .format("jdbc")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("url", url)
      .option("dbtable", "workshop_info")
      .load()

    // DataFrame是最常见的结构化API，简单来说它是包含行和列的数据表。说明这些列和
    //列类型的一些规则被称为模式（schema）
//    df.printSchema()
//    df.show()

    // Counts people by age
    val countsByAge = df.groupBy("company_id").count()
    countsByAge.show()
//    countsByAge.printSchema()

    countsByAge.write.format("json").save("./sql.txt")
    // {"company_id":"0000000001","count":2}
    // {"company_id":"0000000002","count":1}
  }
}
