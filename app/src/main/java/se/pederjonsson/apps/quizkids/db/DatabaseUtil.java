package se.pederjonsson.apps.quizkids.db;

import java.util.ArrayList;
import java.util.List;

import se.pederjonsson.apps.quizkids.Objects.Answer;
import se.pederjonsson.apps.quizkids.Objects.Question;
import se.pederjonsson.apps.quizkids.Objects.QuestionAnswers;
import se.pederjonsson.apps.quizkids.R;

/**
 * Created by Gaming on 2018-04-02.
 */

public class DatabaseUtil {

    public DatabaseUtil(){

    }

    public List<QuestionAnswers> getSampleQuestion(){
        List<QuestionAnswers> qaList = new ArrayList<>();

        Question q = new Question(R.string.q_geo_paris, Question.Category.GEOGRAPHY, R.drawable.eiffel200);
        Answer a = new Answer("Eiffel", true);
        Answer b = new Answer("Big Ben", false);
        Answer c = new Answer("Falafel", false);
        List<Answer> answers = new ArrayList<Answer>();
        answers.add(a);
        answers.add(b);
        answers.add(c);

        qaList.add(new QuestionAnswers(q, answers));

        return qaList;
    }

    public List<QuestionAnswers> generateQAGeography(){
        List<QuestionAnswers> qaList = new ArrayList<>();

        qaList.add(generateQA(R.string.q_geo_paris, Question.Category.GEOGRAPHY, R.drawable.eiffel200, "Eiffel", "Big Ben", "Falafel"));
        qaList.add(generateQA(R.string.q_geo_mounteverest, Question.Category.GEOGRAPHY, R.drawable.mnteverest, "Mount Everest", "Kilimanjaro", "Big Mac"));

        return qaList;
    }

    private QuestionAnswers generateQA(int questionResId, Question.Category category, int drawableResId, String trueAnswer, String answer2, String answer3){
        return new QuestionAnswers(generateQuestion(questionResId, category,drawableResId), generateAnswers(trueAnswer, answer2, answer3));
    }

    private Question generateQuestion(int questionResId, Question.Category category, int drawableResId){
        return new Question(questionResId, category,drawableResId);
    }

    private List<Answer> generateAnswers (String trueAnswer, String answer2, String answer3){
        List<Answer> answers = new ArrayList<Answer>();
        Answer a = new Answer(trueAnswer, true);
        Answer b = new Answer(answer2, false);
        Answer c = new Answer(answer3, false);
        answers.add(a);
        answers.add(b);
        answers.add(c);
        return answers;
    }
}
