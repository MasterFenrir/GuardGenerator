package dnd.guards.builder

import dnd.guards.Guard
import dnd.guards.abilities.{Ability, Equipment, Spell, Spells}

import scala.collection.immutable.ListMap
import scala.collection.mutable
import scala.util.Random

/**
  * Created by Sander on 30-3-2016.
  */
object GuardBuilder {

  def apply(count: Int, tieflingWarforged: Boolean, leader: Boolean): List[Guard] = {
    var meleeChance = 70

    var guardList = mutable.MutableList[Guard]()

    for(i <- 1 to count){
      val race = getRace(tieflingWarforged)
      var stats = ListMap[String, Int]()
      var primeStat = ""
      var equipment = List[Equipment]()
      var proficiencies = List[String]()
      var abilities = getAbilities(race)
      if(Random.nextInt(100) < meleeChance) {
        meleeChance = meleeChance - ((1/count) * 100)
        stats = Stats.melee(race)

        if (race == Races.ELF) {
          primeStat = Guard.StatNames.DEX
          abilities = abilities :+ Abilities.SNEAK_ATTACK
          proficiencies = List(Proficiencies.PERCEPTION, Proficiencies.ACROBATICS, Proficiencies.INVESTIGATION, Proficiencies.DEXTERITY, Proficiencies.CONSTITUTION)
        } else if (race == Races.HALF_ELF) {
          primeStat = Guard.StatNames.DEX
          abilities = abilities :+ Abilities.SNEAK_ATTACK
          proficiencies = List(Proficiencies.PERCEPTION, Proficiencies.PERSUASION, Proficiencies.INTIMIDATION, Proficiencies.CHARISMA, Proficiencies.CONSTITUTION)
        } else {
          primeStat = Guard.StatNames.STR
          proficiencies = List(Proficiencies.PERCEPTION, Proficiencies.ATHLETICS, Proficiencies.INTIMIDATION_STRENGTH, Proficiencies.STRENGTH, Proficiencies.CONSTITUTION)
        }

        equipment = equipment :+ EquipmentBuilder(race)
        equipment = equipment :+ EquipmentBuilder.Weapons.CROSSBOW

        var spells = Spells(List[Int](), List[List[Spell]]())

        if (Random.nextInt(10) == 1) {
          abilities = abilities :+ Abilities.SMITE
          spells = ClassSpells.paladin
        }

        guardList = guardList :+ Guard(race, stats, primeStat, equipment, proficiencies, abilities, spells)

      } else {
        meleeChance = meleeChance + ((1/count) * 100)
        val casterType = Random.nextInt(3)

        if (casterType == 0) {
          primeStat =  Guard.StatNames.WIS
          stats = Stats.Caster.wis(race)
          val classNr = Random.nextInt(2)

          val spells = if (classNr == 0) ClassSpells.cleric else ClassSpells.druid
          proficiencies = List(Proficiencies.PERCEPTION, Proficiencies.INSIGHT, Proficiencies.MEDICINE, Proficiencies.WISDOM, Proficiencies.CONSTITUTION)
          guardList = guardList :+ Guard(race, stats, primeStat, equipment, proficiencies, abilities, spells)
        } else if (casterType == 1) {
          primeStat =  Guard.StatNames.INT
          stats = Stats.Caster.int(race)
          val spells = ClassSpells.wizard

          proficiencies = List(Proficiencies.PERCEPTION, Proficiencies.INVESTIGATION, Proficiencies.ARCANA, Proficiencies.INTELLIGENCE, Proficiencies.DEXTERITY)
          guardList = guardList :+ Guard(race, stats, primeStat, equipment, proficiencies, abilities, spells)
        } else {
          primeStat =  Guard.StatNames.CHA
          stats = Stats.Caster.cha(race)
          val classNr = Random.nextInt(3)

          val spells = if (classNr == 0) ClassSpells.bard else if (classNr == 1) ClassSpells.sorcerer else ClassSpells.warlock
          proficiencies = List(Proficiencies.PERCEPTION, Proficiencies.PERSUASION, Proficiencies.ARCANA, Proficiencies.CHARISMA, Proficiencies.CONSTITUTION)
          guardList = guardList :+ Guard(race, stats, primeStat, equipment, proficiencies, abilities, spells)
        }
      }
    }

    if(leader) {
      val index = Random.nextInt(guardList.length)
      guardList(index) = guardList(index).addAbility(Abilities.LEADER)
    }

    guardList.toList
  }

  def getAbilities(race: String): List[Ability] = {
    race match {
      case Races.ELF => List(Abilities.FROSTBITE_CANTRIP)
      case Races.HALF_ORC => List(Abilities.RAGE, Abilities.ENDURANCE)
      case Races.DRAGONBORN => List(Abilities.DRAGON)
      case Races.TIEFLING => List(Abilities.HELLISH_REBUKE, Abilities.THAUMATURGY)
      case _ => List[Ability]()
    }
  }

