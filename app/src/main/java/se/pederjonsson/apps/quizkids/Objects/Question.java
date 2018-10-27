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

    public Question(int resId, Category _category, int _drawable/*, DifficultyLevel _difficultyLevel*/){
        this.questionResId = resId;
        this.category = _category;
        this.drawableResID = _drawable;
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
        BUILDINGS,
        GEOGRAPHY,
        SCIENCE,
        MATH,
        ABC,
        OCEAN,
        ANIMALS,
        SUPERHEROES,
        SPORT
    }
    private Category category;
    public Category getCategory() {
        return category;
    }



    public int getDrawableResID(){
        return this.drawableResID;
    }


}
