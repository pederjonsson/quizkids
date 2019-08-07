package se.pederjonsson.apps.quizkids.components.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import se.pederjonsson.apps.quizkids.MainActivity;
import se.pederjonsson.apps.quizkids.components.room.categorypoints.CategoryPointsDao;
import se.pederjonsson.apps.quizkids.components.room.categorypoints.CategoryPointsEntity;
import se.pederjonsson.apps.quizkids.components.room.profile.ProfileDao;
import se.pederjonsson.apps.quizkids.components.room.profile.ProfileEntity;
import se.pederjonsson.apps.quizkids.components.room.question.QuestionDao;
import se.pederjonsson.apps.quizkids.components.room.question.QuestionEntity;

@Database(entities = { QuestionEntity.class, ProfileEntity.class, CategoryPointsEntity.class}, version = 1)
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
                MainActivity.DB_NAME).build();
    }

    public void cleanUp(){
        quizDB = null;
    }

}
