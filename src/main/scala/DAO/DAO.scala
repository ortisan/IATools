package DAO

import com.mongodb.casbah.MongoClient

/**
 * Created by marcelosantana on 25/08/2014.
 */
class DAO() {
  def getConnection(host: String = "localhost", port: Int = 27017): MongoClient = MongoClient(host, port)
}
