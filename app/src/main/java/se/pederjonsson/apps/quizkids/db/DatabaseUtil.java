package se.pederjonsson.apps.quizkids.db;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import se.pederjonsson.apps.quizkids.Objects.Answer;
import se.pederjonsson.apps.quizkids.Objects.Question;
import se.pederjonsson.apps.quizkids.Objects.QuestionAnswers;
import se.pederjonsson.apps.quizkids.R;

/**
 * Created by Gaming on 2018-04-02.
 */

public class DatabaseUtil {

    Logger l = Logger.getGlobal();
    public DatabaseUtil(){

    }

    public List<QuestionAnswers> getSampleQuestion(){
        List<QuestionAnswers> qaList = new ArrayList<>();

        Question q = new Question(R.string.q_buildings_paris, Question.Category.GEOGRAPHY, R.drawable.question_eiffel200, Question.DifficultyLevel.EASY);
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

        qaList.add(generateQA(R.string.q_buildings_paris, Question.Category.GEOGRAPHY, R.drawable.question_eiffel200, Question.DifficultyLevel.EASY,"Eiffel", "Big Ben", "Falafel"));
        qaList.add(generateQA(R.string.q_buildings_mounteverest, Question.Category.GEOGRAPHY, R.drawable.question_mnteverest, Question.DifficultyLevel.EASY,"Mount Everest", "Kilimanjaro", "Big Mac"));
        qaList.add(generateQA(R.string.q_buildings_paris, Question.Category.GEOGRAPHY, R.drawable.question_eiffel200, Question.DifficultyLevel.EASY,"Eiffel", "Big Ben", "Falafel"));
        qaList.add(generateQA(R.string.q_buildings_mounteverest, Question.Category.GEOGRAPHY, R.drawable.question_mnteverest, Question.DifficultyLevel.EASY,"Mount Everest", "Kilimanjaro", "Big Mac"));
        qaList.add(generateQA(R.string.q_buildings_paris, Question.Category.GEOGRAPHY, R.drawable.question_eiffel200, Question.DifficultyLevel.EASY,"Eiffel", "Big Ben", "Falafel"));
        qaList.add(generateQA(R.string.q_buildings_mounteverest, Question.Category.GEOGRAPHY, R.drawable.question_mnteverest, Question.DifficultyLevel.EASY,"Mount Everest", "Kilimanjaro", "Big Mac"));
        qaList.add(generateQA(R.string.q_buildings_paris, Question.Category.GEOGRAPHY, R.drawable.question_eiffel200, Question.DifficultyLevel.EASY,"Eiffel", "Big Ben", "Falafel"));
        qaList.add(generateQA(R.string.q_buildings_mounteverest, Question.Category.GEOGRAPHY, R.drawable.question_mnteverest, Question.DifficultyLevel.EASY,"Mount Everest", "Kilimanjaro", "Big Mac"));
        qaList.add(generateQA(R.string.q_buildings_paris, Question.Category.GEOGRAPHY, R.drawable.question_eiffel200, Question.DifficultyLevel.EASY,"Eiffel", "Big Ben", "Falafel"));
        qaList.add(generateQA(R.string.q_buildings_mounteverest, Question.Category.GEOGRAPHY, R.drawable.question_mnteverest, Question.DifficultyLevel.EASY,"Mount Everest", "Kilimanjaro", "Big Mac"));

        return qaList;
    }

    public List<QuestionAnswers> generateQABuildings(Context context){
        List<QuestionAnswers> qaList = new ArrayList<>();

        qaList.add(generateQA(R.string.q_buildings_paris, Question.Category.BUILDINGS, R.drawable.question_eiffel200, Question.DifficultyLevel.EASY,"Eiffel", "Big Ben", "Falafel"));
        qaList.add(generateQA(R.string.q_buildings_mounteverest, Question.Category.BUILDINGS, R.drawable.question_mnteverest, Question.DifficultyLevel.EASY,"Mount Everest", "Kilimanjaro", "Big Mac"));
        qaList.add(generateQA(R.string.q_buildings_burjikhalifa, Question.Category.BUILDINGS, R.drawable.q_burjikhalifa, Question.DifficultyLevel.EASY,"Dubai", "London", "New York"));
        qaList.add(generateQA(R.string.q_buildings_stbasil, Question.Category.BUILDINGS, R.drawable.q_stbasilscathedral,
                Question.DifficultyLevel.EASY,context.getString(R.string.q_buildings_stbasil_correctanswer), context.getString(R.string.q_buildings_stbasil_answer2), "Peking"));
        qaList.add(generateQA(R.string.q_buildings_sphinx, Question.Category.BUILDINGS, R.drawable.q_sphinx, Question.DifficultyLevel.EASY,context.getString(R.string.q_buildings_sphinx_correct_answer), context.getString(R.string.q_buildings_sphinx_answer2), context.getString(R.string.q_buildings_sphinx_answer3)));
        qaList.add(generateQA(R.string.q_buildings_chinesewall, Question.Category.BUILDINGS, R.drawable.q_chinesewall, Question.DifficultyLevel.EASY,context.getString(R.string.q_buildings_chinesewall_correct_answer), context.getString(R.string.q_buildings_chinesewall_answer2), context.getString(R.string.q_buildings_chinesewall_answer3)));
        qaList.add(generateQA(R.string.q_buildings_burjikhalifa7, Question.Category.BUILDINGS, R.drawable.question_eiffel200, Question.DifficultyLevel.EASY,"Eiffel", "Big Ben", "Falafel"));
        qaList.add(generateQA(R.string.q_buildings_burjikhalifa8, Question.Category.BUILDINGS, R.drawable.question_mnteverest, Question.DifficultyLevel.EASY,"Mount Everest", "Kilimanjaro", "Big Mac"));
        qaList.add(generateQA(R.string.q_buildings_burjikhalifa9, Question.Category.BUILDINGS, R.drawable.question_eiffel200, Question.DifficultyLevel.EASY,"Eiffel", "Big Ben", "Falafel"));
        qaList.add(generateQA(R.string.q_buildings_burjikhalifa10, Question.Category.BUILDINGS, R.drawable.question_mnteverest, Question.DifficultyLevel.EASY,"Mount Everest", "Kilimanjaro", "Big Mac"));

        return qaList;
    }

