package com.silverfox.ale;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.PrintWriter;

public class aleChatClientRecieve implements Runnable {

    //private final Controller display;
    private aleChatClient chatClient = new aleChatClient();

    private  Main main = new Main();

    public aleChatClientRecieve(aleChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public static String returnType(String msg) {
        String[] type = msg.split("%%");
        return type[0];
    }

    public static String returnUser(String msg) {
        String[] type = msg.split("%%");
        return type[1];
    }

    public static String returnMsg(String msg) {
        String[] type = msg.split("%%");
        return type[2];
    }

    //@Override
    public void run() {

        while (chatClient.isConnected == true) {

            String MESSAGE = "";
            try {
                MESSAGE = chatClient.reader.readLine();
                if(MESSAGE != null){
                    System.out.println(MESSAGE);
                }

            } catch (IOException ex) {
                chatClient.msgArea.appendText("Cannot Connect! Try Again\n");
            }
//------------------------------------------------------------------------------> Message anylizer + Message append start

            if (returnType(MESSAGE).equals("message")) {
                chatClient.msgArea.appendText("\n" + returnUser(MESSAGE) +
                        " - " + returnMsg(MESSAGE));
                msgToFile(returnUser(MESSAGE) +
                        " - " + returnMsg(MESSAGE) );
            }

            if (returnType(MESSAGE).equals("connect")) {
                System.out.println(MESSAGE);
                chatClient.msgArea.appendText("\n" + returnUser(MESSAGE) +
                        " - " + returnMsg(MESSAGE));
            }

            if (returnType(MESSAGE).equals("disconnect")) {
                System.out.println(MESSAGE);
                chatClient.msgArea.appendText("\n" + returnUser(MESSAGE) +
                        " - " + returnMsg(MESSAGE));
                msgToFile(returnUser(MESSAGE) +
                        " - " + returnMsg(MESSAGE));
            }

        }

    }

    public void msgToFile(String msg){
        try{
            PrintWriter writer
                    = new PrintWriter("messages.txt");
            writer.append(msg);
        }

        catch(Exception e){

        }
    }

    public aleChatClientRecieve(){

    }

}
