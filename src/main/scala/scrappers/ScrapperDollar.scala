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

  def getRateByDate(year: Int = 2014, month: Month = Month.JANUARY, day: Int = 1, nDays: Int = 50, nDaysSkip: Int = 0): Array[DollarRate] = {
    var dollarRates = ArrayBuffer[DollarRate]()
    var dateTime = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0, 0).plus(nDaysSkip, ChronoUnit.DAYS)
    for (i <- 0 until nDays) {
      val dataFormatada = dateTime.format(DateTimeFormatter.BASIC_ISO_DATE)
      val url = s"https://finance.yahoo.com/currency/converter-pocket-guide/$dataFormatada/USD/BRL"
      try {
        val doc = Jsoup.parse(new URL(url), 2000)
        val rate = {
          doc.select("td:eq(1)").first().text().replace("R$", "").toDouble
        }
        dollarRates += new DollarRate(dateTime, rate)
      } catch {
        case e: Exception => println(e.getMessage)
      }
      dateTime = dateTime.plus(1, ChronoUnit.DAYS)
    }
    return dollarRates.toArray
  }
}


