package scrappers

import java.net.URL
import java.time._
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

import dto.DollarRate
import org.jsoup.Jsoup

import scala.collection.mutable.ArrayBuffer


/**
 * Created by marcelosantana on 21/08/2014.
 */
object ScrapperDollar extends App {

  def getRateByDate(ano: Int = 2014, mes: Month = Month.JANUARY, dia: Int = 1, qtdDias: Int = 50, pularDias: Int = 0): Array[DollarRate] = {
    var dollarRates = ArrayBuffer[DollarRate]()
    var dateTime = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0, 0).plus(pularDias, ChronoUnit.DAYS)
    for (i <- 0 until qtdDias) {
      val dataFormatada = dateTime.format(DateTimeFormatter.BASIC_ISO_DATE)
      val url = s"https://finance.yahoo.com/currency/converter-pocket-guide/$dataFormatada/USD/BRL"
      val doc = Jsoup.parse(new URL(url), 2000)
      var rate = {
        doc.select("td:eq(1)").first().text().replace("R$", "").toDouble
      }
      dollarRates += new DollarRate(dateTime, rate)
      dateTime = dateTime.plus(1, ChronoUnit.DAYS)
    }
    return dollarRates.toArray
  }
}


