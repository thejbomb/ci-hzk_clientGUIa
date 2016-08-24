package main.round4;

import data.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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

public class Round4Controller extends MainController implements Initializable, Runnable, ServerInteractionInterface, ServerHandlerInterface {

    @FXML
    private AnchorPane ap_level1Interface;
    @FXML
    private Level1Controller ap_level1InterfaceController;
    @FXML
    private AnchorPane ap_level2Interface;
    @FXML
    private Level2Controller ap_level2InterfaceController;
    @FXML
    private AnchorPane ap_level3Interface;
    @FXML
    private Level3Controller ap_level3InterfaceController;
    @FXML
    private AnchorPane ap_root;
    @FXML
    private GridPane gp_titlePane;
    @FXML
    private GridPane gp_round2;
    @FXML
    private Label lb_userName;
    @FXML
    private Label lb_userLevel;
    @FXML
    private Label lb_point1;
    @FXML
    private Label lb_point2;
    @FXML
    private Label lb_point3;
    @FXML
    private Label lb_point4;
    @FXML
    private Label lb_point5;
    @FXML
    private Label lb_userPoint1;
    @FXML
    private Label lb_userPoint2;
    @FXML
    private Label lb_userPoint3;
    @FXML
    private Label lb_userPoint4;
    @FXML
    private Label lb_userPoint5;

    private int currentLevel = -1;

    private Thread thread;

    public void init(UserData user) {
        this.userData = user;
        lb_userName.setText(userData.getName());
        lb_userLevel.setText(userData.getLevel());
        thread = new Thread(this);
        thread.start();
    }

    @FXML
    private void handleMouseClick(MouseEvent e) {

    }

    private void setData() {
        // initialize all points to 0
        lb_userPoint1.setText("0");
        lb_userPoint2.setText("0");
        lb_userPoint3.setText("0");
        lb_userPoint4.setText("0");
        lb_userPoint5.setText("0");

    }

    protected void show() {
        gp_round2.setVisible(true);
    }

    private void hide() {
        gp_round2.setVisible(false);
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
        System.out.println("R4 TO: command = " + Integer.toHexString(command) + " | data = ");
        super.writeToServer(command);
    }


    @Override
    public void writeToServer(int command, LinkedList<String> data) {
        super.writeToServer(command, data);
    }

    @Override
    public void handleServerData(int command, LinkedList<String> data) {
        System.out.println("R4 FROM: command = " + Integer.toHexString(command) + " | data = " + data);
        switch (command) {
            case Constants.BEGIN_R4L1:
                currentLevel = Constants.LEVEL1;
                hide();
                ap_level1InterfaceController.init(userData, this);
                ap_level1InterfaceController.show();
                break;
            case Constants.BEGIN_R4L2:
                currentLevel = Constants.LEVEL2;
                hide();
                ap_level2InterfaceController.init(userData, this);
                ap_level2InterfaceController.show();
                break;
            case Constants.S2C_R4L2_SCR:
                userData.setRound5Point(Integer.parseInt(data.getFirst()));
                lb_userPoint5.setText(data.getFirst());
                System.out.println(userData.getName() + " current total point for round 5 is " + data.getFirst());
                break;
            case Constants.BEGIN_R4L3:
                currentLevel = Constants.LEVEL3;
                hide();
                ap_level3InterfaceController.init(userData, this);
                ap_level3InterfaceController.show();
                break;
            case Constants.S2C_R4L3_SCR:
                userData.setRound5Point(Integer.parseInt(data.getFirst()));
                lb_userPoint5.setText(data.getFirst());
                System.out.println(userData.getName() + " current total point for round 5 is " + data.getFirst());
                break;
            default:
                switch (currentLevel) {
                    case Constants.LEVEL1:
                        ap_level1InterfaceController.handleServerData(command, data);
                        break;
                    case Constants.LEVEL2:
                        ap_level2InterfaceController.handleServerData(command, data);
                        break;
                    case Constants.LEVEL3:
                        ap_level3InterfaceController.handleServerData(command, data);
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
        AnchorPane.setBottomAnchor(ap_level2Interface, 0.0);
        AnchorPane.setTopAnchor(ap_level2Interface, 0.0);
        AnchorPane.setLeftAnchor(ap_level2Interface, 0.0);
        AnchorPane.setRightAnchor(ap_level2Interface, 0.0);
        ap_level2Interface.setVisible(false);

        AnchorPane.setBottomAnchor(ap_level3Interface, 0.0);
        AnchorPane.setTopAnchor(ap_level3Interface, 0.0);
        AnchorPane.setLeftAnchor(ap_level3Interface, 0.0);
        AnchorPane.setRightAnchor(ap_level3Interface, 0.0);
        ap_level3Interface.setVisible(false);

        setData();
    }

}
