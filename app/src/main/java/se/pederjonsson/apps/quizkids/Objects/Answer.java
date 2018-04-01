package se.pederjonsson.apps.quizkids.Objects;

/**
 * Created by Gaming on 2018-04-01.
 */

public class Answer {

    private String textAnswer;
    private boolean isCorrect;

    public Answer(String text, boolean correct){
        this.textAnswer = text;
        this.isCorrect = correct;
    }

    public String getTextAnswer() {
        return textAnswer;
    }

    public void setTextAnswer(String textAnswer) {
        this.textAnswer = textAnswer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }


}
