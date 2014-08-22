import scala.util.Random

/**
 * Created by marceloortizdesantana on 16/08/14.
 *
 * Based on lessons of https://class.coursera.org/ml-006
 */
class LinearRegression(val input: Array[Double], val output: Array[Double], val learningRate: Double = 0.005) {

  // my guess params.
  var param0: Double = Random.nextInt(10)
  var param1: Double = Random.nextInt(10)

  /**
   * Folows the linear equation ( a + bx ) where a = param0, b = param1 and x = input
   * @param input
   * @return value of linear equation.
   */
  private def hipotesis(input: Double) = param0 + param1 * input

  /**
   * Train the algorithm, minimizing the param0 and param1 of linear formula (param0 + param1 * x)
   */
  def train(callback: () => Unit = () => Unit): Unit = {

    val maxIterations = 5000
    var iteration = 0
    var diffs = 1000.0

    val m = input.length

    do {
      // calculate derivative of cost function
      var derivative0: Double = 0.0
      var derivative1: Double = 0.0
      for (i <- 0 until m) {
        val diffHip_Out = hipotesis(input(i)) - output(i)
        derivative0 += diffHip_Out
        derivative1 += diffHip_Out * input(i)
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


