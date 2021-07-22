package scalaTest

object test71 {
  def main(args: Array[String]): Unit = {
    val john = new Man("John")
    val sara = new Woman("Sara")
    val comet = new Dog("Comet")
    john.listen()
    sara.listen()
    comet.listen()//Comet's listening quietly
    val mansBestFriend: Friend = comet
    mansBestFriend.listen()//Comet's listening quietly
    def helpAsFriend(friend: Friend): Unit = friend.listen()
    helpAsFriend(sara)//Your friend Sara is listening
    helpAsFriend(comet)//Comet's listening quietly

    //使用特质实现装饰器模式
    //在特质中，使用 super
    //来调用方法将会触发延迟绑定（late binding）。
    abstract class Check {
      def check: String = "Checked Application Details..."
    }

    trait CreditCheck extends Check {
      override def check: String = s"Checked Credit... ${super.check}"
    }

    trait EmploymentCheck extends Check {
      override def check: String = s"Checked Employment...${super.check}"
    }

    trait CriminalRecordCheck extends Check {
      override def check: String = s"Check Criminal Records...${super.check}"
    }

    val apartmentApplication =
      new Check with CreditCheck with CriminalRecordCheck
    //Check Criminal Records...Checked Credit... Checked Application Details...
    println(apartmentApplication.check)

    //特质中的方法延迟绑定
    abstract class Writer {
      def writeMessage(message: String): Unit
    }
    //在这段代码中，Scala 在调用 super.writeMessage()方法时做了两件事情。首先，
    //它对该调用执行了延迟绑定。其次，它要求混入了这些特质的类提供一个该方法的具体实现。
    trait UpperCaseWriter extends Writer {
      abstract override def writeMessage(message: String): Unit =
        super.writeMessage(message.toUpperCase)
    }
    trait ProfanityFilteredWriter extends Writer {
      abstract override def writeMessage(message: String): Unit =
        super.writeMessage(message.replace("stupid", "s-----"))
    }

    class StringWriterDelegate extends Writer {
      val writer = new java.io.StringWriter
      def writeMessage(message: String): Unit = writer.write(message)
      override def toString: String = writer.toString
    }

    // ProfanityFilteredWriter 是第一行语句中最右侧的特质，所以它首先生效。
    val myWriterProfanityFirst =
      new StringWriterDelegate with UpperCaseWriter with ProfanityFilteredWriter

    val myWriterProfanityLast =
      new StringWriterDelegate with ProfanityFilteredWriter with UpperCaseWriter

    myWriterProfanityFirst writeMessage "There is no sin except stupidity"
    myWriterProfanityLast writeMessage "There is no sin except stupidity"
    println("myWriterProfanityFirst:" + myWriterProfanityFirst)//THERE IS NO SIN EXCEPT S-----ITY
    println("myWriterProfanityLast:" + myWriterProfanityLast)//THERE IS NO SIN EXCEPT STUPIDITY
  }

  //特质基本属性
  //多继承，多态
  //多重继承通常会带来方法冲突，而特质则不会。
  //对 super 的调用将被解析成
  //对另一个特质或混入了该特质的类方法的调用。
  //特质不仅可以混入类中，也可以混入实例中。
  class Human(val name: String) extends Friend
  class Man(override val name: String) extends Human(name)
  class Woman(override val name: String) extends Human(name)

  trait Friend {
    val name: String
    def listen(): Unit = println(s"Your friend $name is listening")
  }

  class Animal
  //Dog有Friend的属性
  class Dog(val name: String) extends Animal with Friend {
    override def listen(): Unit = println(s"$name's listening quietly")
  }
}
