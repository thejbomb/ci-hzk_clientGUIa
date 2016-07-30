package main;

import com.google.gson.Gson;
import data.round2.Round2Level1Data;
import data.round2.Round2Level2Data;
import data.round2.Round2Level3Data;
import network.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main extends Application {
    public static final Round2Level1Data R2L1_DATA = getRound2Level1Data();
    public static final Round2Level2Data R2L2_DATA = getRound2Level2Data();
    public static final Round2Level3Data R2L3_DATA = getRound2Level3Data();

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainInterface.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("HZK_client");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
        Client newClient = new Client();
        MainController c = loader.getController();
        newClient.start(c);
        c.setServerNotify(newClient.getServer());

    }


    public static void main(String[] args) {
        launch(args);
    }

    private static Round2Level1Data getRound2Level1Data() {
        String fileName = "src/asset/Round2Level1Data.json";
        try {
            return new Gson().fromJson(new FileReader(fileName), Round2Level1Data.class);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static Round2Level2Data getRound2Level2Data() {
        String fileName = "src/asset/Round2Level2Data.json";
        try {
            return new Gson().fromJson(new FileReader(fileName), Round2Level2Data.class);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;

        }
    }

    private static Round2Level3Data getRound2Level3Data() {
        String fileName = "src/asset/Round2Level3Data.json";
        try {
            return new Gson().fromJson(new FileReader(fileName), Round2Level3Data.class);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
