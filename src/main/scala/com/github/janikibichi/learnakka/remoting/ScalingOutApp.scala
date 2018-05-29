package com.github.janikibichi.learnakka.remoting

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.routing.RoundRobinPool
import scala.concurrent.duration._

object ScalingOutWorker extends App {
  //Define ActorSystem
  val actorSystem = ActorSystem("WorkerActorSystem")

  implicit val dispatcher = actorSystem.dispatcher

  //Select the actor and return a future
  val selection = actorSystem.actorSelection("akka.tcp://MasterActorSystem@127.0.0.1:2552/user/masterActor")

  //On resolve the future, success of the callback, pass the actorRef
  selection.resolveOne(3 seconds).onSuccess {
    case masterActor: ActorRef =>
      println("We got the ActorRef for the master actor")
      val pool = RoundRobinPool(10)

      //Register a new Worker
      val workerPool = actorSystem.actorOf(Props[WorkerActor].withRouter(pool), "workerActor")

      //Send a message to create a worker
      masterActor ! RegisterWorker(workerPool)
  }
}

object ScalingOutMaster extends App {
  //Define ActorSystems
  val actorSystem = ActorSystem("MasterActorSystem")
  val masterActor = actorSystem.actorOf(Props[MasterActor],"masterActor")

  (1 to 100).foreach(i => {
    masterActor ! Work(s"$i")
    Thread.sleep(5000) //Simulates sending work to the Master actor every 5 seconds
  })
}