  def getRace(tieflingWarforged: Boolean): String = {
    if(tieflingWarforged) {
      randomRacesTieflingWarforged(Random.nextInt(randomRacesTieflingWarforged.length))
    } else {
      randomRacesDefault(Random.nextInt(randomRacesDefault.length))
    }
  }

  val randomRacesDefault = List(Races.HUMAN, Races.DWARF, Races.ELF, Races.HALF_ELF, Races.DRAGONBORN, Races.HALF_ORC)
  val randomRacesTieflingWarforged = List(Races.HUMAN, Races.DWARF, Races.ELF, Races.HALF_ELF, Races.DRAGONBORN,
    Races.HALF_ORC, Races.TIEFLING, Races.WARFORGED, Races.TIEFLING, Races.WARFORGED, Races.TIEFLING, Races.WARFORGED)

  object Races {
    val HUMAN = "Human"
    val DWARF = "Dwarf"
    val ELF = "Elf"
    val HALF_ELF = "Half-Elf"
    val DRAGONBORN = "Dragonborn"
    val HALF_ORC = "Half-Orc"
    val TIEFLING = "Tiefling"
    val WARFORGED = "Warforged"
  }

  object Stats {

    val melee = ListMap(
      GuardBuilder.Races.HUMAN -> statMap(16, 14, 14, 10, 12, 12, 16, 25, 30, 2),
      GuardBuilder.Races.DWARF -> statMap(16, 14, 16, 10, 10, 10, 16, 30, 25, 2),
      GuardBuilder.Races.WARFORGED -> statMap(14, 14, 16, 10, 12, 8, 17, 22, 30, 2),
      GuardBuilder.Races.ELF -> statMap(8, 16, 14, 16, 10, 10, 16, 30, 22, 2),
      GuardBuilder.Races.HALF_ELF -> statMap(8, 16, 14, 12, 12, 14, 16, 30, 22, 2),
      GuardBuilder.Races.DRAGONBORN -> statMap(16, 14, 14, 9, 10, 12, 16, 30, 22, 2),
      GuardBuilder.Races.HALF_ORC -> statMap(14, 14, 14, 11, 10, 10, 16, 30, 25, 2),
      GuardBuilder.Races.TIEFLING -> statMap(16, 14, 14, 10, 12, 10, 16, 30, 22, 2)
    )

    private def statMap(str: Int, dex: Int, con: Int,
                        int: Int, wis: Int, cha: Int,
                        ac: Int, hp: Int, speed: Int,
                        prof: Int): ListMap[String, Int] = {
      ListMap(
        Guard.StatNames.AC -> ac,
        Guard.StatNames.HP -> hp,
        Guard.StatNames.SPEED -> speed,
        Guard.StatNames.PROF -> prof,
        Guard.StatNames.STR -> str,
        Guard.StatNames.DEX -> dex,
        Guard.StatNames.CON -> con,
        Guard.StatNames.INT -> int,
        Guard.StatNames.WIS -> wis,
        Guard.StatNames.CHA -> cha
      )
    }


    object Caster {

      val wis = ListMap(
        GuardBuilder.Races.HUMAN -> statMap(10, 14, 14, 12, 16, 12, 13, 20, 30, 2),
        GuardBuilder.Races.DWARF -> statMap(10, 12, 14, 12, 16, 12, 13, 25, 25, 2),
        GuardBuilder.Races.WARFORGED -> statMap(10, 12, 14, 10, 14, 14, 14, 20, 30, 2),
        GuardBuilder.Races.ELF -> statMap(8, 14, 13, 10, 16, 14, 13, 20, 30, 2),
        GuardBuilder.Races.HALF_ELF -> statMap(8, 14, 14, 12, 16, 12, 13, 20, 30, 2),
        GuardBuilder.Races.DRAGONBORN -> statMap(10, 14, 13, 13, 14, 12, 13, 22, 30, 2),
        GuardBuilder.Races.HALF_ORC -> statMap(10, 14, 14, 12, 14, 12, 13, 22, 30, 2),
        GuardBuilder.Races.TIEFLING -> statMap(8, 14, 14, 11, 14, 14, 13, 20, 30, 2)
      )

