package dnd.guards.builder

import dnd.guards.abilities.Equipment

/**
  * Created by sander on 4/3/16.
  */
object EquipmentBuilder {

  val equipmentMap: Map[String, List[Equipment]] = ???

  def apply(race: String): Equipment = {
    ???
  }

  def buildEquipmentMap: Map[String, List[Equipment]] = {
    ???
  }

}
