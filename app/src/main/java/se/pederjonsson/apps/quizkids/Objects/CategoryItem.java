package se.pederjonsson.apps.quizkids.Objects;

import java.io.Serializable;

/**
 * Created by Gaming on 2018-04-01.
 */

public class CategoryItem implements Serializable {

    private Question.Category category;

    public CategoryItem(Question.Category category){
        this.category = category;
    }

    public String getName() {
        return category.name();
    }

    public Question.Category getCategory() {
        return category;
    }
}
