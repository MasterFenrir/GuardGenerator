package dnd.guards.abilities

import scala.collection.mutable

/**
  * Created by Sander on 30-3-2016.
  */
class Spells(spellSlots: List[Int], spells: List[List[String]]) {

  def addSpells(spells: Spells, includeSpellSlots: Boolean): Spells = {
    val newSpellSlots = if (includeSpellSlots) addArrays[Int](this.spellSlots, spells.spellSlots, (x, y) => x + y) else this.spellSlots

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

}

object Spells {
  def apply(spellSlots: List[Int], spells: List[List[String]]): Spells = {
    new Spells(spellSlots, spells)
  }
}