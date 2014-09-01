package main

import java.io.{File, PrintWriter}

import config.Config
import dto.{DollarRate, IndexRate}
import scrappers.{ScrapperDollar, ScrapperIndex}

/**
 * Created by marcelosantana on 29/08/2014.
 */
object Exporters extends App {

  def exportDollar(): Unit = {
    val dollarsRate = ScrapperDollar.scrap(year = 2013, nDays = 300)
    val file = new File("c:/temp/dollar.csv")
    if (file.exists()) {
      file.delete()
    }
    val writer = new PrintWriter(file)
    writer.write("Data" + "," + "Valor" + ";\n")
    dollarsRate.foreach((dollarRate: DollarRate) => writer.write(dollarRate.date + "," + dollarRate.value + ";\n"))
    writer.close()
  }

  def exportIndexes(): Unit = {
    var indexRates = Array[IndexRate]()

    for (index <- Config.indexes) {
      indexRates ++= ScrapperIndex.scrap(index, year = 2013, nDays = 300)
    }
    val file = new File("c:/temp/indexes.csv")
    if (file.exists()) {
      file.delete()
    }
    val writer = new PrintWriter(file)
    writer.write("Indice" + "," + "Data" + "," + "Valor" + ";\n")

    indexRates.foreach((indexRate: IndexRate) => writer.write(indexRate.index + "," + indexRate.date + "," + indexRate.value + ";\n"))
    writer.close()
  }

  exportDollar()
  exportIndexes()
}
