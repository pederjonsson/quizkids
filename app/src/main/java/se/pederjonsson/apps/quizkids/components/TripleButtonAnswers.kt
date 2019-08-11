package se.pederjonsson.apps.quizkids.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout

import java.util.Collections

import se.pederjonsson.apps.quizkids.Objects.Answer
import se.pederjonsson.apps.quizkids.R
import se.pederjonsson.apps.quizkids.fragments.Question.QuestionAnswerContract
import se.pederjonsson.apps.quizkids.interfaces.LifecycleInterface
import kotlinx.android.synthetic.main.triplebtnanswers.view.*
import se.pederjonsson.apps.quizkids.components.room.question.QuestionEntity

class TripleButtonAnswers(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs), LifecycleInterface {

    private var mAnswers: List<Answer> = listOf()
    var mAnswerBtns: List<ButtonAnswer> = listOf()
    private var mainView: QuestionAnswerContract.MainView? = null

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.triplebtnanswers, this, true)
        mAnswerBtns = listOf(btnanswer1, btnanswer2, btnanswer3)
    }

    fun setUp(questionEntity: QuestionEntity, _mainView: QuestionAnswerContract.MainView) {
        val answersCopy = listOf<Answer>(Answer(questionEntity.trueanswer, true),
                Answer(questionEntity.answer2, false),
                Answer(questionEntity.answer3, false))
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
        mAnswerBtns.forEach { ans -> ans.inactivate() }
    }

    override fun onPause() {
        mAnswerBtns.forEach { ans -> ans.onPause() }
    }

    override fun onResume() {
        mAnswerBtns.forEach { ans -> ans.onResume() }
    }
}
