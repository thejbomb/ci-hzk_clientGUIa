package tool;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

public class Timer {

    private Thread refreshTime;

    public Timer(Label timerLabel, int time, TimerInterface timerInterface, int type) {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                int timeLeft = time;
                while (timeLeft > 0) {
                    timeLeft--;
                    if (timeLeft == 0)
                        timerInterface.takeNotice();
                Thread.sleep(1000);

                    String time;
                    if (type == 0)
                        time = refreshTimer1(timeLeft);
                    else
                        time = Integer.toString(timeLeft);
                    Platform.runLater(() -> timerLabel.setText(time));
                }
                return null;
            }
        };
        refreshTime = new Thread(task);
        refreshTime.setDaemon(true);
        refreshTime.start();
    }

    private String refreshTimer1(int timeLeft) {
        int minute = timeLeft / 60;
        int second = timeLeft % 60;
        return minute + ":" + (second > 9 ? second : ("0" + second));
    }

    public void stop(){
        refreshTime.interrupt();
    }

}
