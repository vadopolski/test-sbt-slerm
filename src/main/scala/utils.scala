import org.apache.spark.rdd.RDD

import scala.io.{BufferedSource, Source}

object utils extends App {

  def test[A, B <: {def close():Unit}](a: A, b: B): A = {a}

  val cort: (String, Int) => String = (a, b) => {a + b}
  cort("a", 1)
  val intToString: Int => String = cort.curried("a")
  intToString(1)


  def using[A, B <: {def close():Unit}](closeable: B)(f: B => A): A =
    try {
      f(closeable)
    } finally {
      closeable.close()
    }

  class AutoCloseableWrapper[A <: AutoCloseable](protected val c: A) {
    def map[B](f: (A) => B): B = {
      try {
        f(c)
      } finally {
        c.close()
      }
    }

    def foreach(f: (A) => Unit): Unit = map(f)

    // Not a proper flatMap.
    def flatMap[B](f: (A) => B): B = map(f)

    // Hack :)
    def withFilter(f: (A) => Boolean) = this
  }

  object Arm {
    def apply[A <: AutoCloseable](c: A) = new AutoCloseableWrapper(c)
  }


//  Example of using
  for {
    s1 <- Arm(Source.fromFile("src/main/resources/1.txt"))
    s2 <- Arm(Source.fromFile("src/main/resources/2.txt"))
  } {
    println(s1.getLines().mkString("\n"))
    println(s2.getLines().mkString("\n"))
  }



}
