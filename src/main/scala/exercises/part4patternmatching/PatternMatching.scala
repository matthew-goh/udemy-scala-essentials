package exercises.part4patternmatching

import scala.util.Random
import scala.util.matching.Regex.Match

object PatternMatching extends App {

  // pattern matching is like a "switch" on steroids
  val random = new Random
  val x = random.nextInt(10)

  val description = x match {
    case 1 => "the ONE"
    case 2 => "double or nothing"
    case 3 => "third time is the charm"
    case _ => "something else"  // _ = WILDCARD
  }

  println(x)
  println(description)

  // Decompose values - can be used in conjunction with case classes
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 20)

  val greeting = bob match {
    case Person(n, a) if a < 21 => s"Hi, my name is $n and I can't drink in the US" // adding a guard
    case Person(n, a) => s"Hi, my name is $n and I am $a years old"
    case _ => "I don't know who I am"
  }
  println(greeting)

  /*
    Notes:
    1. cases are matched in order
    2. what if no cases match? MatchError
    3. what is the type of the pattern-matched expression? unified type of all the types in all the cases
    -lowest common ancestor of all types from all cases (could be Any)
    4. PM works really well with case classes (come with extractor patterns out of the box)
   */

  // PM on sealed hierarchies
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Parrot(greeting: String) extends Animal

  val animal: Animal = Dog("Terra Nova")
  animal match { // matching on the type
    case Dog(someBreed) => println(s"Matched a dog of the $someBreed breed")
  }
  // since Animal is a sealed class, compiler gives a warning that the match may not be exhaustive

  // beware of trying to match everything!
  val isEven = x match {
    case n if n % 2 == 0 => true
    case _ => false
  }
  // WHY?!
  val isEvenCond = if (x % 2 == 0) true else false // ?!
  val isEvenNormal = x % 2 == 0

  /*
    Exercise:
    Say we have a trait Expr that denotes a maths expression, with case classes Number, Sum and Prod
    Write a simple function that uses PM:
     takes an Expr => returns human-readable form of it

     Sum(Number(2), Number(3)) => 2 + 3
     Sum(Number(2), Number(3), Number(4)) => 2 + 3 + 4
     Prod(Sum(Number(2), Number(1)), Number(3)) = (2 + 1) * 3
     Sum(Prod(Number(2), Number(1)), Number(3)) = 2 * 1 + 3
   */
  trait Expr
  case class Number (n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

//  def show(expr: Expr): String = {
//    expr match {
//      case Number(n) => n.toString
//      case Sum(e1, e2) => s"${show(e1)} + ${show(e2)}"
//      case Prod(Sum(x1, y1), Sum(x2, y2)) => s"(${show(Sum(x1, y1))}) * (${show(Sum(x2, y2))})"
//      case Prod(Sum(x, y), e2) => s"(${show(Sum(x, y))}) * ${show(e2)}"
//      case Prod(e1, Sum(x, y)) => s"${show(e1)} * (${show(Sum(x, y))})"
//      case Prod(e1, e2) => s"${show(e1)} * ${show(e2)}"
//    }
//  }

  def show(expr: Expr): String = {
    expr match {
      case Number(n) => n.toString
      case Sum(e1, e2) => s"${show(e1)} + ${show(e2)}"
      case Prod(Sum(x1, y1), Sum(x2, y2)) => s"(${show(Sum(x1, y1))}) * (${show(Sum(x2, y2))})"
      case Prod(e1, e2) => {
        def maybeShowParentheses(e: Expr): String = e match {
          case Prod(_, _) | Number(_) => show(e)
          case _ => "(" + show(e) + ")"
        }
        maybeShowParentheses(e1) + " * " + maybeShowParentheses(e2)
      }
    }
  }

  println(show(Sum(Number(2), Number(3)))) // 2 + 3
  println(show(Sum(Sum(Number(2), Number(3)), Number(4)))) // 2 + 3 + 4
  println(show(Prod(Sum(Number(2), Number(1)), Number(3)))) // (2 + 1) * 3
  println(show(Prod(Sum(Number(2), Number(1)), Sum(Number(3), Number(4))))) // (2 + 1) * (3 + 4)
  println(show(Sum(Prod(Number(2), Number(1)), Number(3)))) // 2 * 1 + 3
}
