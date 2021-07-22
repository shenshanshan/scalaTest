package scalaTest

import scala.beans.BeanProperty
import scala.collection.mutable

object test11 {
  def main(args: Array[String]): Unit = {
    val symbols = List("AMD", "AAPL", "AMZN", "IBM", "ORCL", "MSFT")
    val year = 2021

//    val(topStack,topPrice) =
//      symbols.map{ticker => (ticker,getYearEndClosingPrice(ticker,year) )}
//      .maxBy{stockPrice => stockPrice._2 }
//
//    println(s"top stock of $year is $topStack closing at price $topPrice")

    //1.2
    val tmp = List(1,2,3,4)
    println("findMax：" + findMax(tmp))


    //1.3
    println("findMax2:" + findMax2(tmp))

    //1.4
    val values = List(1, 2, 3, 4, 5)
    values.map(_ * 2)
          .foreach(s => println(s))

    //1.5
    for (i <- 1 to 3) {
      print(s"$i,")
    }
    println("Scala Rocks!!!")


    //1.6
    (1 to 3).foreach(i => print(s"$i,"))
    println("Scala Rocks!!!")

    //1.8
    val (firstName, lastName, emailAddress) = getPersonInfo(1)
    println(s"First Name: $firstName")
    println(s"Last Name: $lastName")
    println(s"Email Address: $emailAddress")

    //1.9
    println(max(9,1,11))

    //1.10
    val numbers = Array(2, 5, 3, 7, 1, 6)
    println(max(numbers: _*))


    //1.11
    mail("xx","元宵节")
    mail("Houston office", "Priority")
    mail("Boston office")
    mail()

    //1.12
//    connectToNetwork("ss")
    atOffice()
    atJoesHome()
    //在省略参数值时，相应作用域中的隐式变量就会被使用。尽管在不同的
    //函数中调用的是同一个函数，但是所传入的被省去的参数却不是同一个。虽然参数默认值和
    //隐式参数都可以让调用者省去参数，但是编译器绑定到参数的值却完全不同。

      //1.13
    //"""
      val str1 = """In his famous inaugural speech, John F. Kennedy said
                |"And so, my fellow Americans: ask not what your country can do
                |for you-ask what you can do for your country." He then proceeded
                |to speak to the citizens of the World..."""
    println(str1)
    val str2 = """In his famous inaugural speech, John F. Kennedy said
                |"And so, my fellow Americans: ask not what your country can do
                |for you-ask what you can do for your country." He then proceeded
                |to speak to the citizens of the World...""".stripMargin
    println(str2)

    //1.14
    val discount = 10
    val message = s"A discount of $discount% has been applied"
    println(message)

    //1.15
    val product = "ticket"
    val price = 25.12
    // s 插值器只是用值去替换表达式，而不做任何格式处理。
    println(s"On $product $discount% saves $$${price * discount / 100.00}")
    //使用 f 插值器（f-interpolator）。
    println(f"On $product $discount%% saves $$${price * discount/100.00}%2.2f")

    //1.16
    val c1 = new Complex(1, 2)
    val c2 = new Complex(2, -3)
    val sum = c1 + c2
    println(s"($c1) + ($c2) = $sum")


    //1.17
    //; 可有可无，但是List ; 表示匿名函数
    val list1 = new java.util.ArrayList[Int];
    {
      println("Created list1")
    }
    val list2 = new java.util.ArrayList[Int] {
      println("Created list2")
    }
    println(list1.getClass)
    println(list2.getClass)

    //1.18
    class Microwave {
      //默认public
      def start(): Unit = println("started")
//      private def start(): Unit = println("started")
      def stop(): Unit = println("stopped")
      private def turnTable(): Unit = println("turning table")
    }
    val microwave = new Microwave
    microwave.start() // 编译正确

    //1.19
    println("1.19----------------------")
    val car = new Car(2015)
    println(s"Car made in year ${car.year}")
    println(s"Miles driven ${car.miles}")
    println("Drive for 10 miles")
    car.drive(10)
    println(s"Miles driven ${car.miles}")

    //1.21
    println("1.21----------------------")
    println("Let's create an instance")
    new Construct("sample")

    //1.22
    println("1.22----------------------")
    val john = new Person("John", "Smith", "Analyst")
    println(john)
    val bill = new Person("Bill", "Walker")
    println(bill)

    //1.23
//    Dude()

    //1.24
    println("1.24----------------------")
    val car1 = new Car1(1, 2015, 100)
    println(car1)

    //1.25
    echo(1,2)

    //1.26
    println(MarkerFactory getMarker "blue")
    println(MarkerFactory getMarker "blue")
    println(MarkerFactory getMarker "red")
    println(MarkerFactory getMarker "red")
    println(MarkerFactory getMarker "green")
//    Creating marker color red
//    Creating marker color blue
//    Creating marker color yellow
//    marker color blue
//    marker color blue
//    marker color red
//    marker color red
//    Creating marker color green
//    marker color green
  }

