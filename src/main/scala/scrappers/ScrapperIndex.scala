package scrappers

import java.net.URL
import java.time.temporal.ChronoUnit
import java.time.{LocalDateTime, Month}

import dto.IndexRate
import org.jsoup.Jsoup

import scala.collection.mutable.ArrayBuffer

/**
 * Created by marcelosantana on 29/08/2014.
 */
object ScrapperIndex {

  def scrap(index: String, year: Int = 2014, month: Int = 1, day: Int = 1, nDays: Int = 50): Array[IndexRate] = {

    var dateTime = LocalDateTime.of(2014, Month.of(month), day, 0, 0, 0)
    val indexRates = ArrayBuffer[IndexRate]()

    for (i <- 0 until nDays) {
      val url = s"https://finance.yahoo.com/q/hp?s=%5${index}&a=${dateTime.getMonthValue - 1}&b=${dateTime.getDayOfMonth}&c=${dateTime.getYear}&d=${dateTime.getMonthValue - 1}&e=${dateTime.getDayOfMonth}&f=${dateTime.getYear}&g=d"
      try {
        val doc = Jsoup.parse(new URL(url), 3000)
        val elements = doc.select("td.yfnc_tabledata1")
        if (elements.size() > 0) {
          val date = elements.eq(0).text()
          val open = elements.eq(1).text().replace(",", "").toDouble
          val max = elements.eq(2).text().replace(",", "").toDouble
          val min = elements.eq(3).text().replace(",", "").toDouble
          val close = elements.eq(4).text().replace(",", "").toDouble
          val volume = elements.eq(5).text().replace(",", "").toDouble
          val adjClose = elements.eq(6).text().replace(",", "").toDouble

          indexRates += new IndexRate(index, dateTime, close)

        }
      } catch {
        case e: Exception => println(e.getMessage)
      }

      dateTime = dateTime.plus(1, ChronoUnit.DAYS)
    }
    indexRates.toArray
  }
}