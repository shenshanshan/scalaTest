package packageObject

object UseCurrency  extends App {
  //Scala 会自动创建像 toString()这样有意义的方法
  Currency.values.foreach { currency => println(currency) }
  //CNY
  //GBP
  //INR
  //JPY
  //NOK
  //PLN
  //SEK
  //USD
//  Scala 和 Java 在处理 static 和单例上差别非常大
}
