package se.pederjonsson.apps.quizkids.Objects;

import java.io.Serializable;

/**
 * Created by Gaming on 2018-04-01.
 */

public class Profile implements Serializable {

    private String name;

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
