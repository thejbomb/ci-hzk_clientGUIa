package main.round2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import network.ServerInteractionInterface;
import tool.Constants;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Level1Controller implements Initializable, ServerInteractionInterface, Runnable {

    @FXML
    private AnchorPane ap_root;
    @FXML
    private GridPane gp_levelTitle;
    @FXML
    private GridPane gp_instruction;
    @FXML
    private GridPane gp_example;

    private ServerInteractionInterface round2ControllerNotify;

    private Thread thread;

    public void setNotify(ServerInteractionInterface notify) {
        round2ControllerNotify = notify;
    }

    public void init() {
        thread.start();
    }

    @FXML
    private void handleMouseClick(MouseEvent e) {

    }

    @Override
    public void writeToServer(int command) {
        switch (command) {
            case Constants.DIS_R1L1_EX:
                gp_instruction.setVisible(false);
                gp_example.setVisible(true);
                System.out.println(gp_example.isVisible());
                break;
            default:
                break;
        }
    }

    @Override
    public void writeToServer(int command, int source) {

    }

    @Override
    public void writeToServer(int command, LinkedList<String> data) {

    }

    @Override
    public void writeToServer(int command, LinkedList<String> data, int source) {

    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            gp_levelTitle.setVisible(false);
            gp_instruction.setVisible(true);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage() + " " + getClass());
        }
        thread.interrupt();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gp_levelTitle.setVisible(true);
        gp_instruction.setVisible(false);
        gp_example.setVisible(false);

        thread = new Thread(this);
    }


}
