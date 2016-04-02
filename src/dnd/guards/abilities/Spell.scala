package dnd.guards.abilities

/**
  * Created by sander on 4/2/16.
  */
class Spell(name: String, level: Int, school: String,
            castingTime: String, range: String,
            components: String, duration: String,
            description: String, higherLevels: String) {

  override def toString: String = {
    s"Spell: $name\n" +
      s"Level: $level\n" +
      s"School: $school\n" +
      s"Casting time: $castingTime\n" +
      s"Range: $range\n" +
      s"Components: $components\n" +
      s"Duration: $duration\n" +
      s"Description: $description\n" +
      s"At higher levels: $higherLevels"
  }

}

object Spell {

  object Level {
    val CANTRIP = 0
    val FIRST = 1
    val SECOND = 2
    val THIRD = 3
    val FOURTH = 4
    val FIFTH = 5
    val SIXTH = 6
    val SEVENTH = 7
    val EIGHT = 8
    val NINTH = 9
  }

  object Category {
    val CONJURATION = "Conjuration"
    val ABJURATION = "Abjuration"
    val TRANSMUTATION = "Transmutation"
    val DIVINATION = "Divination"
    val EVOCATION = "Evocation"
    val NECROMANCY = "Necromancy"
    val ENCHANTMENT = "Enchantment"
    val ILLUSION = "Illusion"
  }

  def apply(name: String, level: Int, category: String,
            castingTime: String, range: String,
            components: String, duration: String,
            description: String, higherLevels: String) {
    new Spell(name, level, category, castingTime, range,
      components, duration, description, higherLevels)
  }
}