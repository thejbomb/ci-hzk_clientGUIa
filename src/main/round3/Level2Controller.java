package main.round3;

import data.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import main.Main;
import tool.Constants;
import tool.Timer;
import tool.TimerInterface;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Level2Controller extends Round3Controller implements Initializable, TimerInterface {

    @FXML
    private AnchorPane ap_root;
    @FXML
    private GridPane gp_levelTitle;
    @FXML
    private GridPane gp_instruction;
    @FXML
    private GridPane gp_example;
    @FXML
    private GridPane gp_questions;
    @FXML
    private Label lb_titleRoundNumber_zh;
    @FXML
    private Label lb_titleRoundNumber_en;
    @FXML
    private Label lb_titleRoundLevel_zh;
    @FXML
    private Label lb_titleRoundLevel_en;
    @FXML
    private Label lb_titleRoundDescription;
    @FXML
    private Label lb_instructionHeader;
    @FXML
    private Label lb_instructionBody_zh;
    @FXML
    private Label lb_instructionBody_en;
    @FXML
    private Label lb_instructionTime_zh;
    @FXML
    private Label lb_instructionTime_en;
    @FXML
    private Label lb_exampleHeader;
    @FXML
    private Label lb_exampleBody;
    @FXML
    private Label lb_exampleWarning;
    @FXML
    private Label lb_timer;
    @FXML
    private FlowPane chosen;
    @FXML
    private FlowPane root;
    @FXML
    private Button b_submit;
    @FXML
    private FlowPane fp_answer1;

    private LinkedList<String> answer;
    private LinkedList<Button> roots;

    private Round3Controller round3Controller;

    public void init(UserData user, Round3Controller controller) {
        this.userData = user;
        this.round3Controller = controller;
        Runnable delay = new Runnable() {
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

            }
        };
    }

    private LinkedList<String> packageAnswers() {

        LinkedList<String> result = new LinkedList<>();
        String s = "";
        for(int i = 0; i < fp_answer1.getChildren().size(); i++) {
            s = s +((Label)fp_answer1.getChildren().get(i)).getText();
        }

        result.add(s);
        return result;

    }

    private void setData() {
        lb_instructionBody_zh.setText(Main.R3L2_DATA.INSTRUCTION_ZH);
        lb_instructionBody_en.setText(Main.R3L2_DATA.INSTRUCTION_EN);
        String timeLimit_zh = "限时" + Main.R3L2_DATA.TIME_LIMIT + "分钟";
        String timeLimit_en = "Time Limit: " + Main.R3L2_DATA.TIME_LIMIT + (Main.R3L2_DATA.TIME_LIMIT > 1 ? " minutes" : " minute");
        lb_instructionTime_zh.setText(timeLimit_zh);
        lb_instructionTime_en.setText(timeLimit_en);

        lb_exampleBody.setText(Main.R3L2_DATA.EXAMPLES.get(0));


        String timerLabel = Main.R3L2_DATA.TIME_LIMIT + ":00";
        lb_timer.setText(timerLabel);
        for(int i = 0; i < roots.size(); i++) {
            roots.get(i).setText(Main.R3L3_DATA.ROOTS.get(i));
            root.getChildren().add(roots.get(i));
        }
    }

    @Override
    public void writeToServer(int command) {
        switch (command) {
            /*case Constants.DIS_R3L1_EXP:
                gp_instruction.setVisible(false);
                gp_example.setVisible(true);
                break;*/
            default:
                break;
        }
    }

    @Override
    public void writeToServer(int command, LinkedList<String> data) {
        super.writeToServer(command, data);
    }

    @Override
    public void handleServerData(int command, LinkedList<String> data) {
        switch (command) {
            case Constants.DIS_R3L2_INS:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(true);
                gp_example.setVisible(false);
                gp_questions.setVisible(false);
                break;
            case Constants.DIS_R3L2_EXP:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(false);
                gp_example.setVisible(true);
                gp_questions.setVisible(false);
                break;
            case Constants.DIS_R3L2_QST:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(false);
                gp_example.setVisible(false);
                gp_questions.setVisible(true);

                new Timer(lb_timer, Main.R3L2_DATA.TIME_LIMIT * 60, this,0);

                break;
        }
    }

    @FXML
    public void rootClicked(Button button) {
        Label l = new Label(button.getText());
        l.setStyle("-fx-font: bold 45pt KaiTi; -fx-text-fill: rgb(150,0,250)");
        l.setCursor(Cursor.HAND);
        l.setOnMouseClicked(e -> chosen.getChildren().removeAll(l));
        chosen.getChildren().add(l);
    }

    @FXML
    public void submitAnswer() {
        Label label = new Label();
        String string = "";
        for(int i = 0; i < chosen.getChildren().size(); i++) {
            string = string + ((Label) chosen.getChildren().get(i)).getText();
        }
        string = string.replaceAll("[,]","");
        chosen.getChildren().clear();
        answer.add(string);
        label.setText(string + "/");
        label.setStyle("-fx-font: bold 30pt KaiTi; -fx-text-fill: rgb(150,0,250)");
        label.setOnMousePressed(e -> fp_answer1.getChildren().removeAll(label));
        fp_answer1.getChildren().add(label);
    }


    public void show(){
        ap_root.setVisible(true);
    }

    private void hide(){
        ap_root.setVisible(false);
    }

    @Override
    public void takeNotice() {
        writeToServer(Constants.C2S_R3L2_ANS, packageAnswers());
        hide();
        round3Controller.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roots = new LinkedList<>();
        Insets s = new Insets(20, 10, 20, 10);
        for(int i = 0;i < Main.R3L3_DATA.ROOTS.size();i++) {
            Button button = new Button();
            button.setOnAction( e -> rootClicked(button));
            button.setPadding(s);
            roots.add(button);
        }

        answer = new LinkedList<>();

        gp_levelTitle.setVisible(true);
        gp_instruction.setVisible(false);
        gp_example.setVisible(false);
        gp_questions.setVisible(false);
        setData();
    }


}
