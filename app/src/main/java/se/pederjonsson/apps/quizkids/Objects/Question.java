package se.pederjonsson.apps.quizkids.Objects;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by Gaming on 2018-04-01.
 */

public class Question implements Serializable {

    public int getQuestionResId() {
        return questionResId;
    }
    private int drawableResID = -1;
    private int questionResId;
    private DifficultyLevel difficultyLevel;
    private Category category;


    public Question(int resId, Category _category, int _drawable, DifficultyLevel _difficultyLevel){
        this.questionResId = resId;
        this.category = _category;
        this.drawableResID = _drawable;
        this.difficultyLevel = _difficultyLevel;
    }

    public enum DifficultyLevel {
       EASY("easy"), MEDIUM("medium"), HARD("hard");

        public String getDifficultyLevel() {
            return difficultyLevel;
        }

        private String difficultyLevel;

        DifficultyLevel(String _difficultyLevel) {
            difficultyLevel = _difficultyLevel;
        }
    }

    public enum Category {

        BUILDINGS("buildings"),
        OCEAN("ocean"),
        ANIMALS("animals"),
        SUPERHEROES("superheroes"),
        SPORT("sport"),
        GEOGRAPHY("geography"),
        SCIENCE("science"),
        MATH("math"),
        ABC("abc");

        public String getCategory(){return category;}
        private String category;

        Category(String _category){
            category = _category;
        }
    }

    public Category getCategory() {
        return category;
    }

    public String getCategoryString() {
        return category.category;
    }

    public String getDifficultyLevel() {
        return difficultyLevel.difficultyLevel;
    }

    public DifficultyLevel getDifficultyLevelEnum() {
        return difficultyLevel;
    }

    public int getDrawableResID(){
        return this.drawableResID;
    }


}
