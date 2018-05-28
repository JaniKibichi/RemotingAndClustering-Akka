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
