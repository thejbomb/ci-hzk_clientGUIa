/*package main;

import com.google.gson.Gson;
import javafx.scene.input.KeyCombination;
import network.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main extends Application {
    public static final double WINDOW_WIDTH = 1280;
    public static final double WINDOW_HEIGHT = 720;

    public static final data.round1.Level1DataStructure R1L1_DATA;
    public static final data.round1.Level2DataStructure R1L2_DATA;
    public static final data.round1.Level3DataStructure R1L3_DATA;

    public static final data.round2.Level1DataStructure R2L1_DATA;
    public static final data.round2.Level2DataStructure R2L2_DATA;
    public static final data.round2.Level3DataStructure R2L3_DATA;

    public static final data.round3.Level1DataStructure R3L1_DATA;
    public static final data.round3.Level2DataStructure R3L2_DATA;
    public static final data.round3.Level3DataStructure R3L3_DATA;

    public static final data.round4.DataStructure R4L1_DATA;
    public static final data.round4.DataStructure R4L2_DATA;
    public static final data.round4.Level3DataStructure R4L3_DATA;

    public static final data.round5.Level1DataStructure R5L1_DATA;
    public static final data.round5.Level2DataStructure R5L2_DATA;
    public static final data.round5.Level3DataStructure R5L3_DATA;

    static {
        try {

            String fileName = System.getProperty("user.dir") + "/src/data/Round1Level1Data.json";
            R1L1_DATA = new Gson().fromJson(new FileReader(fileName), data.round1.Level1DataStructure.class);
            fileName = System.getProperty("user.dir") + "/src/data/Round1Level2Data.json";
            R1L2_DATA = new Gson().fromJson(new FileReader(fileName), data.round1.Level2DataStructure.class);
            fileName = System.getProperty("user.dir") + "/src/data/Round1Level3Data.json";
            R1L3_DATA = new Gson().fromJson(new FileReader(fileName), data.round1.Level3DataStructure.class);
            fileName = System.getProperty("user.dir") + "/src/data/Round2Level1Data.json";
            R2L1_DATA = new Gson().fromJson(new FileReader(fileName), data.round2.Level1DataStructure.class);
            fileName = System.getProperty("user.dir") + "/src/data/Round2Level2Data.json";
            R2L2_DATA = new Gson().fromJson(new FileReader(fileName), data.round2.Level2DataStructure.class);
            fileName = System.getProperty("user.dir") +"/src/data/Round2Level3Data.json";
            R2L3_DATA = new Gson().fromJson(new FileReader(fileName), data.round2.Level3DataStructure.class);
            fileName = System.getProperty("user.dir") +"/src/data/Round3Level1Data.json";
            R3L1_DATA = new Gson().fromJson(new FileReader(fileName), data.round3.Level1DataStructure.class);
            fileName = System.getProperty("user.dir") +"/src/data/Round3Level2Data.json";
            R3L2_DATA = new Gson().fromJson(new FileReader(fileName), data.round3.Level2DataStructure.class);
            fileName = System.getProperty("user.dir") +"/src/data/Round3Level3Data.json";
            R3L3_DATA = new Gson().fromJson(new FileReader(fileName), data.round3.Level3DataStructure.class);
            fileName = System.getProperty("user.dir") +"/src/data/Round4Level1Data.json";
            R4L1_DATA = new Gson().fromJson(new FileReader(fileName), data.round4.DataStructure.class);
            fileName = System.getProperty("user.dir") +"/src/data/Round4Level2Data.json";
            R4L2_DATA = new Gson().fromJson(new FileReader(fileName), data.round4.DataStructure.class);
            fileName = System.getProperty("user.dir") +"/src/data/Round4Level3Data.json";
            R4L3_DATA = new Gson().fromJson(new FileReader(fileName), data.round4.Level3DataStructure.class);
            fileName = System.getProperty("user.dir") +"/src/data/Round5Level1Data.json";
            R5L1_DATA = new Gson().fromJson(new FileReader(fileName), data.round5.Level1DataStructure.class);
            fileName = System.getProperty("user.dir") +"/src/data/Round5Level2Data.json";
            R5L2_DATA = new Gson().fromJson(new FileReader(fileName), data.round5.Level2DataStructure.class);
            fileName = System.getProperty("user.dir") +"/src/data/Round5Level3Data.json";
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
        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.show();
        Client newClient = new Client();
        MainController c = loader.getController();
        newClient.start(c);
        c.setServerNotify(newClient.getServer());
    }


    public static void main(String[] args) {
        launch(args);
    }
}*/

package main;

