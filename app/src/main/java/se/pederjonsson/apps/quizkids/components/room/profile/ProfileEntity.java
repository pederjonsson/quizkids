package se.pederjonsson.apps.quizkids.components.room.profile;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

import se.pederjonsson.apps.quizkids.MainActivity;
import se.pederjonsson.apps.quizkids.components.room.categorypoints.CategoryPointsEntity;

@Entity(tableName = MainActivity.TABLE_NAME_PROFILE)
public class ProfileEntity implements Serializable {

    @PrimaryKey
    @NonNull
    private String profilename;

    public ProfileEntity(String profilename) {
        this.profilename = profilename;
    }

    public String getProfilename() {
        return profilename;
    }

    List<CategoryPointsEntity> categoryPointsList = null;

    public void setProfilename(String profilename) {
       // this.profilename = profilename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfileEntity)) return false;

        ProfileEntity p = (ProfileEntity) o;

        if (profilename != p.profilename) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 31 + (profilename != null ? profilename.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProfileEntity{" +
                ", profilename='" + profilename + '\'' +
                '}';
    }

    public List<CategoryPointsEntity> getCategoryPointsList() {
        return categoryPointsList;
    }

    public void setCategoryPointsList(List<CategoryPointsEntity> categoryPointsList) {
        this.categoryPointsList = categoryPointsList;
    }

}
