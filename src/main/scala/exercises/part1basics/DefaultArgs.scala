package exercises.part1basics

object DefaultArgs extends App {

  // tail recursive factorial
  def trFact(n: Int, acc: Int = 1): Int =
    if (n <= 1) acc
    else trFact(n-1, n * acc)

  val fact10 = trFact(10)

  def savePicture(format: String = "jpg", width: Int = 1920, height: Int = 1080): Unit = println("saving picture")
  savePicture(width = 800)
  // cannot omit leading default arguments, e.g. savePicture(800)

  /*
    1. EITHER pass in every leading argument
    2. OR name the arguments
   */
  
  savePicture(height = 600, width = 800, format = "bmp") // can reorder arguments if you name them
}
