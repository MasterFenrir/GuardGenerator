package dnd.guards

import dnd.guards.Guard.StatNames
import dnd.guards.abilities.{Ability, Equipment, Spells}

import scala.collection.immutable.ListMap

/**
  * Created by Sander on 30-3-2016.
  */
class Guard(val race: String, stats: ListMap[String, Int], primeStat: String, equipment: List[Equipment], proficiencies: List[String], abilities: List[Ability], spells: Spells) {

  // TODO IMPROVE SEVERELY
  val finalStats = getFinalStats

  def addAbility(ability: Ability): Guard = {
    val newAbilities = this.abilities :+ ability
    Guard(race, finalStats, primeStat, equipment, proficiencies, newAbilities, spells)
  }

  def addSpells(spells: Spells): Guard = {
    val newSpells = this.spells.addSpells(spells)
    Guard(race, finalStats, primeStat, equipment, proficiencies, abilities, newSpells)
  }

  def attackModifier: Int = {
    getModifier(primeStat) + finalStats(Guard.StatNames.PROF)
  }

  def getModifier(stat: String): Int = {
    var value: Double = finalStats(stat) - 10
    value = value / 2
    math.floor(value).toInt
  }

  def defaultDamageModifier: Int = {
    getModifier(primeStat)
  }

  def getFinalStats: ListMap[String, Int] = {
    var finalStats = stats
    for(i <- equipment.indices) {
      finalStats = addModifiers(finalStats, equipment(i).modifiers)
    }
    for(i <- abilities.indices) {
      finalStats = addModifiers(finalStats, abilities(i).modifiers)
    }
    finalStats
  }

  /**
    * Create a new Guard with the given stat modifiers
    *
    * @param statModifiers The stat modifiers
    * @return A new Guard
    */
  def addModifiers(stats: ListMap[String, Int], statModifiers: Map[String, Int]): ListMap[String, Int] = {
    val newStats = stats.map((kv: (String, Int)) => {
      if (statModifiers.contains(kv._1))
        (kv._1, kv._2 + statModifiers(kv._1))
      else
        (kv._1, kv._2)
    })
    newStats
  }

  def getStringModifier(stat: String): String = {
    stat match {
      case StatNames.SPEED => ""
      case StatNames.PROF => ""
      case StatNames.AC => ""
      case StatNames.HP => ""
      case _ =>
        val mod = getModifier(stat)
        createModifierString(mod)
    }

  }

  def createModifierString(mod: Int): String = "(" + (if(mod > 0) "+" else "") + mod + ")"

  override def toString: String = {
    "Race: " + race +
    "\nStats:\n" +
      finalStats.map((kv: (String, Int)) => s"${kv._1}: ${kv._2} ${getStringModifier(kv._1)}\n").mkString +
    "\nPrimary stat: " + primeStat +
    "\nAttack modifier: " + createModifierString(attackModifier) +
    "\n\nProficiencies\n" +
      "-" + proficiencies.mkString("\n-") +
    "\n\nEquipment\n" +
      "-" + equipment.mkString("\n-") +
    "\n\nAbilities:\n" +
    "-" + abilities.mkString("\n-") +
    "\n\n" + spells + "\n"
  }

}

object Guard {

  def apply(race: String, stats: ListMap[String, Int], primeStat: String, equipment: List[Equipment], proficiencies: List[String], abilities: List[Ability], spells: Spells): Guard = {
    new Guard(race, stats, primeStat, equipment, proficiencies, abilities, spells)
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