package se.pederjonsson.apps.quizkids.model.question;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import se.pederjonsson.apps.quizkids.MainActivity;

@Dao
public interface QuestionDao {


    @Query("SELECT * FROM "+ MainActivity.TABLE_NAME_QUESTION)
    List<QuestionEntity> getAll();

    @Query("SELECT * FROM " + MainActivity.TABLE_NAME_QUESTION + " WHERE category = :category")
    List<QuestionEntity> getQuestionsByCategory(String category);

    /*
     * Insert the object in database
     * @param note, object to be inserted
     */
    @Insert
    void insert(QuestionEntity question);

    /*
     * Insert the array of objects into database
     * @param note, array of objects to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<QuestionEntity> question);

    /*
     * update the object in database
     * @param note, object to be updated
     */
    @Update
    void update(QuestionEntity question);

    /*
     * delete the object from database
     * @param note, object to be deleted
     */
    @Delete
    void delete(QuestionEntity question);

    /*
     * delete list of objects from database
     * @param note, array of objects to be deleted
     */
    @Delete
    void delete(QuestionEntity... question);      // Note... is varargs, here note is an array

}
