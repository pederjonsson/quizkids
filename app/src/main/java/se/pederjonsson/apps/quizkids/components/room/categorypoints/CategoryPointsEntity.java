package se.pederjonsson.apps.quizkids.components.room.categorypoints;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import se.pederjonsson.apps.quizkids.MainActivity;

@Entity(tableName = MainActivity.TABLE_NAME_CATEGORYPOINTS)
public class CategoryPointsEntity implements Serializable {

    public int getcpid() {
        return cpid;
    }

    public void setcpid(int cpid) {
        this.cpid = cpid;
    }

    @PrimaryKey(autoGenerate = true)
    private int cpid;

    @ColumnInfo(name = "categoryid")
    private int categoryid;

    @ColumnInfo(name = "profileid")
    private int profileid = 0;

    public CategoryPointsEntity(int categoryid) {
        this.categoryid = categoryid;
    }

    public int getProfileid() {
        return profileid;
    }

    public void setProfileid(int profileid) {
        this.profileid = profileid;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryPointsEntity)) return false;

        CategoryPointsEntity p = (CategoryPointsEntity) o;

        if (cpid != p.cpid) return false;
        return categoryid != -1 ? categoryid == (p.categoryid) : p.categoryid == -1;
    }

    @Override
    public int hashCode() {
        int result = cpid;
        result = 31 * result + (categoryid != -1 ? categoryid : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CategoryPointsEntity{" +
                "cpid=" + cpid +
                ", categoryid='" + categoryid + '\'' +
                ", profileid='" + profileid + '\'' +
                '}';
    }

}
