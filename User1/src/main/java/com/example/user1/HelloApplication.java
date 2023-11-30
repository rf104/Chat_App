package com.example.user1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        try {
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Server");
            stage.setScene(scene);
            stage.show();
        }catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Error!");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}