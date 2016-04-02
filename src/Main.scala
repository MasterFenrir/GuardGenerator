import dnd.guards.abilities.Spell

/**
  * Created by Sander on 28-3-2016.
  */
object Main {

  def main(args: Array[String]) {
    val testSpell = Spell("SPELLNAME", Spell.Level.FIRST, Spell.Category.ABJURATION,
    "1 action", "120 ft", "Booty", "1 hour", "Does stuff", "Nothing!")
    println(testSpell)
  }
  
}
