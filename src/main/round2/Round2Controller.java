package main.round2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import main.MainController;
import network.ServerHandlerInterface;
import network.ServerInteractionInterface;
import tool.Constants;


import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Round2Controller extends MainController implements Initializable, Runnable, ServerInteractionInterface, ServerHandlerInterface {

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

    private int currentLevel = -1;

    private Thread thread;

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

    }


    @Override
    public void writeToServer(int command, LinkedList<String> data) {

    }

    @Override
    public void handleServerData(int command, LinkedList<String> data) {
        switch (command) {
            case Constants.BEGIN_R2L1:
                currentLevel = Constants.LEVEL1;
                gp_titlePane.setVisible(false);
                gp_round2.setVisible(false);
                System.out.println(gp_round2.isVisible());
                ap_level1Interface.setVisible(true);
                ap_level1InterfaceController.init();
                break;
            default:
                switch (currentLevel) {
                    case Constants.LEVEL1:
                        ap_level1InterfaceController.handleServerData(command, data);
                        break;
                    case Constants.LEVEL2:
                        break;
                    case Constants.LEVEL3:
                        break;
                    default:
                        break;
                }

        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AnchorPane.setBottomAnchor(ap_level1Interface, 0.0);
        AnchorPane.setTopAnchor(ap_level1Interface, 0.0);
        AnchorPane.setLeftAnchor(ap_level1Interface, 0.0);
        AnchorPane.setRightAnchor(ap_level1Interface, 0.0);
        ap_level1Interface.setVisible(false);
    }

}
