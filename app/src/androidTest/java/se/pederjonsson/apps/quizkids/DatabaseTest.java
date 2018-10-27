package se.pederjonsson.apps.quizkids;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.logging.Logger;

import se.pederjonsson.apps.quizkids.Objects.Profile;
import se.pederjonsson.apps.quizkids.Objects.QuestionAnswers;
import se.pederjonsson.apps.quizkids.db.Database;
import se.pederjonsson.apps.quizkids.db.DatabaseUtil;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("se.pederjonsson.apps.quizkids", appContext.getPackageName());
    }

    private Database database;
    Logger l = Logger.getGlobal();

    @Before
    public void setUp() {
        database = new Database(InstrumentationRegistry.getTargetContext());
    }

    @After
    public void finish() {
        database.close();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(database);
    }

    @Test
    public void testProfileModInDB() throws Exception {

        Profile p = new Profile("mats");
        p.setAge(3);
        database.insertProfile("mats", p);
        List<Profile> firstResult = database.getAllProfiles();

        assertNotNull (firstResult);

        Profile firstProfile = firstResult.get(0);
        assertTrue(firstProfile.getName().equals("mats"));
        assertTrue((firstProfile.getAge() == 3));

        firstProfile.setAge(4);

        //this is to test that update works
        database.insertProfile(firstProfile.getName(), firstProfile);
        Object secondResult = database.getProfileByName(firstProfile.getName());
        assertNotNull (secondResult);

        assertTrue(((Profile)secondResult).getAge() == 4);
    }

    @Test
    public void testQA() throws Exception {

        DatabaseUtil databaseUtil = new DatabaseUtil();
        List<QuestionAnswers> qaList = databaseUtil.generateQAGeography();
        QuestionAnswers firstQA = qaList.get(0);

        database.insertQa(firstQA.getQuestion().getCategoryString(), firstQA.getQuestion().getDifficultyLevel(), firstQA.getQuestion().getQuestionResId(), firstQA);


        List<QuestionAnswers> qaListFromDB = database.getAllQA();
        assertNotNull (qaListFromDB);
        QuestionAnswers qaFromDB = qaListFromDB.get(0);
        assertNotNull (qaFromDB);

        qaFromDB.setHasBeenShownTo("mats");
        assertTrue(qaFromDB.hasBeenShownTo("mats"));

        database.insertQa(qaFromDB.getQuestion().getCategoryString(), qaFromDB.getQuestion().getDifficultyLevel(), qaFromDB.getQuestion().getQuestionResId(), qaFromDB);
        List<QuestionAnswers> qaListFromDB2 = database.getAllQA();
                //database.getQAListByCategoryAndDifficultyLevel(qaFromDB.getQuestion().getCategoryString(), qaFromDB.getQuestion().getDifficultyLevel());

        QuestionAnswers qaFromDB2 = qaListFromDB2.get(0);
        l.info("**** " + qaFromDB2.getQuestion().toString());
        assertTrue(qaFromDB2.hasBeenShownTo("mats"));
    }

    @Test
    public void testQAWithSpecifiedRequest() throws Exception {

        DatabaseUtil databaseUtil = new DatabaseUtil();
        List<QuestionAnswers> qaList = databaseUtil.generateQAGeography();
        QuestionAnswers firstQA = qaList.get(0);



        database.insertQa(firstQA.getQuestion().getCategoryString(), firstQA.getQuestion().getDifficultyLevel(), firstQA.getQuestion().getQuestionResId(), firstQA);


        l.info("**** firstQA.getQuestion().getCategoryString() " + firstQA.getQuestion().getCategoryString());

        List<QuestionAnswers> qaListFromDB = database.getQAListByCategoryAndDifficultyLevel(firstQA.getQuestion().getCategoryString(), firstQA.getQuestion().getDifficultyLevel());
        assertNotNull (qaListFromDB);
        QuestionAnswers qaFromDB = qaListFromDB.get(0);
        assertNotNull (qaFromDB);

        qaFromDB.setHasBeenShownTo("mats");
        assertTrue(qaFromDB.hasBeenShownTo("mats"));

        database.insertQa(qaFromDB.getQuestion().getCategoryString(), qaFromDB.getQuestion().getDifficultyLevel(), qaFromDB.getQuestion().getQuestionResId(), qaFromDB);
        List<QuestionAnswers> qaListFromDB2 = database.getQAListByCategoryAndDifficultyLevel(qaFromDB.getQuestion().getCategoryString(), qaFromDB.getQuestion().getDifficultyLevel());

        QuestionAnswers qaFromDB2 = qaListFromDB2.get(0);
        assertTrue(qaFromDB2.hasBeenShownTo("mats"));
    }

    @Test
    public void testCreateTableTwice(){
        Exception exception = null;
        try {
            database.createProfilesTable(database.getWritableDatabase());
        } catch (Exception ex){
            exception = ex;
        }

        Assert.assertNull("Threw exception when trying to create settings table twice: ", exception);
    }
}
