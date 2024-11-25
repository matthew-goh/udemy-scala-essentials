package exercises.part3functionalprog

import java.util.Random

object Options extends App {

  val myFirstOption: Option[Int] = Some(4)
  val noOption: Option[Int] = None

  println(myFirstOption)

  // WORK with unsafe APIs - suppose a method returns null
  def unsafeMethod(): String = null
  //  val result = Some(null) // WRONG
  val result = Option(unsafeMethod()) // Some or None depending whether the value is null
  println(result)

  // chained methods
  def backupMethod(): String = "A valid result"
  val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))

  // DESIGN unsafe APIs
  // better to return an Option - no need to wrap API calls in options
  def betterUnsafeMethod(): Option[String] = None
  def betterBackupMethod(): Option[String] = Some("A valid result")
  val betterChainedResult = betterUnsafeMethod() orElse betterBackupMethod()

  // functions on Options
  println(myFirstOption.isEmpty) // whether it is None
  println(myFirstOption.get)  // UNSAFE - DO NOT USE THIS

  // map, flatMap, filter
  println(myFirstOption.map(_ * 2)) // Some(8)
  println(myFirstOption.filter(x => x > 10)) // None
  println(myFirstOption.flatMap(x => Option(x * 10))) // Some(40)

  /*
    Exercise: Suppose we have the following API (config and Connection)
   */
  val config: Map[String, String] = Map(
    // fetched from elsewhere, can't be certain that these values exist
    "host" -> "176.45.36.1",
    "port" -> "80"
  )

  class Connection {
    def connect = "Connected" // connect to some server
  }
  object Connection {
    val random = new Random(System.nanoTime()) // set a seed

    def apply(host: String, port: String): Option[Connection] =
      if (random.nextBoolean()) Some(new Connection)
      else None
  }

  // TODO: try to establish a connection, if so - print the connect method
  val host: Option[String] = config.get("host")
  val port: Option[String] = config.get("port")
  /*
    if (host != null)
      if (port != null)
        return Connection.apply(host, port)
    return null
   */
  val connection: Option[Connection] = host.flatMap(h => port.flatMap(p => Connection.apply(h, p)))
  /*
    if (c != null)
      return c.connect
    return null
   */
  val connectionStatus: Option[String] = connection.map(c => c.connect)
  // if (connectionStatus == null) println(None) else print (Some(connectionstatus.get))
  println(connectionStatus)
  /*
    if (status != null)
      println(status)
   */
  connectionStatus.foreach(println)

  // chained calls - shorthand
  config.get("host")
    .flatMap(host => config.get("port")
      .flatMap(port => Connection(host, port))
      .map(connection => connection.connect))
    .foreach(println)

  // for-comprehensions
  // GIVEN a host from config, GIVEN a port from config, GIVEN a connection from the host and port
  val forConnectionStatus = for {
    host <- config.get("host")
    port <- config.get("port")
    connection <- Connection(host, port)
  } yield connection.connect
  // returns None if anything inside the "for" is None
  
  forConnectionStatus.foreach(println)
}
