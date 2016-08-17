package main.round5;

import data.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import main.Main;
import tool.Constants;
import tool.Timer;
import tool.TimerInterface;

import java.net.URL;
import java.util.*;

public class Level3Controller extends Round5Controller implements Initializable, TimerInterface {

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
    private FlowPane fp_text;
    @FXML
    private FlowPane fp_answers;

    private LinkedList<Integer> answerIndices;
    private LinkedList<String> answers;

    private Round5Controller round5Controller;

    public void init(UserData user, Round5Controller controller) {
        this.userData = user;
        this.round5Controller = controller;
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
        result.add("INDX");
        for (int i : answerIndices) {
            result.add(Integer.toString(i));
        }
        result.add("CANS");
        for (int i = 0; i < fp_answers.getChildren().size(); i++) {
            if (i % 2 == 1)
                result.add(((TextField) (fp_answers.getChildren().get(i))).getText());
        }
        return result;
    }


    private void setData() {
        lb_instructionBody_zh.setText(Main.R5L3_DATA.INSTRUCTION_ZH);
        lb_instructionBody_en.setText(Main.R5L3_DATA.INSTRUCTION_EN);
        String timeLimit_zh = "限时" + Main.R5L3_DATA.TIME_LIMIT + "分钟";
        String timeLimit_en = "Time Limit: " + Main.R5L3_DATA.TIME_LIMIT + (Main.R5L3_DATA.TIME_LIMIT > 1 ? " minutes" : " minute");
        lb_instructionTime_zh.setText(timeLimit_zh);
        lb_instructionTime_en.setText(timeLimit_en);

        String cnExample = "";
        for (String data : Main.R5L3_DATA.EXAMPLES) {
            cnExample += data + "\n";
        }
        lb_exampleBody.setText(cnExample);

        String timerLabel = Main.R5L3_DATA.TIME_LIMIT + ":00";
        lb_timer.setText(timerLabel);

        int index = 0;
        for (Character c : Main.R5L3_DATA.parseText(Main.R5L3_DATA.QUESTIONS)) {
            Label label = new Label(c.toString());
            label.getStyleClass().set(0, "label-questionsText");
            int finalIndex = index;
            label.setOnMouseClicked(e -> {
                if (answerIndices == null)
                    answerIndices = new LinkedList<>();
                if (label.getStyleClass().get(0).compareToIgnoreCase("label-questionsTextSelected") != 0) {
                    label.getStyleClass().set(0, "label-questionsTextSelected");
                    addWrongAnswers(label, finalIndex);
                    answerIndices.add(finalIndex);
                }
            });
            fp_text.getChildren().add(label);
            index++;
        }

    }

    private void addWrongAnswers(Label label, int index) {
        Label label2 = new Label(label.getText() + ".");
        TextField answer = new TextField();

        label2.getStyleClass().set(0, "label-questionsText");

        answer.setPrefColumnCount(2);
        answer.setAlignment(Pos.CENTER);
        answer.getStyleClass().add(0, "text-field-questionsAnswer");
        answer.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DELETE) {
                fp_answers.getChildren().removeAll(label2, answer);
                fp_text.getChildren().get(index).getStyleClass().set(0, "label-questionsText");
                for (int i = 0; i < answerIndices.size(); i++) {
                    if (answerIndices.get(i) == index) {
                        System.out.println("Answer before deleted: " + answerIndices);
                        answerIndices.remove(i);
                        System.out.println("Answer after deleted: " + answerIndices);
                    }
                }
            }
        });
        fp_answers.getChildren().addAll(label2, answer);
    }


    @Override
    public void writeToServer(int command) {
        switch (command) {
            case Constants.DIS_R5L3_EXP:
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
            case Constants.DIS_R5L3_EXP:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(false);
                gp_example.setVisible(true);
                gp_questions.setVisible(false);
                break;
            case Constants.DIS_R5L3_QST:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(false);
                gp_example.setVisible(false);
                gp_questions.setVisible(true);

                new Timer(lb_timer, Main.R5L3_DATA.TIME_LIMIT * 60, this);

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
        writeToServer(Constants.C2S_R5L3_ANS, packageAnswers());
        hide();
        round5Controller.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        gp_levelTitle.setVisible(true);
        gp_instruction.setVisible(false);
        gp_example.setVisible(false);
        gp_questions.setVisible(false);

        setData();
    }


}
