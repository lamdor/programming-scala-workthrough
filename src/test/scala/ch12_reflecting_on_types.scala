import org.specs._

object ReflectingOnTypesSpecification extends Specification {
  trait T[A] {
    val vT: A
    def mT = vT
  }

  class C extends T[String] {
    val vT = "T"
    val vC = "C"
    def mC = vC
  }

  "Scala types" should {
    "allow you to reflect as you would in java land" in {
      val c = new C

      val clazz = c.getClass
      val clazz2 = classOf[C]
      val methods = clazz.getMethods
      val ctors = clazz.getConstructors
      val fields = clazz.getFields
      val annos = clazz.getAnnotations
      val name = clazz.getName
      val parentInterfaces = clazz.getInterfaces
      val superClass = clazz.getSuperclass
      val typeParams = clazz.getTypeParameters

      c.mT mustEqual "T"
      c.mC mustEqual "C"
    }

    "be subject to type erasure" in {
      val x: List[Double] = List(3.14, 2.3, 42.0)
      x.isInstanceOf[List[String]]
    }
  }
}
