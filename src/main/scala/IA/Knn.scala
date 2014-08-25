package IA

import scala.collection.mutable.ArrayBuffer

/**
 * Created by marceloortizdesantana on 18/08/14.
 */
object Knn {

  def classify(input: Array[Double], dataset: Array[Array[Double]], labels: Array[String], k: Int): String = {
    require(dataset.length == labels.length && k < dataset.length, "Length of labels must be equals the dataset length(rows), and k must be <= dataset  length.")

    var distancesLabels: ArrayBuffer[DistanceLabel] = ArrayBuffer()
    for (i <- 0 until dataset.length) {
      distancesLabels += new DistanceLabel(0.0, labels(i))
    }

    for {
      iRow <- 0 until dataset.length
      iCol <- 0 until dataset(iRow).length
    } {
      distancesLabels(iRow).distance += math.pow(dataset(iRow)(iCol) - input(iCol), 2)
    }

    for (i <- 0 until distancesLabels.length) {
      distancesLabels(i).distance = math.sqrt(distancesLabels(i).distance)
    }

    distancesLabels = distancesLabels.sortWith(_.distance < _.distance)

    require(distancesLabels(0).distance <= distancesLabels(distancesLabels.length - 1).distance)

    var labelsCounter = Map[String, Int]()

    for (i <- 0 until k) {

      val label: String = distancesLabels(i).label

      labelsCounter.get(label) match {
        case Some(count: Int) => count + 1
        case None => labelsCounter += distancesLabels(i).label -> 1
      }
    }
    var maxCounter = 0
    var maxLabel = ""

    labelsCounter.foreach {
      case (label, counter) => if (counter > maxCounter) {
        maxCounter = counter
        maxLabel = label
      }
    }

    return maxLabel
  }
}

class DistanceLabel(var distance: Double, val label: String)

