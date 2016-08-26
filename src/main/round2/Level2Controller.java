package main.round2;

import data.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;
import main.Main;
import tool.Constants;
import tool.DrawingPad;
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
    private AnchorPane ap_drawingPane;
    @FXML
    private Button bt_submit;
    @FXML
    private Button bt_clear;
    @FXML
    private FlowPane fp_answers1;
    @FXML
    private FlowPane fp_answers2;
    @FXML
    private FlowPane fp_answers3;
    @FXML
    private FlowPane fp_answers4;

    private LinkedList<Label> questions;
    private LinkedList<FlowPane> answers;
    private LinkedList<LinkedList<Polyline>>[] lines = new LinkedList[4];

    private int currentQuestion = 0;

    private Round2Controller round2Controller;

    public void init(UserData user, Round2Controller controller) {
        userData = user;
        round2Controller = controller;
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

        DrawingPad pad = new DrawingPad();
        pad.setStyle("-fx-background-color: white;");
        ap_drawingPane.setVisible(false);
        ap_drawingPane.getChildren().add(pad);
        pad.init();
        for (int i = 0; i < questions.size(); i++) {
            Label lb = questions.get(i);
            int finalI = i;
            lb.setOnMouseClicked(e -> {
                currentQuestion = finalI;
                ap_drawingPane.setVisible(true);
                pad.getChildren().clear();
                pad.startDrawing();
                System.out.println((ap_drawingPane.localToScene(ap_drawingPane.getBoundsInLocal()).getWidth() - pad.localToScene(pad.getBoundsInLocal()).getWidth()) / 2);
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
            lines[currentQuestion].add(pad.getSmaller(6));
            String s = lines[currentQuestion].toString();
            System.out.println(s);
            answers.get(currentQuestion).getChildren().clear();
            for (LinkedList<Polyline> ll : lines[currentQuestion]) {
                Pane pane = new Pane();
                pane.setStyle("-fx-background-color: yellow;");
                pane.setOnMouseClicked(ee -> {
                    if (ee.getButton() == MouseButton.SECONDARY && pane.getStyle().compareTo("-fx-background-color: red") == 0) {
                        for (FlowPane fp : answers)
                            fp.getChildren().remove(pane);
                        for (LinkedList<LinkedList<Polyline>> lll : lines)
                            lll.remove(ll);
                    } else if (ee.getButton() == MouseButton.PRIMARY) {
                        if (pane.getStyle().compareTo("-fx-background-color: red") != 0) {
                            pane.setStyle("-fx-background-color: red");
                        } else {
                            pane.setStyle("-fx-background-color: yellow;");
                        }
                    }

                });

                pane.getChildren().addAll(ll);
                answers.get(currentQuestion).getChildren().add(pane);
            }
            answers.get(currentQuestion).setVisible(true);
        });

        bt_clear.setOnMouseClicked(e -> {
            pad.startDrawing();
            pad.getChildren().clear();
        });
    }

    private LinkedList<String> packageAnswers() {

        LinkedList<String> result = new LinkedList<>();

        return result;

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
        for (int i = 0; i < questions.size(); i++)
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

                new Timer(lb_timer, Main.R2L2_DATA.TIME_LIMIT * 60, this, 0);

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
        answers.add(fp_answers1);
        answers.add(fp_answers2);
        answers.add(fp_answers3);
        answers.add(fp_answers4);

        for (int i = 0; i < lines.length; i++)
            lines[i] = new LinkedList<>();

        gp_levelTitle.setVisible(true);
        gp_instruction.setVisible(false);
        gp_example.setVisible(false);
        gp_questions.setVisible(false);
        setData();
    }


}
