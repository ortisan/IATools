package stat

/**
 * Created by marcelosantana on 25/08/2014.
 */
object DescriptiveStatistic {

  def mean(inputs: Array[Double]) = inputs.foldLeft(0.0)((acum: Double, x: Double) => acum + x) / inputs.length

  def variance(inputs: Array[Double]): Double = {
    val mean: Double = DescriptiveStatistic.mean(inputs)
    inputs.map(x => math.pow(x - mean, 2)).foldLeft(0.0)((acum: Double, x: Double) => acum + x) / inputs.length
  }

  def meanVariance(inputs: Array[Double]): (Double, Double) = {
    val mean: Double = DescriptiveStatistic.mean(inputs)
    (mean, inputs.map(x => math.pow(x - mean, 2)).foldLeft(0.0)((acum: Double, x: Double) => acum + x) / inputs.length)
  }

  def standardDeviation(inputs: Array[Double]): Double = {
    val variance = DescriptiveStatistic.variance(inputs)
    math.sqrt(variance)
  }

  def minMax(inputs: Array[Double]): (Double, Double) = {
    var min = Double.MaxValue
    var max = Double.MinValue
    for (x <- inputs) {
      if (x < min) min = x
      if (x > max) max = x
    }
    (min, max)
  }

  def meanVarianceAndStandardDeviation(inputs: Array[Double]): (Double, Double, Double) = {
    val (mean, variance) = DescriptiveStatistic.meanVariance(inputs)
    val standardDeviation = math.sqrt(variance)
    (mean, variance, standardDeviation)
  }

  def calculateDescriptiveStatistics(input: Array[Double]): DescriptiveStatData = {
    require(input != null && input.length > 0)
    val (min, max) = minMax(input)
    val (mean, variance, standardDeviation) = meanVarianceAndStandardDeviation(input)
    new DescriptiveStatData(mean, variance, standardDeviation, min, max)
  }
}

class DescriptiveStatData(val mean: Double, val variance: Double, val standardDeviation: Double, val min: Double, val max: Double)


