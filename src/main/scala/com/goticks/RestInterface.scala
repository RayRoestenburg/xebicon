package com.goticks

import akka.actor._

import spray.routing._
import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport._
import spray.routing.RequestContext
import akka.util.Timeout
import scala.concurrent.duration._

class RestInterface extends HttpServiceActor
                    with RestApi {
  def receive = runRoute(routes)
}

trait RestApi extends HttpService with ActorLogging with BoxOfficeCreator { actor: Actor =>
  import com.goticks.TicketProtocol._
  import context._
  implicit val timeout = Timeout(10 seconds)
  import akka.pattern.ask
  import akka.pattern.pipe

  val boxOffice = createBoxOffice

  def routes: Route =

    path("events") {
      put {
        entity(as[Event]) { event => requestContext =>
          log.info(s"Received new event $event, sending to $boxOffice")
          val responder = createResponder(requestContext)
          boxOffice.ask(event).pipeTo(responder)
        }
      } ~
      get { requestContext =>
        val responder = createResponder(requestContext)
        boxOffice.ask(GetEvents).pipeTo(responder)
      }
    } ~
    path("ticket") {
      get {
        entity(as[TicketRequest]) { ticketRequest => requestContext =>
          val responder = createResponder(requestContext)
          boxOffice.ask(ticketRequest).pipeTo(responder)
        }
      }
    } ~
    path("ticket" / PathElement) { eventName =>
      get { ctx =>
        val req = TicketRequest(eventName)
        val responder = createResponder(ctx)
        boxOffice.ask(req).pipeTo(responder)
      }
    }

  def createResponder(requestContext:RequestContext) = {
    context.actorOf(Props(new Responder(requestContext, boxOffice)))
  }

}

class Responder(requestContext:RequestContext, boxOffice:ActorRef) extends Actor with ActorLogging {
  import TicketProtocol._
  import spray.httpx.SprayJsonSupport._

  context.setReceiveTimeout(30 seconds)

  def receive = {

    case ticket:Ticket =>
      requestContext.complete(StatusCodes.OK, ticket)
      self ! PoisonPill

    case EventCreated =>
      requestContext.complete(StatusCodes.OK)
      self ! PoisonPill

    case SoldOut =>
      requestContext.complete(StatusCodes.NotFound)
      self ! PoisonPill

    case Events(events) =>
      requestContext.complete(StatusCodes.OK, events)
      self ! PoisonPill

    case ReceiveTimeout =>
      context.setReceiveTimeout(Duration.Undefined)
      self ! PoisonPill

  }
}