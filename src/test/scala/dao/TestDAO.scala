package dao

import java.time.LocalDateTime

import DAO.{DollarRateDAO, IndexRateDAO}
import dto.{DollarRate, IndexRate}
import org.scalatest.FunSuite

/**
 * Created by marcelosantana on 25/08/2014.
 */
class TestDAO extends FunSuite {

  test("A DollarRateDAO should insert and retrieve a rate on mongodb") {
    val dollarRateDAO = DollarRateDAO
    val dollarRate = new DollarRate(LocalDateTime.now(), 20.0)
    dollarRateDAO.insert(dollarRate)

    val all = dollarRateDAO.getAll()
    assert(all.size > 0)
  }

  test("A IndexRateDAO should insert and retrieve a rate on mongodb") {
    val indexRateDAO = IndexRateDAO
    val indexRate = new IndexRate("ABC", LocalDateTime.now(), 200.0)
    indexRateDAO.insert(indexRate)

    val all = indexRateDAO.getAll()
    assert(all.size > 0)
  }

}