import payroll.api._
import payroll.api.DeductionsCalculator._
import payroll._
import payroll.Type2Money._

object PayrollApiScript extends Application {
  val buck = Employee(Name("Buck", "Trends"), Money(80000))
  val jane = Employee(Name("Jane", "Doe"), Money(90000))

  List(buck, jane).foreach { employee =>

    val biweeklyGross = employee.annualGrossSalary / 26.

                            val deductions = federalIncomeTax(employee, biweeklyGross) +
                            stateIncomeTax(employee, biweeklyGross) +
                            insurancePremiums(employee, biweeklyGross) +
                            retirementFundContributions(employee, biweeklyGross)

                            val check = Paycheck(biweeklyGross, biweeklyGross - deductions, deductions)

                            format("%s %s: %s\n", employee.name.first, employee.name.last, check)
                          }
}
