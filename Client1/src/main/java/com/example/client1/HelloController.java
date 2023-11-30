package com.example.client1;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Button button_send;
    @FXML
    private TextField tf_messages;
    @FXML
    private VBox vbox_messages;
    @FXML
    private ScrollPane sp_main;

    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            client = new Client(new Socket("localhost", 1234));
            System.out.println("Connected to Server");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add a listener to automatically scroll down when new messages are added
        vbox_messages.heightProperty().addListener((observableValue, number, t1) -> sp_main.setVvalue((Double) t1));

        // Start receiving messages from the server
        client.receiveMessageFromServer(vbox_messages);

        button_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String messageToSend = tf_messages.getText();
                    if (!messageToSend.isEmpty()) {
                        // Create an HBox for the new message
                        HBox hBox = new HBox();
                        hBox.setAlignment(Pos.CENTER_RIGHT);
                        hBox.setPadding(new Insets(5, 5, 5, 10));

                        // Create a TextFlow for styling the message
                        Text text = new Text(messageToSend);
                        TextFlow textFlow = new TextFlow(text);
                        textFlow.setStyle("-fx-text-fill: rgb(239,242,255);" +
                                "-fx-background-color: rgb(15,125,242);" +
                                "-fx-background-radius: 20px");

                        // Add the TextFlow to the HBox
                        hBox.getChildren().add(textFlow);

                        // Add the HBox to the VBox
                        vbox_messages.getChildren().add(hBox);

                        // Send the message to the server
                        client.sendMessageToServer(messageToSend);

                        // Clear the text field after sending the message
                        tf_messages.clear();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // This method adds a new label to the VBox with a message from the server
    public static void addLabel(String messageFromServer, VBox vBox) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(messageFromServer);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(233,2330,235);" +
                "-fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(textFlow);

        // Use Platform.runLater to update UI from a non-JavaFX thread
        Platform.runLater(() -> vBox.getChildren().add(hBox));
    }
}

/*package com.example.client1;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    private Button button_send;
    @FXML
    private TextField tf_messages;
    @FXML
    private VBox vbox_messages;
    @FXML
    private ScrollPane sp_main;

    private Client client;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            client = new Client(new Socket("localhost",1234));
            System.out.println("Connnected to Server");
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                sp_main.setVvalue((Double) t1);
            }
        });

        client.receiveMessageFromServer(vbox_messages);

        button_send.setOnAction(new EventHandler<ActionEvent>() throw Exception
        {
            @Override
            public void handle(ActionEvent event) {
                 String messagesTosend = tf_messages.getText();
                if(!messagesTosend.isEmpty())
                {
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);

                    hBox.setPadding(new Insets(5,5,5,10));

                    Text text = new Text(messagesTosend);
                    TextFlow textFlow = new TextFlow(text);
                    textFlow.setStyle("-fx-text-fill: rgb(239,242,255);" +
                            "-fx-background-color: rgb(15,125,242);" +
                            "-fx-background-radius: 20px");
                   /// textFlow.setPadding(new Insets(5, 10, 5, 10));

                    text.setFill(Color.color(0.934,0.945,0.996));

                    hBox.getChildren().add(textFlow);
                    vbox_messages.getChildren().add(hBox);
                    client.sendMessageToServer(messagesTosend);


                    // Clear the text field after sending the message
                    tf_messages.clear();
                }
            }
        });
    }

    public static void addLabel(String messageFromServer,VBox vBox)
    {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));

        Text text =new Text(messageFromServer);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(233,2330,235);" +
                "-fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5,10,5,10));
        hBox.getChildren().add(textFlow);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);

            }
        });
    }
}
*/
