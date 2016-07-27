package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Round1Instruction {
    public void start(Stage window) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Round1Instruction.fxml"));
        Scene scene = new Scene(root, 1280, 720);
        window.setScene(scene);
        window.show();
    }
}
