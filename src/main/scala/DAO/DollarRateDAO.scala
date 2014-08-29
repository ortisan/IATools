package DAO

import com.mongodb.casbah.commons.MongoDBObject
import dto.DollarRate

/**
 * Created by marcelosantana on 25/08/2014.
 */
object DollarRateDAO extends DAO {

  def insert(rates: DollarRate*): Unit = {
    require(rates.length >= 0, "An index rate is required.")

    val mongoClient = super.getConnection()
    val db = mongoClient.getDB("Bolsa")
    val collection = db.getCollection("Dolar")

    for (rate <- rates) {
      val dbo = MongoDBObject("date" -> rate.date, "value" -> rate.value)
      collection.insert(dbo)
    }
  }
}
