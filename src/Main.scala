import dnd.guards.Guard
import dnd.guards.abilities.{Ability, Equipment, Spells}
import dnd.guards.builder.{EquipmentBuilder, GuardBuilder}

/**
  * Created by Sander on 28-3-2016.
  */
object Main {

  def main(args: Array[String]) {
    println(GuardBuilder.Stats.melee("Human"))
  }
}
