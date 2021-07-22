package scalaTest
import java._


object test51 {
  def main(args: Array[String]): Unit = {
    val greet: String = "Shirly!"
    println(greet)

    val greet1 = "Ahoy!"
    println(greet1)
    println(greet1.getClass)


    //Nothing,Any,Option
    //如果不能编译，会直接展示
    var list1 = new util.ArrayList[Int]
    var list2 = new util.ArrayList
//    list2 = list1 // 编译错误


    var list3 = new util.ArrayList[Int]
    var list4 = new util.ArrayList[Any]
//    var ref1: Int = 1
//    var ref2: Any = _
//    ref2 = ref1 // 编译正确
//    list4 = list2 // 编译错误

    println(someOp(2))

    println(commentOnPractice("test1"))

    //for循环，遍历set,getOrElse设置默认值
   for (input <- Set("test", "hack")) {
      val comment = commentOnPractice(input)
      val commentDisplay = comment.getOrElse("Found no comments")
      println(s"input: $input comment: $commentDisplay")
    }

    println(compute(4))//Right(2.0)
    println(compute(-4))//Left(Error computing, invalid input)


    displayResult(compute(4))
    displayResult(compute(-4))
    //调用方式：
    //displayResult("4"),displayResult(4.24)
    //都不对

    //协变
    val dogs = Array(new Dog("Rover"), new Dog("Comet"))
    workWithPets(dogs)

    //逆变
    val pets = new Array[Pet](10)
    copyPets(dogs, pets)

    //通过使用参数化类型-T 而不是 T，我们可以要求 Scala 为自己的类型提供逆变支持。
    class MyList[+T] //...
    var l1 = new MyList[Int]
//    var l2: MyList[Any] = _
//    l2 = l1 // 编译正确


    //隐士函数
    implicit def convertInt2DateHelper(offset: Int): DateHelper = new DateHelper(offset)
    val ago = "ago"
    val from_now = "from_now"
    val today_now = "_"
    val past = 2 days ago // days是方法名，ago是参数
    val appointment = 5 days from_now
    var today = 0 days today_now
    println(past)
    println(appointment)
    println(today)
  }

  //Nothing
  def someOp(number: Int) =
    if (number < 10)
      number * 2
    else
      throw new RuntimeException("invalid argument")

    //Option 类型
    def commentOnPractice(input: String) = {
      // 而不是返回 null,None是Object
      if (input == "test") Some("good") else None
    }

    //either 范围值为其中一个
    def compute(input: Int) =
      if (input > 0)
        Right(Math.sqrt(input))
      else
        Left("Error computing, invalid input")

    //Scala中有Left,Right两个类，继承于Either,主要用途是表示两个可能不同的类型（它们之间没有交集）
    // **Left主要是表示Failure,Right表示有,跟Some类型有点类似**
    def displayResult(result: Either[String, Double]): Unit = {
      println(s"Raw: $result")
      result match {
        case Right(value) => println(s"result $value")
        case Left(err) => println(s"Error: $err")
      }
    }


    //协变
    class Pet(val name: String) {
      override def toString: String = name
    }

    class Dog(override val name: String) extends Pet(name)

    def workWithPets[D <: Pet](pets: Array[D]): Unit = {
      println("Playing with pets: " + pets.mkString(", "))
    }
    //逆变
    def copyPets[S, D >: S](fromPets: Array[S], toPets: Array[D]): Unit = {
    }

    //隐式类
    object DateUtil {
      val ago = "ago"
      val from_now = "from_now"

      //隐式类
      implicit class DateHelper(val offset: Int) {
        import java.time.LocalDate
        def days(when: String): LocalDate = {
          val today = LocalDate.now
          when match {
            case "ago" => today.minusDays(offset)
            case "from_now" => today.plusDays(offset)
            case _ => today
          }
        }
      }
    }

    object UseDateUtil extends App {
      import DateUtil._

      val past = 2 days ago
      val appointment = 5 days from_now

      println(past)
      println(appointment)
    }
}
