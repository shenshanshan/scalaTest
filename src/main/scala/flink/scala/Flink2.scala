package flink.scala

import org.apache.flink.api.scala.ExecutionEnvironment

object Flink2 {
  def main(args: Array[String]): Unit = {
    //获取执行器env
    val env = ExecutionEnvironment.getExecutionEnvironment

    //读取文件中的数据
    val text = env.readTextFile("E://a.txt")

    //对数据过滤，加工处理
//    val counts = text.flatMap { _.toLowerCase.split("\\W+"). filter { _.nonEmpty } }
//      .map { (_, 1) }
//      .groupBy(0)
//      .sum(1)

    //action: 触发任务
//    counts.print()
  }
}
