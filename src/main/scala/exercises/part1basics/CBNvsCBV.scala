package exercises.part1basics

object CBNvsCBV extends App {

  // time functions return Long values
  def calledByValue(x: Long): Unit = {
    println("by value: " + x)
    println("by value: " + x)
  }

  def calledByName(x: => Long): Unit = {
    println("by name: " + x)
    println("by name: " + x)
  }

  calledByValue(System.nanoTime()) // 2 printed values are the same
  // argument is evaluated first and same value is used throughout function definition
  calledByName(System.nanoTime()) // different
  // argument is passed as is and evaluated on the spot at every use in the function

  def infinite(): Int = 1 + infinite()
  def printFirst(x: Int, y: => Int) = println(x)

  //  printFirst(infinite(), 34) // stack overflow
  printFirst(34, infinite()) // infinite() not evaluated
}
