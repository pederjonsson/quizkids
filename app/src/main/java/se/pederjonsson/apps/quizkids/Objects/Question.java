package se.pederjonsson.apps.quizkids.Objects;

/**
 * Created by Gaming on 2018-04-01.
 */

public class Question {

    public String getQuestion() {
        return question;
    }

    private String question;

    public Question(String text){
        this.question = text;
    }
}
