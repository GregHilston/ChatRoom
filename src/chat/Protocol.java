package chat;

public class Protocol {
    private static final int WAITING = 0;
    private static final int SENTNAMEREQUEST = 1;
    private static final int CHATTING = 2;
    private int state = WAITING;
    private User user;

    public Protocol(int clientNumber) {
        user = new User(clientNumber);
    }


    /***
     * Processes the client's input, updating the client's current state and returning the Server's reply
     *
     * @param input     Client's input
     * @return          Server's reply
     */
    public String processInput(String input) {
        String output = null;

        if(state == WAITING) {
            output = "Please enter your name";
            state = SENTNAMEREQUEST;
        }
        else if(state == SENTNAMEREQUEST) {
            user.setUserName(input);

            if(ServerInfo.getInstance().addUserName(getUserName())) {
                output = "Welcome to the chat room " + getUserName() +  ". Type \"/usage\" for a list of commands";
                Logger.getInstance().log("Client " + getClientNumber() + " now known as \"" + getUserName() +"\"");

                if(ServerInfo.getInstance().getNumberOfUsers() > 1) { // 1 because this user's name has been stored already
                    output += " Other users chatting: ";
                    for(int userCounter = 0; userCounter < ServerInfo.getInstance().getNumberOfUsers(); userCounter++) {
                        if(!getUserName().equals(ServerInfo.getInstance().getUserName(userCounter))) {
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
                    for(int userCounter = 0; userCounter < ServerInfo.getInstance().getNumberOfUsers(); userCounter++) {
                        output += ServerInfo.getInstance().getUserName(userCounter) + " ";
                    }

                }
                else if(input.startsWith("/whisper")) {
                    output = Broadcaster.getInstance().whisper(input, getUserName());
                }
                else {
                    output = "Command not found \"" + input +"\" \n";
                    output += usage();
                }
            }
            else {

                output = "CHATTING";
                Broadcaster.getInstance().broadcastMessage(input, getUserName());
            }
        }
        return "Server: " + output;
    }


    /***
     * Shows the usage for the server commands
     *
     * @return      Formatted string of server commands
     */
    private String usage() {
        String output = "Usage:";
        output += "\n\t/usage - List of server commands";
        output += "\n\t/list - List of logged in users";
        output += "\n\t/whisper [userName] [message] - Send a private message to one user";

        return output;
    }


    /***
     * Wrapper for this Protocol's User's getUserName method
     *
     * @return      This protocol's user's registered username
     */
    public String getUserName() {
        return user.getUserName();
    }


    /***
     * Wrapper for this Protocol's User's getClientNumber method
     *
     * @return      This protocol's user's client number
     */
    public int getClientNumber() {
        return user.getClientNumber();
    }
}