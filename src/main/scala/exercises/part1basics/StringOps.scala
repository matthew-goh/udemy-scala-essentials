package exercises.part1basics

object StringOps extends App {

  val str: String = "Hello, I am learning Scala"

  println(str.charAt(2)) // character at index 2: l
  println(str.substring(7, 11)) // includes index 7, excludes index 11: I am
  println(str.split(" ").toList) // list of all substrings separated by " " (split returns an array)
  println(str.startsWith("Hello"))
  println(str.replace(" ", "-"))
  println(str.toLowerCase())
  println(str.length)

  val aNumberString = "2"
  val aNumber = aNumberString.toInt
  println('a' +: aNumberString :+ 'z') // +: is prepending, :+ is appending; used with chars
  println(str.reverse)
  println(str.take(2)) // first 2 characters

  // Scala-specific: String interpolators.

  // S-interpolators
  val name = "David"
  val age = 12
  val greeting = s"Hello, my name is $name and I am $age years old"
  val anotherGreeting = s"Hello, my name is $name and I will be turning ${age + 1} years old."
  println(anotherGreeting)

  // F-interpolators
  val speed = 1.2f
  val myth = f"$name can eat $speed%2.2f burgers per minute"
  println(myth)
  // %2.2f means minimum 2 characters total, 2 d.p. precision
  // could have %s after $name to specify that it's a string
  // %3d is an int with minimum 3 characters (e.g. 12 gets 2 spaces in front)

  // raw-interpolator
  println(raw"This is a \n newline") // ignores escaped characters - \n not escaped
  val escaped = "This is a \n newline"
  println(raw"$escaped") // doesn't work within injected variables!
}
