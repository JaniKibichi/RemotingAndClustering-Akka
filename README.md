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

