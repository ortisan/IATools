package dao

import java.time.LocalDateTime

import DAO.DollarRateDAO
import dto.DollarRate
import org.scalatest.FlatSpec

/**
 * Created by marcelosantana on 25/08/2014.
 */
class TestDAO extends FlatSpec {

  "A DollarRateDAO" should "insert and retrieve a rate on mongodb" in {
    val dollarRateDAO = DollarRateDAO
    val dollarRate = new DollarRate(LocalDateTime.now(), 20.0)

    dollarRateDAO.insert(dollarRate)

  }

}