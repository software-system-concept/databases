import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasicCachingDemo {
    public static void main(String[] args) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName("TestCluster1");
        //clientConfig.setCredentials(new UsernamePasswordCredentials("", ""));
        ClientNetworkConfig clientNetworkConfig = new ClientNetworkConfig();
        clientNetworkConfig.setAddresses(List.of("192.168.178.19"));
        clientConfig.setNetworkConfig(clientNetworkConfig);

        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        IMap<String,String> map = client.getMap("country-map");
        createSingleCacheWithTTL(map);

        client.shutdown();
    }


    private static void createSingleCacheWithTTL(IMap<String,String> map) {
        map.set("test-key", "test-vaue", 10, TimeUnit.SECONDS);
        System.out.println("Let's check if cache exist, cache value = "+map.get("test-key"));
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Let's check if cache exist after timeout, cache value = "+map.get("test-key"));

    }
}
