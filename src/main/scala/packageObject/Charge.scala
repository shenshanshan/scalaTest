
import packageObject._

object Charge {
  def chargeInUSD(money: Money): String = {
    def moneyInUSD = Converter.convert(money, Currency.USD)
    s"charged $$${moneyInUSD.amount}"
  }
}
