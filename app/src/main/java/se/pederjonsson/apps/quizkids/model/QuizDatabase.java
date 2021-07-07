package se.pederjonsson.apps.quizkids.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import se.pederjonsson.apps.quizkids.MainActivity;
import se.pederjonsson.apps.quizkids.model.categorypoints.CategoryPointsDao;
import se.pederjonsson.apps.quizkids.model.categorypoints.CategoryPointsEntity;
import se.pederjonsson.apps.quizkids.model.profile.ProfileDao;
import se.pederjonsson.apps.quizkids.model.profile.ProfileEntity;
import se.pederjonsson.apps.quizkids.model.question.QuestionDao;
import se.pederjonsson.apps.quizkids.model.question.QuestionEntity;

@Database(entities = { QuestionEntity.class, ProfileEntity.class, CategoryPointsEntity.class}, version = 2)
public abstract class QuizDatabase extends RoomDatabase {

    public abstract QuestionDao getQuestionDao();
    public abstract ProfileDao getProfileDao();
    public abstract CategoryPointsDao getCategoryPointsDao();

    private static QuizDatabase quizDB;

    public static QuizDatabase getInstance(Context context) {
        if (null == quizDB) {
            quizDB = buildDatabaseInstance(context);
        }
        return quizDB;
    }

    private static QuizDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                QuizDatabase.class,
                MainActivity.DB_NAME).fallbackToDestructiveMigration().build();
    }

    public void cleanUp(){
        quizDB = null;
    }

}
