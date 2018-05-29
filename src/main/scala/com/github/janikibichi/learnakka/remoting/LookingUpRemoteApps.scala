package com.github.janikibichi.learnakka.remoting

import akka.actor.{ActorRef, ActorSystem, Props}
import scala.concurrent.duration._

//Select the actor '/remoteActor' and send it a message
object LookUpActorSelection extends App{
  val actorSystem = ActorSystem("LookingUpActors")

  implicit val dispatcher = actorSystem.dispatcher

  //Look for the Actor using Actor Selection - This returns a future
  val selection = actorSystem.actorSelection("akka.tcp://LookingUpRemoteActors@127.0.0.1:2553/user/remoteActor")

  //Send Message to Future
  selection ! "test"

  //If resolved after 3 seconds, send a Message :: OnSuccess instead of Await.result which is blocking
  selection.resolveOne(3 seconds).onSuccess {
    case actorRef: ActorRef =>
      println("We got an ActorRef")
      actorRef ! "test"
  }
}


//Create the Actor and name it 'remoteActor'
object LookingUpRemoteActors extends App {
  val actorSystem = ActorSystem("LookingUpRemoteActors")
  actorSystem.actorOf(Props[SimpleActor],"remoteActor")
}
