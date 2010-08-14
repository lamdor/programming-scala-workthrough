package payroll.api

import payroll._
import payroll.Type2Money._

object DeductionsCalculator {
  def federalIncomeTax(e: Employee, gross: Money) = gross * .25
  def stateIncomeTax(e: Employee, gross: Money) = gross * .05
  def insurancePremiums(e: Employee, gross: Money) = Money(500)
  def retirementFundContributions(e: Employee, gross: Money) = gross * .10
}
