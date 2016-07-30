package main.round2;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import main.Main;
import tool.Constants;
import tool.StyleID;
import tool.Timer;
import tool.TimerInterface;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Level1Controller extends Round2Controller implements Initializable, TimerInterface {

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
    private Label lb_question1;
    @FXML
    private Label lb_question2;
    @FXML
    private Label lb_question3;
    @FXML
    private Label lb_question4;
    @FXML
    private Label lb_question5;
    @FXML
    private TextArea ta_answer1;
    @FXML
    private TextArea ta_answer2;
    @FXML
    private TextArea ta_answer3;
    @FXML
    private TextArea ta_answer4;
    @FXML
    private TextArea ta_answer5;

    public void init() {
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
        Thread delayThread = new Thread(delay);
        delayThread.start();
    }

    private void setStyle() {
        lb_titleRoundNumber_zh.setId(StyleID.TITLE_ROUND_NUMBER_ZH);
        lb_titleRoundNumber_en.setId(StyleID.TITLE_ROUND_NUMBER_EN);
        lb_titleRoundLevel_zh.setId(StyleID.TITLE_ROUND_LEVEL_ZH);
        lb_titleRoundLevel_en.setId(StyleID.TITLE_ROUND_LEVEL_EN);
        lb_titleRoundDescription.setId(StyleID.TITLE_ROUND_DESCRIPTION);

        lb_instructionHeader.setId(StyleID.INSTRUCTION_HEADER);
        lb_instructionBody_zh.setId(StyleID.INSTRUCTION_BODY_ZH);
        lb_instructionBody_en.setId(StyleID.INSTRUCTION_BODY_EN);
        lb_instructionTime_zh.setId(StyleID.INSTRUCTION_TIME_ZH);
        lb_instructionTime_en.setId(StyleID.INSTRUCTION_TIME_EN);

        lb_exampleHeader.setId(StyleID.EXAMPLE_HEADER);
        lb_exampleBody.setId(StyleID.EXAMPLE_BODY);
        lb_exampleWarning.setId(StyleID.EXAMPLE_WARNING);

        lb_timer.setId(StyleID.ROUND_TIMER);
        lb_question1.setId(StyleID.ROUND_QUESTIONS);
        lb_question2.setId(StyleID.ROUND_QUESTIONS);
        lb_question3.setId(StyleID.ROUND_QUESTIONS);
        lb_question4.setId(StyleID.ROUND_QUESTIONS);
        lb_question5.setId(StyleID.ROUND_QUESTIONS);
        ta_answer1.setId(StyleID.ROUND_ANSWERS);
        ta_answer2.setId(StyleID.ROUND_ANSWERS);
        ta_answer3.setId(StyleID.ROUND_ANSWERS);
        ta_answer4.setId(StyleID.ROUND_ANSWERS);
        ta_answer5.setId(StyleID.ROUND_ANSWERS);
    }

    private void setData() {
        lb_instructionBody_zh.setText(Main.R2L1_DATA.INSTRUCTION_ZH);
        lb_instructionBody_en.setText(Main.R2L1_DATA.INSTRUCTION_EN);
        String timeLimit_zh = "限时" + Main.R2L1_DATA.TIME_LIMIT + "分钟";
        String timeLimit_en = "Time Limit: " + Main.R2L1_DATA.TIME_LIMIT + (Main.R2L1_DATA.TIME_LIMIT > 1 ? " minutes" : " minute");
        lb_instructionTime_zh.setText(timeLimit_zh);
        lb_instructionTime_en.setText(timeLimit_en);

        lb_exampleBody.setText(Main.R2L1_DATA.EXAMPLES.get(0));

        String timerLabel = Main.R2L1_DATA.TIME_LIMIT + ":00";
        lb_timer.setText(timerLabel);
        lb_question1.setText(Main.R2L1_DATA.QUESTIONS.get(0));
        lb_question2.setText(Main.R2L1_DATA.QUESTIONS.get(1));
        lb_question3.setText(Main.R2L1_DATA.QUESTIONS.get(2));
        lb_question4.setText(Main.R2L1_DATA.QUESTIONS.get(3));
        lb_question5.setText(Main.R2L1_DATA.QUESTIONS.get(4));
    }


    @Override
    public void writeToServer(int command) {
        switch (command) {
            case Constants.DIS_R1L1_EX:
                gp_instruction.setVisible(false);
                gp_example.setVisible(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void writeToServer(int command, LinkedList<String> data) {

    }

    @Override
    public void handleServerData(int command, LinkedList<String> data) {
        switch (command) {
            case Constants.DIS_R1L1_EX:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(false);
                gp_example.setVisible(true);
                gp_questions.setVisible(false);
                break;
            case Constants.DIS_R1L1_QS:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(false);
                gp_example.setVisible(false);
                gp_questions.setVisible(true);

                new Timer(lb_timer, Main.R2L1_DATA.TIME_LIMIT * 60, this);

                break;
        }
    }

    @Override
    public void takeNotice() {
        System.out.println("Do something");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gp_levelTitle.setVisible(true);
        gp_instruction.setVisible(false);
        gp_example.setVisible(false);
        gp_questions.setVisible(false);

        setStyle();
        setData();
    }


}
