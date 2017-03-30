package main.round1;

import data.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import main.Main;
import tool.Constants;
import tool.Timer;
import tool.TimerInterface;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Level3Controller extends Round1Controller implements Initializable, TimerInterface {

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

    private LinkedList<Label> questions;
    private LinkedList<TextField> textFields;
    private LinkedList<FlowPane> answers;
    private LinkedList<String>[] words = new LinkedList[Main.R1L3_DATA.QUESTIONS.size()];

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
        result.add("ANS11");
        result.add(words[10].toString());
        result.add("ANS12");
        result.add(words[11].toString());
        result.add("ANS13");
        result.add(words[12].toString());
        result.add("ANS14");
        result.add(words[13].toString());
        result.add("ANS15");
        result.add(words[14].toString());
        result.add("ANS16");
        result.add(words[15].toString());
        result.add("ANS17");
        result.add(words[16].toString());
        result.add("ANS18");
        result.add(words[17].toString());
        result.add("ANS19");
        result.add(words[18].toString());
        result.add("ANS20");
        result.add(words[19].toString());
        result.add("ANS21");
        result.add(words[20].toString());

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
            label.setStyle("-fx-font: bold 40pt KaiTi; -fx-text-fill: rgb(0,0,255)");
            area.getChildren().add(label);
            answer.clear();
        }
    }

    private void setData() {
        lb_instructionBody_zh.setText(Main.R1L3_DATA.INSTRUCTION_ZH);
        lb_instructionBody_en.setText(Main.R1L3_DATA.INSTRUCTION_EN);
        String timeLimit_zh = "限时" + Main.R1L3_DATA.TIME_LIMIT + "分钟";
        String timeLimit_en = "Time Limit: " + Main.R1L3_DATA.TIME_LIMIT + (Main.R1L3_DATA.TIME_LIMIT > 1 ? " minutes" : " minute");
        lb_instructionTime_zh.setText(timeLimit_zh);
        lb_instructionTime_en.setText(timeLimit_en);

        lb_exampleBody.setText(Main.R1L3_DATA.EXAMPLES.get(0));


        String timerLabel = Main.R1L3_DATA.TIME_LIMIT + ":00";
        lb_timer.setText(timerLabel);
        for(int i = 0;i <questions.size();i++)
            questions.get(i).setText(Main.R1L3_DATA.QUESTIONS.get(i));
    }

    @Override
    public void writeToServer(int command) {
        switch (command) {
            /*case Constants.DIS_R1L3_EXP:
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
            case Constants.DIS_R1L3_INS:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(true);
                gp_example.setVisible(false);
                gp_questions.setVisible(false);
                break;
            case Constants.DIS_R1L3_EXP:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(false);
                gp_example.setVisible(true);
                gp_questions.setVisible(false);
                break;
            case Constants.DIS_R1L3_QST:
                gp_levelTitle.setVisible(false);
                gp_instruction.setVisible(false);
                gp_example.setVisible(false);
                gp_questions.setVisible(true);

                new Timer(lb_timer, Main.R1L3_DATA.TIME_LIMIT * 60, this,0);

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
        writeToServer(Constants.C2S_R1L3_ANS, packageAnswers());
        //hide();
        round1Controller.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        questions = new LinkedList<>();

        textFields = new LinkedList<>();

        answers = new LinkedList<>();

        for(int i = 0; i < words.length; i++) {
            words[i] = new LinkedList<>();
        }

        for(int i = 0; i < Main.R1L3_DATA.QUESTIONS.size(); i++) {
            HBox hbox = new HBox();
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            Label l = new Label(Main.R1L3_DATA.QUESTIONS.get(i));
            questions.add(l);
            l.setStyle("-fx-font: bold 30pt KaiTi; -fx-text-fill: rgb(150,0,250)");
            TextField text = new TextField();
            text.setOnAction(e -> createSubmitted());
            textFields.add(text);
            text.setPrefWidth(200);
            FlowPane flow = new FlowPane();
            answers.add(flow);
            flow.setPrefWidth(800);
            vbox.getChildren().add(l);
            hbox.getChildren().addAll(vbox, text, flow);
            question.getChildren().add(hbox);
        }

        gp_levelTitle.setVisible(true);
        gp_instruction.setVisible(false);
        gp_example.setVisible(false);
        gp_questions.setVisible(false);
        setData();
    }


}
