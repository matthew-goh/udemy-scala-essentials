package exercises.part3functionalprog

import scala.util.{Failure, Random, Success, Try}
// Try is a wrapper for a computation that could fail
// Failure wraps failed computations
// Success wraps successful computations

object HandlingFailure extends App {

  // create success and failure explicitly
  val aSuccess = Success(3)
  val aFailure = Failure(new RuntimeException("SUPER FAILURE"))

  println(aSuccess)
  println(aFailure)

  // usually no need to create them yourself
  // construct Try objects via the apply method
  def unsafeMethod(): String = throw new RuntimeException("NO STRING FOR YOU BUSTER")
  val potentialFailure = Try(unsafeMethod())
  println(potentialFailure) // Failure(java.lang.RuntimeException: NO STRING FOR YOU BUSTER)

  // syntax sugar
  val anotherPotentialFailure = Try {
    // code that might throw
  }

  // utilities
  println(potentialFailure.isSuccess) // Boolean

  // orElse
  def backupMethod(): String = "A valid result"
  val fallbackTry = Try(unsafeMethod()).orElse(Try(backupMethod()))
  println(fallbackTry)

  // IF you design the API
  def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException)
  def betterBackupMethod(): Try[String] = Success("A valid result")
  val betterFallback = betterUnsafeMethod() orElse betterBackupMethod()

  // map, flatMap, filter
  println(aSuccess.map(_ * 2))
  println(aSuccess.flatMap(x => Success(x * 10)))
  println(aSuccess.filter(_ > 10)) // Failure(NoSuchElementException)
  // => for-comprehensions

  /*
    Exercise:
    Suppose we have the function renderHTML, class Connection (method get) and object HttpService (method getConnection)
    If you get the html page from the connection, print it to the console i.e. call renderHTML

    -> Create methods that wrap the unsafe methods into a Try
    - In practice, own method has to be defined outside the class/object if you don't have access to the implementation
   */
  val host = "localhost"
  val port = "8080"

  def renderHTML(page: String): Unit = println(page)

  class Connection {
    def get(url: String): String = {
      val random = new Random(System.nanoTime())
      if (random.nextBoolean()) "<html>...</html>"
      else throw new RuntimeException("Connection interrupted")
    }

    // SAFE METHOD
    def getSafe(url: String): Try[String] = Try(get(url))
  }

  object HttpService {
    val random = new Random(System.nanoTime())

    def getConnection(host: String, port: String): Connection =
      if (random.nextBoolean()) new Connection
      else throw new RuntimeException("Someone else took the port")

    // SAFE METHOD
    def getSafeConnection(host: String, port: String): Try[Connection] = Try(getConnection(host, port))
  }

  // HOW TO CONNECT
  val possibleConnection: Try[Connection] = HttpService.getSafeConnection(host, port)
  val possibleHTML: Try[String] = possibleConnection.flatMap(connection => connection.getSafe("/home"))
  possibleHTML.foreach(renderHTML)

  // shorthand version
  HttpService.getSafeConnection(host, port)
    .flatMap(connection => connection.getSafe("/home"))
    .foreach(renderHTML)

  // for-comprehension version
  for {
    connection <- HttpService.getSafeConnection(host, port)
    html <- connection.getSafe("/home")
  } renderHTML(html)

  /* Imperative version:
    try {
      connection = HttpService.getConnection(host, port)
      try {
        page = connection.get("/home")
        renderHTML(page)
      } catch (some other exception) {

      }
    } catch (exception) {

    }
   */
}
