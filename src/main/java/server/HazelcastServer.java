package server;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;


public class HazelcastServer {
    public static void main(String[] args) {
        Hazelcast.newHazelcastInstance(new Config().setClusterName("TestCluster1"));
    }
}
