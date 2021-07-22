package scalaTest

import scala.collection.mutable

object MapTest {
  def main(args: Array[String]): Unit = {
    val map = Map(
      "1" -> "a",
      "2" -> "b"
    )

    class Marker private (var color : String) {
      override def toString = s"marker color $color"
    }

    object Marker {
      private val markers = mutable.Map(
        "red" -> new Marker("red"),
        "blue" -> new Marker("blue"),
        "yellow" -> new Marker("yellow")
      )
      def supportedColors: Iterable[String] = markers.keys
      def apply(color: String): Marker = markers.getOrElseUpdate(color,
        op = new Marker(color))
    }

    println(s"Supported colors are : ${Marker.supportedColors}")
    println(Marker("blue"))
    println(Marker("red"))
    println(Marker("green"))

    object Greeter {
      def greet(): Unit = println("Ahoy, me hearties!")
    }

    println(Greeter.greet())

    //对象
    class Config(num:String){
      override def toString = s"add config map is $num"
    }

    //伴生对象。static。和单例有关
    object Config{
      private val markers = mutable.Map(
        "1" -> "a",
        "2" -> "b"
      )
      def getValue(num : String): Unit ={
        val value = markers.get(num)
        value
      }

      println(Config.getValue("1"))
    }
    //trait特性和实现有关
    //extends和继承有关

    val markers = mutable.Map(
      "1" -> "a",
      "2" -> "b"
    )

    markers.put("3", "c")

    println(markers.keys)//Set(2, 1, 3)
    println(markers.values)//HashMap(b, a, c)
    println(markers.get("3").toString)//Some(c)
    println(markers("1"))

    val sites = Map("runoob" -> "http://www.runoob.com",
      "baidu" -> "http://www.baidu.com",
      "taobao" -> "http://www.taobao.com")

    sites.keys.foreach{ i =>
      print( "Key = " + i )
      println(" Value = " + sites(i) )}
  }

}
