package flink.scala

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

object ScalaFlinkSocketWindowWordCount extends Serializable {

  // 定义一个数据类型保存单词出现的次数
  case class WordWithCount(word: String, count: Long)

  def main(args: Array[String]) : Unit = {
    // 获取执行器的环境
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    //获取数据: 从socket中获取
    // nc -lk 6666
    val textDataStream = env.socketTextStream("172.16.54.201", 6666, '\n')

    // 计数
//    val resultStream = textDataStream.flatMap(
//      new JavaFlinkSocketWordCount.MyFlatMapper).keyBy(0).sum(1)
//
//    resultStream.print()

    //滑动窗口
//    val result = textDataStream.flatMap(_.split(" ")).map(WordWithCount(_,1))
//    val windowDstram = result.keyBy("word").window(
//      SlidingProcessingTimeWindows.of(Time.milliseconds(3),Time.milliseconds(9)))
//      .allowedLateness(Time.milliseconds(5))
//    windowDstram.sum("count").print();
//
//    //启动执行器，执行程序
//    env.execute("Scala Socket Window WordCount")
  }
}
