package com.github.janikibichi.learnakka.remoting

import akka.actor.{ActorSystem, Address, Deploy, Props}
import akka.remote.RemoteScope

object DeployRemoteActor1 extends App{
  //Create an Actor System
  val actorSystem = ActorSystem("DeployRemoteActor1")
}

object DeployRemoteActor2 extends App {
  //Create an Actor System
  val actorSystem = ActorSystem("DeployRemoteActor2")

  //Print
  println("Creating actor from DeployRemoteActor2")

  val address = Address("akka.tcp", "DeployRemoteActor1", "127.0.0.1", 2552)

  //Create and define an Actor in the ActorSystem
  val actor = actorSystem.actorOf(Props[SimpleActor].withDeploy(Deploy(scope = RemoteScope(address))), "remoteActor")

  actor ! "Checking"
}
