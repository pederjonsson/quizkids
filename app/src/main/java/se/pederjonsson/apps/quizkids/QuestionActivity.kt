package se.pederjonsson.apps.quizkids

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_questions.*
import se.pederjonsson.apps.quizkids.data.CategoryItem
import se.pederjonsson.apps.quizkids.fragments.Question.QuestionFragment.Companion.newInstance
import se.pederjonsson.apps.quizkids.fragments.Question.QuestionGameController
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract.QuestionActivityView
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract.QuestionPresenter
import se.pederjonsson.apps.quizkids.model.DataHolderForQuerys
import se.pederjonsson.apps.quizkids.model.RoomDBUtil
import se.pederjonsson.apps.quizkids.model.RoomQueryAsyncTasks.RoomQuery
import se.pederjonsson.apps.quizkids.model.profile.ProfileEntity
import se.pederjonsson.apps.quizkids.model.question.QuestionEntity
import java.util.*

class QuestionActivity : AppCompatActivity(), QuestionActivityView {
    private var gameControllerPresenter: QuestionPresenter? = null
    var mMediaPlayer: MediaPlayer? = null

    private var mFragmentManager: FragmentManager? = null
    var categoryItem: CategoryItem? = null
    var slide = Slide(Gravity.RIGHT)
    var slideout = Slide(Gravity.LEFT)
    var roomDBUtil: RoomDBUtil? = null
    var textToSpeech: TextToSpeech? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        resultview.show(false);
        gameControllerPresenter = QuestionGameController(this, navbar);
        mFragmentManager = supportFragmentManager
        slide.duration = SLIDE_TIME.toLong()
        slideout.duration = SLIDE_TIME.toLong()
        val extras = intent.extras
        if (extras != null) {
            categoryItem = extras[CATEGORY_ITEM] as CategoryItem?
            val playingProfile = extras[PROFILE_ITEM] as ProfileEntity?
            gameControllerPresenter!!.playingProfile = playingProfile
            roomDBUtil = RoomDBUtil()
            roomDBUtil!!.getQuestionsByCategory(this, this, categoryItem!!.category.category)
        } else {
            finish()
        }
    }

    override fun showResultView(categoryItem: CategoryItem, profileEntity: ProfileEntity, amountCorrect: Int, allCorrect: Boolean) {
        if (allCorrect) {
            playSound(R.raw.drumroll)
            resultview.setUp(categoryItem, profileEntity, this)
        } else {
            resultview.setUpNotAllCorrect(categoryItem, profileEntity, this, amountCorrect)
        }
        resultview.show(true)
    }

    private fun playSound(resId: Int) {
        releaseMediaPlayer()
        mMediaPlayer = MediaPlayer.create(this, resId)
        mMediaPlayer?.isLooping = false
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

    override fun startQuickQuiz() {}
    override fun getViewContext(): Context {
        return this
    }

    override fun showMenu() {
        //not used here
    }

    private fun commitTransaction(fragmentTransaction: FragmentTransaction) {
        if (!mFragmentManager!!.isStateSaved) {
            fragmentTransaction.commit()
        } else {
            fragmentTransaction.commitAllowingStateLoss()
        }
    }

    override fun showCategories() {
        //setResult(SHOW_CATEGORIES); no need because thats the view we came from
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        showCategories()
    }

    override fun showQuestionFragment(questionEntity: QuestionEntity, addToBackstack: Boolean) {
        val fragmentTransaction = mFragmentManager!!.beginTransaction()
        val fragment = newInstance(questionEntity, gameControllerPresenter)
        setSlideInOutTransition(fragment)
        fragmentTransaction.replace(R.id.fragmentcontainer, fragment)
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(null)
        }
        commitTransaction(fragmentTransaction)
    }

    private fun setSlideInOutTransition(fragment: Fragment) {
        fragment.enterTransition = slide
        setSlideOutTransition(fragment)
    }

    private fun setSlideOutTransition(fragment: Fragment) {
        fragment.exitTransition = slideout
        fragment.allowEnterTransitionOverlap = false
    }

    override fun showHighscoreList() {}
    override fun onStartTask(task: RoomQuery) {}
    override fun onProgress(vararg values: Void?) {}
    override fun onSuccess(dh: DataHolderForQuerys?) {
        if (dh!!.requestType === DataHolderForQuerys.RequestType.GETQUESTIONSBYCATEGORY) {
            Log.i("ROOM", "questions by category " + dh!!.category + " fetched: " + dh.questionEntityList!!.size)
            gameControllerPresenter!!.questionsLoadedByCategory(categoryItem, dh.questionEntityList)
        } else if (dh!!.requestType === DataHolderForQuerys.RequestType.INSERTCATEGORYPOINTS) {
            Log.i("ROOM", "inserted categorypoints " + dh!!.categoryPointsEntity!!.points + " for " + dh.categoryPointsEntity!!.categoryid + " name: " + dh.categoryPointsEntity!!.profileid)
            //dbUtil.getAllCategoryPointsForUser(this, this, "testcreateuser");
            // gameControllerPresenter.getPlayingProfile().
            roomDBUtil!!.getAllCategoryPointsForUser(this, this, gameControllerPresenter!!.playingProfile.profilename)
        } else if (dh!!.requestType === DataHolderForQuerys.RequestType.GETCATEGORYPOINTSFORUSER) {
            Log.i("ROOM", "categorypoints for user " + dh!!.categoryPointsEntityList + " for " + dh.profileid)
            dh.categoryPointsEntityList?.let {
                for(c in it){
                    Log.i("CIV", "questionactivity catpoint " + c.points)
                }
            }
            if (gameControllerPresenter!!.playingProfile != null) {
                gameControllerPresenter!!.playingProfile.categoryPointsList = dh.categoryPointsEntityList
            }
        }
    }

    override fun onFail(dh: DataHolderForQuerys?) {
        if (dh != null) {
            Log.i("ROOM", "ONFAIL FOR REQUEST " + dh.requestType + " error " + dh.errorMsg)
        } else {
            Log.i("ROOM", "ONFAIL")
        }
    }
    override fun speekText(speechString: String) {

        if (textToSpeech != null) {
            val speechStatus = textToSpeech!!.speak(speechString, TextToSpeech.QUEUE_FLUSH, null, null)
            if (speechStatus == TextToSpeech.ERROR) {
                Log.i("TTS", "Error in converting Text to Speech!")
            } else {
                Log.i("TTS", "should speak now")
            }
        }
    }

    private fun setupTextToSpeech() {
        textToSpeech = TextToSpeech(this, { i ->
            if (i == TextToSpeech.SUCCESS) {
                val result = textToSpeech!!.setLanguage(Locale.getDefault())
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.i("TTS", "The Language is not supported!")
                } else {
                    Log.i("TTS", "Language Supported")
                }
                Log.i("TTS", "Initialization success.")
            } else {
                Log.i("TTS", "Initialization FAIL.")
            }
        }, "com.google.android.tts")
    }

    override fun onPause() {
        super.onPause()
        if (textToSpeech != null) {
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
            textToSpeech = null
        }
    }

    override fun onResume() {
        super.onResume()
        if (textToSpeech == null) {
            setupTextToSpeech()
        }
    }

    companion object {
        private const val SLIDE_TIME = 300
        var SHOW_CATEGORIES = 101
        var CATEGORY_ITEM = "CATEGORY_ITEM"
        var PROFILE_ITEM = "PROFILE_ITEM"
    }
}