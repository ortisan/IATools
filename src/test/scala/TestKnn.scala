import org.scalatest.FlatSpec

/**
 * Created by marceloortizdesantana on 18/08/14.
 */
class TestKnn extends FlatSpec {

  "A knn" should "return the class of nearest neighbors" in {
    val dataset = Array(Array(1.0, 1.1), Array(1.1, 1.1), Array(0.0, 0.0), Array(0.0, 0.1))
    val input = Array(0.3, 0.2)
    val labels = Array("A", "B")
    val clazz = Knn.classify(input, dataset, labels, 1)
    assert(clazz == "B")
  }

}
