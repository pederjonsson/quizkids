package se.pederjonsson.apps.quizkids.Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gaming on 2018-04-01.
 */

public class Profile implements Serializable {

    private List<Question.Category> clearedCategories;
    private String name;

    public List<Question.Category> getClearedCategories() {
        return clearedCategories;
    }

    public void addClearedCategory(Question.Category clearedCategory) {
        if(clearedCategories == null)
            clearedCategories = new ArrayList<>();
        clearedCategories.add(clearedCategory);
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    private Integer age;

    public Profile(String _name){
        this.name = _name;
    }



}
