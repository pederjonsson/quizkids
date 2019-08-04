package se.pederjonsson.apps.quizkids.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout

import java.util.ArrayList
import java.util.Collections

import se.pederjonsson.apps.quizkids.Objects.Answer
import se.pederjonsson.apps.quizkids.R
import se.pederjonsson.apps.quizkids.fragments.Question.QuestionAnswerContract
import se.pederjonsson.apps.quizkids.interfaces.LifecycleInterface
import kotlinx.android.synthetic.main.triplebtnanswers.view.*

class TripleButtonAnswers(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs), LifecycleInterface {

    private var mAnswers: List<Answer> = listOf()
    var mAnswerBtns: List<ButtonAnswer> = listOf()
    private var mainView: QuestionAnswerContract.MainView? = null

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.triplebtnanswers, this, true)
        mAnswerBtns = listOf(btnanswer1, btnanswer2, btnanswer3)
    }

    fun setUp(answers: List<Answer>, _mainView: QuestionAnswerContract.MainView) {

        val answersCopy = answers.toMutableList()
        Collections.shuffle(answersCopy)
        mAnswers = answersCopy
        mainView = _mainView
        setupBtns()
    }

    private fun setupBtns() {
        btnanswer1.setUp(mAnswers[0], mainView)
        btnanswer2.setUp(mAnswers[1], mainView)
        btnanswer3.setUp(mAnswers[2], mainView)
    }

    fun inactivateButtons() {
       /* btnanswer1?.inactivate()
        btnanswer2?.inactivate()
        btnanswer3?.inactivate()*/
        mAnswerBtns.forEach { ans -> ans.inactivate() }
    }

    override fun onPause() {
        /*btnanswer1?.onPause()
        btnanswer2?.onPause()
        btnanswer3?.onPause()*/
        //mAnswerBtns.listIterator().next().onPause()
        mAnswerBtns.forEach { ans -> ans.onPause() }
    }

    override fun onResume() {
        /*btnanswer1!!.onResume()
        btnanswer2!!.onResume()
        btnanswer3!!.onResume()*/

        mAnswerBtns.forEach { ans -> ans?.onResume() }
    }
}
