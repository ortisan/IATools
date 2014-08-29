import java.time.temporal.{ChronoField, TemporalField}

import IA.{LinearRegression, Knn}
import dto.DollarRate
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.{ChartFactory, ChartFrame}
import org.jfree.data.xy.{XYSeries, XYSeriesCollection}
import scrappers.{ScrapperDollar}

import scala.collection.mutable.ArrayBuffer

/**
 * Created by marceloortizdesantana on 17/08/14.
 */
class Demonstrations {
  def demonstrateLinearRegression(): Unit = {

    //y = 2*x
    val input = Array(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)
    val output = Array(0.0, 2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0, 20.0)

    val result = new XYSeriesCollection()
    val series = new XYSeries("Inicial values")
    for (i <- 0 until input.length) {
      series.add(input(i), output(i))
    }

    result.addSeries(series)
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
    )

    chart.getXYPlot.getRangeAxis().setAutoRange(true)

    val linearRegress = new LinearRegression(input, output, 0.15)

    def updateGraph() {

      val series = result.getSeries(1)

      series.clear()

      for (i <- 0 until input.length) {
        series.add(input(i), linearRegress.predict(input(i)))
      }

      Thread.sleep(50)
    }
    chart.getXYPlot.getRangeAxis().setAutoRange(true)
    // create and display a frame...
    val frame = new ChartFrame("Linear Regression", chart)
    frame.pack()
    frame.setVisible(true)
    linearRegress.train(updateGraph)
  }

  def demonstrateKnn(): Unit = {

    var dataset = Array(Array(1.0, 1.1), Array(1.1, 1.1), Array(0.0, 0.0), Array(0.1, 0.1))
    var input = Array(0.3, 0.2)
    val labels = Array("A", "A", "B", "B")

    val result = new XYSeriesCollection()
    var labelsPoints = Map[String, ArrayBuffer[Array[Double]]]()

    for (i <- 0 until labels.length) {
      val label = labels(i)
      labelsPoints.get(label) match {
        case Some(points: ArrayBuffer[Array[Double]]) => points += dataset(i)
        case None => labelsPoints += label -> ArrayBuffer(dataset(i))
      }
    }

    labelsPoints.foreach {
      case (label, points) => {
        val series = new XYSeries(label)
        for (i <- 0 until points.length) {
          series.add(points(i)(0), points(i)(0))
        }
        result.addSeries(series)
      }
    }

    var clazz = Knn.classify(input, dataset, labels, 1)
    val seriesGuess = new XYSeries(clazz + "_i")
    seriesGuess.add(input(0), input(1))
    result.addSeries(seriesGuess)

    var input2 = Array(0.8, 0.7)
    clazz = Knn.classify(input2, dataset, labels, 1)
    val seriesGuess2 = new XYSeries(clazz + "_i")
    seriesGuess2.add(input2(0), input2(1))
    result.addSeries(seriesGuess2)

    var chart = ChartFactory.createScatterPlot(
      "Knn classification", // chart title
      "Attr1 (Attribute 1)", // x axis label
      "Attr2 (Attribute 2)", // y axis label
      result, // data
      PlotOrientation.VERTICAL,
      true, // include legend
      true, // tooltips
      false // urls
    )

    chart.getXYPlot.getRangeAxis().setAutoRange(true)
    val frame = new ChartFrame("Knn Classification", chart)
    frame.pack()
    frame.setVisible(true)
  }

  def demonstrateLinearRegressionWithRealDatas(): Unit = {

    val cotacoesTeste: Array[DollarRate] = ScrapperDollar.getRateByDate(qtdDias = 10, pularDias = 50)

    val seriesTeste = new XYSeries("Cotacoes de teste")

    var input = ArrayBuffer[Double]()
    var output = ArrayBuffer[Double]()

    for (cotacao <- cotacoesTeste) {
      input += cotacao.date.getLong(ChronoField.MILLI_OF_DAY)
      output += cotacao.value
      seriesTeste.add(cotacao.date.getLong(ChronoField.MILLI_OF_DAY).toDouble, cotacao.value)
    }

    val linearRegress = new LinearRegression(input.toArray, output.toArray, 0.005)
    linearRegress.train()

    val series = new XYSeries("Cotacoes")
    val seriesPrevisao = new XYSeries("Previsao")

    val cotacoes: Array[DollarRate] = ScrapperDollar.getRateByDate(qtdDias = 10, pularDias = 60)
    for (cotacao <- cotacoes) {
      series.add(cotacao.date.getLong(ChronoField.MILLI_OF_DAY), cotacao.value)
      val input = cotacao.date.getLong(ChronoField.MILLI_OF_DAY)
      val output = linearRegress.predict(input)

      println(output)

      seriesPrevisao.add(cotacao.date.getLong(ChronoField.MILLI_OF_DAY).toDouble, output)
    }

    var result = new XYSeriesCollection()

    result.addSeries(seriesTeste)
    result.addSeries(series)
    result.addSeries(seriesPrevisao)

    var chart = ChartFactory.createScatterPlot(
      "Regressão do dólar", // chart title
      "Valor em R$", // x axis label
      "Data", // y axis label
      result, // data
      PlotOrientation.VERTICAL,
      true, // include legend
      true, // tooltips
      false // urls
    )

    chart.getXYPlot.getRangeAxis().setAutoRange(true)


    // create and display a frame...
    val frame = new ChartFrame("Regressão do dólar", chart)
    frame.pack()
    frame.setVisible(true)

  }

  def demonstrateLinearRegressionWithRealDatasBreeze(): Unit = {

    val cotacoesTeste: Array[DollarRate] = ScrapperDollar.getRateByDate(qtdDias = 10, pularDias = 50)

    val seriesTeste = new XYSeries("Cotacoes de teste")

    var input = ArrayBuffer[Double]()
    var output = ArrayBuffer[Double]()

    for (cotacao <- cotacoesTeste) {
      input += cotacao.date.getLong(ChronoField.MILLI_OF_DAY)
      output += cotacao.value
      seriesTeste.add(cotacao.date.getLong(ChronoField.MILLI_OF_DAY).toDouble, cotacao.value)
    }

    val linearRegress = new LinearRegression(input.toArray, output.toArray, 0.005)
    linearRegress.train()

    val series = new XYSeries("Cotacoes")
    val seriesPrevisao = new XYSeries("Previsao")

    val cotacoes: Array[DollarRate] = ScrapperDollar.getRateByDate(qtdDias = 10, pularDias = 60)
    for (cotacao <- cotacoes) {
      series.add(cotacao.date.getLong(ChronoField.MILLI_OF_DAY).toDouble, cotacao.value)
      val input = cotacao.date.getLong(ChronoField.MILLI_OF_DAY).toDouble
      val output = linearRegress.predict(input)

      println(output)

      seriesPrevisao.add(cotacao.date.getLong(ChronoField.MILLI_OF_DAY).toDouble, output)
    }

    var result = new XYSeriesCollection()

    result.addSeries(seriesTeste)
    result.addSeries(series)
    result.addSeries(seriesPrevisao)

    var chart = ChartFactory.createScatterPlot(
      "Regressão do dólar", // chart title
      "Valor em R$", // x axis label
      "Data", // y axis label
      result, // data
      PlotOrientation.VERTICAL,
      true, // include legend
      true, // tooltips
      false // urls
    )

    chart.getXYPlot.getRangeAxis().setAutoRange(true)

    // create and display a frame...
    val frame = new ChartFrame("Regressão do dólar", chart)
    frame.pack()
    frame.setVisible(true)
  }


}

object Demonstrations extends App {
  new Demonstrations().demonstrateLinearRegression()
  //new Demonstrations().demonstrateKnn()
  //new Demonstrations().demonstrateLinearRegressionWithRealDatas()
}


