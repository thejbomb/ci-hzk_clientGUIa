package main.round1;

import data.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

public class Level2Controller extends Round1Controller implements Initializable, TimerInterface {

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
    private Label lb_question6;
    @FXML
    private Label lb_question7;
    @FXML
    private Label lb_question8;
    @FXML
    private Label lb_question9;
    @FXML
    private Label lb_question10;
    @FXML
    private TextField tf_answer1;
    @FXML
    private TextField tf_answer2;
    @FXML
    private TextField tf_answer3;
    @FXML
    private TextField tf_answer4;
    @FXML
    private TextField tf_answer5;
    @FXML
    private TextField tf_answer6;
    @FXML
    private TextField tf_answer7;
    @FXML
    private TextField tf_answer8;
    @FXML
    private TextField tf_answer9;
    @FXML
    private TextField tf_answer10;
    @FXML
    private FlowPane fp_answer1;
    @FXML
    private FlowPane fp_answer2;
    @FXML
    private FlowPane fp_answer3;
    @FXML
    private FlowPane fp_answer4;
    @FXML
    private FlowPane fp_answer5;
    @FXML
    private FlowPane fp_answer6;
    @FXML
    private FlowPane fp_answer7;
    @FXML
    private FlowPane fp_answer8;
    @FXML
    private FlowPane fp_answer9;
    @FXML
    private FlowPane fp_answer10;

    private LinkedList<Label> questions;
    private LinkedList<TextField> textFields;
    private LinkedList<FlowPane> answers;
    private LinkedList<String>[] words = new LinkedList[10];

    private Round1Controller round1Controller;

    public void init(UserData user, Round1Controller controller) {
        this.userData = user;
        this.round1Controller = controller;
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
        result.add("ANS1");
        result.add(words[0].toString());
        result.add("ANS2");
        result.add(words[1].toString());
        result.add("ANS3");
        result.add(words[2].toString());
        result.add("ANS4");
        result.add(words[3].toString());
        result.add("ANS5");
        result.add(words[4].toString());
        result.add("ANS6");
        result.add(words[5].toString());
        result.add("ANS7");
        result.add(words[6].toString());
        result.add("ANS8");
        result.add(words[7].toString());
        result.add("ANS9");
        result.add(words[8].toString());
        result.add("ANS10");
        result.add(words[9].toString());

        return result;

    }

    @FXML
    private void createSubmitted() {
        for (int j = 0; j < textFields.size(); j++) {
            TextField answer = textFields.get(j);
            String input = answer.getText();
            FlowPane area = answers.get(j);
            Label label = new Label(input);
            words[j].add(label.getText());
            label.setStyle("-fx-font: bold 40pt KaiTi; -fx-text-fill: rgb(0,255,0)");
            area.getChildren().add(label);
            //answers.get(j).setText(label.getText());
            answer.clear();
        }
    }

    private void setData() {
        lb_instructionBody_zh.setText(Main.R1L2_DATA.INSTRUCTION_ZH);
        lb_instructionBody_en.setText(Main.R1L2_DATA.INSTRUCTION_EN);
        String timeLimit_zh = "限时" + Main.R1L2_DATA.TIME_LIMIT + "分钟";
        String timeLimit_en = "Time Limit: " + Main.R1L2_DATA.TIME_LIMIT + (Main.R1L2_DATA.TIME_LIMIT > 1 ? " minutes" : " minute");
        lb_instructionTime_zh.setText(timeLimit_zh);
        lb_instructionTime_en.setText(timeLimit_en);

        lb_exampleBody.setText(Main.R1L2_DATA.EXAMPLES.get(0));


        String timerLabel = Main.R1L2_DATA.TIME_LIMIT + ":00";
        lb_timer.setText(timerLabel);
        for(int i = 0;i <questions.size();i++)
            questions.get(i).setText(Main.R1L2_DATA.QUESTIONS.get(i));
    }

    @Override
    public void writeToServer(int command) {
        switch (command) {
            /*case Constants.DIS_R1L2_EXP:
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
            case Constants.DIS_R1L2_INS:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(true);
                gp_example.setVisible(false);
                gp_questions.setVisible(false);
                break;
            case Constants.DIS_R1L2_EXP:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(false);
                gp_example.setVisible(true);
                gp_questions.setVisible(false);
                break;
            case Constants.DIS_R1L2_QST:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(false);
                gp_example.setVisible(false);
                gp_questions.setVisible(true);

                new Timer(lb_timer, Main.R1L1_DATA.TIME_LIMIT * 60, this,0);

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
        /*for(TextField answer : textFields)
            answer.setEditable(false);*/
        writeToServer(Constants.C2S_R1L2_ANS, packageAnswers());
        //hide();
        round1Controller.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        questions = new LinkedList<>();
        questions.add(lb_question1);
        questions.add(lb_question2);
        questions.add(lb_question3);
        questions.add(lb_question4);
        questions.add(lb_question5);
        questions.add(lb_question6);
        questions.add(lb_question7);
        questions.add(lb_question8);
        questions.add(lb_question9);
        questions.add(lb_question10);

        textFields = new LinkedList<>();
        textFields.add(tf_answer1);
        textFields.add(tf_answer2);
        textFields.add(tf_answer3);
        textFields.add(tf_answer4);
        textFields.add(tf_answer5);
        textFields.add(tf_answer6);
        textFields.add(tf_answer7);
        textFields.add(tf_answer8);
        textFields.add(tf_answer9);
        textFields.add(tf_answer10);

        answers = new LinkedList<>();
        answers.add(fp_answer1);
        answers.add(fp_answer2);
        answers.add(fp_answer3);
        answers.add(fp_answer4);
        answers.add(fp_answer5);
        answers.add(fp_answer6);
        answers.add(fp_answer7);
        answers.add(fp_answer8);
        answers.add(fp_answer9);
        answers.add(fp_answer10);

        for(int i = 0; i < words.length; i++) {
            words[i] = new LinkedList<>();
        }


        gp_levelTitle.setVisible(true);
        gp_instruction.setVisible(false);
        gp_example.setVisible(false);
        gp_questions.setVisible(false);
        setData();
    }


}
