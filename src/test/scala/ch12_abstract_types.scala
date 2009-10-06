import org.specs._

object AbstractTypesSpecification extends Specification {
  "Scala abstract types" should {
    "have type bounds" in {
      trait exampleTrait {
        type t1
        type t2 >: t3 <: t1
        type t3 <: t1
        type t4
        type t5 = List[t4]

        val v1: t1
        val v2: t2
        val v3: t3
        val v4: t4
        val v5: t5
      }

      trait T1 { val name1: String }
      trait T2 extends T1 { val name2: String }
      class C(val name1: String, val name2: String) extends T2

      object example extends exampleTrait {
        type t1 = T1
        type t2 = T2
        type t3 = C
        type t4 = Int

        val v1 = new T1 { val name1 = "T1" }
        val v2 = new T2 { val name1 = "T1"; val name2 = "T2"}
        val v3 = new C("C1", "C2")
        val v4 = 0
        val v5 = List(1,2,3,4,5)
      }

      true
    }
  }
}
