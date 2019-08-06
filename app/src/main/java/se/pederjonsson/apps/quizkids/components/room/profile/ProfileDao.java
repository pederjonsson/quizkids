package se.pederjonsson.apps.quizkids.components.room.profile;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import se.pederjonsson.apps.quizkids.MainActivity;

@Dao
public interface ProfileDao {


    @Query("SELECT * FROM "+ MainActivity.TABLE_NAME_PROFILE)
    List<ProfileEntity> getAll();

    /*
     * Insert the object in database
     * @param note, object to be inserted
     */
    @Insert
    void insert(ProfileEntity profile);

    /*
     * Insert the array of objects into database
     * @param note, array of objects to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(List<ProfileEntity> profiles);

    /*
     * update the object in database
     * @param note, object to be updated
     */
    @Update
    void update(ProfileEntity profile);

    /*
     * delete the object from database
     * @param note, object to be deleted
     */
    @Delete
    void delete(ProfileEntity profile);

    /*
     * delete list of objects from database
     * @param note, array of objects to be deleted
     */
    @Delete
    void delete(ProfileEntity... profile);      // Note... is varargs, here note is an array

}
