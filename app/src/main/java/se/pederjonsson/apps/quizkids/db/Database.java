package se.pederjonsson.apps.quizkids.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

import se.pederjonsson.apps.quizkids.Objects.Answer;
import se.pederjonsson.apps.quizkids.Objects.Question;
import se.pederjonsson.apps.quizkids.Objects.QuestionAnswers;
import se.pederjonsson.apps.quizkids.R;
import se.pederjonsson.apps.quizkids.fragments.QuestionAnswerContract;

/**
 * Created by Gaming on 2018-04-01.
 */

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "quizkids.db";
    private static final int DATABASE_VERSION = 1;


    public static final String PROFILE_TABLE_NAME = "profiles";
    public static final String PROFILE_COLUMN_ID = "_id";
    public static final String PROFILE_COLUMN_VALUE = "value";

    public static final String QA_TABLE_NAME = "profiles";
    public static final String QA_COLUMN_ID = "_id";
    public static final String QA_COLUMN_TYPE = "category";
    public static final String QA_COLUMN_VALUE = "value";


    DatabaseUtil dbUtil;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }{
        dbUtil = new DatabaseUtil();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        createProfilesTable(db);
        createQuestionAnswerTable(db);
    }

    public void createProfilesTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS " + PROFILE_TABLE_NAME + "(" +
                PROFILE_COLUMN_ID + " TEXT, " +
                PROFILE_COLUMN_VALUE + " BLOB," +
                "UNIQUE (" + PROFILE_COLUMN_ID + ") ON CONFLICT REPLACE" +
                ")"
        );
    }

    public void createQuestionAnswerTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS " + QA_TABLE_NAME + "(" +
                QA_COLUMN_ID + " TEXT, " +
                QA_COLUMN_TYPE + " TEXT, " +
                QA_COLUMN_VALUE + " BLOB," +
                "UNIQUE (" + QA_COLUMN_ID + "," + QA_COLUMN_TYPE + ") ON CONFLICT REPLACE" +
                ")"
        );
    }

    public synchronized boolean insertQa(String key, String category, Serializable value) {

        if(value == null){
            //LOG.error(tag + " Serializable value in DBHelper insertSetting wass null");
            return false;
        }

        //LOG.debug(tag + " db insert " + type);
        SQLiteDatabase db = getWritableDatabase();

        byte[] bytes = objectTobytes(value);
        if(bytes == null){
            return false;
        }

        ContentValues contentValues = getQAContentValues(key, category, bytes);
        db.insert(QA_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    private ContentValues getQAContentValues(String key, String type, byte[] value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(QA_COLUMN_ID, key);
        contentValues.put(QA_COLUMN_TYPE, type);
        contentValues.put(QA_COLUMN_VALUE, value);
        return contentValues;
    }

    public synchronized Object getQA(String type, ExtensionEntityKey key) {
        if(key == null){
            return null;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT " + SETTINGS_COLUMN_VALUE + " FROM " + SETTINGS_TABLE_NAME + " WHERE " +
                SETTINGS_COLUMN_TYPE + "=? AND " + SETTINGS_COLUMN_ID + "=?", new String[]{type, key.getKey()});


        if (res.getCount() > 0) {
            res.moveToFirst();

            Object obj = byteToObj(res.getBlob(0));
            db.close();
            return obj;
        }
        if(res != null){
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


    public QuestionAnswers getSampleQuestionAnswers(){

        Question q = new Question(R.string.q_geo_paris, Question.Category.GEOGRAPHY, R.drawable.eiffel200);
        Answer a = new Answer("Eiffel", true);
        Answer b = new Answer("Big Ben", false);
        Answer c = new Answer("Falafel", false);
        List<Answer> answers = new ArrayList<Answer>();
        answers.add(a);
        answers.add(b);
        answers.add(c);

        return new QuestionAnswers(q, answers);
    }

    public List<QuestionAnswers> getQuestionsByCategory(Question.Category category){
        switch (category){
            case GEOGRAPHY:
                return dbUtil.generateQAGeography();
            default:
                return dbUtil.generateQAGeography();
        }
    }

}
