package IA

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
 * Created by marceloortizdesantana on 16/08/14.
 *
 * Based on lessons of https://class.coursera.org/ml-006
 */
class LinearRegression(val input: Array[Double], val output: Array[Double], val learningRate: Double = 0.005) {

  val nRows = input.length
  var iRow = -1;
  var inputs = ArrayBuffer.fill[Array[Double]](nRows) {
    iRow += 1
    Array(1.0, input(iRow))
  }

  var params = ArrayBuffer.fill[Double](2) {
    new Random().nextInt(100).toDouble
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
      hip += params(i) * inputs(i)
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

    do {
      var derivatives = ArrayBuffer.fill[Double](params.length)(0)
      // calculate derivative of cost function
      for {
        iRow <- 0 until nRows
        iCol <- 0 until inputs(iRow).length
      } {
        val diffHip_Out = hipotesis(inputs(iRow)) - output(iRow)
        println(diffHip_Out)
        derivatives(iCol) += diffHip_Out * inputs(iRow)(iCol)
      }

      val temps = ArrayBuffer.fill[Double](derivatives.length)(0)

      for (i <- 0 until derivatives.length)
        temps(i) = params(i) - learningRate * ((1.0 / nRows) * derivatives(i))

      for (i <- 0 until params.length) {
        diffs += math.abs(params(i) - temps(i))
        params(i) = temps(i)
      }

      print(diffs)

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


