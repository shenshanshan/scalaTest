package scalaTest

object test111 {
  def main(args: Array[String]): Unit = {
    //递归在许多算法中广泛使用，如快速排序、动态规划、基于栈的操作等。递归极富表现
    //力而且直观。

    //如果参数值为 0，则返回值为 1；否则，它返回参数
    //值与参数值减 1 的阶乘的乘积。
    def factorial(number: Int): BigInt = {
      if (number == 0)
         1
      else
        number * factorial(number - 1)
      }
    println(factorial(2))
//    println(factorial(10000))//Exception in thread "main" java.lang.StackOverflowError

    //尾调用优化（TCO）
    //并不是所有的递归都能够转化为迭代。只有具有特殊结构的递归—尾递归，才能享
    //受这种特权。
    def mad(parameter: Int): Int = {
      if (parameter == 0)
        throw new RuntimeException("Error")
      else
        1 * mad(parameter - 1)
    }
//    mad(5)


    def mad2(parameter: Int): Int = {
      if (parameter == 0)
        throw new RuntimeException("Error")
      else
        mad2(parameter - 1)
    }
//    mad2(5)


    //确保尾调用优化
    //因为这个版本的 factorial()函数是常规递归，而不是尾递归，因此编译器会报一个
    //恰当的错误：
//    @scala.annotation.tailrec
//    def factorial2(number: Int): BigInt = {
//      if (number == 0)
//        1
//      else
//        number * factorial2(number - 1)
//    }
//    println(factorial2(10000))


    //修改后的 factorial()函数接收两个参数，其中第一个参数 fact 是已经计算出来的
    //部分结果。
    //递归
    //只要是尾递归，Scala 都会做尾调用优化。注解是可选的，使用之后明晰了优化的意图。
    //使用注解是一个好方法。它能够在代码重构中保证函数尾递归的性质，并让以后重构这段代
    //码的程序员注意到这个细节。
    @scala.annotation.tailrec
    def factorial2(fact: BigInt, number: Int): BigInt = {
      if (number == 0)
        fact
      else
        factorial2(fact * number, number - 1)
    }
//    println(factorial2(1, 10000))


    //蹦床调用
    //尾调用优化只针对自己调自己，如果递归中调用其他的~？

    //尽管 Scala 编译器并不支持蹦床调用的优化，但是我们可以用 TailRec 类来避免栈溢出
    //的问题
    import scala.io.Source._

    def explore(count: Int, words: List[String]): Int =
      if (words.isEmpty)
        count
      else
        countPalindrome(count, words)

    //回文串
    def countPalindrome(count: Int, words: List[String]): Int = {
      val firstWord = words.head
      if (firstWord.reverse == firstWord)
        explore(count + 1, words.tail)
      else
        explore(count, words.tail)
    }

    def callExplore(text: String): Unit = println(explore(0, text.split(" ").toList))

//    callExplore("dad mom and racecar")//3

    try {
      //最终会：java.lang.StackOverflowError
      val text =
        fromURL("https://en.wikipedia.org/wiki/Gettysburg_Address").mkString
//      callExplore(text)
    } catch {
      case ex: Throwable => println(ex)
    }

    //改进办法：TailRec
    //如果要继续递归，那么使用 tailcall()函数。要终止递归，就用 done()函数。done()
    //又会创建 Done 的实例。
    //像这种函数间相互调用产生的递归，我们可以用 TailRec 类和 scala.util.control.
    //TailCalls 包中的可用函数解决。
    import scala.io.Source._
    import scala.util.control.TailCalls._

    def explore1(count: Int, words: List[String]): TailRec[Int] =
      if (words.isEmpty)
        //要终止递归，就用 done()函数。
        done(count)
      else
        tailcall(countPalindrome1(count, words))

    def countPalindrome1(count: Int, words: List[String]): TailRec[Int] = {
      val firstWord = words.head
      if (firstWord.reverse == firstWord)
        //tailcall。调用tailcall()方法，则继续递归。
        tailcall(explore1(count + 1, words.tail))
      else
        tailcall(explore1(count, words.tail))
    }

    def callExplore1(text: String): Unit =
      println(explore1(0, text.split(" ").toList).result)

//    callExplore1("dad mom and racecar")
//    try {
//      val text =
//        fromURL("https://en.wikipedia.org/wiki/Gettysburg_Address").mkString
//      callExplore1(text)
//    } catch {
//      case ex: Throwable => println(ex)
//    }

  }
}
