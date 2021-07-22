package society {
  //定义嵌套包
  package professional {
    class Executive {

      //private在本包可以访问，其他不可以
      private[professional] var workDetails = null
      private[society] var friends = null
      //因为 secrets 已经被标记为
      //private[this]，所以实例方法只能在隐式实例下访问这个字段，也就是说，在这个实例
      //中，这个字段不能在其他实例中访问
      private[this] var secrets = null

      def help(another: Executive): Unit = {
        println(another.workDetails)
        println(secrets)
//        println(another.secrets) // 编译错误
      }

    }

    class Assistant {
      def assist(anExec: Executive): Unit = {
        println(anExec.workDetails)
        println(anExec.friends)
      }
    }
  }

  package social {
    class Acquaintance {
      def socialize(person: professional.Executive) {
        println(person.friends)
//        println(person.workDetails) // 编译错误
      }
    }
  }
}