  //单例工厂


  //1.26单例对象
//  class Marker private (val color: String) {
//    println(s"Creating ${this}")
//    override def toString = s"marker color $color"
//  }

  class Marker(var color : String) {
    println(s"Creating ${this}")
    override def toString = s"marker color $color"
  }

  object MarkerFactory {
    private val markers = mutable.Map(
      "red" -> new Marker("red"),
      "blue" -> new Marker("blue"),
      "yellow" -> new Marker("yellow"))
    def getMarker(color: String): Marker =
      markers.getOrElseUpdate(color, new Marker(color))
  }

  //1.26
  class Message[T](val content: T) {
    override def toString = s"message content is $content"
    def is(value: T): Boolean = value == content
  }

  //1.25
  def echo[T](input1: T, input2: T): Unit =
    println(s"got $input1 (${input1.getClass}) $input2 (${input2.getClass})")

  //1.24
  class Vehicle(val id: Int, val year: Int) {
    override def toString = s"ID: $id Year: $year"
  }

  class Car1(override val id: Int, override val year: Int, var fuelLevel: Int)
    extends Vehicle(id, year) {
    override def toString = s"${super.toString} Fuel Level: $fuelLevel"
  }

  //1.22
  class Person(val firstName: String, val lastName: String) {
    var position: String = _
    println(s"Creating $toString")

    def this(firstName: String, lastName: String, positionHeld: String) {
      this(firstName, lastName)
      position = positionHeld
    }

    //重载了 toString()方法
    override def toString: String = {
      s"$firstName $lastName holds $position position"
    }
  }

  //1.21
  class Construct(param: String) {
    println(s"Creating an instance of Construct with parameter $param")
  }


  //1.20
  //定义类
  class CreditCard(val number: Int, var creditLimit: Int)

  //  //1.19
//  class Car(val year: Int) {
//    private var milesDriven: Int = 0
//    def miles: Int = milesDriven
//    def drive(distance: Int): Unit = {
//      milesDriven += Math.abs(distance)
//    }
//  }

  //方法入参，是什么类型，也可以静态识别吗？
  //1.1
  def getYearEndClosingPrice(){}

  //1.2
  def findMax(temperatures: List[Int]) = {
    var highTemperature = Integer.MIN_VALUE
    for (temperature <- temperatures) {
      highTemperature = Math.max(highTemperature, temperature)
    }
    highTemperature
  }

  //1.3
  def findMax2(temperatures: List[Int]) = {
    temperatures.foldLeft(Integer.MAX_VALUE) { Math.min }
  }

  //1.7
  class ScalaInt {
    def playWithInt(): Unit = {
      val capacity: Int = 10
      val list = new java.util.ArrayList[String]
      list.ensureCapacity(capacity)
    }
  }

  //1.8
  def getPersonInfo(primaryKey: Int) = {
    // 假定 primaryKey 是用来获取用户信息的主键
    // 这里响应体是固定的
    ("Venkat", "Subramaniam", "venkats@agiledeveloper.com")
  }

  //1.9
  def max(values: Int*) = values.foldLeft(values(0)) { Math.max }

  //1.11
  def mail(destination: String = "head office", mailClass: String = "first"): Unit =
    println(s"sending to $destination by $mailClass class")

  //1.12
  class Wifi(name: String) {
    override def toString: String = name
  }
  def connectToNetwork(user: String)(implicit wifi: Wifi): Unit = {
    println(s"User: $user connected to WIFI $wifi")
  }
  def atOffice(): Unit = {
    println("--- at the office ---")
    implicit def officeNetwork: Wifi = new Wifi("office-network")
    val cafeteriaNetwork = new Wifi("cafe-connect")
    connectToNetwork("guest")(cafeteriaNetwork)
    connectToNetwork("Jill Coder")
    connectToNetwork("Joe Hacker")
  }
  def atJoesHome(): Unit = {
    println("--- at Joe's home ---")
    implicit def homeNetwork: Wifi = new Wifi("home-network")
    connectToNetwork("guest")(homeNetwork)
    connectToNetwork("Joe Hacker")
  }
  atOffice()
  atJoesHome()

  //1.16
  class Complex(val real: Int, val imaginary: Int) {
    def +(operand: Complex): Complex = {
      new Complex(real + operand.real, imaginary + operand.imaginary)
    }
    override def toString: String = {
      val sign = if (imaginary < 0) "" else "+"
      s"$real$sign${imaginary}i"
    }
  }



}
