package data;

/**
 * Created by quang on 08/05/16.
 */
public class UserData {
    private final String USER_NAME;
    private final String USER_LEVEL;

    private int round1Point = 0;

    private int round2Point = 0;

    private int round3Point = 0;

    private int round5Point = 0;

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

    public void setRound1Point(int point) {
        round1Point = point;
    }

    public void setRound2Point(int point) {
        round2Point = point;
    }

    public void setRound3Point(int point) {
        round3Point = point;
    }

    public void setRound5Point(int point){
        round5Point = point;
    }
}
