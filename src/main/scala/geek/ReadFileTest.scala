package geek

import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

import scala.util.parsing.json.JSON

object ReadFileTest {
  def main(args: Array[String]): Unit = {
    val sess = SparkSession.builder().appName("readFile_test")
      .master("local[1]")//使用standalone模式需要注释掉,/yarn
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .getOrCreate()

    val sc = sess.sparkContext

//    text(sess)

//    textFile(sc)

      // jsonFile
      jsonFile(sc)
  }

    def textFile(sc : SparkContext): Unit ={
      val textFile = sc.textFile("./people.txt")
      textFile.collect()
      textFile.saveAsTextFile("./word.txt")
    }

    def jsonFile(sc : SparkContext) : Unit = {
      val jsonFile = sc.textFile("./people.json")
      val result = jsonFile.map(s => JSON.parseFull(s))
      result.foreach({r => r match {
        //Map(name -> Michael)
        //Map(name -> Andy, age -> 30.0)
        //Map(name -> Justin, age -> 19.0)
        case Some(map: Map[String, Any]) => println(map)
        case None => println("Parsing failed")
        case other => println("Unknown data structure: " + other)
        }
      })
    }

//  def text(spark: SparkSession): Unit = {
//    import spark.implicits._
//    val textDF: DataFrame = spark.read.text(".\\people.txt")
//    textDF.show()
//    val result: Dataset[(String)] = textDF.map(x => {
//      val splits: Array[String] = x.getString(0).split(",")
//      (splits(0).trim) //, splits(1).trim
//    })
//    result.show()
//    result.write.format("com.databricks.spark.csv").mode("overwrite")
//      .save("file:///E:/java_workspace/readfile.csv")
//  }
}
