package exercises.part2oop

object AbstractDataTypes extends App {

  // abstract - contain unimplemented fields/members, made to be extended later
  // cannot be instantiated!
  abstract class Animal {
    val creatureType: String = "wild"
    def eat: Unit
  }

  class Dog extends Animal {
    override val creatureType: String = "Canine"
    def eat: Unit = println("crunch crunch") // override keyword not needed for abstract members
  }

  // traits - can be inherited along with classes
  trait Carnivore {
    def eat(animal: Animal): Unit
    val preferredMeal: String = "fresh meat"
  }

  trait ColdBlooded
  class Crocodile extends Animal with Carnivore with ColdBlooded {
    override val creatureType: String = "croc"
    def eat: Unit = println("nomnomnom") // from abstract class
    def eat(animal: Animal): Unit = println(s"I'm a croc and I'm eating ${animal.creatureType}") // from trait Carnivore
  }

  val dog = new Dog
  val croc = new Crocodile
  croc.eat(dog) // I'm a croc and I'm eating Canine

  // traits vs abstract classes
  // 1 - traits do not have constructor parameters
  // 2 - multiple traits may be inherited by the same class
  // 3 - traits = behavior, abstract class = "thing"
  // both can have abstract and non-abstract members
}
