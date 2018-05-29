package com.github.janikibichi.learnakka.remoting

import akka.actor.{ActorRef, ActorSystem, Props}
import scala.concurrent.duration._

object LookUpActorSelection extends App{
  val actorSystem = ActorSystem("LookingUpActors")

  implicit val dispatcher = actorSystem.dispatcher

  //Create and define Actor in ActorSystem
  val selection = actorSystem.actorSelection("akka.tcp://LookingUpRemoteActors@127.0.0.1:2553/user/remoteActor")

  //Send Message to Actors
  selection ! "test"

  selection.resolveOne(3 seconds).onSuccess {
    case actorRef: ActorRef =>
      println("We got an ActorRef")
      actorRef ! "test"
  }
}

object LookingUpRemoteActors extends App {
  val actorSystem = ActorSystem("LookingUpRemoteActors")
  actorSystem.actorOf(Props[SimpleActor],"remoteActor")
}
