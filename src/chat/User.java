package chat;

import java.util.ArrayList;

public class User {
    private String userName;
    private int clientNumber;
    private ArrayList<String> userIgnoreList = new ArrayList<String>();

    public User(int clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public ArrayList<String> getUserIgnoreList() {
        return userIgnoreList;
    }

    public void setUserIgnoreList(ArrayList<String> userIgnoreList) {
        this.userIgnoreList = userIgnoreList;
    }
}
