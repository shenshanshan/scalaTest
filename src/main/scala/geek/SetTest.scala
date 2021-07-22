package geek

import scala.collection.mutable.Set

object SetTest {
  def main(args: Array[String]): Unit = {
    val set = Set(1,2,3)
    println(set.getClass.getName) //

    println(set.exists(_ % 2 == 0)) //true
    println(set.drop(1)) //Set(2,3)

    //-------
    val mutableSet = Set(1,2,3)//可变集合
    println(mutableSet.getClass.getName) // scala.collection.mutable.HashSet

    mutableSet.add(4)
    mutableSet.remove(1)
    mutableSet += 5
    mutableSet -= 2

    println(mutableSet) // Set(5, 3, 4)

    val another = mutableSet.toSet // 不可变集合
    println(another.getClass.getName) // scala.collection.immutable.Set
  }
}
