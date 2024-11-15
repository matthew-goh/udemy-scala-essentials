package exercises.part3functionalprog

object WhatsAFunction extends App {

  // DREAM: use functions as first class elements
  // - work with them like with plain values (pass as parameters, return as results)
  // problem: object-oriented world - classes and methods

  // an instance of MyFunction (a function-like class)
  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }
  // can call doubler as if it's a function
  println(doubler(2))

  // function types = Function1[A, B]  // 1 input parameter
  val stringToIntConverter = new Function1[String, Int] {
    override def apply(string: String): Int = string.toInt
  }
  println(stringToIntConverter("3") + 4)

  val adder: ((Int, Int) => Int) = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a + b
  }

  // Function types: Function2[A, B, R] === (A,B) => R // // up to Function22

  // ALL SCALA FUNCTIONS ARE OBJECTS

  /*
    Exercises
    1.  a function which takes 2 strings and concatenates them
    2.  transform the MyPredicate and MyTransformer into function types
    3.  define a function which takes an int and returns another function which takes an int and returns an int
        - what's the type of this function
        - how to do it
   */

  val concatenator: ((String, String) => String) = new Function2[String, String, String] {
    override def apply(s1: String, s2: String): String = s1 + s2
  }
  println(concatenator("Hello ", "Scala"))

  val superAdder: (Int => (Int => Int)) = new Function1[Int, Function1[Int, Int]] {
    override def apply(x: Int): Int => Int = new Function1[Int, Int] {
      override def apply(y: Int): Int = x + y
    }
  }
  val adder3 = superAdder(3)
  println(adder3(4)) // 7
  println(superAdder(3)(4)) // curried function - can be called with multiple parameter lists

}

trait MyFunction[A, B] {
  def apply(element: A): B
}