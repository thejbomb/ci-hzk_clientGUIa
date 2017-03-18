package main.round2;

import data.UserData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.shape.Polyline;
import main.Main;
import tool.Constants;
import tool.DrawingPad;
import tool.Timer;
import tool.TimerInterface;

import java.net.URL;
import java.util.Arrays;
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
    private VBox question;
    @FXML
    private AnchorPane ap_drawingPane;
    @FXML
    private Button bt_submit;
    @FXML
    private Button bt_clear;

    private LinkedList<Label> questions;
    private LinkedList<FlowPane> answers;
    private LinkedList<LinkedList<Polyline>>[] lines = new LinkedList[Main.R2L1_DATA.QUESTIONS.size()];

    private int currentQuestion = 0;

    private Round2Controller round2Controller;

    public void init(UserData user, Round2Controller controller) {
        userData = user;
        round2Controller = controller;

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
            answers.get(currentQuestion).getChildren().clear();
            for (LinkedList<Polyline> ll : lines[currentQuestion]) {
                Pane pane = new Pane();
                Image image = new Image("file:src/main/round2/paper.jpg");
                BackgroundImage bgi = new BackgroundImage(image,null,null,BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
                Background bg = new Background(bgi);
                image = new Image("file:src/main/round2/paper2.jpg");
                bgi = new BackgroundImage(image,null,null,BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
                Background bg2 = new Background(bgi);
                pane.setBackground(bg);
                pane.setUserData("notSelected");
                System.out.println(pane.getUserData().toString());
                pane.setOnMouseClicked(ee -> {
                    if (ee.getButton() == MouseButton.SECONDARY && pane.getUserData().toString().compareTo("selected") == 0) {
                        for (FlowPane fp : answers)
                            fp.getChildren().remove(pane);
                        for (LinkedList<LinkedList<Polyline>> lll : lines)
                            lll.remove(ll);
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
        result.add("ANS1");
        System.out.println(result);
        result.add(lines[0].toString());
        result.add("ANS2");
        System.out.println(result);
        result.add(lines[1].toString());
        result.add("ANS3");
        System.out.println(result);
        result.add(lines[2].toString());
        result.add("ANS4");
        System.out.println(result);
        result.add(lines[3].toString());
        result.add("ANS5");
        System.out.println(result);
        result.add(lines[4].toString());
        System.out.println(result);
        result.add("ANS6");
        System.out.println(result);
        result.add(lines[5].toString());
        result.add("ANS7");
        System.out.println(result);
        result.add(lines[6].toString());
        result.add("ANS8");
        System.out.println(result);
        result.add(lines[7].toString());
        result.add("ANS9");
        System.out.println(result);
        result.add(lines[8].toString());
        result.add("ANS10");
        System.out.println(result);
        result.add(lines[9].toString());
        System.out.println(result);
        return result;

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
        for (int i = 0; i < questions.size(); i++)
            questions.get(i).setText(Main.R2L1_DATA.QUESTIONS.get(i));

    }

    @Override
    public void writeToServer(int command) {
        switch (command) {
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
        System.out.println("R2L1 FROM: " + command + " | " + data);
        switch (command) {
            case Constants.DIS_R2L1_INS:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(true);
                gp_example.setVisible(false);
                gp_questions.setVisible(false);
                break;
            case Constants.DIS_R2L1_EXP:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(false);
                gp_example.setVisible(true);
                gp_questions.setVisible(false);
                break;
            case Constants.DIS_R2L1_QST:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(false);
                gp_example.setVisible(false);
                gp_questions.setVisible(true);

                new Timer(lb_timer, Main.R2L1_DATA.TIME_LIMIT * 60, this, 0);

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
        writeToServer(Constants.C2S_R2L1_ANS, packageAnswers());
        // hide();
        round2Controller.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        questions = new LinkedList<>();
        answers = new LinkedList<>();

        Insets s = new Insets(15, 10, 15, 0);

        for(int i = 0; i < Main.R2L1_DATA.QUESTIONS.size(); i++) {
            HBox hbox = new HBox();
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            Label l = new Label();
            questions.add(l);
            l.setStyle("-fx-font: bold 35pt KaiTi; -fx-text-fill: rgb(0,0,160)");
            l.setPadding(s);
            FlowPane flow = new FlowPane();
            answers.add(flow);
            vbox.getChildren().add(l);
            hbox.getChildren().addAll(vbox, flow);
            question.getChildren().add(hbox);
        }

        for (FlowPane fp : answers) {
            fp.setVisible(false);
            fp.setHgap(5);
            fp.setVgap(5);
        }

        for (int i = 0; i < lines.length; i++)
            lines[i] = new LinkedList<>();

        gp_levelTitle.setVisible(true);
        gp_instruction.setVisible(false);
        gp_example.setVisible(false);
        gp_questions.setVisible(false);
        setData();
    }
}
