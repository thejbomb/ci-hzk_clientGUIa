package main;

import com.google.gson.Gson;
import network.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main extends Application {
    public static final data.round2.Level1DataStructure R2L1_DATA = getRound2Level1Data();
    public static final data.round2.Level2DataStructure R2L2_DATA = getRound2Level2Data();
    public static final data.round2.Level3DataStructure R2L3_DATA = getRound2Level3Data();

    public static final data.round4.Level1DataStructure R4L1_DATA = getRound4Level1Data();

    public static final data.round5.Level1DataStructure R5L1_DATA = getRound5Level1Data();
    public static final data.round5.Level2DataStructure R5L2_DATA = getRound5Level2Data();
    public static final data.round5.Level3DataStructure R5L3_DATA = getRound5Level3Data();

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

    private static data.round2.Level1DataStructure getRound2Level1Data() {
        String fileName = "src/data/Round2Level1Data.json";
        try {
            return new Gson().fromJson(new FileReader(fileName), data.round2.Level1DataStructure.class);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static data.round2.Level2DataStructure getRound2Level2Data() {
        String fileName = "src/data/Round2Level2Data.json";
        try {
            return new Gson().fromJson(new FileReader(fileName), data.round2.Level2DataStructure.class);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;

        }
    }

    private static data.round2.Level3DataStructure getRound2Level3Data() {
        String fileName = "src/data/Round2Level3Data.json";
        try {
            return new Gson().fromJson(new FileReader(fileName), data.round2.Level3DataStructure.class);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static data.round4.Level1DataStructure getRound4Level1Data() {
        String fileName = "src/data/Round4Level1Data.json";
        try {
            return new Gson().fromJson(new FileReader(fileName), data.round4.Level1DataStructure.class);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static data.round5.Level1DataStructure getRound5Level1Data() {
        String fileName = "src/data/Round5Level1Data.json";
        try {
            return new Gson().fromJson(new FileReader(fileName), data.round5.Level1DataStructure.class);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static data.round5.Level2DataStructure getRound5Level2Data() {
        String fileName = "src/data/Round5Level2Data.json";
        try {
            return new Gson().fromJson(new FileReader(fileName), data.round5.Level2DataStructure.class);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static data.round5.Level3DataStructure getRound5Level3Data() {
        String fileName = "src/data/Round5Level3Data.json";
        try {
            return new Gson().fromJson(new FileReader(fileName), data.round5.Level3DataStructure.class);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
