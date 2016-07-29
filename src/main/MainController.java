package main;


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

    private ServerInteractionInterface serverNotify;

    private ServerInteractionInterface round2ControllerNotify;


    public void setServerNotify(ServerInteractionInterface notify) {
        this.serverNotify = notify;
    }

    private LinkedList<String> packageStringData(String data) {
        LinkedList<String> result = new LinkedList<>();
        result.add(data);

        return result;
    }

    private LinkedList<String> packageIntegerData(int i) {
        LinkedList<String> result = new LinkedList<>();
        String data = Integer.toString(i);
        result.add(data);

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
                serverNotify.writeToServer(Constants.USER_CONNECT, packageStringData(tf_validationId.getText()));
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
    public void writeToServer(int command, int source) {

    }

    @Override
    public void writeToServer(int command, LinkedList<String> data) {

    }

    @Override
    public void writeToServer(int command, LinkedList<String> data, int source) {

    }

    @Override
    public void handleServerData(int command, LinkedList<String> data) {
        switch (command) {
            case Constants.LOGIN_SUCCESS:
                cb_clientReady.setDisable(true);
                tf_validationId.setEditable(false);
                lb_message.setText("OK!");
                lb_message.setVisible(true);
                break;
            case Constants.BEGIN_COMP:
                gp_pane.setVisible(false);
                ap_round2Interface.setVisible(true);
                ap_round2InterfaceController.init();
                break;
            case Constants.BEGIN_R1L2:
                break;
            case Constants.BEGIN_R1L3:
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
                round2ControllerNotify.writeToServer(command);
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

        ap_round2InterfaceController.setNotify(this);

        round2ControllerNotify = ap_round2InterfaceController;

    }


}
