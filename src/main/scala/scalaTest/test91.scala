package scalaTest

object test91 {
  def main(args: Array[String]): Unit = {

    //匹配字面量和常量
    //Actor 之间传递的消息通常都是 String 字面量、数值或者元组。
    def activity(day: String): Unit = {
      day match {
        case "Sunday" => print("Eat, sleep, repeat... ")
        case "Saturday" => print("Hang out with friends... ")
        case "Monday" => print("...code for fun...")
        case "Friday" => print("...read a good book...")
      }
    }
    List("Monday", "Sunday", "Saturday").foreach { activity }

    //匹配通配符
    //定义枚举类
    object DayOfWeek extends Enumeration {
      val SUNDAY: DayOfWeek.Value = Value("Sunday")
      val MONDAY: DayOfWeek.Value = Value("Monday")
      val TUESDAY: DayOfWeek.Value = Value("Tuesday")
      val WEDNESDAY: DayOfWeek.Value = Value("Wednesday")
      val THURSDAY: DayOfWeek.Value = Value("Thursday")
      val FRIDAY: DayOfWeek.Value = Value("Friday")
      val SATURDAY: DayOfWeek.Value = Value("Saturday")
    }
    def activity2(day: DayOfWeek.Value): Unit = {
      day match {
        case DayOfWeek.SUNDAY => println("Eat, sleep, repeat...")
        case DayOfWeek.SATURDAY => println("Hang out with friends")
        case _ => println("...code for fun...")
      }
    }
    activity2(DayOfWeek.SATURDAY)
    activity2(DayOfWeek.MONDAY)

    //匹配元组和列表
    def processCoordinates(input: Any): Unit = {
      input match {
        case (lat, long) => printf("Processing (%d, %d)...", lat, long)
        case "done" => println("done")
        case _ => println("invalid input")
      }
    }
    processCoordinates((39, -104))
    processCoordinates("done")
    //Exception in thread "main" java.util.IllegalFormatConversionException: d != java.lang.String
    //processCoordinates("2","2")
    //invalid input
    processCoordinates((39, -104, 33))

    //非元组，用List<String>来做
    def processItems(items: List[String]): Unit = {
      items match {
        case List("apple", "ibm") => println("Apples and IBMs")
        case List("red", "blue", "white") => println("Stars and Stripes...")
        case List("red", "blue", _*) => println("colors red, blue,... ")
          //对于剩下的元素可以使用数组展开（array explosion）标记（_*）
        case List("apple", "orange", otherFruits @ _*) =>
          println("apples, oranges, and " + otherFruits)
      }
    }
    processItems(List("apple", "ibm"))
    processItems(List("red", "blue", "green"))
    processItems(List("red", "blue", "white"))
    //apples, oranges, and List(grapes, dates)
    //otherFruits @ _*
    processItems(List("apple", "orange", "grapes", "dates"))

    //匹配类型和守卫
    def process(input: Any): Unit = {
      input match {
       case (_: Int, _: Int) => println("Processing (int, int)... ")
         case (_: Double, _: Double) => println("Processing (double, double)... ")
         case msg: Int if msg > 1000000 => println("Processing int > 1000000")
         case _: Int => println("Processing int... ")
         case _: String => println("Processing string... ")
         case _ => println(s"Can't handle $input... ")
         }
       }
     process((34.2, -159.3))
     process(0)
     process(1000001)
     process(2.2)//Can't handle 2.2...
      //在匹配元组时的 lat 和 long。
    //这些就是模式变量
    // Scala 期望模式变量名
    //  都以小写字母开头，而常量名则是大写字母。


    //case 表达式中的模式变量和常量
    class Sample {
      val max = 100
      val MAX = 100

      def process(input: Int): Unit = {
        input match {
          //Scala 将变量 max 推断为模式变量，而不是 Sample 类中 max 字
          //段的不可变变量值
          // case max => println(s"You matched max $max")
          //case this.max => println(s"You matched max1 $max")

          //scala.MatchError: 0 (of class java.lang.Integer)
          //you matched max2 100
          case MAX => println(s"you matched max2 $max")
        }
      }
    }
    val sample = new Sample
    try {
      sample.process(0)
    } catch {
      case ex: Throwable => println(ex)
    }
    sample.process(100)
    //scala.MatchError: 0 (of class java.lang.Integer)
    //You matched max1 100

    //使用 case 类进行模式匹配
    trait Trade
    case class Sell(stockSymbol: String, quantity: Int) extends Trade
    case class Buy(stockSymbol: String, quantity: Int) extends Trade
    case class Hedge(stockSymbol: String, quantity: Int) extends Trade
    object TradeProcessor {
      def processTransaction(request: Trade): Unit = {
        request match {
            //所有具体的 case 类都接受参数
          case Sell(stock, 1000) => println(s"Selling 1000-units of $stock")
          case Sell(stock, quantity) =>
            println(s"Selling $quantity units of $stock")
          case Buy(stock, quantity) if quantity > 2000 =>
            println(s"Buying $quantity (large) units of $stock")
          case Buy(stock, quantity) =>
            println(s"Buying $quantity units of $stock")
        }
      }
    }
    TradeProcessor.processTransaction(Sell("GOOG", 500))//Selling 500 units of GOOG
    TradeProcessor.processTransaction(Sell("GOOG", 1000))//Selling 1000-units of GOOG
    TradeProcessor.processTransaction(Buy("GOOG", 700))//Buying 700 units of GOOG
    TradeProcessor.processTransaction(Buy("GOOG", 3000))//Buying 3000 (large) units of GOOG


    //有不接受任何参数的 case 类
    case class Apple()
    case class Orange()
    case class Book()
    object ThingsAcceptor {
      def acceptStuff(thing: Any): Unit = {
        thing match {
          case Apple() => println("Thanks for the Apple")
          case Orange() => println("Thanks for the Orange")
          case Book() => println("Thanks for the Book")
          case _ => println(s"Excuse me, why did you send me $thing")
        }
      }
    }
    //Thanks for the Apple
    //Thanks for the Book
    //Excuse me, why did you send me Apple
    ThingsAcceptor.acceptStuff(Apple())
    ThingsAcceptor.acceptStuff(Book())
    ThingsAcceptor.acceptStuff(Apple)


//    abstract class Thing
//    case class Apple() extends Thing
//    object ThingsAcceptor {
//      def acceptStuff(thing: Thing) {
//        thing match {
//          //...
//          case _ =>
//        }
//      }
//    }
//    ThingsAcceptor.acceptStuff(Apple)

    //提取器和正则表达式
    //Scala 强大的模式匹配并不止步于内置的模式匹配设施。我们可以使用提取器创建自定义
    //的匹配模式，
    println("-------------------提取器和正则表达式")
    object StockService {
      def process(input: String): Unit = {
        input match {
          case Symbol() => println(s"Look up price for valid symbol $input")
            //String,Option[String,double]
          case ReceiveStockPrice(symbol, price) =>
            println(s"Received price $$$price for symbol $symbol")
          case _ => println(s"Invalid input $input")
        }
      }
    }
    object Symbol {
      // case Symbol() =>被执行的时候，
      // match 表达式将自动将 input 作为参数发送给 unapply()方法。
      //提取器：类似于 evaluate()
      def unapply(symbol: String): Boolean = {
        // 你查找了数据库，但是只识别了 GOOG 和 IBM
        symbol == "GOOG" || symbol == "IBM"
      }
    }
    object ReceiveStockPrice {
      def unapply(input: String): Option[(String, Double)] = {
        try {
          if (input contains ":") {
            val splitQuote = input split ":"
            Some((splitQuote(0), splitQuote(1).toDouble))
          } else {
            None
          }
        } catch {
          case _: NumberFormatException => None
        }
      }
    }

    //Look up price for valid symbol GOOG
    //Look up price for valid symbol IBM
    //Invalid input ERR
    StockService process "GOOG"
    StockService process "IBM"
    StockService process "ERR"

    //Look up price for valid symbol GOOG
    //Received price $310.84 for symbol GOOG
    //Invalid input GOOG:BUY
    //Received price $12.21 for symbol ERR
    StockService process "GOOG"
    StockService process "GOOG:310.84"//unapply(input: String): Option[(String, Double)]
    StockService process "GOOG:BUY"
    StockService process "ERR:12.21"

    //正则表达式
    println("-----------------正则表达式")
    val pattern = "(S|s)cala".r
    val str = "Scala is scalable and cool"
    println(pattern findFirstIn str)//Some(Scala)
    println((pattern findAllIn str).mkString(", "))//Scala, scala
    println("cool".r replaceFirstIn (str, "awesome"))//Scala is scalable and awesome

    //当你创建了一个正则表达式时，将免费得到一个提取器。
    //Scala 的正则表达式是提取器，所以可以马上将其应用到 case 表达式中。Scala 将放置在括
    //号中的每个匹配项看作是一个模式变量
    def process1(input: String): Unit = {
      val GoogStock = """^GOOG:(\d*\.\d+)""".r
      input match {
        case GoogStock(price) => println(s"Price of GOOG is $$$price")
        case _ => println(s"not processing $input")
      }
    }
    //Price of GOOG is $310.84
    //not processing GOOG:310
    //not processing IBM:84.01
    process1("GOOG:310.84")
    process1("GOOG:310")//匹配不上
    process1("IBM:84.01")//匹配不上

    def process2(input: String): Unit = {
      val MatchStock = """^(.+):(\d*\.\d+)""".r
      input match {
        case MatchStock("GOOG", price) => println(s"We got GOOG at $$$price")
        case MatchStock("IBM", price) => println(s"IBM's trading at $$$price")
          //apply(String,double)
        case MatchStock(symbol, price) => println(s"Price of $symbol is $$$price")
        case _ => println(s"not processing $input")
      }
    }
    //We got GOOG at $310.84
    //IBM's trading at $84.01
    //Price of GE is $15.96
    process2("GOOG:310.84")
    process2("IBM:84.01")
    process2("GE:15.96")
    println("-------------- Scala 中正则表达式和模式匹配密不可分")


  }
}
