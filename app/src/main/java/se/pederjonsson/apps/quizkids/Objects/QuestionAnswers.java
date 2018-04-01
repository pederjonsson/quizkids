package se.pederjonsson.apps.quizkids.Objects;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gaming on 2018-04-01.
 */

public class QuestionAnswers implements Serializable {

    private Question textQuestion;
    private Answer textAnswer1;
    private Answer textAnswer2;
    private Answer textAnswer3;
    private List<Answer> answers;

    public QuestionAnswers(Question question, List<Answer> answers){
        this.textQuestion = question;
        this.answers = answers;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public Question getTextQuestion() {
        return textQuestion;
    }

    public void setTextQuestion(Question textQuestion) {
        this.textQuestion = textQuestion;
    }

    public Answer getTextAnswer1() {
        return textAnswer1;
    }

    public void setTextAnswer1(Answer textAnswer1) {
        this.textAnswer1 = textAnswer1;
    }

    public Answer getTextAnswer2() {
        return textAnswer2;
    }

    public void setTextAnswer2(Answer textAnswer2) {
        this.textAnswer2 = textAnswer2;
    }

    public Answer getTextAnswer3() {
        return textAnswer3;
    }

    public void setTextAnswer3(Answer textAnswer3) {
        this.textAnswer3 = textAnswer3;
    }



}
