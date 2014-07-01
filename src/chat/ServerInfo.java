package chat;

import java.util.ArrayList;

public class ServerInfo {
    private static ServerInfo singleton = new ServerInfo();
    private ArrayList<String> userNames = new ArrayList<String>();

    private ServerInfo() {
        // Singleton
    }


    public static ServerInfo getInstance() {
        return singleton;
    }


    /**
     *
     * @param userName  Proposed UserName
     * @return          True: UserName was accepted    False: UserName already taken
     */
    public Boolean addUserName(String userName) {
        if(userNames.contains(userName)) {
            return false;
        }
        else {
            userNames.add(userName);
            return true;
        }
    }


    /***
     *
     * @param userName
     */
    public void removeUserName(String userName) {
        userNames.remove(userName);
    }


    public int numberOfUsers() {
        return userNames.size();
    }


    public String getUserName(int index) {
        if(index > (userNames.size() - 1))
            return "";

        return userNames.get(index);
    }
}
