package se.pederjonsson.apps.quizkids.components.room.profile;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import se.pederjonsson.apps.quizkids.MainActivity;

@Entity(tableName = MainActivity.TABLE_NAME_PROFILE)
public class ProfileEntity implements Serializable {

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @PrimaryKey(autoGenerate = true)
    private int pid;

    @ColumnInfo(name = "profilename")
    private String profilename;

    @ColumnInfo(name = "pointstotal")
    private int pointstotal = 0;

    public ProfileEntity(String profilename) {
        this.profilename = profilename;
    }

    public int getPointstotal() {
        return pointstotal;
    }

    public void setPointstotal(int pointstotal) {
        this.pointstotal = pointstotal;
    }

    public String getProfilename() {
        return profilename;
    }

    public void setProfilename(String profilename) {
        this.profilename = profilename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfileEntity)) return false;

        ProfileEntity p = (ProfileEntity) o;

        if (pid != p.pid) return false;
        return profilename != null ? profilename.equals(p.profilename) : p.profilename == null;
    }

    @Override
    public int hashCode() {
        int result = pid;
        result = 31 * result + (profilename != null ? profilename.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProfileEntity{" +
                "pid=" + pid +
                ", profilename='" + profilename + '\'' +
                ", pointstotal='" + pointstotal + '\'' +
                '}';
    }

}
