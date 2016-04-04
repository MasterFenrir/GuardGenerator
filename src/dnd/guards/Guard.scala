package dnd.guards

import dnd.guards.abilities.{Ability, Equipment, Spells}

/**
  * Created by Sander on 30-3-2016.
  */
class Guard(stats: Map[String, Int], primeStat: String, equipment: List[Equipment], proficiencies: List[String], abilities: List[Ability], spells: Spells) {

  def addEquipment(equipment: Equipment): Guard = {
    val newEquipment = this.equipment :+ equipment
    Guard(stats, primeStat, newEquipment, proficiencies, abilities, spells)
  }

  def addAbility(ability: Ability): Guard = {
    val newAbilities = this.abilities :+ ability
    Guard(stats, primeStat, equipment, proficiencies, newAbilities, spells)
  }

  def addSpells(spells: Spells): Guard = {
    val newSpells = this.spells.addSpells(spells)
    Guard(stats, primeStat, equipment, proficiencies, abilities, newSpells)
  }

  def getModifier(stat: String): Int = {
    var value: Double = stats(stat) - 10
    value = value / 2
    math.floor(value).toInt
  }

  def attackModifier: Int = {
    getModifier(primeStat) + stats(Guard.StatNames.PROF)
  }

  def defaultDamageModifier: Int = {
    getModifier(primeStat)
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
    Guard(newStats, primeStat, equipment, proficiencies, abilities, spells)
  }

}

object Guard {

  def apply(stats: Map[String, Int], primeStat: String, equipment: List[Equipment], proficiencies: List[String], abilities: List[Ability], spells: Spells): Guard = {
    new Guard(stats, primeStat, equipment, proficiencies, abilities, spells)
  }

  object StatNames {
    val STR = "STR"
    val DEX = "DEX"
    val CON = "CON"
    val INT = "INT"
    val WIS = "WIS"
    val CHA = "CHA"
    val AC = "AC"
    val HP = "HP"
    val SPEED = "Speed"
    val PROF = "PROF"
  }

}