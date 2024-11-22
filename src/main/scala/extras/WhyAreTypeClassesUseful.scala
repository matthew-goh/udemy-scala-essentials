package extras

object WhyAreTypeClassesUseful {

  // problem: need for specialised implementations

  // when the trait is combined with one or more implicit instances of it, we have a pattern called a TYPE CLASS
  // allows us to define specific implementations for certain types and not others
  trait Summable[T] {
    def sumElements(list: List[T]): T
  }

  implicit object IntSummable extends Summable[Int] {
    override def sumElements(list: List[Int]): Int = list.sum
  }
  implicit object StringSummable extends Summable[String] {
    override def sumElements(list: List[String]): String = list.mkString("")
  }

  // ad-hoc polymorphism with the implicit argument of a generic type
  // implicit arguments act as both capability enhancer and type constraint
  def processMyList[T](list: List[T])(implicit summable: Summable[T]): T = {
    // sum all elements of list
    // for integers, sum means addition
    // for strings, sum means concatenation
    // for other types, ERROR
    summable.sumElements(list)
  }

  def main(args: Array[String]): Unit = {
    val intSum = processMyList(List(1,2,3)) // needs an instance of Summable[Int] - automatically injected by compiler
    val stringSum = processMyList(List("Scala ", "is ", "awesome"))

    println(intSum)
    println(stringSum)

    // ERROR at COMPILE TIME: "No implicits found for parameter summable: Summable[Boolean]"
    // processMyList(List(true, true, false))
  }
}
