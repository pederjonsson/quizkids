package se.pederjonsson.apps.quizkids.Objects;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.logging.Logger;

import se.pederjonsson.apps.quizkids.R;

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
    static Logger l = Logger.getGlobal();

    public enum Category {

        BUILDINGS("buildings"),
        GEOGRAPHY("geography"),
        SCIENCE("science"),
        MATH("math"),
        ABC("abc"),
        OCEAN("ocean"),
        ANIMALS("animals"),
        SUPERHEROES("superheroes"),
        SPORT("sport");

        public String getCategory(){return category;}

        public String getCategoryTranslated(Context mContext){
            int stringId = mContext.getResources().getIdentifier(category, "string", mContext.getPackageName());
            String catTranslated = mContext.getString(stringId);
            l.info("**** catTranslated = " + catTranslated);
            return mContext.getString(stringId);
        }
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