      val int = ListMap(
        GuardBuilder.Races.HUMAN -> statMap(10, 14, 14, 16, 12, 12, 13, 20, 30, 2),
        GuardBuilder.Races.DWARF -> statMap(10, 14, 16, 14, 12, 12, 13, 28, 25, 2),
        GuardBuilder.Races.WARFORGED -> statMap(10, 12, 14, 14, 10, 14, 14, 20, 30, 2),
        GuardBuilder.Races.ELF -> statMap(8, 14, 14, 16, 12, 11, 13, 20, 30, 2),
        GuardBuilder.Races.HALF_ELF -> statMap(8, 14, 14, 16, 12, 12, 13, 20, 30, 2),
        GuardBuilder.Races.DRAGONBORN -> statMap(10, 14, 13, 14, 13, 12, 13, 22, 30, 2),
        GuardBuilder.Races.HALF_ORC -> statMap(10, 14, 14, 14, 12, 12, 13, 22, 30, 2),
        GuardBuilder.Races.TIEFLING -> statMap(8, 14, 14, 14, 11, 14, 13, 20, 30, 2)
      )

      val cha = ListMap(
        GuardBuilder.Races.HUMAN -> statMap(10, 14, 14, 12, 13, 16, 13, 20, 30, 2),
        GuardBuilder.Races.DWARF -> statMap(10, 14, 16, 12, 12, 14, 13, 25, 25, 2),
        GuardBuilder.Races.WARFORGED -> statMap(10, 12, 14, 14, 10, 14, 14, 20, 30, 2),
        GuardBuilder.Races.ELF -> statMap(10, 14, 14, 12, 12, 14, 13, 20, 30, 2),
        GuardBuilder.Races.HALF_ELF -> statMap(9, 14, 14, 12, 12, 16, 13, 20, 30, 2),
        GuardBuilder.Races.DRAGONBORN -> statMap(10, 14, 14, 10, 10, 16, 13, 20, 30, 2),
        GuardBuilder.Races.HALF_ORC -> statMap(10, 14, 14, 12, 12, 14, 13, 22, 30, 2),
        GuardBuilder.Races.TIEFLING -> statMap(8, 14, 14, 11, 12, 16, 13, 20, 30, 2)
      )

    }

  }

  object ClassSpells {

    private val defaultSpellSlots = List[Int](4, 2)
    private val warlockSpellSlots = List[Int](0, 2)
    private val paladinSpellSlots = List[Int](3)

    private val bardSpellList = List[List[Spell]](
      List[Spell](SpellList.VICIOUS_MOCKERY, SpellList.THUNDERCLAP),
      List[Spell](SpellList.HEALING_WORD, SpellList.FEARIE_FIRE),
      List[Spell](SpellList.HOLD_PERSON, SpellList.PHANTASMAL_FORCE)
    )

    private val sorcererSpellList = List[List[Spell]](
      List[Spell](SpellList.FIRE_BOLT, SpellList.FROSTBITE, SpellList.POISON_SPRAY, SpellList.MINOR_ILLUSION),
      List[Spell](SpellList.CHROMATIC_ORB, SpellList.MAGIC_MISSILE),
      List[Spell](SpellList.MIRROR_IMAGE, SpellList.SCORCHING_RAY)
    )

    private val warlockSpellList = List[List[Spell]](
      List[Spell](SpellList.ELDRITCH_BLAST, SpellList.BLADE_WARD),
      List[Spell](SpellList.HEX),
      List[Spell](SpellList.SPIDER_CLIMB, SpellList.SUGGESTION)
    )

    private val paladinSpellList = List[List[Spell]](
      List[Spell](SpellList.DIVINE_FAVOR, SpellList.SEARING_SMITE, SpellList.WRATHFUL_SMITE)
    )

    private val druidSpellList = List[List[Spell]](
      List[Spell](SpellList.DRUIDCRAFT, SpellList.SHILLELAGH),
      List[Spell](SpellList.CURE_WOUNDS, SpellList.ENTANGLE),
      List[Spell](SpellList.MOONBEAM, SpellList.PASS_WITHOUT_TRACE)
    )

    private val clericSpellList = List[List[Spell]](
      List[Spell](SpellList.SPARE_THE_DYING, SpellList.SACRED_FLAME),
      List[Spell](SpellList.INFLICT_WOUNDS, SpellList.HEALING_WORD, SpellList.SHIELD_OF_FAITH),
      List[Spell](SpellList.SPIRITUAL_WEAPON)
    )

    private val wizardSpellList = List[List[Spell]](
      List[Spell](SpellList.RAY_OF_FROST, SpellList.POISON_SPRAY, SpellList.MENDING),
      List[Spell](SpellList.TASHAS_HIDEOUS_LAUGHTER, SpellList.ENLARGE_REDUCE, SpellList.SHIELD),
      List[Spell](SpellList.SHATTER, SpellList.WEB)
    )

    val bard = Spells(defaultSpellSlots, bardSpellList)
    val sorcerer = Spells(defaultSpellSlots, sorcererSpellList)
    val warlock = Spells(warlockSpellSlots, warlockSpellList)
    val paladin = Spells(paladinSpellSlots, paladinSpellList)
    val druid = Spells(defaultSpellSlots, druidSpellList)
    val cleric = Spells(defaultSpellSlots, clericSpellList)
    val wizard = Spells(defaultSpellSlots, wizardSpellList)

