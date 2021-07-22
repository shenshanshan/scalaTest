package scalaTest

import scala.beans.BeanProperty

//1.23
class Dude(@BeanProperty val firstName: String, val lastName: String) {
  @BeanProperty var position: String = _
}
