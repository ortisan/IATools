package scrappers

import java.net.URL
import java.time._
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

import dto.DollarRate
import org.apache.logging.log4j.LogManager
import org.jsoup.Jsoup

import scala.collection.mutable.ArrayBuffer


/**
 * Created by marcelosantana on 21/08/2014.
 */
object ScrapperDollar {

  private val logger = LogManager.getLogger(ScrapperDollar.getClass)

  def scrap(year: Int = 2014, month: Month = Month.JANUARY, day: Int = 1, nDays: Int = 50, nDaysSkip: Int = 0): Array[DollarRate] = {

    logger.debug(s"Scrapping dolar with params: year = ${year}, month: ${month}, day: ${day}, nDays: ${nDays}, nDaysSkip: ${nDaysSkip}...")

    var dollarRates = ArrayBuffer[DollarRate]()
    var dateTime = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0, 0).plus(nDaysSkip, ChronoUnit.DAYS)
    for (i <- 0 until nDays) {
      val formattedDate = dateTime.format(DateTimeFormatter.BASIC_ISO_DATE)
      val url = s"https://finance.yahoo.com/currency/converter-pocket-guide/$formattedDate/USD/BRL"

      logger.debug(s"with Url: ${url} ...")

      try {
        val doc = Jsoup.parse(new URL(url), 2000)
        val rate = {
          doc.select("td:eq(1)").first().text().replace("R$", "").toDouble
        }
        dollarRates += new DollarRate(dateTime, rate)
      } catch {
        case e: Exception => logger.error("Error scrapping ${url}", e)
      }
      dateTime = dateTime.plus(1, ChronoUnit.DAYS)
    }
    return dollarRates.toArray
  }
}


