package DAO

import java.time.{ZoneId, LocalDateTime}
import java.util.Date

import com.mongodb.casbah.commons.MongoDBObject
import dto.DollarRate

import scala.collection.immutable.HashSet

/**
 *
 * Created by marcelosantana on 25/08/2014.
 */
object DollarRateDAO extends DAO {

  def insert(rates: DollarRate*): Unit = {
    require(rates.length >= 0, "An index rate is required.")

    val mongoClient = super.getConnection()
    val db = mongoClient.getDB("bolsa")
    val collection = db.getCollection("dolar")

    for (rate <- rates) {
      val zdt = rate.date.atZone(ZoneId.systemDefault())
      val date = Date.from(zdt.toInstant())

      val dbo = MongoDBObject("date" -> date, "value" -> rate.value)
      collection.insert(dbo)
    }
  }

  def getAll(): Set[DollarRate] = {
    val mongoClient = super.getConnection()
    val db = mongoClient.getDB("bolsa")
    val collection = db.getCollection("dolar")
    val cursor = collection.find()
    var rates = HashSet[DollarRate]()
    while (cursor.hasNext) {
      val next = cursor.next
      val date = next.get("date").asInstanceOf[Date]
      val ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())

      val value = next.get("value").asInstanceOf[Double]
      rates += new DollarRate(ldt, value)
    }
    rates
  }
}
