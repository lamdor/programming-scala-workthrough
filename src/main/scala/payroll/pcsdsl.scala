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
