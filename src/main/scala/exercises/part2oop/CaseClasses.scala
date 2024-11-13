package exercises.part2oop

object CaseClasses extends App {

//  shorthand for defining a class and companion object with standard methods in one go
  /*
    equals, hashCode, toString
   */

  case class Person(name: String, age: Int)

  // 1. class parameters are fields (no need to put e.g. val name)
  val jim = new Person("Jim", 34)
  println(jim.name)

  // 2. sensible toString method
  // println(instance) is the same as println(instance.toString) // syntactic sugar
  println(jim) // Person(Jim,34)

  // 3. equals and hashCode implemented out of the box
  val jim2 = new Person("Jim", 34)
  println(jim == jim2) // true (case classes have an equals method)

  // 4. CCs have a handy copy method, can receive named parameters
  val jim3 = jim.copy(age = 45)
  println(jim3) // Person(Jim,45)

  // 5. CCs have companion objects
  val thePerson = Person
  val mary = Person("Mary", 23) // companion object's apply method (factory method) does the same as the constructor

  // 6. CCs are serializable - can send instances through the network and between JVMs
  // Akka framework

  // 7. CCs have extractor patterns = CCs can be used in PATTERN MATCHING

  // case object acts like a case class, but is an object
  // same properties except they don't have companion objects
  case object UnitedKingdom {
    def name: String = "The UK of GB and NI"
  }

  /*
    Expand MyList - use case classes and case objects
   */
}
