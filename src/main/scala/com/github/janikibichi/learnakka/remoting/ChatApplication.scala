package com.github.janikibichi.learnakka.remoting

import akka.actor.{ActorRef, ActorSystem, Props}
import scala.concurrent.duration._

object ChatClientApplication extends App {
  val actorSystem = ActorSystem("ChatServer")
  implicit val dispatcher = actorSystem.dispatcher

  //Define Server Running ActorSystem
  val chatServerAddress = "akka.tcp://ChatServer@127.0.0.1:2552/user/chatServer"

  //Select an Actor from the ActorSystem and create a client on the found Actor
  actorSystem.actorSelection(chatServerAddress).resolveOne(3 seconds).onSuccess {
    case chatServer: ActorRef =>
      val client = actorSystem.actorOf(ChatClient.props(chatServer), "chatClient")
      actorSystem.actorOf(ChatClientInterface.props(client), "chatClientInterface")
  }
}

//Define the ActorSystem running on the ChatServer
object ChatServerApplication extends App {
  val actorSystem = ActorSystem("ChatServer")
  actorSystem.actorOf(ChatServer.props, "chatServer")
}
