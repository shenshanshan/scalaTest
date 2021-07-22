package geek


import org.apache.spark.{SparkConf, SparkContext}

object ReadFileTest2 {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf
    sparkConf.setMaster("local[1]")
    sparkConf.setAppName("test-for-spark-ui")
    val sc = new SparkContext(sparkConf)

    //知识，哪怕是知识的幻影，也会成为你的铠甲，保护你不被愚昧反噬。
    val counts = sc.textFile("E:\\gitproject\\ScalaTest\\sparkUI.txt")
      .flatMap(_.split(" ")).map((_, 1)).reduceByKey (_ + _)

    counts.cache
    // counts 收集类的算子
    val result  = counts.collect
    for (t2 <- result) {
      println(t2._1 + " : " + t2._2)
    }
    sc.stop()
  }
}
