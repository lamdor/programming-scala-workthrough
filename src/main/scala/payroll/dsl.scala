package payroll.dsl

import payroll._

case class Duration(val amount: Int) {
  def weeks = amount * 5
  def years = amount * 260
}

object rules {
  def apply(rules: Employee => Paycheck) = new PayrollBuilderRules(rules)
  implicit def int2Duration(i: Int) = Duration(i)
  implicit def employee2GrossPayBuilder(e: Employee) = new GrossPayBuilder(e)
  implicit def grossPayBuilder2DeductionsBuilder(gpb: GrossPayBuilder) = new DeductionsBuilder(gpb)
  implicit def double2DeductionsBuilderDeductionHelper(d: Double) = new DeductionsBuilderDeductionHelper(d)
}

class PayrollException(message: String, cause: Throwable) extends RuntimeException(message, cause)

protected[dsl] class PayrollBuilderRules(rules: Employee => Paycheck) {
  def apply(employee: Employee) =
    try {
      rules(employee)
    } catch {
      case th: Throwable => new PayrollException(
        "Failed to process payroll for employee: " + employee, th)
    }
}

import payroll.Type2Money._

protected[dsl] class GrossPayBuilder(val employee: Employee) {
  var gross: Money = Money(0)

  def salary_for(days: Int) = {
    gross += dailyGrossSalary(employee.annualGrossSalary) * days
    this
  }

  def weeklyGrossSalary(annual: Money) = annual / 52.0
  def dailyGrossSalary(annual: Money) = annual / 260.0
}

protected[dsl] class DeductionsBuilder(gpb: GrossPayBuilder) {
  val employee = gpb.employee
  var paycheck = new Paycheck(gpb.gross, gpb.gross, 0)

  def currency = this

  def minus_deductions_for(deductionRules: DeductionsBuilder => Unit) = {
    deductionRules(this)
    paycheck
  }

  def addDeductions(amount: Money) = paycheck = paycheck plusDeductions amount
  def addDeductionsPercentage(percentage: Double) = {
    val amount = paycheck.gross * (percentage/100.0)
    addDeductions(amount)
  }

}

class DeductionsCalculator {
  def is(builder: DeductionsBuilder) = apply(builder)
  def are(builder: DeductionsBuilder) = apply(builder)

  def apply(builder: DeductionsBuilder) = { }
}

object federalIncomeTax extends DeductionsCalculator
object stateIncomeTax extends DeductionsCalculator
object insurancePremiums extends DeductionsCalculator
object retirementFundContributions extends DeductionsCalculator

protected[dsl] class DeductionsBuilderDeductionHelper(factor: Double) {
  def percent_of(builder: DeductionsBuilder) = {
    builder.addDeductionsPercentage(factor)
    builder
  }

  def in(builder: DeductionsBuilder) = {
    builder addDeductions Money(factor)
    builder
  }
}
