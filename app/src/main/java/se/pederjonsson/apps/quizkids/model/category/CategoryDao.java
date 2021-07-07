package se.pederjonsson.apps.quizkids.model.category;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import se.pederjonsson.apps.quizkids.MainActivity;

@Dao
public interface CategoryDao {


    @Query("SELECT * FROM "+ MainActivity.TABLE_NAME_CATEGORY)
    List<CategoryEntity> getAll();

    /*
     * Insert the object in database
     * @param note, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(CategoryEntity category);

    /*
     * Insert the array of objects into database
     * @param note, array of objects to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(List<CategoryEntity> categories);

    /*
     * update the object in database
     * @param note, object to be updated
     */
    @Update
    void update(CategoryEntity category);

    /*
     * delete the object from database
     * @param note, object to be deleted
     */
    @Delete
    void delete(CategoryEntity category);

    /*
     * delete list of objects from database
     * @param note, array of objects to be deleted
     */
    @Delete
    void delete(CategoryEntity... category);      // Note... is varargs, here note is an array

}
