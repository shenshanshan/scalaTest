package scalaTest

object test131 {
  def main(args: Array[String]): Unit = {
    //一个顺序耗时问题

    //使用程序来查找该目录下的子目录层次结构中的文件数量
    import java.io.File
    def getChildren(file: File) = {
      val children = file.listFiles()
      if (children != null) children.toList else List()
    }

    val start = System.nanoTime
    val exploreFrom = new File(args(0))
    var count = 0L
    var filesToVisit = List(exploreFrom)

    while (filesToVisit.nonEmpty) {
      val head = filesToVisit.head
      filesToVisit = filesToVisit.tail
      val children = getChildren(head)
      count = count + children.count { !_.isDirectory }
      filesToVisit = filesToVisit ::: children.filter { _.isDirectory }
    }

    val end = System.nanoTime
    //Number of files found: 282
    //Time taken: 0.2421638 seconds
    //E:\Z2021fighting program arguments
    println(s"Number of files found: $count")
    println(s"Time taken: ${(end - start) / 1.0e9} seconds")

    //创建Actor
//    import akka.actor._
//    class HollywoodActor() extends Actor {
//      //一个使用 Scala 编写的非常强大的反应式库
//      //方法的主体是一个偏函数，少了match//去掉了 match 关键字的模式匹配语法
//      def receive: Receive = {
//        case message => println(s"playing the role of $message")
//      }
//    }

//    import akka.actor._
//    import scala.actors.Actor
//    import scala.concurrent.Await
//    import scala.concurrent.duration.Duration
//
//    object CreateActors extends App {
//
//      val system = ActorSystem("sample")
//      val depp = system.actorOf(Props[HollywoodActor])
//      depp ! "Wonka"
//      val terminateFuture = system.terminate()
//      Await.ready(terminateFuture, Duration.Inf)
//    }


  }
}
