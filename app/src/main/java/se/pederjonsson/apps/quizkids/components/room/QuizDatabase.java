package se.pederjonsson.apps.quizkids.components.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import se.pederjonsson.apps.quizkids.MainActivity;

@Database(entities = { QuestionEntity.class }, version = 1)
public abstract class QuizDatabase extends RoomDatabase {

    public abstract QuestionDao getQuestionDao();

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
                MainActivity.DB_NAME)
                .allowMainThreadQueries().build();
    }

    public void cleanUp(){
        quizDB = null;
    }

}
