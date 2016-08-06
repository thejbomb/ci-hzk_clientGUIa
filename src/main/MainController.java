package main;


import data.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import main.round2.Round2Controller;
import network.ServerHandlerInterface;
import network.ServerInteractionInterface;
import tool.Constants;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class MainController implements ServerInteractionInterface, Initializable, ServerHandlerInterface {
    private static final String CSS_ZH_TITLE = "zh-title";
    private static final String CSS_EN_TITLE = "en-title";
    private static final String CSS_EN_MESSAGE = "en-message";

    @FXML
    private AnchorPane ap_round2Interface;
    @FXML
    private Round2Controller ap_round2InterfaceController;
    @FXML
    private AnchorPane ap_root;
    @FXML
    private GridPane gp_pane;
    @FXML
    private Label lb_zhTitle;
    @FXML
    private Label lb_enTitle;
    @FXML
    private CheckBox cb_clientReady;
    @FXML
    private TextField tf_validationId;
    @FXML
    private Label lb_message;

    private static ServerInteractionInterface serverController;

    protected UserData userData;

    private int currentRound = 0;

    public void setServerNotify(ServerInteractionInterface controller) {
        serverController = controller;
    }

    private LinkedList<String> packageData(Object obj) {
        System.out.println(obj.getClass());
        LinkedList<String> result = new LinkedList<>();
        if (obj.getClass() == String.class) {
            result.add((String) obj);
        } else if (obj.getClass() == Integer.class) {
            String data = Integer.toString((int) obj);
            result.add(data);
        }

        return result;
    }

    @FXML
    private void handleAction(ActionEvent e) {
        if (e.getSource() == tf_validationId) {
            handleUserInput();
        }
    }

    @FXML
    private void handleMouseClick(MouseEvent e) {


        if (e.getSource() == cb_clientReady) {
            handleUserInput();


        }
    }

    private void handleUserInput() {
        if (tf_validationId.getText().compareTo("") != 0) {
            cb_clientReady.setSelected(true);
            try {
                serverController.writeToServer(Constants.USER_CONNECT, packageData(tf_validationId.getText()));
            } catch (NumberFormatException ex) {
                lb_message.setText("Only numeric characters are allowed");
                lb_message.setVisible(true);
                ex.printStackTrace();
                System.out.println("[HANDLED]" + ex.getMessage() + " " + getClass());
            }
        } else if (tf_validationId.getText().compareTo("") == 0) {
            lb_message.setText("Field is empty.");
            lb_message.setVisible(true);
            cb_clientReady.setSelected(false);
        } else {
            cb_clientReady.setSelected(false);
        }
    }

    @Override
    public void writeToServer(int command) {

    }

    @Override
    public void writeToServer(int command, LinkedList<String> data) {
        MainController.serverController.writeToServer(command, data);
    }

    @Override
    public void handleServerData(int command, LinkedList<String> data) {
        switch (command) {
            case Constants.LOGIN_SUCCESS:
                userData = new UserData(data.getFirst(),data.getLast());
                cb_clientReady.setDisable(true);
                tf_validationId.setEditable(false);
                lb_message.setText("Welcome " + userData.getName() + "!");
                lb_message.setVisible(true);
                break;
            case Constants.BEGIN_COMP:
                gp_pane.setVisible(false);
                ap_round2Interface.setVisible(true);
                ap_round2InterfaceController.init(userData);
                break;
            case Constants.BEGIN_R2L1:
                currentRound = Constants.ROUND2;
                ap_round2InterfaceController.handleServerData(command, data);
                break;
            case Constants.BEGIN_R2L2:
                currentRound = Constants.ROUND2;
                ap_round2InterfaceController.handleServerData(command,data);
                break;
            case Constants.ERROR_ID_NOT_FOUND:
                lb_message.setText("ID was not found.");
                lb_message.setVisible(true);
                cb_clientReady.setSelected(false);
                break;
            case Constants.ERROR_CONNECTION_LOST:
                lb_message.setText("Connection lost");
                break;
            default:
                switch (currentRound) {
                    case Constants.ROUND1:
                        //ap_round2InterfaceController.handleServerData(command, data);
                        break;
                    case Constants.ROUND2:
                        ap_round2InterfaceController.handleServerData(command, data);
                        break;
                    case Constants.ROUND3:
                        //  ap_round2InterfaceController.handleServerData(command, data);
                        break;
                    case Constants.ROUND4:
                        // ap_round2InterfaceController.handleServerData(command, data);
                        break;
                    case Constants.ROUND5:
                        // ap_round2InterfaceController.handleServerData(command, data);
                        break;
                }
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lb_message.setVisible(false);

        ap_root.getStyleClass().addAll("pane");

        lb_zhTitle.setId(CSS_ZH_TITLE);
        lb_enTitle.setId(CSS_EN_TITLE);
        lb_message.setId(CSS_EN_MESSAGE);

        AnchorPane.setBottomAnchor(ap_round2Interface, 0.0);
        AnchorPane.setTopAnchor(ap_round2Interface, 0.0);
        AnchorPane.setLeftAnchor(ap_round2Interface, 0.0);
        AnchorPane.setRightAnchor(ap_round2Interface, 0.0);

        ap_round2Interface.setVisible(false);
    }


}
