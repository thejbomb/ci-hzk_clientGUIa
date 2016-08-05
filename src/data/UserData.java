package data;

/**
 * Created by quang on 08/05/16.
 */
public class UserData {
    private final String USER_NAME;

    private int round2Point = 0;

    public UserData(String name) {
        USER_NAME = name;
    }

    public String getName() {
        return USER_NAME;
    }

    public void setRound2Point(int point) {
        round2Point = point;
    }
}