    object SpellList {
      val VICIOUS_MOCKERY = Spell("Vicious Mockery", 0)
      val THUNDERCLAP = Spell("Thunderclap", 0)
      val HEALING_WORD = Spell("Healing Word", 1)
      val FEARIE_FIRE = Spell("Fearie Fire", 1)
      val HOLD_PERSON = Spell("Hold Person", 2)
      val PHANTASMAL_FORCE = Spell("Phantasmal Force", 2)
      val FIRE_BOLT = Spell("Fire Bolt", 0)
      val FROSTBITE = Spell("Frostbite", 0)
      val POISON_SPRAY = Spell("Poison Spray", 0)
      val MINOR_ILLUSION = Spell("Minor Illusion", 0)
      val CHROMATIC_ORB = Spell("Chromatic Orb", 1)
      val MAGIC_MISSILE = Spell("Magic Missile", 1)
      val MIRROR_IMAGE = Spell("Mirror Image", 2)
      val SCORCHING_RAY = Spell("Scorching Ray", 2)
      val ELDRITCH_BLAST = Spell("Eldritch Blast, plus Charisma and pushback", 0)
      val BLADE_WARD = Spell("Blade Ward", 0)
      val HEX = Spell("Hex", 1)
      val SPIDER_CLIMB = Spell("Spider Climb", 2)
      val SUGGESTION = Spell("Suggestion", 2)
      val DIVINE_FAVOR = Spell("Divine Favor", 1)
      val SEARING_SMITE = Spell("Searing Smite", 1)
      val WRATHFUL_SMITE = Spell("Wrathful Smite", 1)
      val DRUIDCRAFT = Spell("Druidcraft", 0)
      val SHILLELAGH = Spell("Shillelagh", 0)
      val CURE_WOUNDS = Spell("Cure Wounds", 1)
      val ENTANGLE = Spell("Entangle", 1)
      val MOONBEAM = Spell("Moonbeam", 2)
      val PASS_WITHOUT_TRACE = Spell("Pass without trace", 2)
      val SPARE_THE_DYING = Spell("Spare the Dying", 0)
      val SACRED_FLAME = Spell("Sacred Flame", 0)
      val INFLICT_WOUNDS = Spell("Inflict Wounds", 1)
      val SHIELD_OF_FAITH = Spell("Shield of Faith", 1)
      val SPIRITUAL_WEAPON = Spell("Spiritual Weapon", 2)
      val RAY_OF_FROST = Spell("Ray of Frost", 0)
      val MENDING = Spell("Mending", 0)
      val TASHAS_HIDEOUS_LAUGHTER = Spell("Tasha's Hideous Laughter", 1)
      val ENLARGE_REDUCE = Spell("Enlarge/Reduce", 1)
      val SHIELD = Spell("Shield", 1)
      val SHATTER = Spell("Shatter", 2)
      val WEB = Spell("Web", 2)
    }

  }

  object Abilities {
    val SMITE = Ability("Smite, 2D8 per expended first level spellslot")
    val RAGE = Ability("Can activate Rage")
    val ENDURANCE = Ability("Can drop to 1 hitpoint instead of 0 when not killed outright")
    val THAUMATURGY = Ability("Can cast the Thaumaturgy cantrip")
    val HELLISH_REBUKE = Ability("Can cast Hellish Rebuke as a second level spell once per day")
    val DRAGON = Ability("Has elemental resistance and a breath weapon of 2D6 damage")
    val SNEAK_ATTACK = Ability("Can sneak attack for 2D6 damage")
    val FROSTBITE_CANTRIP = Ability("Can cast the cantrip Frostbite, with a DC of 13")
    val LEADER = Ability("Commander's Strike & Inspire (1D8)", Map[String, Int](Guard.StatNames.CHA -> 2))
  }

  object Proficiencies {
    val PERCEPTION = "Perception"
    val ATHLETICS = "Atlethics"
    val INTIMIDATION = "Intimidation"
    val INTIMIDATION_STRENGTH = "Intimidation (on Strength)"
    val STRENGTH = "Strength saving throws"
    val CONSTITUTION = "Constitution saving throws"
    val ACROBATICS = "Acrobatics"
    val INVESTIGATION = "Investigation"
    val DEXTERITY = "Dexterity saving throws"
    val PERSUASION = "Persuasion"
    val CHARISMA = "Charisma saving throws"
    val ARCANA = "Arcana"
    val INSIGHT = "Insight"
    val MEDICINE = "Medicine"
    val WISDOM = "Wisdom saving throws"
    val INTELLIGENCE = "Intelligence saving throws"
  }

}