package exercises.part3functionalprog

// equivalent of Option
abstract class Maybe[+T] {
  def map[B](transformer: T => B): Maybe[B]
  def filter(predicate: T => Boolean): Maybe[T]
  def flatMap[B](transformer: T => Maybe[B]): Maybe[B]
}

case object Empty extends Maybe[Nothing] {
  def map[B](transformer: Nothing => B): Maybe[B] = Empty
  def filter(predicate: Nothing => Boolean): Maybe[Nothing] = Empty
  def flatMap[B](transformer: Nothing => Maybe[B]): Maybe[B] = Empty
}

case class Just[+T](value: T) extends Maybe[T] {
  def map[B](transformer: T => B): Maybe[B] = Just(transformer(value))
  def filter(predicate: T => Boolean): Maybe[T] = {
    if (predicate(value)) this
    else Empty
  }
  def flatMap[B](transformer: T => Maybe[B]): Maybe[B] = transformer(value)
}

object MaybeTest extends App {
  val just3 = Just(3)
  println(just3.map(_ * 2)) // Just(6)
  println(just3.flatMap(x => Just(x % 2 == 0))) // Just(false)
  println(just3.filter(_ % 2 == 0)) // Empty
}