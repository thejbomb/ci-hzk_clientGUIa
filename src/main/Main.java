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
    public static final data.round2.Level1DataStructure R2L1_DATA;
    public static final data.round2.Level2DataStructure R2L2_DATA;
    public static final data.round2.Level3DataStructure R2L3_DATA;

    public static final data.round4.DataStructure R4L1_DATA;
    public static final data.round4.DataStructure R4L2_DATA;
    public static final data.round4.Level3DataStructure R4L3_DATA;

    public static final data.round5.Level1DataStructure R5L1_DATA;
    public static final data.round5.Level2DataStructure R5L2_DATA;
    public static final data.round5.Level3DataStructure R5L3_DATA;

    static {
        try {
            String fileName = "src/data/Round2Level1Data.json";
            R2L1_DATA = new Gson().fromJson(new FileReader(fileName), data.round2.Level1DataStructure.class);
            fileName = "src/data/Round2Level2Data.json";
            R2L2_DATA = new Gson().fromJson(new FileReader(fileName), data.round2.Level2DataStructure.class);
            fileName = "src/data/Round2Level3Data.json";
            R2L3_DATA = new Gson().fromJson(new FileReader(fileName), data.round2.Level3DataStructure.class);
            fileName = "src/data/Round4Level1Data.json";
            R4L1_DATA = new Gson().fromJson(new FileReader(fileName), data.round4.DataStructure.class);
            fileName = "src/data/Round4Level2Data.json";
            R4L2_DATA = new Gson().fromJson(new FileReader(fileName), data.round4.DataStructure.class);
            fileName = "src/data/Round4Level3Data.json";
            R4L3_DATA = new Gson().fromJson(new FileReader(fileName), data.round4.Level3DataStructure.class);
            fileName = "src/data/Round5Level1Data.json";
            R5L1_DATA = new Gson().fromJson(new FileReader(fileName), data.round5.Level1DataStructure.class);
            fileName = "src/data/Round5Level2Data.json";
            R5L2_DATA = new Gson().fromJson(new FileReader(fileName), data.round5.Level2DataStructure.class);
            fileName = "src/data/Round5Level3Data.json";
            R5L3_DATA = new Gson().fromJson(new FileReader(fileName), data.round5.Level3DataStructure.class);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Could not initialize static levelData members.");
        }
    }


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
}
