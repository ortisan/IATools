/**
 * Created by marcelosantana on 25/08/2014.
 */
object StatTools {

  def mean(inputs: Array[Double]) = inputs.foldLeft(0.0)((acum: Double, x: Double) => acum + x) / inputs.length

  def variance(inputs: Array[Double]): Double = {
    val mean: Double = StatTools.mean(inputs)
    return inputs.map(x => math.pow(x - mean, 2)).foldLeft(0.0)((acum: Double, x: Double) => acum + x) / inputs.length
  }

  def standardDeviation(inputs: Array[Double]): Double = {
    val variance = StatTools.variance(inputs)
    return math.sqrt(variance)
  }

  def minMax(inputs: Array[Double]): (Double, Double) = {
    var min = Double.MaxValue
    var max = Double.MinValue
    for (x <- inputs) {
      if (x < min) min = x
      if (x > max) max = x
    }
    return (min, max)
  }

  def varianceAndStandardDeviation(inputs: Array[Double]): (Double, Double) = {
    val variance = StatTools.variance(inputs)
    val standardDeviation = math.sqrt(variance)
    // returns  a tuple with the variance and standard deviation
    return (variance, standardDeviation)
  }

  def descriptiveStatistics(input: Array[Double]): DescriptiveStat = {
    var min, max, mean, variance, standardDeviation = 0.0
    mean = StatTools.mean(input)
    (min, max) = minMax(input)
    (variance, standardDeviation) = varianceAndStandardDeviation(input)
    return new DescriptiveStat(mean, variance, standardDeviation, min, max)
  }

}

class DescriptiveStat(val mean: Double, val variance: Double, val standardDeviation: Double, val min: Double, val max: Double)


