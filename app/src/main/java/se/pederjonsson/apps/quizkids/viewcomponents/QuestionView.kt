package se.pederjonsson.apps.quizkids.viewcomponents

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.question_view.view.*
import se.pederjonsson.apps.quizkids.R
import se.pederjonsson.apps.quizkids.fragments.Question.QuestionAnswerContract.MainView
import se.pederjonsson.apps.quizkids.model.question.QuestionEntity

/**
 * Created by Gaming on 2018-04-01.
 */
class QuestionView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private var question: QuestionEntity? = null
    private var mContext: Context? = null
    private var mainView: MainView? = null

    init{
        mContext = context
        inflate()
    }

    protected fun inflate() {
        inflate(mContext, R.layout.question_view, this)
    }

    fun setUp(_question: QuestionEntity?, _mainView: MainView?) {
        question = _question
        mainView = _mainView
        text_question.text = question?.questiontext;
        if (question!!.drawableid > 0) {
            imageView1.setImageDrawable(resources.getDrawable(question?.drawableid!!));
        }
        texttospeechbtn.setUp(question?.questiontext!!, mainView!!);
    }
}