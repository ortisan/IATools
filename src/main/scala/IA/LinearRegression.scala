package IA

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
 * Created by marceloortizdesantana on 16/08/14.
 *
 * Based on lessons of https://class.coursera.org/ml-006
 */
class LinearRegression(val input: Array[Double], val output: Array[Double], val learningRate: Double = 0.005) {

  var inputs = ArrayBuffer[Array[Double]]()
  var params = ArrayBuffer[Double]()

  for (i <- 0 until input.length) {
    inputs(0) = Array(1.0)
    inputs(1) = Array(input(i))
    params(i) = new Random().nextInt(100).toDouble
  }

  /**
   * Folows the linear equation ( a + bx ) where a = params(0), b = params(1) and x = input
   * @param input
   * @return value of linear equation.
   */
  private def hipotesis(input: Double): Double = {
    hipotesis(Array(input))
  }

  private def hipotesis(input: Array[Double]): Double = {
    var hip = 0.0
    var inputs = ArrayBuffer[Double](1.0) ++= input
    for (i <- 0 until params.length) {
      hip += params(i) * input(i)
    }
    hip
  }

  /** d
    * Train the algorithm, minimizing the param0 and param1 of linear formula (param0 + param1 * x)
    */
  def train(callback: () => Unit = () => Unit): Unit = {

    val maxIterations = 5000
    var iteration = 0
    var diffs = 1000.0

    val rows = inputs.length


    do {
      var derivatives = ArrayBuffer[Double]()
      // calculate derivative of cost function
      for {
        iRow <- 0 until inputs.length
        iCol <- 0 until inputs(iRow).length
      } {
        val diffHip_Out = hipotesis(inputs(iRow)) - output(iRow)
        derivatives(iRow) += diffHip_Out * inputs(iRow)(iCol)
      }

      val temps = ArrayBuffer[Double]()

      for (i <- 0 until derivatives.length)
        temps(i) = params(0) - learningRate * ((1.0 / inputs.length) * derivatives(i))

      for (i <- 0 until params.length) {
        diffs += math.abs(params(i) - temps(i))
        params(i) = temps(i)
      }
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


