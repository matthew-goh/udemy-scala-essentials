package exercises.part3functionalprog

object MapFlatmapFilterFor extends App {

  val list = List(1,2,3)
  println(list.head) // 1
  println(list.tail) // List(2, 3)

  // map
  println(list.map(_ + 1)) // List(2, 3, 4)
  println(list.map(_ + " is a number"))

  // filter
  println(list.filter(_ % 2 == 0)) // List(2)

  // flatMap
  // -given a function that turns an element into a list,
  // it applies that function to all elements, then concatenates the lists
  val toPair = (x: Int) => List(x, x+1)
  println(list.flatMap(toPair)) // List(1, 2, 2, 3, 3, 4)

  // print all combinations between two lists, i.e. List("a1", "a2"... "d4")
  val numbers = List(1,2,3,4)
  val chars = List('a','b','c','d')
  val colors = List("black", "white")
  println(numbers.flatMap(n => chars.map(c => ""+ c + n)))

  // "iterating"
  // for 2 loops, do a flatMap and a map
  // for 3 loops, do 2 flatMaps and a map
  val combinations = numbers.filter(_ % 2 == 0).flatMap(n => chars.flatMap(c => colors.map(color => "" + c + n + "-" + color)))
  println(combinations)


  // foreach - receives a function returning Unit
  list.foreach(println)

  // for-comprehensions
  val forCombinations = for {
    n <- numbers if n % 2 == 0 // using a guard
    c <- chars
    color <- colors
  } yield "" + c + n + "-" + color
  println(forCombinations) // same as combinations

  for {
    n <- numbers
  } println(n)

  // syntax overload
  list.map { x =>
    x * 2
  }

  /*
    Exercises
    1.  Does MyList support for comprehensions?
    - map, flatMap and filter need to have certain function signatures for it to work:
        map(f: A => B) => MyList[B]
        filter(p: A => Boolean) => MyList[A]
        flatMap(f: A => MyList[B]) => MyList[B]
      This is indeed what we have in MyList!
    2.  Implement a small collection of at most ONE element, called Maybe[+T]
        - subtypes are (i) an empty collection and (ii) a collection with 1 element of type T
        - map, flatMap, filter
   */
}
