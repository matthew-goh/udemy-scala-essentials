package exercises.part2oop

// singly linked list of integers
abstract class MyList[+A] {
  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](elem: B): MyList[B]
  def printElements: String
  override def toString: String = "[" + printElements + "]" // need to override the built-in method
  /*
      head = first element of the list
      tail = remainder of the list (pointer)
      isEmpty = whether it is empty
      add(int) => new list with this element added
      toString => string representation of the list
  */
}

// Nothing is a proper substitute for any type
// Want Empty to be a proper substitute for a list of any type
object Empty extends MyList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](elem: B): MyList[B] = new Cons(elem, Empty)
  def printElements: String = ""
}

class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  def head: A = h
  def tail: MyList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](elem: B): MyList[B] = new Cons(elem, this)
  def printElements: String = {
    if (t.isEmpty) h + ""
    else h + ", " + t.printElements
  }
}

object TestingMyList extends App{
  val list1: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
  val list2: MyList[String] = new Cons("Hello", new Cons("Scala", Empty))

  println(list1.toString)
  println(list2.toString)
}
