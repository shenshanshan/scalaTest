package mask

object Mask {

  import MyInterpolator._

  def main(args: Array[String]): Unit = {
    val ssn = "123-45-6789"
    val account = "0246781263"
    val balance = 20145.23
    println(mask"""Account: $account
                  |Social Security Number: $ssn
                  |Balance: $$^$balance
                  |Thanks for your business.""".toString().stripMargin)
    //Account: ...1263
    //Social Security Number: ...6789
    //Balance: $20145.23
    //Thanks for your business
  }

}
