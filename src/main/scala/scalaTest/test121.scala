package scalaTest

object test121 {
  def main(args: Array[String]): Unit = {

    //许多编程语言都支持条件的短路求值②（short-circuit evaluation）。
    //释放惰性
    def expensiveComputation() = {
      println("...assume slow operation...")
      false
    }

    def evaluate(input: Int): Unit = {
      println(s"evaluate called with $input")
//      //&& expensiveComputation()
//      if (input >= 10 && expensiveComputation())
//        println("doing work...")
//      else
//        println("skipping")

      //关键字惰性加载
      lazy val perform = expensiveComputation()
      if (input >= 10 && perform)
        println("doing work...")
    }
    //evaluate called with 0
    //skipping
    //evaluate called with 100
    //...assume slow operation...
    //skipping
    //执行顺序
    evaluate(0)
    //该程序对于 expensiveComputation()方法的求值是相当惰性的。只有大于100的时候执行
    //&&
    evaluate(100)
    //lazy关键字
    //evaluate called with 0
    //evaluate called with 100
    //...assume slow operation...

    import scala.io._
    //控制台输入first second
//    def read = StdIn.readInt()
//    lazy val first = read
//    lazy val second = read
//    if (Math.random() < 0.5)
//      second
//    println(first - second)

    //释放严格集合的惰性
    val people = List(("Mark", 32), ("Bob", 22), ("Jane", 8), ("Jill", 21),
      ("Nick", 50), ("Nancy", 42), ("Mike", 19), ("Sara", 12), ("Paula", 42),
      ("John", 21))

    def isOlderThan17(person: (String, Int)) = {
      println(s"isOlderThan17 called for $person")
      val (_, age) = person
      age > 17
    }

    def isNameStartsWithJ(person: (String, Int)) = {
      println(s"isNameStartsWithJ called for $person")
      val (name, _) = person
      name.startsWith("J")
    }

//    println(people.filter { isOlderThan17 }.filter { isNameStartsWithJ }.head)
    println("people.view.filter---------------")
    //view()方法来获取一个严格集合的惰性视图。严格集合
    //在操作被调用时将会立即求值，而惰性集合则会推迟相应的操作。
    //在这个修改后的版本中，在第一个 filter 操作执行了之后，即将执行第
    //二个 filter 操作。如果第二个 filter 测试通过，则随后将立即对 head()函数进行求值。
    //只有当这两个 filter 操作之一失败了之后，才会检查列表中的下一个元素。
    println("people.view.filter head---------------")
    println(people.view.filter { isOlderThan17 }.filter { isNameStartsWithJ }.head)
    println("people.view.filter last---------------")
    println(people.view.filter { isOlderThan17 }.filter { isNameStartsWithJ }.last)


    //终极惰性流
    def generate(starting: Int): Stream[Int] = {
      //一个特殊的函数#::来将starting变量的值和递归调用generate()函数的值连接起来。
      starting #:: generate(starting + 1)
    }
    println(generate(25))//Stream(25, <not computed>)
    //force会强制流生成值
    println(generate(25).take(10).force)
    //toList强制流生成值.还将结果转换为了一个严格集合—一个列表。
    println(generate(25).take(10).toList)
    //takeWhile
    println(generate(25).takeWhile { _ < 40 }.force)

    //Primes.scala
    def isDivisibleBy(number: Int, divisor: Int) = number % divisor == 0

    def isPrime(number: Int) =
      number > 1 && !(2 until number).exists { isDivisibleBy(number, _) }

    def primes(starting: Int): Stream[Int] = {
      println(s"computing for $starting")
      if (isPrime(starting))
        starting #:: primes(starting + 1)
      else
        primes(starting + 1)
    }
    val primesFrom100 = primes(100)
    println(primesFrom100.take(3).toList)
    println("Let's ask for more...")
    //流是 Scala 标准库中最迷人的特性之一。
    //尾递归可以看作是一个无限序列的问题。一次递归的执行可能会产生另一次递归，
    //它可以被惰性求值，也可以被终止并产生结果值。
    println(primesFrom100.take(4).toList)

    //并行集合
    //从顺序集合入手
//    import scala.io.Source
//    import scala.xml._
//    def getWeatherData(city: String) = {
//      val response = Source.fromURL(
//        s"https://raw.githubusercontent.com/ReactivePlatform/" +
//          s"Pragmatic-Scala-StaticResources/master/src/main/resources/" +
//          s"weathers/$city.xml")
//      val xmlResponse = XML.loadString(response.mkString)
//      val cityName = (xmlResponse \\ "city" \ "@name").text
//      val temperature = (xmlResponse \\ "temperature" \ "@value").text
//      val condition = (xmlResponse \\ "weather" \ "@value").text
//      (cityName, temperature, condition)
//    }
//
//    def printWeatherData(weatherData: (String, String, String)): Unit = {
//      val (cityName, temperature, condition) = weatherData
//      println(f"$cityName%-15s $temperature%-6s $condition")
//    }
//
//    def timeSample(getData: List[String] => List[(String, String, String)]): Unit = {
//      val cities = List("Houston,us", "Chicago,us", "Boston,us", "Minneapolis,us",
//        "Oslo,norway", "Tromso,norway", "Sydney,australia", "Berlin,germany",
//        "London,uk", "Krakow,poland", "Rome,italy", "Stockholm,sweden",
//        "Bangalore,india", "Brussels,belgium", "Reykjavik,iceland")
//      val start = System.nanoTime
//      getData(cities) sortBy { _._1 } foreach printWeatherData
//      val end = System.nanoTime
//      println(s"Time taken: ${(end - start) / 1.0e9} sec")
//    }
//
//    timeSample { cities => cities map getWeatherData }


  }
}
