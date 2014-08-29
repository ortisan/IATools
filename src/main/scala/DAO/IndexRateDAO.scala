package DAO

import com.mongodb.casbah.commons.MongoDBObject
import dto.IndexRate


object IndexRateDAO extends DAO {

  def insert(rates: IndexRate*): Unit = {
    require(rates.length >= 0, "An dollar rate is required.")

    val mongoClient = super.getConnection()
    val db = mongoClient.getDB("Bolsa")
    val collection = db.getCollection("Indice")

    for (rate <- rates) {
      val dbo = MongoDBObject("index" -> rate.index, "date" -> rate.date, " value" -> rate.value)
      collection.insert(dbo)
    }
  }
}
