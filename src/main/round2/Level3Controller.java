package main.round2;

import data.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import main.Main;
import tool.Constants;
import tool.DrawingPad;
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
    private AnchorPane ap_drawingPane;
    @FXML
    private Button bt_submit;
    @FXML
    private Button bt_clear;
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
    private LinkedList<LinkedList<Polyline>> lines = new LinkedList<>();
    private LinkedList<FlowPane> answers;

    private LinkedList<String> randomizedAnswers;
    private int currentQuestion = 0;

    private Round2Controller round2Controller;

    public void init(UserData user, Round2Controller controller) {
        this.userData = user;
        this.round2Controller = controller;

        lines = new LinkedList<>();
        for (int i = 0; i < questions.size(); i++)
            lines.add(new LinkedList<>());
        randomizedAnswers = randomizeAnswers(Main.R2L3_DATA.ANSWERS);

        setData();
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
        for (int i = 0; i < answers.size(); i++) {
            result.add("ANS" + (i + 1));
            result.add(lines.get(i).toString());
        }
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
        DrawingPad pad = new DrawingPad();
        pad.setStyle("-fx-background-color: white;");
        ap_drawingPane.setVisible(false);
        ap_drawingPane.getChildren().add(pad);
        pad.init();
        for (int i = 0; i < questions.size(); i++) {
            questions.get(i).setText((i + 1) + "." + Main.R2L3_DATA.QUESTIONS.get(i));

            int finalI = i;
            questions.get(i).setOnMouseClicked(e -> {
                currentQuestion = finalI;
                ap_drawingPane.setVisible(true);
                pad.getChildren().clear();
                pad.startDrawing();
                AnchorPane.setLeftAnchor(bt_submit, (ap_drawingPane.localToScene(ap_drawingPane.getBoundsInLocal()).getWidth() - pad.localToScene(pad.getBoundsInLocal()).getWidth()) / 2);
                AnchorPane.setRightAnchor(bt_clear, (ap_drawingPane.localToScene(ap_drawingPane.getBoundsInLocal()).getWidth() - pad.localToScene(pad.getBoundsInLocal()).getWidth()) / 2);
                AnchorPane.setTopAnchor(bt_submit, (ap_drawingPane.localToScene(ap_drawingPane.getBoundsInLocal()).getHeight() - pad.localToScene(pad.getBoundsInLocal()).getHeight()) / 2 -
                        bt_submit.localToScene(bt_submit.getBoundsInLocal()).getHeight());
                AnchorPane.setTopAnchor(bt_clear, (ap_drawingPane.localToScene(ap_drawingPane.getBoundsInLocal()).getHeight() - pad.localToScene(pad.getBoundsInLocal()).getHeight()) / 2 -
                        bt_clear.localToScene(bt_clear.getBoundsInLocal()).getHeight());
                AnchorPane.setLeftAnchor(pad, (ap_drawingPane.localToScene(ap_drawingPane.getBoundsInLocal()).getWidth() - pad.localToScene(pad.getBoundsInLocal()).getWidth()) / 2);
                AnchorPane.setRightAnchor(pad, (ap_drawingPane.localToScene(ap_drawingPane.getBoundsInLocal()).getWidth() - pad.localToScene(pad.getBoundsInLocal()).getWidth()) / 2);
                AnchorPane.setTopAnchor(pad, (ap_drawingPane.localToScene(ap_drawingPane.getBoundsInLocal()).getHeight() - pad.localToScene(pad.getBoundsInLocal()).getHeight()) / 2);
                AnchorPane.setBottomAnchor(pad, (ap_drawingPane.localToScene(ap_drawingPane.getBoundsInLocal()).getHeight() - pad.localToScene(pad.getBoundsInLocal()).getHeight()) / 2);
                ap_drawingPane.widthProperty().addListener(((observable, oldValue, newValue) -> {
                    pad.resize(ap_drawingPane.getScene().getWindow().getWidth() / 4, ap_drawingPane.getScene().getWindow().getWidth() / 4);
                    AnchorPane.setLeftAnchor(bt_submit, (newValue.doubleValue() - pad.localToScene(pad.getBoundsInLocal()).getWidth()) / 2);
                    AnchorPane.setRightAnchor(bt_clear, (newValue.doubleValue() - pad.localToScene(pad.getBoundsInLocal()).getWidth()) / 2);
                    AnchorPane.setLeftAnchor(pad, (newValue.doubleValue() - pad.localToScene(pad.getBoundsInLocal()).getWidth()) / 2);
                    AnchorPane.setRightAnchor(pad, (newValue.doubleValue() - pad.localToScene(pad.getBoundsInLocal()).getWidth()) / 2);
                }));
                ap_drawingPane.heightProperty().addListener((observable, oldValue, newValue) -> {
                    AnchorPane.setTopAnchor(bt_submit, (newValue.doubleValue() - pad.localToScene(pad.getBoundsInLocal()).getHeight()) / 2 -
                            bt_submit.localToScene(bt_submit.getBoundsInLocal()).getHeight());
                    AnchorPane.setTopAnchor(bt_clear, (newValue.doubleValue() - pad.localToScene(pad.getBoundsInLocal()).getHeight()) / 2 -
                            bt_clear.localToScene(bt_clear.getBoundsInLocal()).getHeight());
                    AnchorPane.setTopAnchor(pad, (newValue.doubleValue() - pad.localToScene(pad.getBoundsInLocal()).getHeight()) / 2);
                    AnchorPane.setBottomAnchor(pad, (newValue.doubleValue() - pad.localToScene(pad.getBoundsInLocal()).getHeight()) / 2);
                });
            });
        }

        bt_submit.setOnMouseClicked(e -> {
            ap_drawingPane.setVisible(false);
            lines.set(currentQuestion, pad.getSmaller(4));
            answers.get(currentQuestion).getChildren().clear();

            Pane pane = new Pane();
            pane.setPrefSize(Main.WINDOW_WIDTH/16,Main.WINDOW_WIDTH/16);
            Image image = new Image("file:src/main/round2/paper.jpg");
            BackgroundImage bgi = new BackgroundImage(image,null,null,BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
            Background bg = new Background(bgi);
            image = new Image("file:src/main/round2/paper2.jpg");
            bgi = new BackgroundImage(image,null,null,BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
            Background bg2 = new Background(bgi);
            pane.setBackground(bg);
            pane.setUserData("notSelected");
            pane.setOnMouseClicked(ee -> {
                if (ee.getButton() == MouseButton.SECONDARY && pane.getUserData().toString().compareTo("selected") == 0) {
                    for (FlowPane fp : answers)
                        fp.getChildren().remove(pane);
                    lines.set(currentQuestion,new LinkedList<>());
                } else if (ee.getButton() == MouseButton.PRIMARY) {
                    if (pane.getUserData().toString().compareTo("selected") != 0) {
                        pane.setUserData("selected");
                        pane.setBackground(bg2);
                    } else {
                        pane.setUserData("notSelected");
                        pane.setBackground(bg);
                    }
                }

            });

            pane.getChildren().addAll(lines.get(currentQuestion));
            answers.get(currentQuestion).getChildren().add(pane);

            answers.get(currentQuestion).setVisible(true);
        });

        bt_clear.setOnMouseClicked(e -> {
            pad.startDrawing();
            pad.getChildren().clear();
        });

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
            case Constants.DIS_R2L3_INS:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(true);
                gp_example.setVisible(false);
                gp_questions.setVisible(false);
                break;
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

                new Timer(lb_timer, Main.R2L3_DATA.TIME_LIMIT * 60, this, 0);

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

        gp_levelTitle.setVisible(true);
        gp_instruction.setVisible(false);
        gp_example.setVisible(false);
        gp_questions.setVisible(false);
    }


}
