package se.pederjonsson.apps.quizkids

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*
import se.pederjonsson.apps.quizkids.data.CategoryItem
import se.pederjonsson.apps.quizkids.fragments.MenuFragment
import se.pederjonsson.apps.quizkids.fragments.category.CategoryFragment
import se.pederjonsson.apps.quizkids.highscore.HighscoreActivity
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract.MainActivityView
import se.pederjonsson.apps.quizkids.model.DataHolderForQuerys
import se.pederjonsson.apps.quizkids.model.RoomDBUtil
import se.pederjonsson.apps.quizkids.model.RoomQueryAsyncTasks.RoomQuery

class MainActivity : AppCompatActivity(), MainActivityView {
    private var gameControllerMenuPresenter: GameControllerContract.MenuPresenter? = null

    //  Database db;
    var mMediaPlayer: MediaPlayer? = null

    private var mFragmentManager: FragmentManager? = null
    private lateinit var dbUtil: RoomDBUtil
    var slide = Slide(Gravity.RIGHT)
    var slideout = Slide(Gravity.LEFT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameControllerMenuPresenter = MenuGameController(this, navbar)
        mFragmentManager = supportFragmentManager
        slide.duration = SLIDE_TIME.toLong()
        slideout.duration = SLIDE_TIME.toLong()
        showMenu()
        dbUtil = RoomDBUtil()
        //dbUtil.generateQABuildingsRoom(this, this);
        dbUtil.populateDbWithQuestions(this, this, dbUtil.generateQABuildings(this))
        dbUtil.populateDbWithQuestions(this, this, dbUtil.generateQAOceans(this))
        dbUtil.populateDbWithQuestions(this, this, dbUtil.generateQAAnimals(this))
    }



    private fun playSound(resId: Int) {
        releaseMediaPlayer()
        mMediaPlayer = MediaPlayer.create(this, resId)
        mMediaPlayer?.setLooping(false)
        mMediaPlayer?.start()
    }

