package exercises.part3functionalprog

import scala.util.Random

object Sequences extends App {

  // Seq - general interface for data structures that (i) have a well-defined order and (ii) can be indexed
  val aSequence = Seq(1,3,2,4)
  println(aSequence) // List(1, 3, 2, 4)
  println(aSequence.reverse)
  println(aSequence(2)) // get index, 2
  println(aSequence ++ Seq(7,5,6)) // append, List(1, 3, 2, 4, 7, 5, 6)
  println(aSequence.sorted) // ascending order

  // Ranges
//  val aRange: Seq[Int] = 1 to 10 // inclusive of upper bound
  val aRange: Seq[Int] = 1 until 10 // non-inclusive of upper bound

  aRange.foreach(println)
//  (1 to 10).foreach(x => println("Hello")) // easy way to do something 10 times

  // lists
  // head, tail, isEmpty are O(1), most other operations are O(n)
  val aList = List(1,2,3)
  val prepended = 42 :: aList // // List(42, 1, 2, 3)
  val prependAppend = 42 +: aList :+ 89 // List(42, 1, 2, 3, 89)
  println(prependAppend)

  // fill is a curried function
  val apples5 = List.fill(5)("apple")
  println(apples5)

  println(aList.mkString("-|-")) // 1-|-2-|-3

  // arrays - mutable
  // can be manually constructed with predefined lengths
  val numbers = Array(1,2,3,4)
  val threeElements = Array.ofDim[String](3) // values not supplied
  threeElements.foreach(println) // default values used (0 for Int, null for String)

  // mutation
  numbers(2) = 0  // syntax sugar for numbers.update(2, 0)
  println(numbers.mkString(" ")) // 1 2 0 4

  // arrays and seq
  val numbersSeq: Seq[Int] = numbers  // implicit conversion
  println(numbersSeq) // ArraySeq(1, 2, 0, 4) or WrappedArray?

  // vectors - default implementation for immutable sequences
  val vector: Vector[Int] = Vector(1,2,3)
  println(vector)

  // vectors vs lists (performance test)
  val maxRuns = 1000
  val maxCapacity = 1000000 // length of Seq

  def getWriteTime(collection: Seq[Int]): Double = {
    val r = new Random
    val times = for {
      it <- 1 to maxRuns
    } yield {
      val currentTime = System.nanoTime()
      collection.updated(r.nextInt(maxCapacity), r.nextInt()) //(index to update, new elem)
      System.nanoTime() - currentTime // time taken for operation in nanoseconds
    }
    // milliseconds -> microseconds -> nanoseconds

    times.sum * 1.0 / maxRuns
  }

  val numbersList = (1 to maxCapacity).toList
  val numbersVector = (1 to maxCapacity).toVector

  // pro: keeps reference to tail
  // con: updating an element in the middle takes long
  println(getWriteTime(numbersList)) // 2000677.487
  // pro: depth of the tree is small
  // con: needs to replace an entire 32-element chunk
  println(getWriteTime(numbersVector)) // 5555.697

}
