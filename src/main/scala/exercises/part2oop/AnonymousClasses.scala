package exercises.part2oop

object AnonymousClasses extends App {

  abstract class Animal {
    def eat: Unit
  }

  // instantiate a subclass and provide definition on the spot, without naming it ourselves
  // - must pass in all parameters and provide implementation of all methods (if extending an abstract class)

  // anonymous class
  val funnyAnimal: Animal = new Animal {
    override def eat: Unit = println("ahahahahahaah")
  }
  /*
    equivalent with

    class AnonymousClasses$$anon$1 extends Animal {
      override def eat: Unit = println("ahahahahahaah")
    }
    val funnyAnimal: Animal = new AnonymousClasses$$anon$1
   */

  println(funnyAnimal.getClass) // class exercises.part2oop.AnonymousClasses$$anon$1

  class Person(name: String) {
    def sayHi: Unit = println(s"Hi, my name is $name, how can I help?")
  }
 // anonymous class extending a non-abstract class
  val jim = new Person("Jim") {
    override def sayHi: Unit = println(s"Hi, my name is Jim, how can I be of service?")
  }

  /*
    Exercises:
    1.  Generic trait MyPredicate[-T] with a little method test(T) => Boolean
    2.  Generic trait MyTransformer[-A, B] with a method transform(A) => B
    Every class extending these traits will have their own implementation of test and transform
    Without the [-T] and [-A] , the code will not compile (for complex reasons)
    3.  MyList:
        - map(transformer) => MyList
        - filter(predicate) => MyList
        - flatMap(transformer from A to MyList[B]) => MyList[B]

        class EvenPredicate extends MyPredicate[Int]
        class StringToIntTransformer extends MyTransformer[String, Int]

        [1,2,3].map(n * 2) = [2,4,6]
        [1,2,3,4].filter(n % 2) = [2,4]
        [1,2,3].flatMap(n => [n, n+1]) => [1,2,2,3,3,4]
   */
}
