package data.round1;

import java.util.ArrayList;

public class Level3DataStructure {
    public final String INSTRUCTION_ZH;
    public final String INSTRUCTION_EN;
    public final int TIME_LIMIT;
    public final ArrayList<String> EXAMPLES;
    public final ArrayList<String> QUESTIONS;
    public final ArrayList<String> ANSWERS;

    public Level3DataStructure(String cnInstruction, String enInstruction, int timeLimit, ArrayList<String> examples, ArrayList<String> questions, ArrayList<String> answers) {
        INSTRUCTION_ZH = cnInstruction;
        INSTRUCTION_EN = enInstruction;
        TIME_LIMIT = timeLimit;
        EXAMPLES = examples;
        QUESTIONS = questions;
        ANSWERS = answers;
    }

}
