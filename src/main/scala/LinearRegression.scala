/**
 * Created by marceloortizdesantana on 16/08/14.
 */
class LinearRegression(val input: Array[Double], val output: Array[Double], val learningRate: Double) {
  // TODO Improve with scala features (functional)
  // TODO Use graphs for demonstrations
  // my guess params.
  var param0 = 15.0
  var param1 = 50.0

  def train() = {

    val m = input.length

    var error = 0.0

    do {
      var outRegress: Array[Double] = Array()
      var diff = 0.0
      for (i <- 0 until m) {
        outRegress = outRegress :+ (param0 + param1 * input(i))
        diff += math.pow(outRegress(i) - output(i), 2)
      }
      error = math.sqrt(diff)

      // calculate derivative of cost function
      var temp0: Double = 0.0
      for (i <- 0 until m) {
        temp0 += outRegress(i) - output(i)
      }
      temp0 = param0 - learningRate * ((1.0 / m) * temp0)

      var temp1: Double = 0.0
      for (i <- 0 until m) {
        temp1 += (outRegress(i) - output(i)) * input(i)
      }
      temp1 = param1 - learningRate * ((1.0 / m) * temp1)

      param0 = temp0
      param1 = temp1

    } while (error > 0.05)

  }

  def forecast(input: Double) = {
    param0 + param1 * input
  }
}


