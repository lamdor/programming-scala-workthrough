package payroll.pcdsl

import scala.util.parsing.combinator._
import org.specs._
import payroll._
import payroll.Type2Money._

object PayrollParserCombinatorsV1Spec extends Specification {
  "PayrollParserCombinatorsV1" should {
    "parse rules when there are no deductions" in {
      val input = """paycheck for employee "Buck Trends"
      is salary for 2 weeks minus deductions for {}"""
      val p = new PayrollParserCombinatorsV1
      p.parseAll(p.paycheck, input) match {
        case p.Success(r, _) => r.toString mustEqual """(("Buck Trends"~(2~weeks))~List())"""
        case x => fail(x.toString)
      }
    }

    "calculate the gross, net, and deductions for the pay period" in {
      val input = """paycheck for employee "Buck Trends"
      is salary for 2 weeks minus deductions for {
        federal income tax            is  25.  percent of gross,
        state income tax              is  5.   percent of gross,
        insurance premium             is  500. in gross currency,
        retirement fund contributions are 10.  percent of gross
      }"""
      val p = new PayrollParserCombinatorsV1
      p.parseAll(p.paycheck, input) match {
        case p.Success(r, _) => r.toString mustEqual """(("Buck Trends"~(2~weeks))~List(25., 5., 500., 10.))"""
        case x => fail(x.toString)
      }

    }

  }
}

object PayrollParserCombinatorsSpec extends Specification {
  val salary = Money(1000000.1) // for a full year
  val gross = salary / 26. // for 2 weeks
  val buck = Employee(Name("Buck", "Trends"), salary)
  val employees = Map(buck.name -> buck)

  implicit def money2Double(m: Money) = m.amount.doubleValue

  "PayrollParserCombinators" should {
    "calculate the gross == net when there are no deductions" in {
      val input = """paycheck for employee "Buck Trends"
      is salary for 2 weeks minus deductions for {}"""
      val p = new PayrollParserCombinators(employees)
      p.parseAll(p.paycheck, input) match {
        case p.Success(Pair(employee, paycheck), _) => {
          employee mustEqual buck
          //paycheck.gross must beCloseTo(gross, Money(0.001))
          //paycheck.net must beCloseTo(gross, Money(0.001))
          paycheck.deductions must beEqualTo(Money(0))
        }
        case x => fail(x.toString)
      }
    }

    "calculate the gross, net, and deductions for the pay period" in {
      val input = """paycheck for employee "Buck Trends"
      is salary for 2 weeks minus deductions for {
        federal income tax            is  25.  percent of gross,
        state income tax              is  5.   percent of gross,
        insurance premium             is  500. in gross currency,
        retirement fund contributions are 10.  percent of gross
      }"""

      val p = new PayrollParserCombinators(employees)

      p.parseAll(p.paycheck, input) match {
        case p.Success(Pair(employee, paycheck), _) => {
          employee mustEqual buck
          //paycheck.gross must beCloseTo(gross, Money(0.001))
          val deductions = (gross * 0.4) + Money(500)
          val net = gross - deductions
          //paycheck.net must beCloseTo(net, Money(0.001))
          //paycheck.deductions must beCloseTo(deductions, Money(0.001))
          true
        }
        case x => fail(x.toString)
      }
    }

  }
}
