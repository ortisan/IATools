package IA

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
 * Created by marceloortizdesantana on 16/08/14.
 *
 * Based on lessons of https://class.coursera.org/ml-006
 */
class LinearRegressionGD(val input: Array[Double], val output: Array[Double], val learningRate: Double = 0.005) {

  var inputs = ArrayBuffer[Array[Double]]()
  var params = ArrayBuffer[Double]()

  for (i <- 0 until input.length) {
    inputs(0)(i) = 1.0
    inputs(1)(i) = input(i)
    params(i) = new Random().nextInt(100).toDouble
  }

  /**
   * Folows the linear equation ( a + bx ) where a = param0, b = param1 and x = input
   * @param input
   * @return value of linear equation.
   */
  private def hipotesis(input: Double*): Double = {
    var hip = params(0)
    for (i <- 1 until params.length) {
      hip += params(i) * input(i - 1)
    }
    return hip
  }

  /**
   * Train the algorithm, minimizing the param0 and param1 of linear formula (param0 + param1 * x)
   */
  def train(callback: () => Unit = () => Unit): Unit = {

    val maxIterations = 5000
    var iteration = 0
    var diffs = 1000.0

    val rows = inputs.length

    do {
      // calculate derivative of cost function
      var derivatives = ArrayBuffer[Double]()

      for {
        iRow <- 0 until inputs.length
        iCol <- 0 until inputs(iRow).length
      } {

        derivatives(iRow) +=



        val diffHip_Out = hipotesis(inputs(i)) - output(i)
        derivative0 += diffHip_Out
        derivative1 += diffHip_Out * inputs(i)
      }

      val temp0 = param0 - learningRate * ((1.0 / m) * derivative0)
      val temp1 = param1 - learningRate * ((1.0 / m) * derivative1)

      println(temp0, temp1)

      diffs = math.abs(param0 - temp0) + math.abs(param1 - temp1)

      param0 = temp0
      param1 = temp1

      iteration += 1

      // call any callback
      callback()

    } while (iteration < maxIterations && diffs > 0.001)
  }

  /**
   * Make the prediction
   * @return value of prediction
   */
  def predict(input: Double): Double = {
    hipotesis(input)
  }
}


