package com.github.janikibichi.learnakka.remoting

import akka.actor.ActorSystem
import akka.cluster.Cluster
import akka.cluster.ddata.Replicator.{ReadFrom, ReadMajority}
import akka.pattern.ask
import akka.util.Timeout
import com.github.janikibichi.learnakka.remoting.SubscriptionManager._
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.Random

object DistributedDataApp extends App {
  //Set up actors from actorSystem
  val actorSystem = ActorSystem("ClusterSystem")

    Cluster(actorSystem).registerOnMemberUp{
      //Create and define actor in ActorSystem
      val subscriptionManager = actorSystem.actorOf(SubscriptionManager.props)
      val subscription = Subscription(Random.nextInt(3), Cluster(actorSystem).selfUniqueAddress.toString, System.currentTimeMillis())

      subscriptionManager ! AddSubscription(subscription)

      //Simulate that time has passed
      Thread.sleep(10000)

      implicit val timeout = Timeout(5 seconds)
      val readMajority = ReadMajority(timeout = 5.seconds)
      val readFrom = ReadFrom(n=2, timeout=5.seconds)

      Await.result(subscriptionManager ? GetSubscriptions(readMajority), 5 seconds) match {
        case GetSubscriptionsSuccess(subscriptions)=>
          println(s"The current set of subscriptions is $subscriptions")

        case GetSubscriptionsFailure =>
          println(s"Subscription manager was not able to get subscriptions successfully.")
      }

      //Send Remove message to subscriptionManager
      subscriptionManager ! RemoveSubscription(subscription)

      Await.result(subscriptionManager ? GetSubscriptions(readFrom), 5 seconds) match {
        case GetSubscriptionsSuccess(subscriptions) =>
          println(s"The current set of subscriptions is $subscriptions")
        case GetSubscriptionsFailure =>
          println(s"Subscription manager was not able to get subscriptions successfully.")
      }

  }
}