import com.google.gson.Gson;
import javafx.scene.input.KeyCombination;
import network.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {
    public static final double WINDOW_WIDTH = 1280;
    public static final double WINDOW_HEIGHT = 720;

    public static final data.round1.Level1DataStructure R1L1_DATA;
    public static final data.round1.Level2DataStructure R1L2_DATA;
    public static final data.round1.Level3DataStructure R1L3_DATA;

    public static final data.round2.Level1DataStructure R2L1_DATA;
    public static final data.round2.Level2DataStructure R2L2_DATA;
    public static final data.round2.Level3DataStructure R2L3_DATA;

    public static final data.round3.Level1DataStructure R3L1_DATA;
    public static final data.round3.Level2DataStructure R3L2_DATA;
    public static final data.round3.Level3DataStructure R3L3_DATA;

    public static final data.round4.DataStructure R4L1_DATA;
    public static final data.round4.DataStructure R4L2_DATA;
    public static final data.round4.Level3DataStructure R4L3_DATA;

    public static final data.round5.Level1DataStructure R5L1_DATA;
    public static final data.round5.Level2DataStructure R5L2_DATA;
    public static final data.round5.Level3DataStructure R5L3_DATA;

    static {
        String fileName = System.getProperty("user.dir") + "/src/data/Round1Level1Data.json";
        R1L1_DATA = new Gson().fromJson(ReadClassFromJson(fileName), data.round1.Level1DataStructure.class);
        fileName = System.getProperty("user.dir") + "/src/data/Round1Level2Data.json";
        R1L2_DATA = new Gson().fromJson(ReadClassFromJson(fileName), data.round1.Level2DataStructure.class);
        fileName = System.getProperty("user.dir") + "/src/data/Round1Level3Data.json";
        R1L3_DATA = new Gson().fromJson(ReadClassFromJson(fileName), data.round1.Level3DataStructure.class);
        fileName = System.getProperty("user.dir") + "/src/data/Round2Level1Data.json";
        R2L1_DATA = new Gson().fromJson(ReadClassFromJson(fileName), data.round2.Level1DataStructure.class);
        fileName = System.getProperty("user.dir") + "/src/data/Round2Level2Data.json";
        R2L2_DATA = new Gson().fromJson(ReadClassFromJson(fileName), data.round2.Level2DataStructure.class);
        fileName = System.getProperty("user.dir") +"/src/data/Round2Level3Data.json";
        R2L3_DATA = new Gson().fromJson(ReadClassFromJson(fileName), data.round2.Level3DataStructure.class);
        fileName = System.getProperty("user.dir") +"/src/data/Round3Level1Data.json";
        R3L1_DATA = new Gson().fromJson(ReadClassFromJson(fileName), data.round3.Level1DataStructure.class);
        fileName = System.getProperty("user.dir") +"/src/data/Round3Level2Data.json";
        R3L2_DATA = new Gson().fromJson(ReadClassFromJson(fileName), data.round3.Level2DataStructure.class);
        fileName = System.getProperty("user.dir") +"/src/data/Round3Level3Data.json";
        R3L3_DATA = new Gson().fromJson(ReadClassFromJson(fileName), data.round3.Level3DataStructure.class);
        fileName = System.getProperty("user.dir") +"/src/data/Round4Level1Data.json";
        R4L1_DATA = new Gson().fromJson(ReadClassFromJson(fileName), data.round4.DataStructure.class);
        fileName = System.getProperty("user.dir") +"/src/data/Round4Level2Data.json";
        R4L2_DATA = new Gson().fromJson(ReadClassFromJson(fileName), data.round4.DataStructure.class);
        fileName = System.getProperty("user.dir") +"/src/data/Round4Level3Data.json";
        R4L3_DATA = new Gson().fromJson(ReadClassFromJson(fileName), data.round4.Level3DataStructure.class);
        fileName = System.getProperty("user.dir") +"/src/data/Round5Level1Data.json";
        R5L1_DATA = new Gson().fromJson(ReadClassFromJson(fileName), data.round5.Level1DataStructure.class);
        fileName = System.getProperty("user.dir") +"/src/data/Round5Level2Data.json";
        R5L2_DATA = new Gson().fromJson(ReadClassFromJson(fileName), data.round5.Level2DataStructure.class);
        fileName = System.getProperty("user.dir") +"/src/data/Round5Level3Data.json";
        R5L3_DATA = new Gson().fromJson(ReadClassFromJson(fileName), data.round5.Level3DataStructure.class);
    }

    private static String ReadClassFromJson(String t_filename)
    {
        System.out.println("Reading json file");
        File file = new File(t_filename);
        String targetString = "";
        try
        {
            Reader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");

            //StringReader stringreader = new StringReader("text");
            int intValueOfChar;

            while ((intValueOfChar = reader.read()) != -1) {
                targetString += (char) intValueOfChar;
            }

            reader.close();
        }
        catch(java.io.UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return null;
        }
        catch(java.io.IOException e)
        {
            e.printStackTrace();
            return null;
        }

        //System.out.println(t_filename + ": " + targetString);

        return targetString;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainInterface.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("HZK_client");
        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
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

