import IA.LinearRegression
import org.scalatest.FlatSpec

/**
 * Created by marceloortizdesantana on 16/08/14.
 */
class TestLinearRegression extends FlatSpec {


  "An linear regression" should "learn from inputs and outputs" in {
    //y = 2*x
    val linearRegress = new LinearRegression(input = Array(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0),
      output = Array(0.0, 2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0, 20.0), learningRate = 0.005)
    linearRegress.train(() => println("training..."))
    assert(linearRegress.predict(-1.0) >= -2.30 && linearRegress.predict(-1.0) <= -1.70)
    assert(linearRegress.predict(20.0) >= 39.70 && linearRegress.predict(20.0) <= 40.30)
    assert(linearRegress.predict(70.0) >= 139.70 && linearRegress.predict(20.0) <= 140.30)
  }


}
