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

import se.pederjonsson.apps.quizkids.Objects.Profile;
import se.pederjonsson.apps.quizkids.db.Database;

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

    private Database dbHelper;

    @Before
    public void setUp() {
        dbHelper = new Database(InstrumentationRegistry.getTargetContext());
    }

    @After
    public void finish() {
        dbHelper.close();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(dbHelper);
    }

    @Test
    public void testShouldAddExtensionKey() throws Exception {

        Profile p = new Profile("mats");
        p.setAge(3);
        dbHelper.insertProfile("mats", p);
        List<Profile> firstResult = dbHelper.getAllProfiles();

        assertNotNull (firstResult);

        Profile firstProfile = firstResult.get(0);
        assertTrue(firstProfile.getName().equals("mats"));
        assertTrue((firstProfile.getAge() == 3));

        firstProfile.setAge(4);

        //this is to test that update works
        dbHelper.insertProfile(firstProfile.getName(), firstProfile);
        Object secondResult = dbHelper.getProfileByName(firstProfile.getName());
        assertNotNull (secondResult);

        assertTrue(((Profile)secondResult).getAge() == 4);
    }

    @Test
    public void testCreateTableTwice(){
        Exception exception = null;
        try {
            dbHelper.createProfilesTable(dbHelper.getWritableDatabase());
        } catch (Exception ex){
            exception = ex;
        }

        Assert.assertNull("Threw exception when trying to create settings table twice: ", exception);
    }
}
