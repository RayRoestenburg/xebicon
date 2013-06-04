package com.goticks

import akka.testkit.{ImplicitSender, TestKit}
import akka.actor.{Props, ActorSystem}
import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers

class BoxOfficeSpec extends TestKit(ActorSystem("testTickets"))
                       with WordSpec
                       with MustMatchers
                       with ImplicitSender
                       with StopSystemAfterAll {
  "The BoxOffice" must {

    "Create an event and get tickets from the correct Ticket Seller" in {
      // write test

      // get protocol

      // create boxOffice

      // send Event

      // expect EventCreated

      // send TicketRequest

      // expect Ticket

    }
  }
}
