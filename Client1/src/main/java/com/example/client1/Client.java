package com.example.client1;

import javafx.application.Platform;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;

    public Client(Socket socket) {
        try{
        this.socket = socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Error Creating Client!");
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;


    public void closeEverything() {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error closing resources!");
        }
    }

    public void sendMessageToServer(String messageToServer)
    {
        try {
            if (bufferedWriter != null) {
                bufferedWriter.write(messageToServer);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } else {
                System.out.println("Error: BufferedWriter is null");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message to client!");
            closeEverything();
        }
    }


    public void closeEverything(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter)
    {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null)
            {
                socket.close();
            }
        }catch (IOException e)
        {
            e.printStackTrace();
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    public void receiveMessageFromServer(VBox vboxMessages) {
        new Thread(() -> {
            try {
                while (socket.isConnected()) {
                    String messagesFromServer = bufferedReader.readLine();
                    if (messagesFromServer != null) {
                        Platform.runLater(() -> HelloController.addLabel(messagesFromServer, vboxMessages));
                    } else {
                        // Handle disconnection or end of stream
                        closeEverything();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error receiving message!");
                closeEverything();
            }
        }).start();
    }
}
