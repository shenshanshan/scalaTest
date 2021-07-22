package geek

import breeze.linalg.sum
import org.apache.spark.sql.DataFrame

object geekTest {
  def main(args: Array[String]): Unit = {
    println("ss")

    val r1 = new Row("a",3)
    val r2 = new Row("a",3)
    val r3 = new Row("c",3)
    val r4 = new Row("d",3)
    val param = List(r1,r2,r3,r4)

//    extractFields(param).foreach(e =>
//    println(e._1 + "---" + e._2))

    extractFields2(param).foreach(x => println(x))
  }

  class Row(string:String, int:Int){
    def getString(): String = string
    def getInt(): Int = int
  }

  //实现方案1 —— 反例
  //Seq是一个trait,它相当于Java的接口,但相当于即将到来的防御者方法。
  val extractFields: Seq[Row] => Seq[(String, Int)] = {
    (rows: Seq[Row]) => {
      var fields = Seq[(String, Int)]()
      rows.map(row => {
        fields = fields :+ (row.getString(), row.getInt())
      })
      fields
    }
  }

  //实现方案2 —— 正例
  val extractFields2: List[Row] => List[(String, Int)] = {
    (rows: List[Row]) =>
      rows.map(row => (row.getString(), row.getInt())).toList
  }


//      /**
//      (startDate, endDate)
//        e.g. ("2021-01-01", "2021-01-31")
//       */
//      val pairDF: DataFrame = _
//
//      /**
//      (dim1, dim2, dim3, eventDate, value)
//        e.g. ("X", "Y", "Z", "2021-01-15", 12)
//       */
//      val factDF: DataFrame = _
//
//      // Storage root path
//      val rootPath: String = _


    //实现方案1 —— 反例
//    def createInstance(factDF: DataFrame, startDate: String, endDate: String): DataFrame = {
//      val instanceDF = factDF
//        .filter(col("eventDate") > lit(startDate) && col("eventDate") <= lit(endDate))
//        .groupBy("dim1", "dim2", "dim3", "event_date")
//        .agg(sum("value") as "sum_value")
//      instanceDF
//    }
//
//    pairDF.collect.foreach{
//      case (startDate: String, endDate: String) =>
//        val instance = createInstance(factDF, startDate, endDate)
//        val outPath = s"${rootPath}/endDate=${endDate}/startDate=${startDate}"
//        instance.write.parquet(outPath)
//    }
//
//
//  //实现方案2 —— 正例
//  val instances = factDF
//    //Broadcast joins
//    .join(pairDF, factDF("eventDate") > pairDF("startDate") && factDF("eventDate") <= pairDF("endDate"))
//    .groupBy("dim1", "dim2", "dim3", "eventDate", "startDate", "endDate")
//    .agg(sum("value") as "sum_value")
//
//  instances.write.partitionBy("endDate", "startDate").parquet(rootPath)
}
