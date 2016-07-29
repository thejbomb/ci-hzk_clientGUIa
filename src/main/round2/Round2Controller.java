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

public class Round2Controller implements Initializable, Runnable, ServerInteractionInterface {

    @FXML
    private AnchorPane ap_level1Interface;
    @FXML
    private Level1Controller ap_level1InterfaceController;

    @FXML
    private AnchorPane ap_root;
    @FXML
    private GridPane gp_titlePane;
    @FXML
    private GridPane gp_round2;

    private int activeLevel = -1;

    private Thread thread;

    private ServerInteractionInterface mainControllerNotify;

    private ServerInteractionInterface level1ControllerNotify;

    public void setNotify(ServerInteractionInterface notify) {
        mainControllerNotify = notify;
    }

    public void init() {
        thread = new Thread(this);
        thread.start();
    }

    @FXML
    private void handleMouseClick(MouseEvent e) {

    }


    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            gp_titlePane.setVisible(false);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage() + " " + getClass());
        }
        thread.interrupt();
    }

    @Override
    public void writeToServer(int command) {
        switch (command) {
            case Constants.BEGIN_R1L1:
                activeLevel = Constants.LEVEL1;
                gp_titlePane.setVisible(false);
                gp_round2.setVisible(false);
                ap_level1Interface.setVisible(true);
                ap_level1InterfaceController.init();
                break;
            case Constants.BEGIN_R1L2:
                activeLevel = Constants.LEVEL2;
                break;
            case Constants.BEGIN_R1L3:
                activeLevel = Constants.LEVEL3;
                break;
            default:
                level1ControllerNotify.writeToServer(command);
                break;
        }
    }

    @Override
    public void writeToServer(int command, int source) {
        if (source == Constants.SRC_SERVER) {
            switch (command) {
                default:
                    switch (activeLevel) {
                        case Constants.LEVEL1:
                            level1ControllerNotify.writeToServer(command);
                            break;
                        case Constants.LEVEL2:
                            break;
                        case Constants.LEVEL3:
                            break;
                        default:
                            break;
                    }
                    break;
            }
        } else if (source == Constants.SRC_R2L1) {
            switch (command) {
                default:
                    break;
            }
        } else if (source == Constants.SRC_R2L2) {
            switch (command) {
                default:
                    break;
            }
        } else if (source == Constants.SRC_R2L3) {
            switch (command) {
                default:
                    break;
            }
        }
    }

    @Override
    public void writeToServer(int command, LinkedList<String> data) {

    }

    @Override
    public void writeToServer(int command, LinkedList<String> data, int source) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AnchorPane.setBottomAnchor(ap_level1Interface, 0.0);
        AnchorPane.setTopAnchor(ap_level1Interface, 0.0);
        AnchorPane.setLeftAnchor(ap_level1Interface, 0.0);
        AnchorPane.setRightAnchor(ap_level1Interface, 0.0);
        ap_level1Interface.setVisible(false);

        ap_level1InterfaceController.setNotify(this);
        level1ControllerNotify = ap_level1InterfaceController;
    }
}
