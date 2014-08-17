import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.{ChartFactory, ChartFrame}
import org.jfree.data.xy.{XYSeries, XYSeriesCollection}

/**
 * Created by marceloortizdesantana on 17/08/14.
 */
class Demonstrations {
  def demonstrateLinearRegression(): Unit = {

    //y = 2*x
    val input = Array(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)
    val output = Array(0.0, 2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0, 20.0)

    val result = new XYSeriesCollection();
    val series = new XYSeries("Inicial values");
    for (i <- 0 until input.length) {
      series.add(input(i), output(i));
    }

    result.addSeries(series);
    result.addSeries(new XYSeries("Fitting prediction"))

    var chart = ChartFactory.createScatterPlot(
      "Prediction Graph", // chart title
      "X", // x axis label
      "Y", // y axis label
      result, // data
      PlotOrientation.VERTICAL,
      true, // include legend
      true, // tooltips
      false // urls
    );

    chart.getXYPlot.getRangeAxis().setAutoRange(true)

    val linearRegress = new LinearRegression(input, output, 0.015)

    def updateGraph(): Unit = {

      val series = result.getSeries(1)

      for (i <- 0 until input.length) {
        series.add(input(i), linearRegress.predict(input(i)))
      }

      Thread.sleep(1000);
    }

    // create and display a frame...
    val frame = new ChartFrame("Linear Regression", chart)
    frame.pack()
    frame.setVisible(true)
    linearRegress.train(updateGraph)
  }
}

object Demonstrations extends App {
  new Demonstrations().demonstrateLinearRegression()
}


