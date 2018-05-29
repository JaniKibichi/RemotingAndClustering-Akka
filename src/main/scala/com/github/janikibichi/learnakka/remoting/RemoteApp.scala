package com.github.janikibichi.learnakka.remoting

import akka.actor.{ActorSystem, Props}

object HelloAkkaRemote1 extends App{
  val actorSystem = ActorSystem("HelloAkkaRemote1")
}

object HelloAkkaRemote2 extends App{
  val actorSystem = ActorSystem("HelloAkkaRemote2")

  //Print
  println("Creating actor from HelloAkkaRemoting")

  val actor = actorSystem.actorOf(Props[SimpleActor],"SimpleRemoteActor")
  //Send Message
  actor ! "Checking"
}
