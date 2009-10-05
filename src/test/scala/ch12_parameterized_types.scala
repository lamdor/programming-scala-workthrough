import org.specs._

object ParameterizedTypesSpecification extends Specification {
  "Scala manifests" should {
    "allow you to see the parameterized type that was erased during compilation" in {
      import scala.reflect.Manifest
      object WhichList {
        def apply[B](value: List[B])(implicit m: Manifest[B]) = m.toString match {
          case "int" => "List[Int]"
          case "double" => "List[Double]"
          case "java.lang.String" => "List[String]"
          case _ => "List[???]"
        }
      }
      WhichList(List(1,2,3)) mustEqual "List[Int]"
      WhichList(List(3.14,2.2,42.0)) mustEqual "List[Double]"
      WhichList(List("hello", "list")) mustEqual "List[String]"

      List(List(1,2,3), List("hello", "list")).foreach {
        WhichList(_) mustEqual "List[???]"
      }
    }
  }


  "Scala covariants X[+A]  and contravariants X[-A]" should {
      class CSuper { def msuper = "msuper" }
      class C extends CSuper { def m = "m" }
      class CSub extends C { def msub = "msub" }

    "Function1" should {
      "have type parameters of [-P,+R]" in {
        def useF(f: C => C) { // means [-C,+C]
          val c1 = new C
          val c2 = f(c1)
          c2.msuper mustEqual "msuper"
          c2.m mustEqual "m"
        }
        useF((c:C) => new C)
        useF((c:CSuper) => new CSub)
        // useF((c:CSub) => new CSuper) // doesn't work - type mismatch

      }
    }

    "allow only vars to have invariance [T] not [+T] nor [-T]" in {
      // class ContainerPlus[+A](var value: A) // covariant type A occurs in contravariant position in type A of parameter of setter value_=
      // class ContainerMinus[-A](var value: A) // contravariant type A occurs in covariant position in type => A of method value
      // new ContainerPlus("hello world")
      // new ContainerMinus("hello world")

      true
    }

  }
}
