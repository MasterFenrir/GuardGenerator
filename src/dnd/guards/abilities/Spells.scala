package dnd.guards.abilities

import scala.collection.mutable

/**
  * Created by Sander on 30-3-2016.
  *
  * Class to hold spells and spellslots
  */
class Spells(val spellSlots: List[Int], val spells: List[List[Spell]]) {

  /**
    * Add more spells to this object
    *
    * @param spellsToAdd       The spells to add
    * @param includeSpellSlots If spellslots need to be included or not (Default is false)
    * @return A new Spells object containing all the spells
    */
  def addSpells(spellsToAdd: Spells, includeSpellSlots: Boolean = false): Spells = {
    val newSpellSlots = if (includeSpellSlots) addArrays[Int](this.spellSlots, spellsToAdd.spellSlots, (x, y) => x + y) else this.spellSlots
    val newSpells = addArrays[List[Spell]](this.spells, spellsToAdd.spells, (l1, l2) => l1 ++ l2)
    Spells(newSpellSlots, newSpells)
  }


  private def addArrays[K](list1: List[K], list2: List[K], adder: (K, K) => K): List[K] = {
    val large = if (list1.length > list2.length) list1 else list2
    val small = if (list1.length <= list2.length) list1 else list2

    val newArray = mutable.MutableList[K]()
    for (i <- 0 to small.length) {
      newArray(i) = adder(large(i), small(i))
    }

    newArray.toList ++ large.slice(small.length, large.length)
  }

  override def toString: String = {
    var resultString = "Spellslots:"
    for (i <- spellSlots.indices) {
      resultString = s"$resultString ${i + 1}:${spellSlots(i)}"
    }
    resultString = s"$resultString\nSpells:\n"
    for (i <- spells.indices) {
      resultString = s"$resultString${i + 1}: ${spells(i).mkString(", ")}\n"
    }
    resultString
  }

}

object Spells {
  def apply(spellSlots: List[Int], spells: List[List[Spell]]): Spells = {
    new Spells(spellSlots, spells)
  }

  def apply(): Spells = {
    new Spells(List[Int](), List[List[Spell]]())
  }
}