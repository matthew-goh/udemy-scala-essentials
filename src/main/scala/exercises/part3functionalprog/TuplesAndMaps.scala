package exercises.part3functionalprog

import scala.annotation.tailrec

/**
  * Created by Daniel.
  */
object TuplesAndMaps extends App {

  // tuples = finite ordered "lists"
  val aTuple = (2, "hello, Scala")  // Tuple2[Int, String] = (Int, String)

  println(aTuple._1)  // 2 - tuples are 1-indexed
  println(aTuple.copy(_2 = "goodbye Java"))
  println(aTuple.swap)  // ("hello, Scala", 2)

  // Maps - keys -> values
  val aMap: Map[String, Int] = Map()

  val phonebook = Map(("Jim", 555), "Daniel" -> 789, ("JIM", 9000)).withDefaultValue(-1)
  // a -> b is sugar for (a, b)
  println(phonebook)

  // map ops
  println(phonebook.contains("Jim")) // query whether a key is present
  println(phonebook("Mary")) // -1, default value

  // add a pairing
  val newPairing = "Mary" -> 678
  val newPhonebook = phonebook + newPairing
  println(newPhonebook)

  // functionals on maps
  // map, flatMap, filter
  //  println(phonebook.map(pair => pair._1.toLowerCase -> pair._2))

  // filterKeys
  println(phonebook.view.filterKeys(x => x.startsWith("J")).toMap)
  // mapValues
  println(phonebook.view.mapValues(number => "0245-" + number).toMap)

  // conversions to other collections
  println(phonebook.toList) // List((Jim,555), (Daniel,789), (JIM,9000))
  println(List(("Daniel", 555)).toMap)
  val names = List("Bob", "James", "Angela", "Mary", "Daniel", "Jim")
  // form a HashMap: keys are all possible values output by the lambda; values are lists containing the corresponding list elements
  println(names.groupBy(name => name.charAt(0))) // HashMap(J -> List(James, Jim), A -> List(Angela), M -> List(Mary), B -> List(Bob), D -> List(Daniel))
  println(names.groupBy(name => name.charAt(0) == 'J')) // HashMap(false -> List(Bob, Angela, Mary, Daniel), true -> List(James, Jim))

  /*
    1.  What would happen if I had two original entries "Jim" -> 555 and "JIM" -> 900
        - both keys are added
        !!! careful with mapping keys - could lose data if 2 keys map to the same thing

    2.  Design an overly simplified social network based on maps
        Person = String (name)
        map each person to a list of friends

        actions:
        - add a person to the network
        - remove
        - friend (mutual)
        - unfriend

        some stats:
        - number of friends of a person
        - person with most friends
        - how many people have NO friends
        - if there is a social connection between two people (direct or not)
   */

  // val socialNetwork: Map[String, List[String]] = Map()

  def addToNetwork(name: String, network: Map[String, Set[String]]): Map[String, Set[String]] = {
    if (network.contains(name)) network
    else network + (name -> Set())
  }

  def removeFromNetwork(name: String, network: Map[String, Set[String]]): Map[String, Set[String]] = {
    // auxiliary function to unfriend all existing friends before removing the key
    def removeAux(friends: Set[String], networkAcc:Map[String, Set[String]]): Map[String, Set[String]] = {
      if (friends.isEmpty) networkAcc
      else removeAux(friends.tail, unfriend(name, friends.head, networkAcc))
    }

    val unfriended = removeAux(network(name), network)
    unfriended - name // remove a key
  }

  def friend(name1: String, name2: String, network: Map[String, Set[String]]): Map[String, Set[String]] = {
//    // add the 2 people if not there already
//    val intNetwork = addToNetwork(name2, addToNetwork(name1, network))
//    // add to each other's friend lists (if not already friends)
//    intNetwork.map(pair => {
//      if (pair._1 == name1 && !pair._2.contains(name2)) pair._1 -> (pair._2 :+ name2)
//      else if (pair._1 == name2 && !pair._2.contains(name1)) pair._1 -> (pair._2 :+ name1)
//      else pair._1 -> pair._2
//    })

    val friends1 = network(name1)
    val friends2 = network(name2)
    network + (name1 -> (friends1 + name2)) + (name2 -> (friends2 + name1))
    // when adding a pair, if the new key exists in the map, it replaces the old key
  }

  def unfriend(name1: String, name2: String, network: Map[String, Set[String]]): Map[String, Set[String]] = {
    val friends1 = network(name1)
    val friends2 = network(name2)
    network + (name1 -> (friends1 - name2)) + (name2 -> (friends2 - name1))
  }

  def numFriends(name: String, network: Map[String, Set[String]]): Int = {
    if (!network.contains(name)) 0
    else network(name).size
  }

  def mostFriends(network: Map[String, Set[String]]): String = {
    network.maxBy(pair => pair._2.size)._1 // maxBy returns a pairing
  }

  def numPeopleWithoutFriends(network: Map[String, Set[String]]): Int = {
//    network.view.filterKeys(name => network(name).isEmpty).size
    network.count(pair => pair._2.isEmpty)
  }

  def socialConnection(name1: String, name2: String, network: Map[String, Set[String]]): Boolean = {
    // use BFS!
    @tailrec
    def bfs(target: String, consideredPeople: Set[String], discoveredPeople: Set[String]): Boolean = {
      if (discoveredPeople.isEmpty) false
      else {
        val person = discoveredPeople.head
        if (person == target) true
        else if (consideredPeople.contains(person)) bfs(target, consideredPeople, discoveredPeople.tail)
        else bfs(target, consideredPeople + person, discoveredPeople.tail ++ network(person))
      }
    }

    bfs(name2, Set(), network(name1) + name1)
  }

  val empty: Map[String, Set[String]] = Map()
  val network = addToNetwork("Mary", addToNetwork("Bob", empty))
  println(network)
  println(friend("Bob", "Mary", network))
  println(unfriend("Mary", "Bob", friend("Bob", "Mary", network)))
  println(removeFromNetwork("Bob", friend("Bob", "Mary", network)))

  val people = addToNetwork("Jim", addToNetwork("Mary", addToNetwork("Bob", empty)))
  val jimBob = friend("Jim", "Bob", people)
  val testNet = friend("Bob", "Mary", jimBob)
  println(testNet)
  println(numFriends("Bob", testNet))
  println(mostFriends(testNet))
  println(numPeopleWithoutFriends(testNet))

  println(socialConnection("Mary", "Jim", testNet))
  println(socialConnection("Mary", "Bob", network))
}
