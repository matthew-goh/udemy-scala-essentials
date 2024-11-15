package exercises.part3functionalprog

object AnonymousFunctions extends App {

  // anonymous function (LAMBDA)
  // - instead of new Function1[Int, Int] (still OO way of defining an anonymous function and instantiating on the spot)
  val doubler: Int => Int = (x: Int) => x * 2
  // compiler will infer types for the below 2 lines
//  val doubler = (x: Int) => x * 2
//  val doubler: Int => Int = x => x * 2

  // multiple params in a lambda - put them in parentheses for type declaration
  val adder: (Int, Int) => Int = (a: Int, b: Int) => a + b

  // no params - use ()
  val justDoSomething: () => Int = () => 3

  // careful - MUST call lambdas with parentheses
  println(justDoSomething) // function itself
  println(justDoSomething()) // call - prints 3

  // curly braces with lambdas
  val stringToInt = { (str: String) =>
    str.toInt
  }

  // MOAR syntactic sugar - each underscore stands for a different parameter
  val niceIncrementer: Int => Int = _ + 1 // equivalent to x => x + 1
  val niceAdder: (Int, Int) => Int = _ + _ // equivalent to (a,b) => a + b

  /*
    Exercises
    1.  MyList: replace all FunctionX calls with lambdas
    2.  Rewrite the "special" adder as an anonymous function
   */

//  val superAdder: (Int => (Int => Int)) = new Function1[Int, Function1[Int, Int]] {
//    override def apply(x: Int): Int => Int = new Function1[Int, Int] {
//      override def apply(y: Int): Int = x + y
//    }
//  }
  val superAdder: (Int => (Int => Int)) = { (x: Int) =>
    { (y: Int) => x + y }
  }
  println(superAdder(3)(4))
}
