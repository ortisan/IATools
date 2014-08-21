import java.net.URL
import java.time.format.DateTimeFormatter
import java.time.temporal.{ChronoUnit, TemporalUnit, TemporalAdjusters}
import java.time._

import org.jsoup.Jsoup

import scala.collection.mutable.ArrayBuffer


/**
 * Created by marcelosantana on 21/08/2014.
 */
object Scrapper extends App {

  def cotacaoDolarEmReal(ano: Int = 2014, mes: Month = Month.JANUARY, dia: Int = 1, qtdDias: Int = 50, pularDias: Int = 0): Array[DataCotacao] = {
    var datasCotacao = ArrayBuffer[DataCotacao]()
    var zona = ZoneId.of("America/Los_Angeles")
    var date = LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0, 0)
    for (i <- pularDias until qtdDias + pularDias) {
      val dataFormatada = date.format(DateTimeFormatter.BASIC_ISO_DATE)
      val url = (s"https://finance.yahoo.com/currency/converter-pocket-guide/$dataFormatada/USD/BRL")
      val doc = Jsoup.parse(new URL(url), 2000)
      var cotacao = {
        doc.select("td:eq(1)").first().text().replace("R$", "").toDouble
      }
      var dataZona = ZonedDateTime.of(date, zona)
      datasCotacao ++= ArrayBuffer(new DataCotacao(dataZona.toEpochSecond(), cotacao))
      date = date.plus(1, ChronoUnit.DAYS)
    }

    return datasCotacao.toArray
  }

  cotacaoDolarEmReal()
}

class DataCotacao(var data: Long, var cotacao: Double)
