package dnd.guards.abilities

/**
  * Created by Sander on 30-3-2016.
  */
class Equipment(val equipment: String, val modifiers: Map[String, Int]) {

  override def toString = equipment
}

object Equipment {
  def apply(equipment: String, modifiers: Map[String, Int]): Equipment = {
    new Equipment(equipment, modifiers)
  }
}
