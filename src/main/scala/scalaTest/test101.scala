package scalaTest

object test101 {
  def main(args: Array[String]): Unit = {
    //Scala 中的异常
    object Tax {
      def taxFor(amount: Double): Double = {
        if (amount < 0)
          throw new IllegalArgumentException("Amount must be greater than zero")
        if (amount < 0.01)
          throw new RuntimeException("Amount too small to be taxed")
        if (amount > 1000000) throw new Exception("Amount too large...")
        amount * 0.08
      }
    }

    //, 1000001.0
    for (amount <- List(100.0, 0.009, -2.0)) {
      try {
        print(s"Amount: $$$amount ")
        println(s"Tax: $$${Tax.taxFor(amount)}")
      } catch {
        case ex: IllegalArgumentException => println(ex.getMessage)
        case ex: RuntimeException =>
          // 如果需要一段代码来处理异常
          println(s"Don't bother reporting...${ex.getMessage}")
          //Amount: $1000001.0 Exception in thread "main" java.lang.Exception: Amount too large...
      }
    }

    val amount = -2
    try {
      print(s"Amount: $$$amount ")
      println(s"Tax: $$${Tax.taxFor(amount)}")
    } catch {
      case _: Exception => println("Something went wrong")
      case ex: IllegalArgumentException => println(ex.getMessage)
    }

  }
}
