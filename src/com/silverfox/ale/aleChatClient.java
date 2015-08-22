package com.silverfox.ale;

import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class aleChatClient {

    private Socket sock;
    public BufferedReader reader;
    PrintWriter writer;
    Boolean isConnected = false;

    //GUI Components
    TextArea msgArea;
    TextArea msgInputArea;
    String userName;

    public void connect(TextArea messageArea, TextArea messageInputArea, String username, String chatRoom){
        if (isConnected == false) {

            msgArea = messageArea;
            msgInputArea = messageInputArea;
            userName = username;

            messageArea.setText("");
            String IP = "197.83.220.202";

            try {
                sock = new Socket(IP, 6129);
                InputStreamReader streamreader
                        = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream(), true);

                //Sends connect message
                writer.println("connect" + "%%" + username + "%%"
                        + " Has Connected" + "%%" + chatRoom);

                //Sets variables to connected state
                isConnected = true;

                //Listen Thread
                aleChatClientRecieve chatClientRecieve = new aleChatClientRecieve(this);
                Thread listenThread = new Thread(chatClientRecieve);
                listenThread.start();


            } catch (Exception ex) {
                messageArea.appendText("Cannot Connect! Try Again\n");
            }
            // ListenThread();
        } else if (isConnected == true) {
            messageArea.appendText("You are connected\n");
        }

    }

    public void send(TextArea messageArea, TextArea messageInputArea, String username, String chatRoom){
        if ((messageInputArea.getText()).equals("")) {
            messageInputArea.setText("");
            messageInputArea.requestFocus();
        } else {
            if(isConnected == true){
                try {
                    //Sends unencrypted message
                    writer.println("message" + "%%" + username + "%%"
                            + messageInputArea.getText() + "%%" + chatRoom);
                } catch (Exception ex) {
                    messageArea.appendText("Message was not sent\n");
                }
            }else{
                messageArea.appendText("Not Connected");
            }

            messageInputArea.setText("");
            messageInputArea.requestFocus();
        }

    }

    public void disconnect(TextArea messageArea, TextArea messageInputArea, String username){
        try {
            //Sends disconnect message
            writer.println("disconnect" + "%%" + username + "%%"
                    + messageInputArea.getText());

            //Resets variables and closes readers, writers, etc.
            writer.close();
            reader.close();
            isConnected = false;

        } catch (Exception ex) {
            messageArea.appendText("Cannot disconnect\n");
        }
    }
    public void test(TextArea messageArea, TextArea messageInputArea){
        messageArea.appendText("Test");
        messageInputArea.appendText("test");
    }


    public aleChatClient() {
    }
}

