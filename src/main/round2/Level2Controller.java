package main.round2;

import data.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import main.Main;
import tool.Constants;
import tool.StyleId;
import tool.Timer;
import tool.TimerInterface;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Level2Controller extends Round2Controller implements Initializable, TimerInterface {

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
    private TextArea ta_answer1;
    @FXML
    private TextArea ta_answer2;
    @FXML
    private TextArea ta_answer3;
    @FXML
    private TextArea ta_answer4;

    private LinkedList<Label> questions;
    private LinkedList<TextArea> answers;

    private Round2Controller round2Controller;

    public void init(UserData user, Round2Controller round2Controller) {
        this.userData = user;
        this.round2Controller = round2Controller;
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

    private LinkedList<String> packageAnswers() {
        String answer1 = ta_answer1.getText();
        String answer2 = ta_answer2.getText();
        String answer3 = ta_answer3.getText();
        String answer4 = ta_answer4.getText();

        LinkedList<String> result = new LinkedList<>();
        result.add("ANS1");
        result.add(answer1);
        result.add("ANS2");
        result.add(answer2);
        result.add("ANS3");
        result.add(answer3);
        result.add("ANS4");
        result.add(answer4);

        return result;

    }

    private void setStyle() {
        lb_titleRoundNumber_zh.setId(StyleId.TITLE_ROUND_NUMBER_ZH);
        lb_titleRoundNumber_en.setId(StyleId.TITLE_ROUND_NUMBER_EN);
        lb_titleRoundLevel_zh.setId(StyleId.TITLE_ROUND_LEVEL_ZH);
        lb_titleRoundLevel_en.setId(StyleId.TITLE_ROUND_LEVEL_EN);
        lb_titleRoundDescription.setId(StyleId.TITLE_ROUND_DESCRIPTION);

        lb_instructionHeader.setId(StyleId.INSTRUCTION_HEADER);
        lb_instructionBody_zh.setId(StyleId.INSTRUCTION_BODY_ZH);
        lb_instructionBody_en.setId(StyleId.INSTRUCTION_BODY_EN);
        lb_instructionTime_zh.setId(StyleId.INSTRUCTION_TIME_ZH);
        lb_instructionTime_en.setId(StyleId.INSTRUCTION_TIME_EN);

        lb_exampleHeader.setId(StyleId.EXAMPLE_HEADER);
        lb_exampleBody.setId(StyleId.EXAMPLE_BODY);
        lb_exampleWarning.setId(StyleId.EXAMPLE_WARNING);

        lb_timer.setId(StyleId.ROUND_TIMER);
        for(Label question : questions)
            question.setId(StyleId.ROUND_QUESTIONS);
        for(TextArea answer : answers)
            answer.setId(StyleId.ROUND_ANSWERS);
    }

    private void setData() {
        lb_instructionBody_zh.setText(Main.R2L2_DATA.INSTRUCTION_ZH);
        lb_instructionBody_en.setText(Main.R2L2_DATA.INSTRUCTION_EN);
        String timeLimit_zh = "限时" + Main.R2L2_DATA.TIME_LIMIT + "分钟";
        String timeLimit_en = "Time Limit: " + Main.R2L2_DATA.TIME_LIMIT + (Main.R2L2_DATA.TIME_LIMIT > 1 ? " minutes" : " minute");
        lb_instructionTime_zh.setText(timeLimit_zh);
        lb_instructionTime_en.setText(timeLimit_en);

        String cnExample = "";
        for (String data : Main.R2L2_DATA.EXAMPLES) {
            cnExample += data + "\n";
        }
        lb_exampleBody.setText(cnExample);

        String timerLabel = Main.R2L2_DATA.TIME_LIMIT + ":00";
        lb_timer.setText(timerLabel);
        for(int i = 0;i <questions.size();i++)
            questions.get(i).setText(Main.R2L2_DATA.QUESTIONS.get(i));
    }

    @Override
    public void writeToServer(int command) {
        switch (command) {
            case Constants.DIS_R2L2_EXP:
                gp_instruction.setVisible(false);
                gp_example.setVisible(true);
                break;
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
            case Constants.DIS_R2L2_EXP:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(false);
                gp_example.setVisible(true);
                gp_questions.setVisible(false);
                break;
            case Constants.DIS_R2L2_QST:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(false);
                gp_example.setVisible(false);
                gp_questions.setVisible(true);

                new Timer(lb_timer, Main.R2L2_DATA.TIME_LIMIT * 60, this);

                break;
        }
    }

    public void show(){
        ap_root.setVisible(true);
    }

    private void hide(){
        ap_root.setVisible(false);
    }

    @Override
    public void takeNotice() {
        for(TextArea answer : answers)
            answer.setEditable(false);
        writeToServer(Constants.C2S_R2L2_ANS, packageAnswers());
        hide();
        round2Controller.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        questions = new LinkedList<>();
        questions.add(lb_question1);
        questions.add(lb_question2);
        questions.add(lb_question3);
        questions.add(lb_question4);

        answers = new LinkedList<>();
        answers.add(ta_answer1);
        answers.add(ta_answer2);
        answers.add(ta_answer3);
        answers.add(ta_answer4);

        gp_levelTitle.setVisible(true);
        gp_instruction.setVisible(false);
        gp_example.setVisible(false);
        gp_questions.setVisible(false);
        setStyle();
        setData();
    }


}
