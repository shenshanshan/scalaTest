package scalaTest

import java.util.Date

object test61 {
  def main(args: Array[String]): Unit = {

    //1 匿名的即时函数-codeBlock（just-in-time function），即一个没有名称只有参数和实现的函数。
    def totalResultOverRange(number: Int, codeBlock: Int => Int) = {
      var result = 0
      for (i <- 1 to number) {
        result += codeBlock(i)
      }
      result
    }

    println(totalResultOverRange(11,i => i))
    println(totalResultOverRange(11, i => if (i % 2 == 0) i else 0))


    //2 没有参数的函数值
    def printValue(generator: () => Int): Unit = {
      println(s"Generated value is ${generator()}")
      }
      printValue(() => 42)
    }

    //3 具有多个参数的函数值
//    def inject(arr: Array[Int], initial: Int, operation: (Int, Int) => Int) = {
//      var carryOver = initial
//      arr.foreach(element => carryOver = operation(carryOver, element))
//      carryOver
//    }

//    val array = Array(2, 3, 5, 1, 6, 4)
//    val sum = inject(array, 0, (carry, elem) => carry + elem)
//    println(s"Sum of elements in array is $sum")
//
//    val max = inject(array, Integer.MIN_VALUE, (carry, elem) => Math.max(carry, elem))
//    println(s"Max of elements in array is $max")

    val array = Array(2, 3, 5, 1, 6, 4)
    //0 + 2 + 3 + 5 + 1 + 6 + 4
    val sum = array.foldLeft(0) { (sum, elem) => sum + elem }
    val max = array.foldLeft(Integer.MIN_VALUE) { (large, elem) =>
      Math.max(large, elem)
    }
    println(s"Sum of elements in array is $sum")
    println(s"Max of elements in array is $max")

    val sum1 = (0 /: array) ((sum, elem) => sum + elem)
    val max1 = (Integer.MIN_VALUE /: array) { (large, elem) => Math.max(large, elem) }

    //4 柯里化（currying）
    //Scala 中的柯里化（currying）会把接收多个参数的函数转化为接收多个参数列表的函数。
    //如果你会用同样的一组参数多次调用一个函数，你就能用柯里化去除噪声并使代码更加有趣。
    //val sum = inject(array, 0) { (carryOver, elem) => carryOver + elem }
    def inject(arr: Array[Int], initial: Int)(operation: (Int, Int) => Int): Int = {
      var carryOver = initial
      arr.foreach(element => carryOver = operation(carryOver, element))
      carryOver
    }
    //使用柯里化将函数值从括号中移了出来。非常美观
    val sum2: Int = inject(array, 0) { (carryOver, elem) => carryOver + elem }


    // 5 参数的占位符
    val arr = Array(1, 2, 3, 4, 5)
    //方法 /: 用于计算变量 arr 表示的数组中元素的和
    val total = (0 /: arr) { (sum, elem) => sum + elem }
    println("total:----------- " + total)

    val total2= (0 /: arr) { _ + _ }

    val negativeNumberExists1 = arr.exists { elem => elem < 0 }
    val negativeNumberExists2 = arr.exists { _ < 0 }

    // 6 参数路由
    val largest =
      (Integer.MIN_VALUE /: arr) { (carry, elem) => Math.max(carry, elem) }
    val largest1 = (Integer.MIN_VALUE /: arr) { Math.max(_, _) }
    val largest2 = (Integer.MIN_VALUE /: arr) { Math.max _ }
    val largest3 = (Integer.MIN_VALUE /: arr) { Math.max }


    //复用函数
    class Equipment(val routine: Int => Int) {
      def simulate(input: Int): Int = {
        print("Running simulation...")
        routine(input)
      }
    }

//    object EquipmentUseNotDry extends App {
      val equipment1 = new Equipment(
        { input => println(s"calc with $input"); input })
      val equipment2 = new Equipment(
        { input => println(s"calc with $input"); input })
      equipment1.simulate(4)//Running simulation...calc with 4
      equipment2.simulate(6)//Running simulation...calc with 6
//    }

      val calculator = { input: Int => println(s"calc with $input"); input }
      val equipment3 = new Equipment(calculator)
      val equipment4 = new Equipment(calculator)
      equipment3.simulate(4)
      equipment4.simulate(6)

      //7 部分应用函数
      def log(date: Date, message: String): Unit = {
        println(s"$date ---- $message")
      }
      val date = new Date(1420095600000L)
      log(date, "message1")
      log(date, "message2")
      log(date, "message3")

      val date1 = new Date(1420095600000L)
      //新增函数，减少重复代码
      val logWithDateBound = log(date1, _: String)
      logWithDateBound("message1")
      logWithDateBound("message2")
      logWithDateBound("message3")

      //8 闭包
      def loopThrough(number: Int)(closure: Int => Unit): Unit = {
        for (i <- 1 to number) { closure(i) }
      }
      var result = 0
      val addIt = { value: Int => result += value }

      loopThrough(10) { elem => addIt(elem) }
      println(s"Total of values from 1 to 10 is $result")
      result = 0
      loopThrough(5) { addIt }
      println(s"Total of values from 1 to 5 is $result")

      //9 Execute Around Method 模式
      // Resource 类的构造器标记为 private。因此，我们不能在这个类和它的伴生
      //对象之外创建它的实例。这种设计可以强制我们以某种方式使用对象，以保证自动的、确定性的行为。
      class Resource private () {
        //初始化就会执行
        println("Starting transaction...")
        //private
        private def cleanUp(): Unit = { println("Ending transaction...") }
        def op1(): Unit = println("Operation 1")
        def op2(): Unit = println("Operation 2")
        def op3(): Unit = println("Operation 3")
      }
      //伴生对象
      object Resource {
        def use(codeBlock: Resource => Unit): Unit = {
          val resource = new Resource
          try {
            codeBlock(resource)
          } finally {
            //cleanup最终会执行
            resource.cleanUp()
          }
        }
      }

      //传递了一个代码块作为参数
      Resource.use { resource =>
          resource.op1()
          resource.op2()
          resource.op3()
          resource.op1()
    }

    import java.io._
    def writeToFile(fileName: String)(codeBlock: PrintWriter => Unit): Unit = {
      val writer = new PrintWriter(new File(fileName))
      try {
        codeBlock(writer)
      } finally {
        writer.close()
      }
    }
    writeToFile("output/output.txt") { writer =>
      writer write "hello from Scala"
    }


}
