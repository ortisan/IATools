package testing

import java.io.{File, PrintWriter}

import dto.DollarRate
import scrappers.ScrapperDollar

/**
 * Created by marcelosantana on 29/08/2014.
 */
object Tools extends App {

  val dollarsRate = ScrapperDollar.getRateByDate(ano = 2013, qtdDias = 300)
  val writer = new PrintWriter(new File("c:/temp/dollar.csv"))
  writer.write("Data" + "," + "Valor" + ";\n")
  dollarsRate.foreach((dollarRate: DollarRate) => writer.write(dollarRate.date + "," + dollarRate.value + ";\n"))
  writer.close()

}
