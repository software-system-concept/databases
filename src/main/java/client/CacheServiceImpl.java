package client;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CacheServiceImpl {

    private static BackendServiceImpl backendService = new BackendServiceImpl();

    private static HazelcastInstance getHazelcastHandle(){
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName("TestCluster1");
        //clientConfig.setCredentials(new UsernamePasswordCredentials("", ""));
        ClientNetworkConfig clientNetworkConfig = new ClientNetworkConfig();
        clientNetworkConfig.setAddresses(List.of("192.168.178.19"));
        clientConfig.setNetworkConfig(clientNetworkConfig);
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
        return client;
    }

    private static String getProfileData(String userName) {
        HazelcastInstance client = getHazelcastHandle();
        IMap<String,String> map = client.getMap("profile-map");
        if (map.containsKey(userName)) {
            System.out.println("Will return from cache");
            String cachedData =  map.get(userName);
            client.shutdown();
            return cachedData;
        } else {
            System.out.println("Will return from backend");
            String profileFromBackend = backendService.getProfileDetails(userName);
             map.put(userName,profileFromBackend, 10, TimeUnit.SECONDS);
             client.shutdown();
             return profileFromBackend;
        }
    }

    private static void updateProfileData(String userName, String newProfileData) {
        HazelcastInstance client = getHazelcastHandle();
        IMap<String,String> map = client.getMap("profile-map");
        backendService.updateProfileDetails(userName, newProfileData);
        map.put(userName,newProfileData, 10, TimeUnit.SECONDS);
        client.shutdown();
    }

    public static void main(String[] args) {
        updateProfileData("user1", "profile-details-1");
        getProfileData("user1");
        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getProfileData("user1");
    }

}
