package payroll.pcdsl

import scala.util.parsing.combinator._
import payroll._
import payroll.Type2Money._

class PayrollParserCombinatorsV1 extends JavaTokenParsers {
  def paycheck = empl ~ gross ~ deduct
  def empl = "paycheck" ~> "for" ~> "employee" ~> employeeName
  def gross = "is" ~> "salary" ~> "for" ~> duration
  def deduct = "minus" ~> "deductions" ~> "for" ~> "{" ~> deductItems <~ "}"
  def employeeName = stringLiteral
  def duration = decimalNumber ~ weeksDays
  def deductItems = repsep(deductItem, ",")
  def deductItem = deductKind ~> deductAmount
  def deductKind = tax | insurance | retirement
  def tax = fedState <~ "income" <~ "tax"
  def insurance = "insurance" ~> "premium"
  def retirement = "retirement" ~> "fund" ~> "contributions"
  def fedState = "federal" | "state"
  def deductAmount = percentage | amount
  def percentage = toBe ~> doubleNumber <~ "percent" <~ "of" <~ "gross"
  def amount = toBe ~> doubleNumber <~ "in" <~ "gross" <~ "currency"
  def toBe = "is" | "are"
  def weeksDays = "weeks" | "week" | "days" | "day"
  def doubleNumber = floatingPointNumber
}

class UnknownEmployee(name: Name) extends RuntimeException(name.toString)

class PayrollParserCombinators(employees: Map[Name, Employee]) extends JavaTokenParsers {
  var currentEmployee: Employee = null
  var grossAmount: Money = Money(0)

  def paycheck = empl ~ gross ~ deduct ^^ {
    case e ~ g ~ d => (e, Paycheck(g, g - d, d))
  }

  def empl = "paycheck" ~> "for" ~> "employee" ~> employeeName ^^ { name =>
    val names = name.substring(1, name.length - 1).split(" ")
    val n = Name(names(0), names(1))
    if (!employees.contains(n))
      throw new UnknownEmployee(n)
    currentEmployee = employees(n)
    currentEmployee
  }

  def gross = "is" ~> "salary" ~> "for" ~> duration ^^ { dur =>
    grossAmount = salaryForDays(dur)
    grossAmount
  }

  def deduct = "minus" ~> "deductions" ~> "for" ~> "{" ~> deductItems <~ "}"

  def employeeName = stringLiteral

  def duration = decimalNumber ~ weeksDays ^^ {
    case n ~ factor => n.toInt * factor
  }

  def weeksDays = weeks | days
  def weeks = "weeks?".r ^^ { _ => 5 }
  def days = "days?".r ^^ { _ => 1 }

  def deductItems = repsep(deductItem, ",") ^^ { items =>
    items.foldLeft(Money(0)) { _ + _ }
  }

  def deductItem = deductKind ~> deductAmount
  def deductKind = tax | insurance | retirement
  def tax = fedState <~ "income" <~ "tax"
  def insurance = "insurance" ~> "premium"
  def retirement = "retirement" ~> "fund" ~> "contributions"
  def fedState = "federal" | "state"

  def deductAmount = percentage | amount
  def percentage = toBe ~> doubleNumber <~ "percent" <~ "of" <~ "gross" ^^ {
    percentage => grossAmount * (percentage / 100.0)
  }
  def amount = toBe ~> doubleNumber <~ "in" <~ "gross" <~ "currency" ^^ {
    Money(_)
  }
  def toBe = "is" | "are"

  def doubleNumber = floatingPointNumber ^^ { _.toDouble }

  def salaryForDays(days: Int) = (currentEmployee.annualGrossSalary / 260.0) * days
}
