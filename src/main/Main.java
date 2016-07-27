package main;

import network.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import network.Notify;
import tool.Constants;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InitialInterface.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("HZK_client");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
        Client newClient = new Client();
        Controller_Initial c = loader.getController();
        newClient.start(c);
        c.setServerNotify(newClient.getServer());

    }


    public static void main(String[] args) {
        launch(args);
    }

}
