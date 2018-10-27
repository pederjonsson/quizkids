package se.pederjonsson.apps.quizkids.db;

import java.util.ArrayList;
import java.util.List;

import se.pederjonsson.apps.quizkids.Objects.Answer;
import se.pederjonsson.apps.quizkids.Objects.Question;
import se.pederjonsson.apps.quizkids.Objects.QuestionAnswers;
import se.pederjonsson.apps.quizkids.R;

/**
 * Created by Gaming on 2018-04-01.
 */

public class Database {

    DatabaseUtil dbUtil;

    public Database(){
        dbUtil = new DatabaseUtil();
    }

    public QuestionAnswers getSampleQuestionAnswers(){

        Question q = new Question(R.string.q_geo_paris, Question.Category.GEOGRAPHY, R.drawable.eiffel200);
        Answer a = new Answer("Eiffel", true);
        Answer b = new Answer("Big Ben", false);
        Answer c = new Answer("Falafel", false);
        List<Answer> answers = new ArrayList<Answer>();
        answers.add(a);
        answers.add(b);
        answers.add(c);

        return new QuestionAnswers(q, answers);
    }

    public List<QuestionAnswers> getQuestionsByCategory(Question.Category category){
        switch (category){
            case GEOGRAPHY:
                return dbUtil.generateQAGeography();
            default:
                return dbUtil.generateQAGeography();
        }
    }

}
