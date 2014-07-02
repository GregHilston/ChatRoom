package chat;

public class Protocol {
    private static final int WAITING = 0;
    private static final int SENTNAMEREQUEST = 1;
    private static final int CHATTING = 2;
    private int state = WAITING;
    private String userName;
    private int clientNumber;

    public String processInput(String input) {
        String output = null;

        if(state == WAITING) {
            output = "Please enter your name";
            state = SENTNAMEREQUEST;
        }
        else if(state == SENTNAMEREQUEST) {
            userName = input;

            if(ServerInfo.getInstance().addUserName(userName)) {
                output = "Welcome to the chat room " + userName +  ". Type \"/usage\" for a list of commands";
                Logger.getInstance().log("Client " + clientNumber + " now known as \"" + userName +"\"");

                if(ServerInfo.getInstance().numberOfUsers() > 1) { // 1 because this user's name has been stored already
                    output += " Other users chatting: ";
                    for(int userCounter = 0; userCounter < ServerInfo.getInstance().numberOfUsers(); userCounter++) {
                        if(!userName.equals(ServerInfo.getInstance().getUserName(userCounter))) {
                            output += ServerInfo.getInstance().getUserName(userCounter) + " ";
                        }
                    }
                }
                state = CHATTING;
            }
            else {
                output = "Name already in use. Please choose another";
            }
        }
        else if(state == CHATTING) {
            if(input.startsWith("/")) { // Server command prefix
                if(input.equals("/usage")) {
                    output = usage();
                }
                else if(input.equals("/list")) {
                    output = "List of logged in users: ";
                    for(int userCounter = 0; userCounter < ServerInfo.getInstance().numberOfUsers(); userCounter++) {
                        output += ServerInfo.getInstance().getUserName(userCounter) + " ";
                    }

                }
                else {
                    output = "Command not found \"" + input +"\" \n";
                    output += usage();
                }
            }
            else {

                output = "CHATTING";
                Broadcaster.getInstance().broadcastMessage(input, userName);
            }
        }
        return "Server: " + output;
    }

    private String usage() {
        String output = "Usage:";
        output += "\n\t/usage - List of server commands";
        output += "\n\t/list - List of logged in users";

        return output;
    }

    public String getUserName() {
        return userName;
    }

    public void setClientNumber(int clientNumber) {
        this.clientNumber = clientNumber;
    }
}