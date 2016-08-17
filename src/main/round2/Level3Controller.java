package main.round2;

import data.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
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
    private Line line1;
    @FXML
    private Line line2;
    @FXML
    private Line line3;
    @FXML
    private Line line4;
    @FXML
    private Line line5;
    @FXML
    private Line line6;
    @FXML
    private Line line7;
    @FXML
    private Line line8;
    @FXML
    private Line line9;
    @FXML
    private Line line10;

    private LinkedList<Label> questions;
    private LinkedList<Label> choices;
    private LinkedList<TextField> answers;
    private LinkedList<Line> lines;

    private LinkedList<String> randomizedAnswers;

    private Round2Controller round2Controller;

    public void init(UserData user, Round2Controller controller) {
        this.userData = user;
        this.round2Controller = controller;

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

    private void drawLine(){

    }

    @FXML
    private void handleKeyboard(KeyEvent e) {
        for (int j = 0; j < answers.size(); j++) {
            TextField answer = answers.get(j);
            String input = answer.getText();
            if (input.compareTo("") != 0) {
                input = input.toUpperCase();
                if (input.length() > 1)
                    answer.setText(input.substring(0, 1));
                else if(input.charAt(0) >= answers.size() + 0x41)
                    answer.setText("");
                else
                    answer.setText(input);

                int _input = input.charAt(0);

                for (int i = 0; i < answers.size(); i++) {
                    if (_input == i + 0x41) {
                        // ONLY WORK IN MAXIMIZED SCREEN MODE RIGHT NOW
                        Scene scene = choices.get(i).getScene();
                        Point2D windowC = new Point2D(scene.getWindow().getX(),scene.getWindow().getY());
                        Point2D sceneC = new Point2D(scene.getX(),scene.getY());
                        Point2D node = choices.get(i).localToScene(0.0,0.0);
                        double x = windowC.getX() + sceneC.getX() + node.getX();
                        double y = windowC.getY() + sceneC.getY() + node.getY();
                        lines.get(j).setStartX(x);
                        lines.get(j).setStartY(y);
                        scene = questions.get(j).getScene();
                        windowC = new Point2D(scene.getWindow().getX(),scene.getWindow().getY());
                        sceneC = new Point2D(scene.getX(),scene.getY());
                        node = questions.get(j).localToScene(0.0,0.0);
                        x = windowC.getX() + sceneC.getX() + node.getX();
                        y = windowC.getY() + sceneC.getY() + node.getY();
                        lines.get(j).setEndX(x + questions.get(i).getWidth());
                        lines.get(j).setEndY(y);

                        lines.get(j).setVisible(true);
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
        lines.add(line1);
        lines.add(line2);
        lines.add(line3);
        lines.add(line4);
        lines.add(line5);
        lines.add(line6);
        lines.add(line7);
        lines.add(line8);
        lines.add(line9);
        lines.add(line10);

        for (Line line : lines)
            line.setVisible(false);
        gp_levelTitle.setVisible(true);
        gp_instruction.setVisible(false);
        gp_example.setVisible(false);
        gp_questions.setVisible(false);

        setData();
    }


}
