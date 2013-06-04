package com.goticks

import org.scalatest.matchers.MustMatchers
import org.scalatest.WordSpec
import akka.testkit.{ImplicitSender, TestKit}
import akka.actor.{Props, ActorSystem}

class TicketSellerSpec extends TestKit(ActorSystem("testTickets"))
                         with WordSpec
                         with MustMatchers
                         with ImplicitSender
                         with StopSystemAfterAll {
  "The TicketSeller" must {
    "Give out tickets until they are sold out" in {
      fail("not implemented")

      // write test

      // get protocol

      // mk Tickets

      // create ticketSellet

      // send Tickets

      // send BuyTicket

      // expect Ticket

      // 2 to 10 Buy Ticket

      // receiveN9

      // zip with nrs foreach , validate nr

      // buy Ticket

      // expect SoldOut
    }
  }
}
