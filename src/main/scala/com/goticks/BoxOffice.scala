package com.goticks

import akka.actor._
import concurrent.Future
import scala.concurrent.duration._
import akka.util.Timeout

// implement BoxOffice
class BoxOffice extends Actor {
  def receive = { case msg => }
}
  // import protocol
  // timeout for ask
  // Event =>
  // TicketRequest =>

//    case GetEvents =>
//      import akka.pattern.ask
//
//      val capturedSender = sender
//
//      def askAndMapToEvent(ticketSeller:ActorRef) =  {
//
//        val futureInt = ticketSeller.ask(GetEvents).mapTo[Int]
//
//        futureInt.map(nrOfTickets => Event(ticketSeller.actorRef.path.name, nrOfTickets))
//      }
//      val futures = context.children.map(ticketSeller => askAndMapToEvent(ticketSeller))
//
//      Future.sequence(futures).map { events =>
//        capturedSender ! Events(events.toList)
//      }
