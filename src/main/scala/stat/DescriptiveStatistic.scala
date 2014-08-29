package stat

import dto.DescriptiveStatData

/**
 * Created by marcelosantana on 25/08/2014.
 */
object DescriptiveStatistic {

  def mean(inputs: Array[Double], populacional: Boolean = true) = inputs.foldLeft(0.0)((acum: Double, x: Double) => acum + x) / (if (populacional) inputs.length else inputs.length - 1)

  def variance(inputs: Array[Double], populacional: Boolean = true): Double = {
    val mean: Double = DescriptiveStatistic.mean(inputs, populacional)
    inputs.map(x => math.pow(x - mean, 2)).foldLeft(0.0)((acum: Double, x: Double) => acum + x) / inputs.length
  }

  def meanVariance(inputs: Array[Double], populacional: Boolean = true): (Double, Double) = {
    val mean: Double = DescriptiveStatistic.mean(inputs, populacional)
    (mean, inputs.map(x => math.pow(x - mean, 2)).foldLeft(0.0)((acum: Double, x: Double) => acum + x) / inputs.length)
  }

  def standardDeviation(inputs: Array[Double], populacional: Boolean = true): Double = {
    val variance = DescriptiveStatistic.variance(inputs, populacional)
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

  def meanVarianceAndStandardDeviation(inputs: Array[Double], populacional: Boolean = true): (Double, Double, Double) = {
    val (mean, variance) = DescriptiveStatistic.meanVariance(inputs, populacional)
    val standardDeviation = math.sqrt(variance)
    (mean, variance, standardDeviation)
  }

  def standarize(inputs: Array[Double]): Array[Double] = {
    val (mean, variance, standardDeviation) = meanVarianceAndStandardDeviation(inputs, populacional = false)
    for (input <- inputs) yield (input - mean) / standardDeviation
  }

  /**
   * Make a descriptive statistics from an input.
   * @param input
   * @return Dto
   */
  def calculateDescriptiveStatistics(input: Array[Double]): DescriptiveStatData = {
    require(input != null && input.length > 0)
    val (min, max) = minMax(input)
    val (mean, variance, standardDeviation) = meanVarianceAndStandardDeviation(input)
    new DescriptiveStatData(mean, variance, standardDeviation, min, max)
  }
}


