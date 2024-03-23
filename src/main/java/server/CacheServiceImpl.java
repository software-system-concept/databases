package server;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CacheServiceImpl {

    private BackendServiceImpl backendService = new BackendServiceImpl();

    private HazelcastInstance getHazelcastHandle(){
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName("TestCluster1");
        //clientConfig.setCredentials(new UsernamePasswordCredentials("", ""));
        ClientNetworkConfig clientNetworkConfig = new ClientNetworkConfig();
        clientNetworkConfig.setAddresses(List.of("192.168.178.19"));
        clientConfig.setNetworkConfig(clientNetworkConfig);
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
        return client;
    }

    public String getProfileData(String userName) {
        HazelcastInstance client = getHazelcastHandle();
        IMap<String,String> map = client.getMap("profile-map");
        if (map.containsKey(userName)) {
            return map.get(userName);
        } else {
            String profileFromBackend = backendService.getProfileDetails(userName);
             map.put(userName,profileFromBackend, 3600, TimeUnit.SECONDS);
             client.shutdown();
             return profileFromBackend;
        }
    }

    public void updateProfileData(String userName, String newProfileData) {
        HazelcastInstance client = getHazelcastHandle();
        IMap<String,String> map = client.getMap("profile-map");
        backendService.updateProfileDetails(userName, newProfileData);
        map.put(userName,newProfileData, 3600, TimeUnit.SECONDS);
    }

}
