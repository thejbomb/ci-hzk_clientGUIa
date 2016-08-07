package main.round2;

import data.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import main.Main;
import tool.Constants;
import tool.StyleId;
import tool.Timer;
import tool.TimerInterface;

import java.net.URL;
import java.util.*;

public class Level3Controller extends Round2Controller implements Initializable, TimerInterface {

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
    private Label lb_choice1;
    @FXML
    private Label lb_choice2;
    @FXML
    private Label lb_choice3;
    @FXML
    private Label lb_choice4;
    @FXML
    private Label lb_choice5;
    @FXML
    private Label lb_choice6;
    @FXML
    private Label lb_choice7;
    @FXML
    private Label lb_choice8;
    @FXML
    private Label lb_choice9;
    @FXML
    private Label lb_choice10;
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
    private Line line2;

    private LinkedList<Label> questions;
    private LinkedList<Label> choices;
    private LinkedList<TextField> answers;
    private LinkedList<Line> lines;

    private LinkedList<String> randomizedAnswers;

    private Round2Controller round2Controller;

    public void init(UserData user, Round2Controller round2Controller) {
        this.userData = user;
        this.round2Controller = round2Controller;

        randomizedAnswers = randomizeAnswers(Main.R2L3_DATA.ANSWERS);
        for (int i = 0; i < choices.size(); i++)
            choices.get(i).setText((char) (i + 0x41) + "." + randomizedAnswers.get(i));
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

    private LinkedList<String> packageData(Object data) {
        LinkedList<String> result = new LinkedList<>();
        if (data.getClass() == Long.class)
            result.add(Long.toString((long) data));

        return result;
    }

    private LinkedList<String> randomizeAnswers(ArrayList<String> data) {
        LinkedList<String> result = new LinkedList<>(data);

        long seed = System.nanoTime();

        writeToServer(Constants.C2S_R2L3_SEED, packageData(seed));

        Collections.shuffle(result, new Random(seed));

        return result;
    }

    private LinkedList<String> packageAnswers() {
        LinkedList<String> result = new LinkedList<>();
        for (TextField answer : answers)
            result.add(answer.getText());

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
        for (Label question : questions)
            question.setId(StyleId.ROUND_QUESTIONS);
        for (Label choice : choices)
            choice.setId(StyleId.ROUND_QUESTIONS);
        for (TextField answer : answers)
            answer.setId(StyleId.ROUND_ANSWERS);
    }

    private void setData() {
        lb_instructionBody_zh.setText(Main.R2L3_DATA.INSTRUCTION_ZH);
        lb_instructionBody_en.setText(Main.R2L3_DATA.INSTRUCTION_EN);
        String timeLimit_zh = "限时" + Main.R2L3_DATA.TIME_LIMIT + "分钟";
        String timeLimit_en = "Time Limit: " + Main.R2L3_DATA.TIME_LIMIT + (Main.R2L3_DATA.TIME_LIMIT > 1 ? " minutes" : " minute");
        lb_instructionTime_zh.setText(timeLimit_zh);
        lb_instructionTime_en.setText(timeLimit_en);

        String cnExample = "";
        for (String data : Main.R2L3_DATA.EXAMPLES) {
            cnExample += data + "\n";
        }
        lb_exampleBody.setText(cnExample);

        String timerLabel = Main.R2L3_DATA.TIME_LIMIT + ":00";
        lb_timer.setText(timerLabel);
        for (int i = 0; i < questions.size(); i++) {
            questions.get(i).setText(Main.R2L3_DATA.QUESTIONS.get(i));
        }
    }

    @FXML
    private void handleKeyboard(KeyEvent e) {
        for (int i1 = 0; i1 < answers.size(); i1++) {
            TextField answer = answers.get(i1);
            String input = answer.getText();
            if (input.compareTo("") != 0) {
                input = input.toUpperCase();
                if (input.length() > 1)
                    answer.setText(input.substring(0, 1));
                else
                    answer.setText(input);

                int _input = input.charAt(0);

                for (int i = 0; i < answers.size(); i++) {
                    if (_input == i + 0x41) {
                       /* lines.get(i1).setStartX(choices.get(i).getLayoutX());
                        lines.get(i1).setStartY(choices.get(i).getLayoutY());
                        lines.get(i1).setEndX(questions.get(i1).getLayoutX() + questions.get(i1).getWidth());
                        lines.get(i1).setEndY(questions.get(i1).getLayoutY() + questions.get(i1).getHeight());
                        lines.get(i1).setVisible(true);
                        lines.get(i1).setStrokeWidth(100);*/
                        Bounds bounds = choices.get(i).getBoundsInParent();
                        Bounds screenBounds = choices.get(i).localToScreen(bounds);
                        double x = screenBounds.getMinX() - choices.get(i).getWidth();
                        double y = screenBounds.getMinY();

                        line2.setStartX(x);
                        line2.setStartY(y);
                        line2.setEndX(questions.get(i1).getLayoutX() + questions.get(i1).getWidth());
                        line2.setEndY(questions.get(i1).getLayoutY() + questions.get(i1).getHeight());
                    }
                }
            }
        }
    }

    @Override
    public void writeToServer(int command) {
        switch (command) {
            case Constants.DIS_R2L3_EXP:
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
            case Constants.DIS_R2L3_EXP:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(false);
                gp_example.setVisible(true);
                gp_questions.setVisible(false);
                break;
            case Constants.DIS_R2L3_QST:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(false);
                gp_example.setVisible(false);
                gp_questions.setVisible(true);

                new Timer(lb_timer, Main.R2L3_DATA.TIME_LIMIT * 60, this);

                break;
        }
    }

    public void show() {
        ap_root.setVisible(true);
    }

    private void hide() {
        ap_root.setVisible(false);
    }

    @Override
    public void takeNotice() {
        for (TextField answer : answers)
            answer.setEditable(false);
        writeToServer(Constants.C2S_R2L3_ANS, packageAnswers());
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
        questions.add(lb_question5);
        questions.add(lb_question6);
        questions.add(lb_question7);
        questions.add(lb_question8);
        questions.add(lb_question9);
        questions.add(lb_question10);

        choices = new LinkedList<>();
        choices.add(lb_choice1);
        choices.add(lb_choice2);
        choices.add(lb_choice3);
        choices.add(lb_choice4);
        choices.add(lb_choice5);
        choices.add(lb_choice6);
        choices.add(lb_choice7);
        choices.add(lb_choice8);
        choices.add(lb_choice9);
        choices.add(lb_choice10);

        answers = new LinkedList<>();
        answers.add(tf_answer1);
        answers.add(tf_answer2);
        answers.add(tf_answer3);
        answers.add(tf_answer4);
        answers.add(tf_answer5);
        answers.add(tf_answer6);
        answers.add(tf_answer7);
        answers.add(tf_answer8);
        answers.add(tf_answer9);
        answers.add(tf_answer10);

        lines = new LinkedList<>();
        for (int i = 0; i < answers.size(); i++)
            lines.add(new Line());

        for (Line line : lines)
            line.setVisible(false);
        gp_levelTitle.setVisible(true);
        gp_instruction.setVisible(false);
        gp_example.setVisible(false);
        gp_questions.setVisible(false);

        setStyle();
        setData();
    }


}