    private fun releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer!!.isPlaying) {
                mMediaPlayer!!.stop()
            }
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    override fun startQuickQuiz() {
        val intent = Intent(this, QuestionActivity::class.java)
        intent.putExtra(QuestionActivity.CATEGORY_ITEM, CategoryItem(CategoryItem.Category.QUICKPLAY))
        intent.putExtra(QuestionActivity.PROFILE_ITEM, gameControllerMenuPresenter!!.playingProfile)
        startActivityForResult(intent, 1)
    }

    override fun getViewContext(): Context {
        return this
    }

    override fun showMenu() {
        val fragmentTransaction = mFragmentManager!!.beginTransaction()
        val fragment = MenuFragment.newInstance(this, gameControllerMenuPresenter)
        fragmentTransaction.replace(R.id.fragmentcontainer, fragment)
        commitTransaction(fragmentTransaction)
    }

    private fun commitTransaction(fragmentTransaction: FragmentTransaction) {
        if (!mFragmentManager!!.isStateSaved) {
            fragmentTransaction.commit()
        } else {
            fragmentTransaction.commitAllowingStateLoss()
        }
    }

    override fun showCategories() {
        val fragmentTransaction = mFragmentManager!!.beginTransaction()
        val fragment = CategoryFragment.newInstance(this, gameControllerMenuPresenter)
        setSlideInOutTransition(fragment)
        fragmentTransaction.replace(R.id.fragmentcontainer, fragment)
        fragmentTransaction.addToBackStack(null)
        commitTransaction(fragmentTransaction)
    }

    override fun showHighscoreList() {
        val intent = Intent(this, HighscoreActivity::class.java)
        //intent.putExtra(QuestionActivity.CATEGORY_ITEM, new CategoryItem(Question.Category.QUICKPLAY));
        //intent.putExtra(QuestionActivity.PROFILE_ITEM, gameControllerMenuPresenter.getPlayingProfile());
        startActivity(intent)
    }

    private fun setSlideInOutTransition(fragment: Fragment) {
        fragment.enterTransition = slide
        setSlideOutTransition(fragment)
    }

    private fun setSlideOutTransition(fragment: Fragment) {
        fragment.exitTransition = slideout
        fragment.allowEnterTransitionOverlap = false
    }

    override fun onPause() {
        super.onPause()
        releaseMediaPlayer()
        if (task != null && !task!!.isCancelled) {
            task!!.cancel(true)
            task = null
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == QuestionActivity.SHOW_CATEGORIES) {
            showCategories()
        }
    }

    override fun onProgress(vararg values: Void?) {
        Log.i("ROOM", "ONPROGRESS")
    }

    var task: RoomQuery? = null
    override fun onStartTask(task: RoomQuery) {
        Log.i("ROOM", "onStartTask")
        this.task = task
    }

    override fun onSuccess(dh: DataHolderForQuerys?) {
        if (dh != null) {
            Log.i("ROOM", "ONSUCCESS FOR REQUEST " + dh.requestType!!.requestType)
            if (dh.requestType === DataHolderForQuerys.RequestType.INSERTQUESTIONS) {
                //dbUtil.getAllQuestions(this, this);
                //dbUtil.saveProfile(this, this, new ProfileEntity("testcreateuser"));
                Log.i("ROOM", "questions inserted: " + dh.questionEntityList!![0])
            } else if (dh.requestType === DataHolderForQuerys.RequestType.GETALLQUESTIONS) {
                Log.i("ROOM", "questions fetched: " + dh.questionEntityList!!.size)
                //dbUtil.getQuestionsByCategory(this, this, Question.Category.BUILDINGS.getCategory());
            } else if (dh.requestType === DataHolderForQuerys.RequestType.SAVEPROFILE) {
                Log.i("ROOM", "profilesaved for: " + dh.profile!!.profilename)
                // dbUtil.insertCategoryPoints(this, this, new CategoryPointsEntity(Question.Category.BUILDINGS.getCategory(), "testcreateuser", 5));
                //dbUtil.getAllProfiles(this, this);
            } else if (dh.requestType === DataHolderForQuerys.RequestType.GETALLPROFILES) {
                Log.i("ROOM", "all profiles = " + dh.profileEntityList)
                gameControllerMenuPresenter!!.profiles = dh.profileEntityList
            } else if (dh.requestType === DataHolderForQuerys.RequestType.GETQUESTIONSBYCATEGORY) {
                Log.i("ROOM", "questions by category " + dh.category + " fetched: " + dh.questionEntityList!!.size)
            } else if (dh.requestType === DataHolderForQuerys.RequestType.INSERTCATEGORYPOINTS) {
                Log.i("ROOM", "inserted categorypoints " + dh.categoryPointsEntity!!.points + " for " + dh.categoryPointsEntity!!.categoryid + " name: " + dh.categoryPointsEntity!!.profileid)
                //dbUtil.getAllCategoryPointsForUser(this, this, "testcreateuser");
            } else if (dh.requestType === DataHolderForQuerys.RequestType.GETCATEGORYPOINTSFORUSER) {
                Log.i("ROOM", "categorypoints for user " + dh.categoryPointsEntityList + " for " + dh.profileid)
                dh.categoryPointsEntityList?.let {
                    for(c in it){
                        Log.i("CIV", " mainactivity catpoint " + c.points)
                    }
                }
                if (gameControllerMenuPresenter!!.playingProfile != null) {
                    gameControllerMenuPresenter!!.playingProfile.categoryPointsList = dh.categoryPointsEntityList
                }
            }
        } else {
            Log.i("ERROR", "No data returned frmo request")
        }
    }

    override fun onFail(dh: DataHolderForQuerys?) {
        if (dh != null) {
            Log.i("ROOM", "ONFAIL FOR REQUEST " + dh.requestType + " error " + dh.errorMsg)
        } else {
            Log.i("ROOM", "ONFAIL")
        }
    }

    companion object {
        const val TABLE_NAME_QUESTION = "QUESTIONS"
        const val TABLE_NAME_PROFILE = "PROFILES"
        const val TABLE_NAME_CATEGORY = "CATEGORIES"
        const val TABLE_NAME_CATEGORYPOINTS = "CATEGORYPOINTS"
        const val DB_NAME = "QUIZ_DB"
        private const val SLIDE_TIME = 300
    }
}