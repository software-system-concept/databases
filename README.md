# Title
Demo of a simple Hazelcast Server and Client programs in Java.

## Description
This repo contains the following :
* A very simple hazelcast server
* Java based hazelcast clients which demonstrates the "cache aside" caching pattern and also the concept of TTL (Time to Live).

## Prerequisites
* JDK 17 - You can download the JDK from here : https://jdk.java.net/archive/
* Maven 3.x - You can download from here : https://maven.apache.org/download.cgi
* Intellij Community edition  - You can download from here : https://www.jetbrains.com/idea/download/?section=windows

## How To
* Clone the repo in your local system and open the project in Intellij as a maven project (by selecting pom.xml file when opening)
* First start the hazelcast server by running the program HazelcastServer.java from the IDE. It will keep running.
* Then start the client programs by :
  * Running the program BasicCachingDemo.java also from the IDE
  * Running the program CacheServiceImpl.java also from the IDE
