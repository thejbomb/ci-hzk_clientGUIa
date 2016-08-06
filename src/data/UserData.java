package data;

/**
 * Created by quang on 08/05/16.
 */
public class UserData {
    private final String USER_NAME;
    private final String USER_LEVEL;

    private int round2Point = 0;

    public UserData(String name, String level) {
        USER_NAME = name;
        USER_LEVEL = level;
    }

    public String getName() {
        return USER_NAME;
    }

    public String getLevel(){
        return USER_LEVEL;
    }

    public void setRound2Point(int point) {
        round2Point = point;
    }
}
