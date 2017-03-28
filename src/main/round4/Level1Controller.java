package main.round4;

import data.UserData;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import main.Main;
import tool.Constants;
import tool.Timer;
import tool.TimerInterface;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Level1Controller extends Round4Controller implements Initializable, TimerInterface {

    @FXML
    private AnchorPane ap_root;
    @FXML
    private GridPane gp_levelTitle;
    @FXML
    private GridPane gp_instruction;
    @FXML
    private GridPane gp_example;
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
    private Label lb_instruction;
    @FXML
    private Label lb_timer;
    @FXML
    private AnchorPane ap_question;
    @FXML
    private FlowPane fp_choices;
    @FXML
    private AnchorPane ap_buzzer;
    @FXML
    private Button bt_buzzer;
    @FXML
    private GridPane gp_questions;
    @FXML
    private Label lb_score;
    @FXML
    private BorderPane bp_result;

    private Timer timer;

    private Round4Controller round4Controller;

    public void init(UserData user, Round4Controller controller) {
        this.userData = user;
        this.round4Controller = controller;
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

        if (data.getClass() == String.class)
            result.add((String) data);
        else if (data.getClass() == Integer.class)
            result.add(Integer.toString((int) data));

        return result;
    }

    private void setData() {
        lb_instructionBody_zh.setText(Main.R4L1_DATA.getINSTRUCTION_ZH());
        lb_instructionBody_en.setText(Main.R4L1_DATA.getINSTRUCTION_EN());
        String timeLimit_zh = "限时" + Main.R4L1_DATA.getTIME_LIMIT() + "分钟";
        String timeLimit_en = "Time Limit: " + Main.R4L1_DATA.getTIME_LIMIT() + (Main.R4L1_DATA.getTIME_LIMIT() > 1 ? " minutes" : " minute");
        lb_instructionTime_zh.setText(timeLimit_zh);
        lb_instructionTime_en.setText(timeLimit_en);

        lb_exampleBody.setText(Main.R4L1_DATA.getEXAMPLES().get(0));
    }

    @FXML
    private void handleMouseClick(MouseEvent e) {
        if (e.getSource() == bt_buzzer) {
            writeToServer(Constants.C2S_R4L1_BUZZ);
            int time = 10; // time to answer the question (seconds)
            timer = new Timer(lb_timer, time, this, 1);
            lb_timer.setVisible(true);
        }
    }

    @FXML
    private void handleKeyboard(ActionEvent e){
        System.out.println("Action Event detected");
        fp_choices.setDisable(false);
        writeToServer(Constants.C2S_R4L1_BUZZ);
        int time = 10; // time to answer the question (seconds)
        timer = new Timer(lb_timer, time, this, 1);
        lb_timer.setVisible(true);

    }

    private void displayInstruction(int questionNumber) throws Exception {
        if (questionNumber < 0 || questionNumber >= Main.R4L1_DATA.getQuestions().size())
            throw new Exception("R4L1: No such question exist");
        String instruction = "";
        if (Main.R4L1_DATA.getQuestionType(questionNumber) == 0) {
            instruction += Main.R4L1_DATA.QUESTION_INSTRUCTION1_ZH + "\n" + Main.R4L1_DATA.QUESTION_INSTRUCTION1_EN;
            lb_instruction.setText(instruction);
            lb_instruction.getStyleClass().set(0, "label-questionsInstruction-en");
        } else if (Main.R4L1_DATA.getQuestionType(questionNumber) == 1) {
            instruction += Main.R4L1_DATA.QUESTION_INSTRUCTION2_ZH + "\n" + Main.R4L1_DATA.QUESTION_INSTRUCTION2_EN;
            lb_instruction.setText(instruction);
            lb_instruction.getStyleClass().set(0, "label-questionsInstruction-en");
        }
    }

    private void displayQuestion(int questionNumber) throws Exception {
        if (questionNumber < 0 || questionNumber >= Main.R4L1_DATA.getQuestions().size())
            throw new Exception("R4L1: No such question exist");
        if (Main.R4L1_DATA.getQuestionType(questionNumber) == 0 || Main.R4L1_DATA.getQuestionType(questionNumber) == 1) {
            String question = (questionNumber + 1) + ". " + Main.R4L1_DATA.getQuestions(questionNumber);
            Label label = new Label(question);
            label.getStyleClass().set(0, "label-questionsQuestion");
            AnchorPane.setBottomAnchor(label, 0.0);
            AnchorPane.setTopAnchor(label, 0.0);
            AnchorPane.setLeftAnchor(label, 0.0);
            AnchorPane.setRightAnchor(label, 0.0);
            label.setAlignment(Pos.CENTER);
            ap_question.getChildren().clear();
            ap_question.getChildren().add(label);
        } else if (Main.R4L1_DATA.getQuestionType(questionNumber) == 2) {
            Image image = new Image("file:src/data/round4/" + Main.R4L1_DATA.getQuestions(questionNumber));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(400.0);
            Label l = new Label();
            l.setGraphic(imageView);
            l.setAlignment(Pos.CENTER);
            AnchorPane.setBottomAnchor(l, 0.0);
            AnchorPane.setTopAnchor(l, 0.0);
            AnchorPane.setLeftAnchor(l, 0.0);
            AnchorPane.setRightAnchor(l, 0.0);
            ap_question.getChildren().clear();
            ap_question.getChildren().add(l);
        }
    }

    private void displayChoices(int questionNumber) throws Exception {
        if (questionNumber < 0 || questionNumber >= Main.R4L1_DATA.getQuestions().size())
            throw new Exception("No such question exist");
        Button[] choices = new Button[Main.R4L1_DATA.getChoices(questionNumber).size()];
        for (int i = 0; i < choices.length; i++) {
            choices[i] = new Button((char) (0x41 + i) + "." + Main.R4L1_DATA.getChoices(questionNumber).get(i));
            choices[i].getStyleClass().set(0, "button-questionsChoice");
            int finalI = i;
            choices[i].setOnMouseClicked(e -> {
                writeToServer(Constants.C2S_R4L1_ANS, packageData(finalI));
                for (int j = 0; j < fp_choices.getChildren().size(); j++) {
                    fp_choices.getChildren().get(j).getStyleClass().set(0, "button-questionsChoice");
                    fp_choices.getChildren().get(j).setDisable(true);
                    lb_timer.setVisible(false);
                }
                if (timer.isRunning())
                    timer.stop();
                fp_choices.getChildren().get(finalI).getStyleClass().set(0, "button-questionsUserChoice");
            });
        }
        fp_choices.getChildren().clear();
        fp_choices.getChildren().addAll(choices);
        fp_choices.setDisable(true);
    }

    @Override
    public void writeToServer(int command) {
        switch (command) {
            default:
                System.out.println("R4L1 TO: command = " + Integer.toHexString(command) + " | data = ");
                super.writeToServer(command);
                break;
        }
    }

    @Override
    public void writeToServer(int command, LinkedList<String> data) {
        super.writeToServer(command, data);
    }

    @Override
    public void handleServerData(int command, LinkedList<String> data) {
        System.out.println("R4L1 FROM: command = " + Integer.toHexString(command) + " | data = " + data);
        switch (command) {
            case Constants.DIS_R4L1_EXP:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(false);
                gp_example.setVisible(true);
                gp_questions.setVisible(false);
                break;
            case Constants.DIS_R4L1_QST:
                lb_timer.setText("10");
                if (timer != null && timer.isRunning())
                    timer.stop();
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(false);
                gp_example.setVisible(false);
                gp_questions.setVisible(true);
                bt_buzzer.setDisable(false);
                int currentQuestion = Integer.parseInt(data.getFirst());
                System.out.println("Current question: " + currentQuestion);
                try {
                    displayInstruction(currentQuestion);
                    displayQuestion(currentQuestion);
                    displayChoices(currentQuestion);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                break;
            case Constants.S2C_R4L1_SCR:
                lb_score.setText(data.getFirst());
                break;
            case Constants.S2C_R4L1_SEED:
                Main.R4L1_DATA.init(Long.parseLong(data.getFirst()));
                break;
            case Constants.S2C_R4L1_BUZZ:
                bt_buzzer.setDisable(true);
                fp_choices.setDisable(false);
                break;
            case Constants.S2C_R4LX_CRCT:
                if (timer.isRunning())
                    timer.stop();
                break;
            case Constants.S2C_R4LX_WRNG:
                if (timer.isRunning())
                    timer.stop();
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
        for (int i = 0; i < fp_choices.getChildren().size(); i++)
            fp_choices.getChildren().get(i).setDisable(true);
        writeToServer(Constants.C2S_R4LX_TMUP);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        gp_levelTitle.setVisible(true);
        gp_instruction.setVisible(false);
        gp_example.setVisible(false);
        gp_questions.setVisible(false);

        lb_timer.setVisible(false);
        lb_score.setText("0");

        setData();
    }


}
