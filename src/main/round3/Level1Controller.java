package main.round3;

import data.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class Level1Controller extends Round3Controller implements Initializable, TimerInterface {

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
    private Label l_root;
    @FXML
    private Label l_char;
    @FXML
    private Button b_submit;
    @FXML
    private Button b_root1;
    @FXML
    private Button b_root2;
    @FXML
    private Button b_root3;
    @FXML
    private Button b_root4;
    @FXML
    private Button b_root5;
    @FXML
    private Button b_root6;
    @FXML
    private Button b_root7;
    @FXML
    private Button b_root8;
    @FXML
    private Button b_root9;
    @FXML
    private Button b_root10;
    @FXML
    private Button b_char1;
    @FXML
    private Button b_char2;
    @FXML
    private Button b_char3;
    @FXML
    private Button b_char4;
    @FXML
    private Button b_char5;
    @FXML
    private Button b_char6;
    @FXML
    private Button b_char7;
    @FXML
    private Button b_char8;
    @FXML
    private Button b_char9;
    @FXML
    private Button b_char10;
    @FXML
    private Button b_char11;
    @FXML
    private Button b_char12;
    @FXML
    private Button b_char13;
    @FXML
    private Button b_char14;
    @FXML
    private Button b_char15;
    @FXML
    private Button b_char16;
    @FXML
    private Button b_char17;
    @FXML
    private Button b_char18;
    @FXML
    private FlowPane fp_answer1;

    private int counter;
    private String[] answer = new String[180];
    private LinkedList<Button> roots;
    private LinkedList<Button> chars;
    private LinkedList<FlowPane> answers;
    private LinkedList<LinkedList<String>> temp;

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

        /*LinkedList<String> result = new LinkedList<>();
        result.add("ANS");
        result.add(answer);

        return result;*/
        LinkedList<String> result = new LinkedList<>();
        for (int i = 0; i < answer.length && answer[i] != null; i++) {
            //result.add("ANS");
            result.add(answer[i]);
            //result.add(lines.get(i).toString());
        }
        return result;

    }

    private void setData() {
        lb_instructionBody_zh.setText(Main.R3L1_DATA.INSTRUCTION_ZH);
        lb_instructionBody_en.setText(Main.R3L1_DATA.INSTRUCTION_EN);
        String timeLimit_zh = "限时" + Main.R3L1_DATA.TIME_LIMIT + "分钟";
        String timeLimit_en = "Time Limit: " + Main.R3L1_DATA.TIME_LIMIT + (Main.R3L1_DATA.TIME_LIMIT > 1 ? " minutes" : " minute");
        lb_instructionTime_zh.setText(timeLimit_zh);
        lb_instructionTime_en.setText(timeLimit_en);

        lb_exampleBody.setText(Main.R3L1_DATA.EXAMPLES.get(0));


        String timerLabel = Main.R3L1_DATA.TIME_LIMIT + ":00";
        lb_timer.setText(timerLabel);
        for(int i = 0; i < chars.size(); i++)
            chars.get(i).setText(Main.R3L1_DATA.CHARS.get(i));
        for(int i = 0; i < roots.size(); i++)
            roots.get(i).setText(Main.R3L1_DATA.ROOTS.get(i));
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
            case Constants.DIS_R3L1_INS:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(true);
                gp_example.setVisible(false);
                gp_questions.setVisible(false);
                break;
            case Constants.DIS_R3L1_EXP:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(false);
                gp_example.setVisible(true);
                gp_questions.setVisible(false);
                break;
            case Constants.DIS_R3L1_QST:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(false);
                gp_example.setVisible(false);
                gp_questions.setVisible(true);
                //gp_answers.setVisible(true);

                new Timer(lb_timer, Main.R3L1_DATA.TIME_LIMIT * 60, this,0);

                break;
        }
    }

    @FXML
    public void rootClicked(Button button) {
        l_root.setText(button.getText());
    }

    @FXML
    public void charClicked(Button button) {
        l_char.setText(button.getText());
    }

    @FXML
    public void submitAnswer() {
        answer[counter] = l_root.getText() + l_char.getText();
        Label label = new Label();
        label.setText(l_root.getText() + l_char.getText() + "/");
        label.setStyle("-fx-font: bold 30pt KaiTi; -fx-text-fill: rgb(255,0,0)");
        fp_answer1.getChildren().add(label);
        counter++;
    }


    public void show(){
        ap_root.setVisible(true);
    }

    private void hide(){
        ap_root.setVisible(false);
    }

    @Override
    public void takeNotice() {
        writeToServer(Constants.C2S_R3L1_ANS, packageAnswers());
        hide();
        round3Controller.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roots = new LinkedList<>();
        roots.add(b_root1);
        roots.add(b_root2);
        roots.add(b_root3);
        roots.add(b_root4);
        roots.add(b_root5);
        roots.add(b_root6);
        roots.add(b_root7);
        roots.add(b_root8);
        roots.add(b_root9);
        roots.add(b_root10);


        chars = new LinkedList<>();
        chars.add(b_char1);
        chars.add(b_char2);
        chars.add(b_char3);
        chars.add(b_char4);
        chars.add(b_char5);
        chars.add(b_char6);
        chars.add(b_char7);
        chars.add(b_char8);
        chars.add(b_char9);
        chars.add(b_char10);
        chars.add(b_char11);
        chars.add(b_char12);
        chars.add(b_char13);
        chars.add(b_char14);
        chars.add(b_char15);
        chars.add(b_char16);
        chars.add(b_char17);
        chars.add(b_char18);


        answers = new LinkedList<>();


        b_root1.setOnAction( e -> rootClicked(b_root1));
        b_root2.setOnAction( e -> rootClicked(b_root2));
        b_root3.setOnAction( e -> rootClicked(b_root3));
        b_root4.setOnAction( e -> rootClicked(b_root4));
        b_root5.setOnAction( e -> rootClicked(b_root5));
        b_root6.setOnAction( e -> rootClicked(b_root6));
        b_root7.setOnAction( e -> rootClicked(b_root7));
        b_root8.setOnAction( e -> rootClicked(b_root8));
        b_root9.setOnAction( e -> rootClicked(b_root9));
        b_root10.setOnAction( e -> rootClicked(b_root10));


        b_char1.setOnAction( e -> charClicked(b_char1));
        b_char2.setOnAction( e -> charClicked(b_char2));
        b_char3.setOnAction( e -> charClicked(b_char3));
        b_char4.setOnAction( e -> charClicked(b_char4));
        b_char5.setOnAction( e -> charClicked(b_char5));
        b_char6.setOnAction( e -> charClicked(b_char6));
        b_char7.setOnAction( e -> charClicked(b_char7));
        b_char8.setOnAction( e -> charClicked(b_char8));
        b_char9.setOnAction( e -> charClicked(b_char9));
        b_char10.setOnAction( e -> charClicked(b_char10));
        b_char11.setOnAction( e -> charClicked(b_char11));
        b_char12.setOnAction( e -> charClicked(b_char12));
        b_char13.setOnAction( e -> charClicked(b_char13));
        b_char14.setOnAction( e -> charClicked(b_char14));
        b_char15.setOnAction( e -> charClicked(b_char15));
        b_char16.setOnAction( e -> charClicked(b_char16));
        b_char17.setOnAction( e -> charClicked(b_char17));
        b_char18.setOnAction( e -> charClicked(b_char18));


        gp_levelTitle.setVisible(true);
        gp_instruction.setVisible(false);
        gp_example.setVisible(false);
        gp_questions.setVisible(false);
        setData();
    }


}
