import java.io.{File, PrintWriter}
import java.text.SimpleDateFormat
import java.util.Calendar

import dnd.guards.builder.GuardBuilder

/**
  * Created by Sander on 28-3-2016.
  */
object Main {

  def main(args: Array[String]) {
    if (args.length > 3) {
      println("Too many arguments!")
    }

    try {
      var tieflingWarforged = false
      var leader = false
      val count = args(0).toInt

      for (i <- 0 until args.length) {
        args(i) match {
          case "l" | "L" => leader = true
          case "tw" | "TW" => tieflingWarforged = true
          case _ =>
        }
      }
      val guardList = GuardBuilder(count, tieflingWarforged, leader)

      val format = new SimpleDateFormat("d-M-y HH-mm-ss")
      val timestamp = format.format(Calendar.getInstance().getTime)

      for (i <- 0 until guardList.length) {
        val pw = new PrintWriter(new File(s"$timestamp (${i+1}) ${guardList(i).race} Guard.txt"))
        pw.write(guardList(i).toString)
        pw.close()
      }

    } catch {
      case ex: Exception => println(ex) //println("Failure! Are you sure you passed the correct arguments? The count goes first!")
    }
  }
}
