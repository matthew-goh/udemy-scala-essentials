package exercises.part2oop

object Objects extends App {
  // SCALA DOES NOT HAVE CLASS-LEVEL FUNCTIONALITY ("static")
  // - can't access class fields from the class itself

  // Scala object = SINGLETON INSTANCE
  // when an object is defined, we define its type and its only instance
  object Person {
    // "static"/"class" - level functionality
    val N_EYES = 2
    def canFly: Boolean = false

    // factory method - purpose is to build persons given some parameters
    def apply(mother: Person, father: Person): Person = new Person("Bobbie")
  }

  class Person(val name: String) {
    // instance-level functionality
  }

  // COMPANIONS - can access each other's private members
  // Write objects and classes with the same name in the same scope
  // to separate instance-level functionality from class-level functionality

  println(Person.N_EYES)
  println(Person.canFly)

  val mary = new Person("Mary")
  val john = new Person("John")
  println(mary == john) // false

  val person1 = Person
  val person2 = Person
  println(person1 == person2) // true - point to the same instance

  val bobbie = Person(mary, john)
  // Person.apply(mary, john)
    
  // Scala Applications = Scala objects with
  // def main(args: Array[String]): Unit
  // Note: extends App already has a main method
}
