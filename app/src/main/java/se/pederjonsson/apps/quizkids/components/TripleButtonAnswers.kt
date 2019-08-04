package se.pederjonsson.apps.quizkids.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout

import java.util.ArrayList
import java.util.Collections

import butterknife.ButterKnife
import butterknife.Unbinder
import se.pederjonsson.apps.quizkids.Objects.Answer
import se.pederjonsson.apps.quizkids.R
import se.pederjonsson.apps.quizkids.fragments.Question.QuestionAnswerContract
import se.pederjonsson.apps.quizkids.interfaces.LifecycleInterface

//import kotlinx.android.synthetic.main.triplebtnanswers.*

class TripleButtonAnswers(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs), LifecycleInterface {


    var btnAnswer1: ButtonAnswer? = null
    var btnAnswer2: ButtonAnswer? = null
    var btnAnswer3: ButtonAnswer? = null

    private var mAnswers: List<Answer>? = null
    private var unbinder: Unbinder? = null
    private var mainView: QuestionAnswerContract.MainView? = null

    init {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.triplebtnanswers, this, true)
        btnAnswer1 = view.findViewById(R.id.btnanswer1)
        btnAnswer2 = view.findViewById(R.id.btnanswer2)
        btnAnswer3 = view.findViewById(R.id.btnanswer3)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        unbinder = ButterKnife.bind(this)
    }

    fun setUp(answers: List<Answer>, _mainView: QuestionAnswerContract.MainView) {

        val answersCopy = ArrayList<Answer>()
        answersCopy.add(answers[0])
        answersCopy.add(answers[1])
        answersCopy.add(answers[2])
        Collections.shuffle(answersCopy)
        mAnswers = answersCopy
        mainView = _mainView
        setupBtns()
    }

    private fun setupBtns() {
        btnAnswer1?.setUp(mAnswers!![0], mainView)
        btnAnswer2?.setUp(mAnswers!![1], mainView)
        btnAnswer3?.setUp(mAnswers!![2], mainView)
    }

    fun inactivateButtons() {
        btnAnswer1?.inactivate()
        btnAnswer2?.inactivate()
        btnAnswer3?.inactivate()
    }

    override fun onPause() {
        btnAnswer1?.onPause()
        btnAnswer2?.onPause()
        btnAnswer3?.onPause()
    }

    override fun onResume() {
        btnAnswer1!!.onResume()
        btnAnswer2!!.onResume()
        btnAnswer3!!.onResume()
    }
}
