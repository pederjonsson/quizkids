package se.pederjonsson.apps.quizkids.components.room.categorypoints;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import se.pederjonsson.apps.quizkids.MainActivity;


@Dao
public interface CategoryPointsDao {


    @Query("SELECT * FROM "+ MainActivity.TABLE_NAME_CATEGORYPOINTS)
    List<CategoryPointsEntity> getAll();

    @Query("SELECT * FROM "+ MainActivity.TABLE_NAME_CATEGORYPOINTS + " WHERE profileid = :profileid")
    List<CategoryPointsEntity> getAllByProfile(String profileid);

    /*
     * Insert the object in database
     * @param note, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CategoryPointsEntity categoryPoints);

    /*
     * Insert the array of objects into database
     * @param note, array of objects to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<CategoryPointsEntity> categoryPointss);

    /*
     * update the object in database
     * @param note, object to be updated
     */
    @Update
    void update(CategoryPointsEntity categoryPoints);

    /*
     * delete the object from database
     * @param note, object to be deleted
     */
    @Delete
    void delete(CategoryPointsEntity categoryPoints);

    /*
     * delete list of objects from database
     * @param note, array of objects to be deleted
     */
    @Delete
    void delete(CategoryPointsEntity... categoryPoints);      // Note... is varargs, here note is an array

}
