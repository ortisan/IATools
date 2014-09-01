package DAO

import com.mongodb.casbah.MongoClient
import config.Config

/**
 * Created by marcelosantana on 25/08/2014.
 */
class DAO() {
  def getConnection(host: String = "localhost", port: Int = 27017): MongoClient = MongoClient(Config.database.get("host").asInstanceOf[String], Config.database.get("port").asInstanceOf[Int])
}
