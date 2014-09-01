package DAO

import java.time.{ZonedDateTime, ZoneId, LocalDateTime}
import java.util.Date

import com.mongodb.casbah.commons.MongoDBObject
import dto.IndexRate

import scala.collection.immutable.HashSet


object IndexRateDAO extends DAO {

  def insert(rates: IndexRate*): Unit = {
    require(rates.length >= 0, "An dollar rate is required.")

    val mongoClient = super.getConnection()
    val db = mongoClient.getDB("bolsa")
    val collection = db.getCollection("indice")

    for (rate <- rates) {
      val zdt = rate.date.atZone(ZoneId.systemDefault())
      val date = Date.from(zdt.toInstant())
      val dbo = MongoDBObject("index" -> rate.index, "date" -> date, " value" -> rate.value)
      collection.insert(dbo)
    }
  }

  def getAll(): Set[IndexRate] = {
    val mongoClient = super.getConnection()
    val db = mongoClient.getDB("bolsa")
    val collection = db.getCollection("indice")
    val cursor = collection.find()
    var rates = HashSet[IndexRate]()
    while (cursor.hasNext) {
      val next = cursor.next
      val index = next.get("index").asInstanceOf[String]

      val date = next.get("date").asInstanceOf[Date]
      val ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())

      val value = next.get("value").asInstanceOf[Double]
      rates += new IndexRate(index, ldt, value)
    }

    rates
  }
}
