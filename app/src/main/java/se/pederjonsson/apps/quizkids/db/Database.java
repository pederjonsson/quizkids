package se.pederjonsson.apps.quizkids.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import se.pederjonsson.apps.quizkids.Objects.Answer;
import se.pederjonsson.apps.quizkids.Objects.Profile;
import se.pederjonsson.apps.quizkids.Objects.Question;
import se.pederjonsson.apps.quizkids.Objects.QuestionAnswers;
import se.pederjonsson.apps.quizkids.R;

/**
 * Created by Gaming on 2018-04-01.
 */

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "quizkids.db";
    private static final int DATABASE_VERSION = 1;


    public static final String PROFILE_TABLE_NAME = "profiles";
    public static final String PROFILE_COLUMN_ID = "_id";
    public static final String PROFILE_COLUMN_VALUE = "value";

    public static final String QA_TABLE_NAME = "questionsanswers";
    public static final String QA_COLUMN_ID = "_id";
    public static final String QA_COLUMN_TYPE = "category";
    public static final String QA_COLUMN_DIFFICULTY = "difficulty";
    public static final String QA_COLUMN_VALUE = "value";

    Logger l = Logger.getGlobal();

    DatabaseUtil dbUtil;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    {
        dbUtil = new DatabaseUtil();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createProfilesTable(db);
        createQuestionAnswerTable(db);
    }

    public void createProfilesTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + PROFILE_TABLE_NAME + "(" +
                PROFILE_COLUMN_ID + " TEXT, " +
                PROFILE_COLUMN_VALUE + " BLOB," +
                "UNIQUE (" + PROFILE_COLUMN_ID + ") ON CONFLICT REPLACE" +
                ")"
        );
    }

    public void createQuestionAnswerTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + QA_TABLE_NAME + "(" +
                QA_COLUMN_ID + " INTEGER, " +
                QA_COLUMN_TYPE + " TEXT, " +
                QA_COLUMN_DIFFICULTY + " TEXT, " +
                QA_COLUMN_VALUE + " BLOB," +
                "UNIQUE (" + QA_COLUMN_ID + ") ON CONFLICT REPLACE" +
                ")"
        );
    }

    public synchronized boolean insertQa(String category, String difficulty, int qaId, Serializable value) {

        if (value == null) {
            return false;
        }

        SQLiteDatabase db = getWritableDatabase();

        byte[] bytes = objectTobytes(value);
        if (bytes == null) {
            return false;
        }

        ContentValues contentValues = getQAContentValues(category, difficulty, qaId, bytes);
        db.insert(QA_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    private ContentValues getQAContentValues(String type, String difficulty, int qaId, byte[] value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(QA_COLUMN_TYPE, type);
        contentValues.put(QA_COLUMN_ID, qaId);
        contentValues.put(QA_COLUMN_DIFFICULTY, difficulty);
        contentValues.put(QA_COLUMN_VALUE, value);
        return contentValues;
    }

    public synchronized List<QuestionAnswers> getQAListByCategoryAndDifficultyLevel(String category, String difficultyLevel) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT " + QA_COLUMN_VALUE + " FROM " + QA_TABLE_NAME + " WHERE " +
                        QA_COLUMN_TYPE + "=? "
                /*"AND " + QA_COLUMN_DIFFICULTY + "=?"*/
                , new String[]{category});

        final List<QuestionAnswers> list = new ArrayList<QuestionAnswers>();
        if (res.getCount() > 0) {
            try {
                while (res.moveToNext()) {
                    list.add((QuestionAnswers) byteToObj(res.getBlob(0)));
                }
            } finally {
                res.close();
            }
            db.close();
            return list;
        }

        if (res != null) {
            res.close();
        }
        db.close();
        return null;
    }

    public synchronized List<QuestionAnswers> getAllQA() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT ALL " + QA_COLUMN_VALUE + " FROM " + QA_TABLE_NAME, null);

        final List<QuestionAnswers> list = new ArrayList<QuestionAnswers>();
        if (res.getCount() > 0) {
            try {
                while (res.moveToNext()) {
                    list.add((QuestionAnswers) byteToObj(res.getBlob(0)));
                }
            } finally {
                res.close();
            }

            db.close();
            return list;
        }
        if (res != null) {
            res.close();
        }
        db.close();
        return null;
    }

    public synchronized boolean insertProfile(String firstName, Serializable value) {

        if (value == null) {
            return false;
        }

        SQLiteDatabase db = getWritableDatabase();

        byte[] bytes = objectTobytes(value);
        if (bytes == null) {
            return false;
        }

        ContentValues contentValues = getProfileContentValues(firstName, bytes);
        db.insert(PROFILE_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    private ContentValues getProfileContentValues(String firstName, byte[] value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_COLUMN_ID, firstName);
        contentValues.put(PROFILE_COLUMN_VALUE, value);
        return contentValues;
    }

    public synchronized Object getProfileByName(String firstName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT " + PROFILE_COLUMN_VALUE + " FROM " + PROFILE_TABLE_NAME + " WHERE " +
                PROFILE_COLUMN_ID + "=?", new String[]{firstName});

        if (res.getCount() > 0) {
            res.moveToFirst();
            Object obj = byteToObj(res.getBlob(0));
            db.close();
            Log.i("DB", "getprofilebyname found match that will be returned");
            return obj;
        }
        if (res != null) {
            res.close();
        }
        db.close();
        return null;
    }

    public synchronized List<Profile> getAllProfiles() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT ALL " + PROFILE_COLUMN_VALUE + " FROM " + PROFILE_TABLE_NAME, null);

        final List<Profile> list = new ArrayList<Profile>();
        if (res.getCount() > 0) {
            try {
                while (res.moveToNext()) {
                    list.add((Profile) byteToObj(res.getBlob(0)));
                }
            } finally {
                res.close();
            }
            db.close();
            return list;
        }
        if (res != null) {
            res.close();
        }
        db.close();
        return null;
    }


    public static byte[] objectTobytes(Object o) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(o);
            out.close();
            // Get the bytes of the serialized object
            byte[] buf = bos.toByteArray();
            return buf;
        } catch (IOException ioe) {
            // Log.e("serializeObject", "error", ioe);
            return null;
        }
    }

    public static Object byteToObj(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            Object o = in.readObject();
            return o;

        } catch (Exception e) {
            //TODO log error
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return null;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // good read when needed: https://thebhwgroup.com/blog/how-android-sqlite-onupgrade
    }

/*
    public QuestionAnswers getSampleQuestionAnswers() {

        Question q = new Question(R.string.q_geo_paris, Question.Category.GEOGRAPHY, R.drawable.question_eiffel200, Question.DifficultyLevel.EASY);
        Answer a = new Answer("Eiffel", true);
        Answer b = new Answer("Big Ben", false);
        Answer c = new Answer("Falafel", false);
        List<Answer> answers = new ArrayList<Answer>();
        answers.add(a);
        answers.add(b);
        answers.add(c);

        return new QuestionAnswers(q, answers);
    }*/

    public List<QuestionAnswers> getQuestionsByCategory(Question.Category category) {
        l.info("**** getQuestionsByCategory-" + category.name().toLowerCase() + "----");
        return getQAListByCategoryAndDifficultyLevel(category.name().toLowerCase(), "");
    }

    public void populate(Context context) {
        dbUtil.populateDB(this, context);
    }

    static Database instance;
    public static synchronized Database getInstance(Context mContext) {
        if (instance == null) {
            instance = new Database(mContext);
        }
        return instance;
    }

}
