import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ChatProtocol {
    private static final int WAITING = 0;
    private static final int SENTNAMEREQUEST = 1;
    private static final int CHATTING = 2;
    private int state = WAITING;
    private static ArrayList<String> userNames = new ArrayList<String>();
    private String userName;

    public String processInput(String input) {
        String output = null;

        if(state == WAITING) {
            output = "Please enter your name";
            state = SENTNAMEREQUEST;
        }
        else if(state == SENTNAMEREQUEST) {
            userName = input;

            if(userNames.contains(userName)) {
                output = "Name already in use. Please choose another";
            }
            else {
                output = "Welcome to the chat room " + userName +  ".";

                if(userNames.size() != 0) {
                    output += " Other users chatting: ";
                    for(int userCounter = 0; userCounter < userNames.size(); userCounter++) {
                        output += userNames.get(userCounter) + " ";
                    }
                }

                userNames.add(userName);
                state = CHATTING;
            }
        }
        else if(state == CHATTING) {

        }

        return output;
    }
}