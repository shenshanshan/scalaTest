package packageObject

object Currency  extends Enumeration {
  //这里Currency没啥用，过度作用，但是和Object同名，会导致问题
  type Currency = Value
  val CNY, GBP, INR, JPY, NOK, PLN, SEK, USD = Value
}
