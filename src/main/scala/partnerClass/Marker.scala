package partnerClass

import scala.collection._
//半生对象，private
class Marker private (val color: String) {
  println(s"Creating ${this}")
  override def toString = s"marker color $color"
}

//半生对象
object Marker {
  private val markers = mutable.Map(
    "red" -> new Marker("red"),
    "blue" -> new Marker("blue"),
    "yellow" -> new Marker("yellow"))
  def getMarker(color: String): Marker =
    markers.getOrElseUpdate(color, new Marker(color))
}
