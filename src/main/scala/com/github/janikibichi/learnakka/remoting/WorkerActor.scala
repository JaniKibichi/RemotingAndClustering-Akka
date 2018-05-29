package com.github.janikibichi.learnakka.remoting

import akka.actor.{Actor, ActorRef}

//Create messages
case class Work(workdId: String)
case class WorkDone(workId: String)

//Define the work Actor
class WorkerActor extends Actor{
  def receive = {
    case Work(workId) =>
      Thread.sleep(3000)
      sender ! WorkDone(workId)
      println(s"Work $workId was done by worker actor")
  }
}

