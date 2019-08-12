package se.pederjonsson.apps.quizkids.data;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by Gaming on 2018-04-01.
 */

public class CategoryItem implements Serializable {

    private Category category;

    public CategoryItem(Category category){
        this.category = category;
    }

    public String getName() {
        return category.name();
    }

    public Category getCategory() {
        return category;
    }

    public enum  Category {

        BUILDINGS("buildings"),
        OCEAN("ocean"),
        MATH("math"),
        SCIENCE("science"),
        ABC("abc"),
        ANIMALS("animals"),
        SUPERHEROES("superheroes"),
        SPORT("sport"),
        GEOGRAPHY("geography"),
        QUICKPLAY("quickplay");

        public String getCategory(){return category;}

        public String getCategoryTranslated(Context mContext){
            int stringId = mContext.getResources().getIdentifier(category, "string", mContext.getPackageName());
            return mContext.getString(stringId);
        }
        private String category;

        Category(String _category){
            category = _category;
        }
    }

    public String getCategoryString() {
        return category.category;
    }
}
