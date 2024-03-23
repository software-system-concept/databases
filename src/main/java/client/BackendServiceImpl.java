package client;

import java.util.HashMap;
import java.util.Map;

public class BackendServiceImpl {

    private Map profileData = new HashMap<String, String>();

    public String getProfileDetails(String username) {
        return (String) profileData.get(username);
    }

    public void updateProfileDetails(String username, String profileDate) {
        profileData.put(username, profileDate);
    }
}
