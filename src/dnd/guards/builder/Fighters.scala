package dnd.guards.builder

import dnd.guards.Guard

import scala.collection.mutable

/**
  * Created by Sander on 30-3-2016.
  */
object Fighters {

  val guards: Map[String, Guard] = buildGuards

  def buildGuards: Map[String, Guard] = {
    val guardMap = mutable.HashMap[String, Guard]()
    Map[String, Guard]()
  }

}
