package exercises.part2oop

object OOBasics extends App {
  val person = new Person("John", 26)
  println(person.age)
  person.greet("Daniel")
  person.greet()

  // Testing exercises
  val author = new Writer("Charles", "Dickens", 1812)
  val impostor = new Writer("Charles", "Dickens", 1812)
  val novel = new Novel("Great Expectations", 1861, author)
  println(novel.authorAge())
  println(novel.isWrittenBy(impostor)) // false - is this what we want? (issues with equality)

  val counter = new Counter()
  counter.inc.print // prints Incrementing, then 1
  counter.inc.inc.inc.print
  counter.inc(10).print
}

// class definitions can sit on the top level code - outside the object
// class parameters are NOT fields
// - put val (or var) in front to make them fields

// constructor
class Person(name: String, val age: Int = 0) {
  // body: this whole block is executed on instantiation, including side effects
  // val and var definitions are fields
  val x = 2

  // method
  def greet(name: String): Unit = println(s"${this.name} says: Hi, $name")

  // overloading: defining methods with the same name but different signatures (number and type of parameters)
  // compiler knows which method to run unless both have the same parameter lists
  def greet(): Unit = println(s"Hi, I am $name") // no name parameter, so this.name is used

  // multiple constructors (overloading constructors)
  // auxiliary constructor calls primary constructor
  def this(name: String) = this(name, 0) // more easily done with a default parameter!
  def this() = this("John Doe")
}

// Exercises
/*
  Novel and a Writer

  Writer: first name, surname, year (of birth)
    - fullname: returns first name + surname

  Novel: name, year of release, author (instance of type Writer)
  - authorAge (at year of release)
  - isWrittenBy(author) (Boolean)
  - copy (new year of release) = new instance of Novel
 */
class Writer(val firstName: String, val surname: String, val yearOfBirth: Int) {
  def fullName(): String = firstName + " " + surname
}

class Novel(val name: String, val yearOfRelease: Int, val author: Writer) {
  def authorAge(): Int = this.yearOfRelease - author.yearOfBirth
  def isWrittenBy(author: Writer): Boolean = (author == this.author)
  def copy(newYear: Int): Novel = new Novel(this.name, newYear, this.author)
}

/*
  Counter class
    - receives an int value
    - method: returns current count
    - method to increment/decrement => new Counter
    - overload inc/dec to receive an amount => new Counter
 */
class Counter(val count: Int = 0) {
  //def count: Int = value // same effect as making value a field

  // immutability - instances are fixed, must return a new instance to change the value
//  def inc(): Counter = new Counter(count + 1)
//  def dec(): Counter = new Counter(count - 1)
//
//  def inc(amount: Int): Counter = new Counter(count + amount)
//  def dec(amount: Int): Counter = new Counter(count - amount)

  // suppose we want inc and dec to print messages
  def inc: Counter = {
    println("Incrementing")
    new Counter(count + 1)
  }
  def dec: Counter = {
    println("Decrementing")
    new Counter(count - 1)
  }
  // then we need recursion here
  def inc(amount: Int): Counter = {
    if (amount <= 0) this
    else inc.inc(amount-1) // new Counter(n+1), apply this inc(n-1)
  }
  def dec(amount: Int): Counter = {
    if (amount <= 0) this
    else dec.dec(amount-1) // new Counter(n-1), apply this dec(n-1)
  }

  def print: Unit = println(count)
}