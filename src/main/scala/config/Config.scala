package config

import scala.collection.immutable.HashMap

/**
 * Created by marcelosantana on 01/09/2014.
 */
object Config {

  val database = Map("host" -> "localhost", "port" -> 27017)
  var indexes = Array("EBVSP", "EDJI", "EIXIC", "EHSI", "EN225", "EFTSE", "EGDAXI")
}