    public List<QuestionAnswers> generateQAScience(){
        List<QuestionAnswers> qaList = new ArrayList<>();

        qaList.add(generateQA(R.string.q_buildings_paris, Question.Category.SCIENCE, R.drawable.question_eiffel200, Question.DifficultyLevel.EASY,"Eiffel", "Big Ben", "Falafel"));
        qaList.add(generateQA(R.string.q_buildings_mounteverest, Question.Category.SCIENCE, R.drawable.question_mnteverest, Question.DifficultyLevel.EASY,"Mount Everest", "Kilimanjaro", "Big Mac"));
        qaList.add(generateQA(R.string.q_buildings_paris, Question.Category.SCIENCE, R.drawable.question_eiffel200, Question.DifficultyLevel.EASY,"Eiffel", "Big Ben", "Falafel"));
        qaList.add(generateQA(R.string.q_buildings_mounteverest, Question.Category.SCIENCE, R.drawable.question_mnteverest, Question.DifficultyLevel.EASY,"Mount Everest", "Kilimanjaro", "Big Mac"));
        qaList.add(generateQA(R.string.q_buildings_paris, Question.Category.SCIENCE, R.drawable.question_eiffel200, Question.DifficultyLevel.EASY,"Eiffel", "Big Ben", "Falafel"));
        qaList.add(generateQA(R.string.q_buildings_mounteverest, Question.Category.SCIENCE, R.drawable.question_mnteverest, Question.DifficultyLevel.EASY,"Mount Everest", "Kilimanjaro", "Big Mac"));
        qaList.add(generateQA(R.string.q_buildings_paris, Question.Category.SCIENCE, R.drawable.question_eiffel200, Question.DifficultyLevel.EASY,"Eiffel", "Big Ben", "Falafel"));
        qaList.add(generateQA(R.string.q_buildings_mounteverest, Question.Category.SCIENCE, R.drawable.question_mnteverest, Question.DifficultyLevel.EASY,"Mount Everest", "Kilimanjaro", "Big Mac"));
        qaList.add(generateQA(R.string.q_buildings_paris, Question.Category.SCIENCE, R.drawable.question_eiffel200, Question.DifficultyLevel.EASY,"Eiffel", "Big Ben", "Falafel"));
        qaList.add(generateQA(R.string.q_buildings_mounteverest, Question.Category.SCIENCE, R.drawable.question_mnteverest, Question.DifficultyLevel.EASY,"Mount Everest", "Kilimanjaro", "Big Mac"));

        return qaList;
    }

    public void populateDB(Database database, Context context){
        insertQaIntoDB(generateQAGeography(), database);
        insertQaIntoDB(generateQABuildings(context), database);
    }

    private void insertQaIntoDB(List<QuestionAnswers> qaList, Database database){
        for (QuestionAnswers q:qaList) {
            l.info("**** insert " + q.getQuestion().getCategoryString());
            database.insertQa(q.getQuestion().getCategoryString(), q.getQuestion().getDifficultyLevel(), q.getQuestion().getQuestionResId(), q);
        }
    }

    private QuestionAnswers generateQA(int questionResId, Question.Category category, int drawableResId, Question.DifficultyLevel difficultyLevel, String trueAnswer, String answer2, String answer3){
        return new QuestionAnswers(generateQuestion(questionResId, category,drawableResId, difficultyLevel), generateAnswers(trueAnswer, answer2, answer3));
    }

    private Question generateQuestion(int questionResId, Question.Category category, int drawableResId, Question.DifficultyLevel difficultyLevel){
        return new Question(questionResId, category,drawableResId, difficultyLevel);
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
