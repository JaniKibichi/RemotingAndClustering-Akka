# Remoting And Akka Clustering:

Akka actors are distributed by default. You can expand your actor system to beyond one JVM, with Akka providing the mechanisms to connect to all remote JVMs seamlessly.

Using Akka remoting and Akka clustering, we can achieve high availability and resilience.

#### Definition of terms:
###### Akka remoting -
- All actors know the location of remote actors and use fully qualified addressed to communicate to them.
###### Akka clustering -
- Members use the Gossip protocol to share information

<br><br>
- Branch out to explore enabling Akka remoting
````
git checkout -b akka_remoting_warmup master
````
- Update the .conf file application.conf in src/main/resources to indicate remoting.

<br><br>
- Branch out to explore describing remote actors on different nodes
````
git checkout -b remote_actors_different_nodes akka_remoting_warmup 
````
- Create a .conf file application-1.conf in src/main/resources to indicate remote actor on different node
- Create a .conf file application-2.conf in src/main/resources to indicate remote actor on different node
- Create and define an simple actor: <b>com.github.janikibichi.learnakka.remoting.SimpleActor.scala</b>
- Create and define an remote app with actor: <b>com.github.janikibichi.learnakka.remoting.RemoteApp.scala</b>
- Run the app by passing the first config file:
````
sbt -Dconfig.resource=application-1.conf "runMain com.github.janikibichi.learnakka.remoting.HelloAkkaRemote1"
````
- Run the app by passing the second config file:
````
sbt -Dconfig.resource=application-2.conf "runMain com.github.janikibichi.learnakka.remoting.HelloAkkaRemote2"
````
- See the [Akka Remoting App.](https://asciinema.org/a/tlvIp1MqAQZgykHOOzv6aXsbO)

<br><br>
- Branch out to explore remote actors from different machines
````
git checkout -b remote_actors_different_machines remote_actors_different_nodes
````
- Create the file:<b>com.github.janikibichi.learnakka.remoting.LookingUpRemoteApps.scala</b>
- Run the app by passing the second config file:
````
sbt -Dconfig.resource=application-2.conf "runMain com.github.janikibichi.learnakka.remoting.LookingUpRemoteActors"
````
- Run the second app in a different terminal by passing the first config file:
````
sbt -Dconfig.resource=application-1.conf "runMain com.github.janikibichi.learnakka.remoting.LookUpActorSelection"
````
- See the remote actors interacting: [Actor 1](https://asciinema.org/a/A4CAYNbOLYWyOYZgSxz1RpnkX) 
and [Actor 2.](https://asciinema.org/a/oIPCnTJa825EQCWWeH5Lnr3np)

<br><br>
- Branch out to deploy remote actors programmatically
````
git checkout -b deploy_actors_programmatically remote_actors_different_machines
````
- Create the file:<b>com.github.janikibichi.learnakka.remoting.DeployActorsProgrammatically.scala</b>
- Run the app by passing the application-1.conf file:
````
sbt -Dconfig.resource=application-1.conf "runMain com.github.janikibichi.learnakka.remoting.DeployRemoteActor1"
````
- Run the second app by passing the application-2.conf file in a second terminal:
````
sbt -Dconfig.resource=application-2.conf "runMain com.github.janikibichi.learnakka.remoting.DeployRemoteActor2"
````
- See the remote actors interacting: [Actor 1](https://asciinema.org/a/HUdX2Z6kIdw2OKdXrIvTAIq1T) 
and [Actor 2](https://asciinema.org/a/LP6ZyrpG7QAWolRWa78haxoTl).

<br><br>
- Branch out to explore scaling out the app with remote actors
````
git checkout -b deploy_app_to_remote_actors deploy_actors_programmatically
````
- Create the file: <b>com.github.janikibichi.learnakka.remoting.MasterActor.scala</b>
- Create the file: <b>com.github.janikibichi.learnakka.remoting.WorkerActor.scala</b>
- Create a .conf file application-3.conf in src/main/resources
- Create the scaling out application: <b>com.github.janikibichi.learnakka.remoting.ScalingOutApp.scala</b>
- Run the first App by passing the application-1.conf configuration:
````
sbt -Dconfig.resource=application-1.conf "runMain com.github.janikibichi.learnakka.remoting.ScalingOutMaster"
````
- Run the second App by passing the application-2.conf configuration:
````
sbt -Dconfig.resource=application-2.conf "runMain com.github.janikibichi.learnakka.remoting.ScalingOutWorker"
````
- Run the first App by passing the application-3.conf configuration:
````
sbt -Dconfig.resource=application-3.conf "runMain com.github.janikibichi.learnakka.remoting.ScalingOutWorker"
````
- See the [Master Actor](https://asciinema.org/a/ftBjjuN8YQz024bmWW5CaAxPa), 
[Worker Actor 1](https://asciinema.org/a/whnfDa8KPPBgOShtsV0TW4vGO) and 
[Worker Actor 2](https://asciinema.org/a/IVfVleLoiiWlIX6Dxo8O5sRxC) in action.

<br><br>
- Branch out to explore deploying a chat app to remote actors
````
git checkout -b deploy_chat_app_to_actors deploy_app_to_remote_actors
````
- Create the file: <b>com.github.janikibichi.learnakka.remoting.ChatServer.scala</b>
- Create the file: <b>com.github.janikibichi.learnakka.remoting.ChatClient.scala</b>
- Create the file: <b>com.github.janikibichi.learnakka.remoting.ChatApplication.scala</b>

- Run the Chat Server by passing the application-1.conf configuration:
````
sbt -Dconfig.resource=application-1.conf "runMain com.github.janikibichi.learnakka.remoting.ChatServerApplication"
````
- Run the ChatClient1 by passing the application-2.conf configuration:
````
sbt -Dconfig.resource=application-2.conf "runMain com.github.janikibichi.learnakka.remoting.ChatClientApplication"
````
- Run the ChatClient2 by passing the application-3.conf configuration:
````
sbt -Dconfig.resource=application-3.conf "runMain com.github.janikibichi.learnakka.remoting.ChatClientApplication"
````
- See the [chat server](https://asciinema.org/a/aAbTcIHvn3HqItkPqbzfhcRn0) and
 [chat app 1](https://asciinema.org/a/0tUlPQo65ysWQ9Fv8703WRYTY) and
  [chat app 2.](https://asciinema.org/a/SDFEUZ1IbqkDDVuYP4YUTIf46)

<br><br>
- Branch to explore Akka clustering
````
git checkout -b run_akka-clustering deploy_chat_app_to_actors 
````
-Add the Akka-cluster dependency in build.sbt
````
libraryDependencies += "com.typesafe.akka" %% "akka-cluster" % "2.5.12"
````
- Create a .conf file application-cluster.conf in src/main/resources

<br><br>
- Branch to explore publish-subscribe in the cluster
````
git checkout -b publish_subscribe_cluster run_akka-clustering
````
- Add the required dependency for Akka cluster tools.
````
libraryDependencies += "com.typesafe.akka" %% "akka-cluster-tools" % "2.5.12"
````
- Create the file application-cluster-1.conf in src/main/resources
- Create the file application-cluster-2.conf in src/main/resources
- Create the file: <b>com.github.janikibichi.learnakka.remoting.NotificationPublisher.scala</b>
- Create the file: <b>com.github.janikibichi.learnakka.remoting.NotificationSubscriber.scala</b>
- Create the file: <b>com.github.janikibichi.learnakka.remoting.DistributedPubSubApplication.scala</b>
- Run the DistributedPubSubApp and pass application-cluster-1.conf
````
sbt -Dconfig.resource=application-cluster-1.conf "runMain com.github.janikibichi.learnakka.remoting.DistributedPubSubApplication"
````
- Run the DistributedPubSubApp and pass application-cluster-2.conf
````
sbt -Dconfig.resource=application-cluster-2.conf "runMain com.github.janikibichi.learnakka.remoting.DistributedPubSubApplication"
````
- Run the PubSub [App One](https://asciinema.org/a/jqt4IgRW9AFczMork6ZElX47V) 
and [App Two.](https://asciinema.org/a/jqt4IgRW9AFczMork6ZElX47V)

<br><br>
- Branch out to explore cluster sharding
````
git checkout -b cluster_sharding publish_subscribe_cluster
````




























