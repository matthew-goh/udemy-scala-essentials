package exercises.part2oop

object Generics extends App {
  // generic class
  class MyList[+A] {
    // use the type A - generic type
    def add[B >: A](element: B): MyList[B] = ???
    /*
      A = Cat
      B = Animal
     */
  }

  // multiple type parameters
  class MyMap[Key, Value]

  // Note: traits can also be type parameterised

  val listOfIntegers = new MyList[Int]
  val listOfStrings = new MyList[String]

  // generic methods
  object MyList { // companion for class MyList
    def empty[A]: MyList[A] = ???
  }
  val emptyListOfIntegers = MyList.empty[Int]


  // variance problem
  // if Cat extends Animal, does List[Cat] extend List[Animal]?
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  // 1. yes, List[Cat] extends List[Animal] = COVARIANCE
  class CovariantList[+A] // + sign before type parameter
  val animal: Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]
  // animalList.add(new Dog) ??? HARD QUESTION => we return a list of Animals - turns it into something more generic

  // 2. NO = INVARIANCE
  class InvariantList[A] // no sign with type parameter
  val invariantAnimalList: InvariantList[Animal] = new InvariantList[Animal] // can't have new InvariantList[Cat]

  // 3. Hell, no! CONTRAVARIANCE
  class Trainer[-A] // - sign before type parameter
  val trainer: Trainer[Cat] = new Trainer[Animal] // trainer of animal is better than trainer of cat!


  // bounded types
  class Cage[A <: Animal](animal: A) // upper bounded: A must be Animal or a subtype of it
  val cage = new Cage(new Dog)
  val cage1 = new Cage(new Animal)

  class Car
  // generic type needs proper bounded type
  //  val newCage = new Cage(new Car)

  // lower bounded types (e.g. A >: Animal) : requires a supertype of Animal

  // Exercise: expand MyList to be generic and covariant

}
