package main;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import network.Notify;
import tool.Constants;

public class Controller_Initial implements Notify {
    @FXML
    private CheckBox cb_clientReady;
    @FXML
    private TextField tf_userName;
    @FXML
    private Label lb_errNameNotFound;

    private Notify serverNotify;


    public void setServerNotify(Notify notify) {
        this.serverNotify = notify;
    }



    @FXML
    private void handleAction(ActionEvent e) {

    }

    @FXML
    private void handleMouseClick(MouseEvent e) {


        if (e.getSource() == cb_clientReady) {
            lb_errNameNotFound.setVisible(false);
            if (tf_userName.getText().compareTo("") != 0) {
                cb_clientReady.setSelected(true);
                try {
                    serverNotify.takeNotice(Constants.USER_CONNECT, Integer.parseInt(tf_userName.getText()));
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    System.out.println(ex.getMessage() + " " + getClass());
                    System.out.println("Only numbers are allowed");
                }
            } else if (tf_userName.getText().compareTo("") == 0) {
                lb_errNameNotFound.setVisible(true);
                cb_clientReady.setSelected(false);
            } else {
                cb_clientReady.setSelected(false);
            }


        }
    }

    private void errorsHandler(String message) {
        if (message.compareTo("Error 999") == 0) {
            System.out.println("Error 9999: ID not found");
            cb_clientReady.setSelected(false);
        } else if (message.compareTo("Connection reset") == 0) {
            System.out.println("Disconnected from server");
        }
    }

    @Override
    public void takeNotice() {

    }

    @Override
    public void takeNotice(int command) {
        switch (command) {
            case Constants.BEGIN_COMP:
                Stage stage = (Stage) cb_clientReady.getScene().getWindow();
                Round1Introduction openScene = new Round1Introduction();
                try {
                    openScene.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Constants.LOGIN_SUCCESS:

                tf_userName.setEditable(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void takeNotice(int command, Object data) {
        switch (command) {
            case 1:
                Stage stage = (Stage) cb_clientReady.getScene().getWindow();
                Round1Introduction openScene = new Round1Introduction();
                try {
                    openScene.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Constants.ERROR: //error message
                errorsHandler((String) data);
                break;
            default:
                break;
        }
    }
}
