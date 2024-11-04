package exercises.part1basics

import scala.annotation.tailrec
import scala.math._

object Recursion extends App {

  def factorial(n: Int): Int =
    if (n <= 1) 1
    else {
      println("Computing factorial of " + n + " - I first need factorial of " + (n-1))
      val result = n * factorial(n-1)
      println("Computed factorial of " + n)

      result
    }

  println(factorial(10))
  //  println(factorial(5000)) // stack overflow!

  def anotherFactorial(n: Int): BigInt = {
    @tailrec
    def factHelper(x: Int, accumulator: BigInt): BigInt =
      if (x <= 1) accumulator
      else factHelper(x - 1, x * accumulator) // TAIL RECURSION = use recursive call as the LAST expression

    factHelper(n, 1)
  }
//  println(anotherFactorial(20000))

  /*
    Breakdown:
    
    anotherFactorial(10) = factHelper(10, 1)
    = factHelper(9, 10 * 1)
    = factHelper(8, 9 * 10 * 1)
    = factHelper(7, 8 * 9 * 10 * 1)
    = ...
    = factHelper(2, 3 * 4 * ... * 10 * 1)
    = factHelper(1, 1 * 2 * 3 * 4 * ... * 10)
    = 1 * 2 * 3 * 4 * ... * 10
   */

  // WHEN YOU NEED LOOPS, USE _TAIL_ RECURSION.

  /*
    Exercises:
    1.  Concatenate a string n times
    2.  IsPrime function tail recursive
    3.  Fibonacci function, tail recursive.
   */

  def repeatString(str: String, n: Int): String = {
    @tailrec
    def accumulateStrings(x: Int, acc: String): String =
      if (x <= 0) acc
      else accumulateStrings(x-1, acc + str)
    accumulateStrings(n, "")
  }
  println(repeatString("hello", 3))

  def testPrime(n: Int): Boolean = {
    val sqrtn = sqrt(n).toInt

    @tailrec
    def hasNoFactor(larger: Int, smaller: Int): Boolean = {
      if (smaller == 1) true
      else if (larger % smaller == 0) false
      else hasNoFactor(larger, smaller - 1)
    }

    hasNoFactor(n, sqrtn)
  }
  println(testPrime(2003))
  println(testPrime(629))

//  def isPrime(n: Int): Boolean = {
//    @tailrec
//    def isPrimeTailrec(t: Int, isStillPrime: Boolean): Boolean =
//      if (!isStillPrime) false
//      else if (t <= 1) true
//      else isPrimeTailrec(t - 1, n % t != 0 && isStillPrime)
//
//    isPrimeTailrec(n / 2, true)
//  }
//
//  println(isPrime(2003))
//  println(isPrime(629))

  def fibonacci(n: Int): Int = {
    @tailrec
    def accumulateFib(x: Int, prev: Int, curr: Int): Int = {
      if (x <= 1) curr
      else accumulateFib(x-1, curr, prev + curr)
    }
    accumulateFib(n, 0, 1)
  }
  println(fibonacci(1))
  println(fibonacci(2))
  println(fibonacci(10))

//  def fibonacci(n: Int): Int = {
//    def fiboTailrec(i: Int, last: Int, nextToLast: Int): Int =
//      if(i >= n) last
//      else fiboTailrec(i + 1, last + nextToLast, last)
//
//    if (n <= 2) 1
//    else fiboTailrec(2, 1, 1)
//  }
}
