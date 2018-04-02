package se.pederjonsson.apps.quizkids.db;

import java.util.ArrayList;
import java.util.List;

import se.pederjonsson.apps.quizkids.Objects.Answer;
import se.pederjonsson.apps.quizkids.Objects.Question;
import se.pederjonsson.apps.quizkids.Objects.QuestionAnswers;
import se.pederjonsson.apps.quizkids.fragments.QuestionAnswerContract;

/**
 * Created by Gaming on 2018-04-01.
 */

public class Database {

    public Database(){

    }

    public QuestionAnswers getSampleQuestionAnswers(){

        Question q = new Question("vad heter tornet i paris?", Question.Category.GEOGRAPHY);
        Answer a = new Answer("Eiffel", true);
        Answer b = new Answer("Big Ben", false);
        Answer c = new Answer("Falafel", false);
        List<Answer> answers = new ArrayList<Answer>();
        answers.add(a);
        answers.add(b);
        answers.add(c);

        return new QuestionAnswers(q, answers);
    }

    public QuestionAnswerContract getQuestionsByCategory(Question.Category category){
        switch (category){
            case Question.Category.GEOGRAPHY:
                return getGeographyQuestions();
            default:
                return getGeographyQuestions();
        }
    }

    private void getGeographyQuestions(){

    }

}
