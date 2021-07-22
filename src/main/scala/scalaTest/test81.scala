package scalaTest

object test81 {

  def feeds1 = ???

  def main(args: Array[String]): Unit = {
    //不可变集合
    val colors1 = Set("Blue", "Green", "Red")
    println(s"colors1: $colors1")//colors1: Set(Blue, Green, Red)

    val colors2 = colors1 + "Black"
    println(s"colors2: $colors2")//colors2: Set(Blue, Green, Red, Black)
    println(s"colors1: $colors1")//colors1: Set(Blue, Green, Red)

    //Set的操作。filter ++： Set—无序的集合
    val feeds1 = Set("blog.toolshed.com", "pragdave.me", "blog.agiledeveloper.com")
    val feeds2 = Set("blog.toolshed.com", "martinfowler.com/bliki")
    val blogFeeds = feeds1 filter (_ contains "blog")
    //blog feeds: blog.toolshed.com, blog.agiledeveloper.com
    println(s"blog feeds: ${blogFeeds.mkString(", ")}")

    val mergedFeeds = feeds1 ++ feeds2
    println(s"# of merged feeds: ${mergedFeeds.size}")//4

    //两个 feed Set 上执行求交集操作之后的结果  &
    //common feeds: blog.toolshed.com
    val commonFeeds = feeds1 & feeds2
    println(s"common feeds: ${commonFeeds.mkString(", ")}")

    //在每个 feed 的前面加上一个“http://”字符串前缀，我们可以使用 map()方法。
    //这将对每个元素应用给定的函数值、将结果收集到一个 Set 中，并最终返回这个 Set
    val urls = feeds1 map ("http://" + _)
    println(s"One url: ${urls.head}")

    //循环set
    println("Refresh Feeds:")
    // Refreshing blog.toolshed.com...
    // Refreshing pragdave.me...
    // Refreshing blog.agiledeveloper.com...
    feeds1 foreach { feed => println(s" Refreshing $feed...") }

    //关联映射
    val feeds = Map(
      "Andy Hunt" -> "blog.toolshed.com",
      "Dave Thomas" -> "pragdave.me",
      "NFJS" -> "nofluffjuststuff.com/blog")

    //filterKeys() 过滤key
    val filterNameStartWithD = feeds filterKeys (_ startsWith "D")
    println(s"# of Filtered: ${filterNameStartWithD.size}")

    //处理key value
    val filterNameStartWithDAndPragprogInFeed = feeds filter { element =>
      val (key, value) = element
      (key startsWith "D") && (value contains "pragdave")
    }
    print("# of feeds with auth name D* and pragdave in URL: ")
    println(filterNameStartWithDAndPragprogInFeed.size)

    //get(keys)
    println(s"Get Andy's Feed: ${feeds.get("Andy Hunt")}")
    println(s"Get Bill's Feed: ${feeds.get("Bill Who")}")


    try {
      //使用 apply()方法来获取一个键的值
      //get()方法不同的是，apply()方法
      //返回的不是 Option[T]，而是返回的值（即 T）。
      println(s"Get Andy's Feed Using apply(): ${feeds("Andy Hunt")}")
      print("Get Bill's Feed: ")
      println(feeds("Bill Who"))
    } catch {
      case _: java.util.NoSuchElementException => println("Not found")
    }

    //updated更新
    //要添加 feed，请使用 updated()方法。因为我们使用的是不可变集合，所以 updated()
    //方法不会影响原来的 Map。所以feed不改变，newFeed改变
    val newFeeds1 = feeds.updated("Venkat Subramaniam", "blog.agiledeveloper.com")
    //Venkat's blog in original feeds: None
    println("Venkat's blog in original feeds: " + feeds.get("Venkat Subramaniam"))
    //Venkat's blog in new feed: blog.agiledeveloper.com
    println("Venkat's blog in new feed: " + newFeeds1("Venkat Subramaniam"))

    //可变Map
    val mutableFeeds = scala.collection.mutable.Map(
      "Scala Book Forum" -> "forums.pragprog.com/forums/87")
    mutableFeeds("Groovy Book Forum") = "forums.pragprog.com/forums/246"
    //Number of forums: 2
    println(s"Number of forums: ${mutableFeeds.size}")

    //List操作
    val feedss = List("blog.toolshed.com", "pragdave.me", "blog.agiledeveloper.com")
    //将 a 前插到 list
    val prefixedList = "forums.pragprog.com/forums/87" :: feedss
    //First Feed In Prefixed: forums.pragprog.com/forums/87
    println(s"First Feed In Prefixed: ${prefixedList.head}")

    //:::  将 list 实际上前插到 listA
    val feedsWithForums =
      feedss ::: List(
        "forums.pragprog.com/forums/87",
        "forums.pragprog.com/forums/246")
    println(s"First feed in feeds with forum: ${feedsWithForums.head}")
    println(s"Last feed in feeds with forum: ${feedsWithForums.last}")

    println(s"Feeds with blog: ${feedss.filter(_ contains "blog").mkString(", ")}")
    //想要检查是
    //否所有的 feed 都满足某个特定的条件，则可以使用 forall()方法。另
    println(s"All feeds have com: ${feedss.forall(_ contains "com")}")
    println(s"All feeds have dave: ${feedss.forall(_ contains "dave")}")
    println(s"Any feed has dave: ${feedss.exists(_ contains "dave")}")
    println(s"Any feed has bill: ${feedss.exists(_ contains "bill")}")

    //Feed url lengths: 17, 11, 23
    println(s"Feed url lengths: ${feedss.map(_.length).mkString(", ")}")

    //计算字符总和
    //foldLeft()方法将从列表的左侧开始，为列表中的每个元素调用给定的函数值
    val total = feedss.foldLeft(0) { (total, feed) => total + feed.length }
    //Total length of feed urls: 51
    println(s"Total length of feed urls: $total")

    //而\:()方法等价于 foldRight()方法
    val total2 = (0 /: feedss) { (total, feed) => total + feed.length }
    println(s"Total length of feed urls: $total2")

    //使用 Scala 的多项约定，
    val total3 = (0 /: feedss) { _ + _.length }
    println(s"Total length of feed urls: $total3")


    //方法名约定
    class Cow {
      def ^(moon: Moon): Unit = println("Cow jumped over the moon")
    }
    class Moon {
      def ^:(cow: Cow): Unit = println("This cow jumped over the moon too")
    }
    val cow = new Cow
    val moon = new Moon
    cow ^ moon//调用发生在cow上//Cow jumped over the moon
    cow ^: moon//调用发生在 moon 上//This cow jumped over the moon too

    moon.^:(cow)//This cow jumped over the moon too

    //除了以:结尾的操作符之外，还有一组调用目标也是跟随它们之后的实例的操作符
    //分别是+、-、!和~。其中一元+操作符被映射为对 unary_+()方法的
    //调用，而一元-操作符被映射为对 unary_-()方法的调用，以此类推。
    //其中一元+操作符被映射为对 unary_+()方法的
    //调用
    class Sample {
      def unary_+(): Unit = println("Called unary +")
      def unary_-(): Unit = println("called unary -")
      def unary_!(): Unit = println("called unary !")
      def unary_~(): Unit = println("called unary ~")
    }
    val sample = new Sample
    +sample
    -sample
    !sample
    ~sample


    //for表达式
    //scalaTest.test81$$$Lambda$20/1684106402@13fee20c
    //scalaTest.test81$$$Lambda$20/1684106402@13fee20c
    //scalaTest.test81$$$Lambda$20/1684106402@13fee20c
//    for (_ <- 1 to 3) { println("ho" + _) }
//
//    //for 表达式接受一个或者多个生成器作为参数，并带有 0 个或者多个定义以及 0 个或者
//    //多个过滤器。
//    for (_ <- 1 to 3) {println("ho")}
//
//    val result = for (i <- 1 to 10)
//    yield i * 2
//    result foreach { r => println(s" Refreshing $r...") }
//
//    val result2 = (1 to 10).map(_ * 2)
//    result2 foreach { r => println(s" Refreshing $r...") }
//
//    //“返回一个 i * 2 的集合，其中 i 是一个给定区间的成员，且
//    //i 是偶数
//    //就像是对一个值的集合进行 SQL 查询,被称作列表推导（list comprehension）。
//    val doubleEven = for (i <- 1 to 10; if i % 2 == 0)
//    yield i * 2
//
//    class Person(val firstName: String, val lastName: String)
//    object Person {
//      def apply(firstName: String, lastName: String): Person =
//        new Person(firstName, lastName)
//    }
//    val friends = List(Person("Brian", "Sletten"), Person("Neal", "Ford"),
//      Person("Scott", "Davis"), Person("Stuart", "Halloway"))
//    val lastNames =
//    for (friend <- friends; lastName = friend.lastName) yield lastName
//
//    //上面的代码也是 Scala 语法糖的一个例子
//    println(lastNames.mkString(", "))//Sletten, Ford, Davis, Halloway
//
//    //使用多个生成器，可以轻松地将这些值组合起来，以创建强大的组合。
//    for (i <- 1 to 3; j <- 4 to 6) {
//      print(s"[$i,$j] ")
//    }
  }
}
