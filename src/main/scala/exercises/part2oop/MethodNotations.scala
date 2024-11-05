package exercises.part2oop

import scala.language.postfixOps

object MethodNotations extends App {

  class Person(val name: String, favoriteMovie: String, val age: Int = 0) {
    def likes(movie: String): Boolean = movie == favoriteMovie

    // def hangOutWith(person: Person): String = s"${this.name} is hanging out with ${person.name}"
    // hangOutWith acts like an operator
    def +(person: Person): String = s"${this.name} is hanging out with ${person.name}"
    // overload an infix operator
    def +(nickname: String): Person = new Person(s"$name ($nickname)", favoriteMovie)

    // writing unary operators for Person
    def unary_! : String = s"$name, what the heck?!"
    // unary + operator to increment age
    def unary_+ : Person = new Person(name, favoriteMovie, age + 1)

    // no parameters, can be used in postfix notation
    def isAlive: Boolean = true

    // apply(...) is the same as just (...)
    def apply(): String = s"Hi, my name is $name and I like $favoriteMovie"
    // overload apply(), e.g. person(2)
    def apply(n: Int): String = s"$name watched $favoriteMovie $n times"

    // Add a "learns" method in the Person class => "Mary learns Scala"
    def learns(thing: String): String = s"$name learns $thing"
    // Add a learnsScala method, calls learns method with "Scala".
    def learnsScala: String = this learns "Scala" // infix notation!
  }

  val mary = new Person("Mary", "Inception")
  println(mary.likes("Inception"))
  println(mary likes "Inception") // equivalent
  // infix notation = operator notation (an example of syntactic sugar - nicer way of writing code)
  // only works with methods with 1 parameter

  // "operators" in Scala
  val tom = new Person("Tom", "Fight Club")
  println(mary + tom) // Mary is hanging out with Tom
  println(mary.+(tom))

  println(1 + 2)
  println(1.+(2)) // + is the same as .+()

  // ALL OPERATORS ARE METHODS.
  // Akka actors have ! ?

  // prefix notation
  val x = -1  // equivalent with 1.unary_-
  val y = 1.unary_-
  // unary_ prefix only works with - + ~ !

  println(!mary) // Mary, what the heck?!
  println(mary.unary_!)

  // postfix notation - only for methods with no parameters, rarely used in practice
  println(mary.isAlive)
  println(mary isAlive) // only available with the scala.language.postfixOps import - discouraged

  // apply
  println(mary.apply())
  println(mary()) // equivalent

  /*
    1.  Overload the + operator
        mary + "the rockstar" => new person "Mary (the rockstar)"

    2.  Add an age to the Person class
        Add a unary + operator => new person with the age + 1
        +mary => mary with the age incrementer

    3.  Add a "learns" method in the Person class => "Mary learns Scala"
        Add a learnsScala method, calls learns method with "Scala".
        Use it in postfix notation.

    4.  Overload the apply method
        mary.apply(2) => "Mary watched Inception 2 times"
   */

  println((mary + "the Rockstar").apply()) // Hi, my name is Mary (the Rockstar) and I like Inception
  println((+mary).age) // 1
  println(mary learnsScala)
  println(mary(10)) // Mary watched Inception 10 times

}
