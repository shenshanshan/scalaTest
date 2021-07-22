package scalaTest

//查看二进制文件
class Car(val year: Int) {
  private var milesDriven: Int = 0

  def miles: Int = milesDriven

  def drive(distance: Int): Unit = {
    milesDriven += Math.abs(distance)
  }
}
