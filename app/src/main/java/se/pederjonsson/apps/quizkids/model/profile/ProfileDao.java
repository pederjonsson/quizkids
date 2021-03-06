package se.pederjonsson.apps.quizkids.model.profile;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import se.pederjonsson.apps.quizkids.MainActivity;

@Dao
public interface ProfileDao {


    @Query("SELECT * FROM "+ MainActivity.TABLE_NAME_PROFILE)
    List<ProfileEntity> getAll();

    @Query("SELECT * FROM "+ MainActivity.TABLE_NAME_PROFILE + " WHERE profilename = :profileid")
    ProfileEntity getProfileByName(String profileid);

    /*
     * Insert the object in database
     * @param note, object to be inserted
     */

    @Insert(onConflict = OnConflictStrategy.ABORT)
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
