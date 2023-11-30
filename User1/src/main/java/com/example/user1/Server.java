package com.example.user1;

import javafx.application.Platform;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Server(ServerSocket serverSocket) {
        try {
            this.serverSocket = serverSocket;
            this.socket = serverSocket.accept();
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error Creating server!");
        }
    }

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

    public void sendMessageFromClient(String messageToClient) {
        try {
            if (bufferedWriter != null) {
                bufferedWriter.write(messageToClient);
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

    public void receiveMessageFromClient(VBox vboxMessage) {
        new Thread(() -> {
            try {
                while (socket.isConnected()) {
                    String messageFromClient = bufferedReader.readLine();
                    if (messageFromClient != null) {
                        Platform.runLater(() -> ChatEmployer.addlabel(messageFromClient, vboxMessage));
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

/*package com.example.dtl;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;

    public Server(ServerSocket serverSocket, Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        this.serverSocket = serverSocket;
        this.socket = socket;
        this.bufferedReader = bufferedReader;
        this.bufferedWriter = bufferedWriter;
    }

    private BufferedWriter bufferedWriter;
    public Server(ServerSocket serverSocket) {
        try {
            this.serverSocket = serverSocket;
            this.socket = serverSocket.accept();
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Error Creating server!");
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

    public void sendMessageFromClient(String messageToClient)
    {
        try{
            bufferedWriter.write(messageToClient);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Error sending message to client!");
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    public void receiveMessageFromClient(VBox vboxMessage) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(socket.isConnected()){
                    try{
                        String messageFromClient = bufferedReader.readLine();
                        Controller.addLabel(messageFromClient,vboxMessage);
                    }catch (IOException e)
                    {
                        e.printStackTrace();
                        System.out.println("Error for receving message!");
                        closeEverything(socket,bufferedReader,bufferedWriter);
                        break;
                    }

                }
            }
        }).start();
    }
}
*/
