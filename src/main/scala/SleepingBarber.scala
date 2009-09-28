package sleepingbarber

import scala.actors.Actor
import scala.actors.Actor._
import scala.collection.{ immutable, mutable }

case object Haircut

class Customer(val id: Int) extends Actor {
  var shorn = false

  def act {
    loop {
      react {
        case Haircut => {
          shorn = true
          println("Customer " + id + " got a haircut")
        }
      }
    }
  }
}

import scala.util.Random

class Chair extends Actor {
  private val random = new Random
  val inWaiting = new mutable.SynchronizedQueue[Customer]

  def cutHair(customer: Customer) {
    println("[b] cutting hair on  customer " + customer.id)
    Thread.sleep(5 + random.nextInt(5))
    customer ! Haircut
  }

  def act {
    loop {
      if (!inWaiting.isEmpty) {
        val nextCustomer = inWaiting.dequeue
        cutHair(nextCustomer)
      }
    }
  }
}

class Barber extends Actor {
  private val chair = new Chair
  chair.start

  def helpCustomer(customer: Customer) {
    if (chair.inWaiting.size > 3) {
      println("[b] not enough seats. turning customer " + customer.id + " away")
    } else {
      chair.inWaiting += customer
    }
  }

  def act {
    loop {
      react {
        case customer: Customer => helpCustomer(customer)
      }
    }
  }
}

class Shop extends Actor {
  val barber = new Barber
  barber.start

  def act {
    println("[s] the shop is open!")

    loop {
      react {
        case customer: Customer => barber ! customer
      }
    }
  }
}

object BarbershopSimulation {
  private val random = new Random
  private val customers = new mutable.ArrayBuffer[Customer]
  private val shop = new Shop

  def generateCustomers {
    for(i <- 1 to 300) {
      val customer = new Customer(i)
      customer.start
      customers += customer
    }

    println("[!] I generated " + customers.size + " customers")
  }

  def trickleCustomers {
    for (customer <- customers) {
      shop ! customer
      Thread.sleep(random.nextInt(15))
    }
  }

  def tallyCuts {
    Thread.sleep(5000)

    val shornCount = customers.filter(c => c.shorn).size
    println("[!] " + shornCount + " customers got haircuts today")
  }

  def main(args: Array[String]) {
    println("[!] starting barbershop simulation")
    shop.start

    generateCustomers
    trickleCustomers
    tallyCuts

    System.exit(0)
  }

}
