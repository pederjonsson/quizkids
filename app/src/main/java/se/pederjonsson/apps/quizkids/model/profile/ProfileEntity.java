package se.pederjonsson.apps.quizkids.model.profile;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import android.util.Log;

import java.io.Serializable;
import java.util.List;

import se.pederjonsson.apps.quizkids.MainActivity;
import se.pederjonsson.apps.quizkids.model.categorypoints.CategoryPointsEntity;

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

    @Ignore
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
        Log.i("ROOM", "now im settings categorypoints on profileentity " + categoryPointsList);
    }

    public int getPointsForCategory(String category) {
        if (categoryPointsList != null) {
            for (CategoryPointsEntity c : categoryPointsList) {
                if (c.getCategoryid().equals(category)) {
                    return c.getPoints();
                } else {
                    Log.i("CIV", c.getCategoryid() + " was not same as " + category);
                }
            }
        } else {
            Log.i("CIV", "categorypointslist null in profileentity");
        }
        return 0;
    }

}
