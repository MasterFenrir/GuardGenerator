package dnd.guards.builder

import dnd.guards.Guard
import dnd.guards.abilities.Equipment

import scala.util.Random

/**
  * Created by sander on 4/3/16.
  */
object EquipmentBuilder {

  val equipmentMap = Map(
    GuardBuilder.Races.HUMAN -> eqList(Weapons.SWORD_SHIELD, Weapons.SPEAR, Weapons.CROSSBOW),
    GuardBuilder.Races.DWARF -> eqList(Weapons.SWORD_SHIELD, Weapons.SPEAR, Weapons.HALBERD, Weapons.CROSSBOW),
    GuardBuilder.Races.WARFORGED -> eqList(Weapons.WAR_HAMMER, Weapons.MORNING_STAR, Weapons.CROSSBOW),
    GuardBuilder.Races.ELF -> eqList(Weapons.RAPIER_SHIELD, Weapons.CROSSBOW),
    GuardBuilder.Races.HALF_ELF -> eqList(Weapons.RAPIER_SHIELD, Weapons.CROSSBOW),
    GuardBuilder.Races.DRAGONBORN -> eqList(Weapons.BATTLE_AXE_SHIELD, Weapons.SPEAR, Weapons.CROSSBOW),
    GuardBuilder.Races.HALF_ORC -> eqList(Weapons.GREAT_AXE, Weapons.MORNING_STAR, Weapons.CROSSBOW),
    GuardBuilder.Races.TIEFLING -> eqList(Weapons.SWORD_SHIELD, Weapons.SPEAR, Weapons.CROSSBOW)
  ).withDefaultValue(eqList(Weapons.SWORD_SHIELD, Weapons.SPEAR, Weapons.CROSSBOW))

  def apply(race: String): Equipment = {
    val i = Random.nextInt(equipmentMap(race).length)
    equipmentMap(race)(i)
  }

  private def eqList(equipments: Equipment*): List[Equipment] = {
    equipments.toList
  }

  object Weapons {
    val SWORD_SHIELD = Equipment("Sword & Shield:\n1D6 + stat modifier slashing damage", Map[String, Int](Guard.StatNames.AC -> 1))
    val SPEAR = Equipment("Spear\nThrowable, 20/60 ft., 1D6 + stat modifier\nMelee attack 1D8 + stat modifier", Map[String, Int]())
    val CROSSBOW = Equipment("Crossbow:\n80/320 ft. range, 1D8 + stat modifier piercing damage", Map[String, Int]())
    val HALBERD = Equipment("Halberd:\n1D10 + stat modifier slashing damage", Map[String, Int]())
    val WAR_HAMMER = Equipment("Warhammer:\n1D10 + stat modifier piercing damage", Map[String, Int]())
    val MORNING_STAR = Equipment("Morningstar:\n1D8 + stat modifier piercing damage", Map[String, Int]())
    val RAPIER_SHIELD = Equipment("Rapier & Shield:\n1D8 + stat modifier piercing damage", Map[String, Int](Guard.StatNames.AC -> 1))
    val BATTLE_AXE_SHIELD = Equipment("Battleaxe & Shield:\n1D8 + stat modifier slashing damage", Map[String, Int]())
    val GREAT_AXE = Equipment("Greataxe\n1D12 + stat modifier slashing damage", Map[String, Int]())
  }

}
