/**
 * Created by marceloortizdesantana on 18/08/14.
 */
object Knn {

  def classify(input: Array[Double], dataset: Array[Array[Double]], labels: Array[String], k: Int): String = {
    var distancias: Array[Double] = Array()
    for (i <- 0 until dataset.length) {
      distancias = distancias :+ 0.0
    }

    print(distancias.length)

    for {
      iRow <- 0 until dataset.length
      iCol <- 0 until dataset(iRow).length
    } {
      distancias(iRow) += math.pow(dataset(iRow)(iCol) - input(iCol), 2)
    }

    for (i <- 0 until distancias.length) {
      distancias(i) = math.sqrt(distancias(i))
    }

    println(distancias.sortWith(_ > _))



    for (i <- 0 until k) {

    }





    "A"
  }

}
