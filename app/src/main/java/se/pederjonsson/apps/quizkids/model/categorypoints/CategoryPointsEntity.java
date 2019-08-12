package se.pederjonsson.apps.quizkids.model.categorypoints;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import se.pederjonsson.apps.quizkids.MainActivity;

@Entity(tableName = MainActivity.TABLE_NAME_CATEGORYPOINTS)
public class CategoryPointsEntity implements Serializable {

    public int getCpid() {
        return cpid;
    }

    public void setCpid(int cpid) {
        this.cpid = cpid;
    }

    @PrimaryKey(autoGenerate = true)
    private int cpid;

    @ColumnInfo(name = "categoryid")
    private String categoryid;

    @ColumnInfo(name = "profileid")
    private String profileid;

    @ColumnInfo(name = "points")
    private int points;

    public CategoryPointsEntity(String categoryid, String profileid, int points) {
        this.categoryid = categoryid;
        this.profileid = profileid;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getProfileid() {
        return profileid;
    }

    public void setProfileid(String profileid) {
        this.profileid = profileid;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryPointsEntity)) return false;

        CategoryPointsEntity p = (CategoryPointsEntity) o;

        if (cpid != p.cpid) return false;
        return categoryid != null ? categoryid.equals(p.categoryid) : p.categoryid == null;
    }

    @Override
    public int hashCode() {
        int result = cpid;
        result = 31 * result + (categoryid != null ? categoryid.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CategoryPointsEntity{" +
                "cpid=" + cpid +
                ", categoryid='" + categoryid + '\'' +
                ", profileid='" + profileid + '\'' +
                ", points='" + points + '\'' +
                '}';
    }

}
