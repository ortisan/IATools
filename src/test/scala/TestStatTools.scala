import org.scalatest.FlatSpec
import stat.DescriptiveStatistic

/**
 * Created by marcelosantana on 25/08/2014.
 */
class TestStatTools extends FlatSpec {

  "An descriptive statistic" should "calculate " in {
    val input = Array(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)
    val descriptiveStatData =
      DescriptiveStatistic.calculateDescriptiveStatistics(input)
    assert(descriptiveStatData.min == 0.0)
    assert(descriptiveStatData.max == 10.0)
    assert(descriptiveStatData.mean == 5.0)
    assert(descriptiveStatData.variance == 10.0)
    assert(descriptiveStatData.standardDeviation > 3.16 && descriptiveStatData.standardDeviation < 3.17)
  }

  it should "produce throw IllegalArgumentException when input is null or empty" in {
    intercept[IllegalArgumentException] {
      DescriptiveStatistic.calculateDescriptiveStatistics(Array[Double]())
    }
  }

}
