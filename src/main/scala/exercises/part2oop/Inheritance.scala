package exercises.part2oop

object Inheritance extends App {

  // single class inheritance - can only extend one class at a time
  sealed class Animal {
    val creatureType = "wild"
    def eat = println("nomnom")
    // protected modifier allows subclasses only to access it
  }

  class Cat extends Animal {
    def crunch = {
      eat
      println("crunch crunch")
    }
  }

  // Cat is a subclass of Animal - inherits all non-private methods
  // Animal is a superclass of Cat
  val cat = new Cat
  cat.crunch


  // constructors
  class Person(name: String, age: Int) {
    def this(name: String) = this(name, 0)
  }
  // must call constructor of Person before that of Adult
  // class Adult(name: String, age: Int, idCard: String) extends Person(name, age)
  class Adult(name: String, age: Int, idCard: String) extends Person(name)
  val adult1 = new Adult("John", 21, "ID123")

  // overriding
  class Dog(override val creatureType: String) extends Animal {
    //    override val creatureType = "domestic" // can override in the body or directly in the constructor arguments
    override def eat = {
      super.eat
      println("crunch, crunch")
    }
  }
  val dog = new Dog("K9")
  dog.eat
  println(dog.creatureType) // K9


  // type substitution (broad: polymorphism) - defined type is Animal but call Dog instead
  val unknownAnimal: Animal = new Dog("K9")
  unknownAnimal.eat // use the most overridden version possible

  // overRIDING (supplying a different implementation in derived classes)
  // vs overLOADING (supplying multiple methods with the same name but different signatures in the same class)

  // super - reference a field/method from parent class

  // preventing overrides
  // 1 - use final on member (e.g. final def eat ...)
  // 2 - use final on the entire class (e.g. final class Animal) - class cannot be extended
  // 3 - seal the class = extend classes in THIS FILE, prevent extension in other files (fixed number of subclasses, all defined in this file)
}
