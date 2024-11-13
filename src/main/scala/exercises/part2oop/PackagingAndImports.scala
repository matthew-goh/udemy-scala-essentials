package exercises.part2oop

import playground.{Cinderella => Princess, PrinceCharming} // aliasing for Cinderella
// import playground._ // use if the list gets too long

import java.sql.{Date => SqlDate}
import java.util.Date

object PackagingAndImports extends App {
  // package: bunch of definitions grouped under the same name

  // package members are accessible by their simple name
  val writer = new Writer("Daniel", "RockTheJVM", 2018) // from OOBasics - in the same package

  // best practice: packages are in hierarchy matching folder structure

  // package object - universal constants/methods residing outside specific classes/objects
  // can only have one per package
  // but quite rarely used in practice
  sayHello()
  println(SPEED_OF_LIGHT)

  // imports
  val prince = new PrinceCharming
  val princess = new Princess  // or use new playground.Cinderella (fully qualified name)

  // What if you need to import multiple classes with the same name from different packages?
  // 1. use fully qualified names
  // 2. use aliasing - useful if you need to import multiple classes with the same name from different packages
  val date = new Date
  val sqlDate = new SqlDate(2018, 5, 4)

  // default imports
  // java.lang - String, Object, Exception
  // scala - Int, Nothing, Function
  // scala.Predef - println, ???

}
