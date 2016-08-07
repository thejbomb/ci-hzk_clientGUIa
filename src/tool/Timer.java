package tool;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

public class Timer {

    public Timer(Label timerLabel, int time, TimerInterface timerInterface) {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                int timeLeft = time;
                while (timeLeft > 0) {
                    timeLeft--;
                    if (timeLeft == 0)
                        timerInterface.takeNotice();
                    Thread.sleep(100);
                    final String TIME = refreshTimer(timeLeft);
                    Platform.runLater(() -> timerLabel.setText(TIME));
                }
                return null;
            }
        };
        Thread refreshTime = new Thread(task);
        refreshTime.setDaemon(true);
        refreshTime.start();
    }

    private String refreshTimer(int timeLeft) {
        int minute = timeLeft / 60;
        int second = timeLeft % 60;
        return minute + ":" + (second > 9 ? second : ("0" + second));
    }

}
