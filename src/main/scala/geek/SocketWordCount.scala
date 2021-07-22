package geek

import breeze.linalg.rank
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.sql.Column

import org.apache.spark.sql.functions._

object SocketWordCount {
  def main(args: Array[String]): Unit = {
    //第一种情况 rank并列可取2条
//    val spark = SparkSession
//      .builder().master("local[1]")
//      .appName("Spark SQL basic example")
//      .getOrCreate()

//    import spark.implicits._
//    val df = spark.sparkContext.parallelize(Seq(
//      (1,"2019-03-01","河北","ios"),
//      (1,"2019-06-01","北京","Android"),
//      (1,"2019-07-01","北京","Android"),
//      (1,"2019-07-01","北京","Android"),
//      (2,"2019-04-01","河南","Android"),
//      (2,"2019-06-01","山西","ios"),
//      (2,"2019-08-01","湖南","ios")
//    )).toDF("id", "date", "address","device")//转化df的三列数据s
//    df.createOrReplaceTempView("login")
//
//    //先对组内数据，进行排序
//    val s2=spark.sql("select id, date,address,device," +
//                    "rank() over (partition by id order by date desc ) as rank " +
//                    "from login")
//      s2.createOrReplaceTempView("login2")
//      s2.show()
//
//      //取top N，这种会把并列第一的输出
//      val s3=spark.sql("select * from login2 where rank=1")
//      s3.show()

        // 第二种情况
        val spark = SparkSession
          .builder().master("local[1]")
          .appName("Spark SQL basic example")
          .getOrCreate()
        import spark.implicits._
        val df = spark.sparkContext.parallelize(Seq(
                  (1,"2019-03-01","河北","ios"),
                  (1,"2019-06-01","北京","Android"),
                  (1,"2019-07-01","北京","Android"),
                  (1,"2019-07-01","北京","Android"),
                  (2,"2019-04-01","河南","Android"),
                  (2,"2019-06-01","山西","ios"),
                  (2,"2019-08-01","湖南","ios")
                )).toDF("id", "date", "address","device")
        //转化df的三列数据
        df.createOrReplaceTempView("login")

        //DFrame API

        // Spark SQL
        // 第一例
        import spark.implicits._
        spark.sparkContext.parallelize(List(
          (1, "zs", 18, 1500, "A", true),
          (2, "ls", 20, 3000, "B", false),
          (3, "ww", 20, 2000, "B", false),
          (4, "zl", 30, 3500, "A", true),
          (3, "tq", 40, 2500, "A", false)
        ))
        //字段起别名
        .toDF("id", "name", "age", "salary", "dept_id", "sex")
        .createOrReplaceTempView("t_user")
        //窗口函数语法
        spark
          .sql("select id,name,age,salary,dept_id,sex, " +
            "max(salary) over(partition by dept_id) as dept_max_salary " +
            "from t_user")
          // 查询员工信息和部门员工的最高工资
          .show()

          // 第二例
          import spark.implicits._
          spark
            .sparkContext
            .parallelize(
              List(
                ("2018-01-01", 1, "www.baidu.com", "10:01"),
                ("2018-01-01", 2, "www.baidu.com", "10:01"),
                ("2018-01-01", 1, "www.sina.com", "10:01"),
                ("2018-01-01", 3, "www.baidu.com", "10:01"),
                ("2018-01-01", 3, "www.baidu.com", "10:01"),
                ("2018-01-01", 1, "www.sina.com", "10:01")
              )
            )
            .toDF("day", "user_id", "page_id", "time")
            .createOrReplaceTempView("t_page")

          //窗口函数语法
          spark
            .sql("select * from " +
              "(select user_id,page_id,num,rank() over(partition by user_id order by num desc) as rank " +
              "from " +
              "(select user_id,page_id,count(page_id) as num from t_page group by user_id,page_id)) " +
              "where rank < 10")
            .show()

          //4.释放资源
          spark.stop()

    // 3例子 ： 连续区间
    // max(value) over(order by value asc rows between 0 preceding and 1 following) as next_time
    // 窗口函数语法：
    // window_func(args) OVER ( [PARTITION BY col_name, col_name, ...]
    //  [ORDER BY col_name, col_name, ...]
    //  [ROWS | RANGE BETWEEN (CURRENT ROW | (UNBOUNDED |[num]) PRECEDING) AND (CURRENT ROW | ( UNBOUNDED | [num]) FOLLOWING)] )

//        // to_date 转化为时间格式
//        val dfWithDate = df.withColumn("date", to_date(col("date"),
//        "yyyy-MM-dd"))
//        dfWithDate.createOrReplaceTempView("dfWithDate")
////        dfWithDate.show()
//
//      val windowSpec = Window
//        .partitionBy("id", "date")
//        .orderBy(col("date").desc)
//        .rowsBetween(Window.unboundedPreceding, Window.currentRow)
//
//      dfWithDate.select(dfWithDate("id"),
//        dfWithDate("date"),
//        dfWithDate("address"),
////        ntile(2).over(windowSpec).as("ntile_2"),
////        ntile(3).over(windowSpec).as("ntile_3")
////      ,
////      // rank [Unit, VR]
////      // rank().over(windowSpec).as("date"),
////      dense_rank().over(windowSpec).as("date"),
////      percent_rank().over(windowSpec).as("id")
//      ).orderBy("address").show

//      val maxDate = max(col("date")).over(windowSpec)
//       dense_rank.over(windowSpec)
  }
}
