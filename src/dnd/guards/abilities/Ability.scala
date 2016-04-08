package dnd.guards.abilities

/**
  * Created by Sander on 30-3-2016.
  */
class Ability(val ability: String, val modifiers:Map[String, Int]) {
  override def toString: String = ability
}

object Ability {
  def apply(ability: String): Ability = {
    new Ability(ability, Map[String, Int]())
  }

  def apply(ability:String, modifiers:Map[String, Int]): Ability = {
    new Ability(ability, modifiers)
  }
}