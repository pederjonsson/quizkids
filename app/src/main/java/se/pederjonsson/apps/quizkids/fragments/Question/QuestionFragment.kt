package se.pederjonsson.apps.quizkids.fragments.Question

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.question_fragment.*
import se.pederjonsson.apps.quizkids.R
import se.pederjonsson.apps.quizkids.data.Answer
import se.pederjonsson.apps.quizkids.fragments.Question.QuestionAnswerContract.MainView
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract.QuestionActivityView
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract.QuestionPresenter
import se.pederjonsson.apps.quizkids.model.question.QuestionEntity

/**
 * Created by Gaming on 2018-04-01.
 */
class QuestionFragment : Fragment(), MainView {
    var questionEntity:QuestionEntity? = null
    var mMediaPlayer: MediaPlayer? = null
    var gameControllerPresenter: QuestionPresenter? = null
    var stopLoadingAnimationHandler: Handler? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.question_fragment, container, false)
        questionEntity = arguments!!.getSerializable(QUESTION_DATA) as QuestionEntity?


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionview.setUp(questionEntity, this);
        triplebtnanswers.setUp(questionEntity!!, this)
    }

    override fun onPause() {
        super.onPause()
        releaseMediaPlayer()
        if (stopLoadingAnimationHandler != null && stopLoadingAnimationRunnable != null) {
            stopLoadingAnimationHandler!!.removeCallbacks(stopLoadingAnimationRunnable)
        }
    }

    override fun getViewContext(): Context {
        return this.context!!
    }

    override fun publishChosenAnswer(answer: Answer) {
        if (!triplebtnanswers.mAnswerBtns[0].chosenAnswer) {
            triplebtnanswers.mAnswerBtns[0].hide()
        }
        if (!triplebtnanswers.mAnswerBtns[1].chosenAnswer) {
            triplebtnanswers.mAnswerBtns[1].hide()
        }
        if (!triplebtnanswers.mAnswerBtns[2].chosenAnswer) {
            triplebtnanswers.mAnswerBtns[2].hide()
        }
        if (answer.isCorrect) {
            print("** correct answer")
            gameControllerPresenter!!.answered(true)
            playSound(R.raw.correct)
        } else {
            print("** wrong answer")
            gameControllerPresenter!!.answered(false)
            playSound(R.raw.error)
        }
        triplebtnanswers.inactivateButtons()
        loadNewQuestion()
    }

    var stopLoadingAnimationRunnable: Runnable? = Runnable {
        if (activity != null) {
            activity!!.runOnUiThread { gameControllerPresenter!!.nextQuestion() }
        }
    }

    fun loadNewQuestion() {
        stopLoadingAnimationHandler = Handler()
        stopLoadingAnimationHandler!!.postDelayed(stopLoadingAnimationRunnable, 1000)
    }

    private fun playSound(resId: Int) {
        releaseMediaPlayer()
        mMediaPlayer = MediaPlayer.create(context, resId)
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

    override fun getQActivityView(): QuestionActivityView {
        return activity as QuestionActivityView
    }

    companion object {
        var QUESTION_DATA = "QUESTION_DATA"
        @JvmStatic
        fun newInstance(questionEntity: QuestionEntity?, _gameControllerP: QuestionPresenter?): QuestionFragment {
            val questionFragment = QuestionFragment()
            val args = Bundle()
            args.putSerializable(QUESTION_DATA, questionEntity)
            questionFragment.arguments = args
            questionFragment.gameControllerPresenter = _gameControllerP
            return questionFragment
        }
    }
}