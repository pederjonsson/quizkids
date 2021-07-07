package se.pederjonsson.apps.quizkids.model.categorypoints;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import se.pederjonsson.apps.quizkids.MainActivity;


@Dao
public interface CategoryPointsDao {


    @Query("SELECT * FROM "+ MainActivity.TABLE_NAME_CATEGORYPOINTS)
    List<CategoryPointsEntity> getAll();

    @Query("SELECT * FROM "+ MainActivity.TABLE_NAME_CATEGORYPOINTS + " WHERE profileid = :profileid")
    List<CategoryPointsEntity> getAllByProfile(String profileid);

    @Query("DELETE FROM " + MainActivity.TABLE_NAME_CATEGORYPOINTS + " WHERE profileid = :profileid AND categoryid = :categoryid")
    void deleteByProfileAndCategory(String profileid, String categoryid);

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
    @Update(onConflict = OnConflictStrategy.REPLACE)
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
