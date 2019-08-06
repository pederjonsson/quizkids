package se.pederjonsson.apps.quizkids.components.room.category;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import se.pederjonsson.apps.quizkids.MainActivity;

@Entity(tableName = MainActivity.TABLE_NAME_CATEGORY)
public class CategoryEntity implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private int cid;

    @ColumnInfo(name = "name")
    private String name;

    public CategoryEntity(String name) {
        this.name = name;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryEntity)) return false;

        CategoryEntity c = (CategoryEntity) o;

        if (cid != c.cid) return false;
        return name != null ? name.equals(c.name) : c.name == null;
    }

    @Override
    public int hashCode() {
        int result = cid;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "cid=" + cid +
                ", name='" + name + '\'' + '}';
    }

}
