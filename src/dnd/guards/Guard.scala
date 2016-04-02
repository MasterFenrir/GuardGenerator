package dnd.guards

import dnd.guards.abilities.{Ability, Equipment, Spells}

/**
  * Created by Sander on 30-3-2016.
  */
class Guard(stats: Map[String, Int], equipment: List[Equipment], abilities: List[Ability], spells: Spells) {

  def addEquipment(equipment: Equipment): Guard = {
    val newEquipment = this.equipment :+ equipment
    Guard(stats, newEquipment, abilities, spells)
  }

  def addAbility(ability: Ability): Guard = {
    val newAbilities = this.abilities :+ ability
    Guard(stats, equipment, newAbilities, spells)
  }

  def addSpells(spells: Spells): Guard = {
    val newSpells = this.spells.addSpells(spells, false)
    Guard(stats, equipment, abilities, newSpells)
  }

  /**
    * Create a new Guard with the given stat modifiers
    *
    * @param statModifiers The stat modifiers
    * @return A new Guard
    */
  def addModifiers(statModifiers: Map[String, Integer]): Guard = {
    val newStats = stats.map((kv: (String, Int)) => {
      if (statModifiers.contains(kv._1))
        (kv._1, kv._2 + statModifiers(kv._1))
      else
        (kv._1, kv._2)
    })
    Guard(newStats, equipment, abilities, spells)
  }

}

object Guard {
  def apply(stats: Map[String, Int], equipment: List[Equipment], abilities: List[Ability], spells: Spells): Guard = {
    new Guard(stats, equipment, abilities, spells)
  }
}