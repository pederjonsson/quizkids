package se.pederjonsson.apps.quizkids.Objects;

/**
 * Created by Gaming on 2018-04-01.
 */

public class Question {

    public int getQuestionResId() {
        return questionResId;
    }

    private int questionResId;
    private DifficultyLevel difficultyLevel;

    public Question(int resId, Category _category/*, DifficultyLevel _difficultyLevel*/){
        this.questionResId = resId;
        this.category = _category;
        //this.difficultyLevel = _difficultyLevel;
    }

    public enum DifficultyLevel {
       EASY(1), MEDIUM(2), HARD(3);

        public int getDifficultyLevel() {
            return difficultyLevel;
        }

        private int difficultyLevel;

        DifficultyLevel(int _difficultyLevel) {
            difficultyLevel = _difficultyLevel;
        }
    }

    public enum Category {
        GEOGRAPHY,
        SCIENCE,
        MATH,
        ABC
    }

    public Category getCategory() {
        return category;
    }

    private Category category;


}
