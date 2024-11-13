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

  def map[B](transformer: MyTransformer[A, B]): MyList[B]
  def filter(predicate: MyPredicate[A]): MyList[A]
  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B]
  // concatenation function
  def ++[B >: A](list: MyList[B]): MyList[B]
}

// Nothing is a proper substitute for any type
// Want Empty to be a proper substitute for a list of any type
case object Empty extends MyList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](elem: B): MyList[B] = new Cons(elem, Empty)
  def printElements: String = ""

  def map[B](transformer: MyTransformer[Nothing, B]): MyList[B] = Empty
  def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = Empty
  def flatMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B] = Empty

  def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
}

case class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  def head: A = h
  def tail: MyList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](elem: B): MyList[B] = new Cons(elem, this)
  def printElements: String = {
    if (t.isEmpty) h + ""
    else h + ", " + t.printElements
  }

  def map[B](transformer: MyTransformer[A, B]): MyList[B] = {
    new Cons(transformer.transform(h), t.map(transformer))
  }
  def filter(predicate: MyPredicate[A]): MyList[A] = {
    if (predicate.test(h)) new Cons(h, t.filter(predicate))
    else t.filter(predicate)
  }

  def ++[B >: A](list: MyList[B]): MyList[B] = new Cons(h, t ++ list)
  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] = {
    transformer.transform(h) ++ t.flatMap(transformer)
  }
}

object TestingMyList extends App{
  val list1: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
  val list2: MyList[String] = new Cons("Hello", new Cons("Scala", Empty))

  println(list1.toString)
  println(list2.toString)

  // using anonymous classes extending the traits
  println(list1.map(new MyTransformer[Int, Int] {
    override def transform(item: Int): Int = item * 2
  }).toString)
  println(list1.filter(new MyPredicate[Int] {
    override def test(item: Int): Boolean = item % 2 == 0
  }).toString)
  println(list1.flatMap(new MyTransformer[Int, MyList[Int]] {
    override def transform(item: Int): MyList[Int] = new Cons(item, new Cons(item + 1, Empty))
  }).toString)

  // after changing to case class/object
  val cloneList1: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
  println(cloneList1 == list1) // true - otherwise need new method to compare all elements recursively
}

trait MyPredicate[-T] {
  def test(item: T): Boolean
}

trait MyTransformer[-A, B] {
  def transform(item: A): B
}
