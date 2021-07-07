package se.pederjonsson.apps.quizkids.viewcomponents

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.question_view.view.*
import se.pederjonsson.apps.quizkids.R
import se.pederjonsson.apps.quizkids.fragments.Question.QuestionAnswerContract.MainView
import se.pederjonsson.apps.quizkids.ktextensions.afterMeasured
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

    private fun inflate() {
        inflate(mContext, R.layout.question_view, this)

    }

    fun setUp(_question: QuestionEntity?, _mainView: MainView?) {
        question = _question
        mainView = _mainView
        text_question.text = question?.questiontext;
        if (question!!.drawableid > 0) {



            //imageView1.requestLayout()
            imageView1.setImageDrawable(ContextCompat.getDrawable(mContext!!, question?.drawableid!!)) //resources.getDrawable(question?.drawableid!!))
            /*imageView1.afterMeasured{
                Log.i("bug", "aftermeasured fix height")
                imageView1.layoutParams.height = this.width
                imageView1.requestLayout()
                imageView1.setImageDrawable(resources.getDrawable(question?.drawableid!!))
            }*/
        }
        texttospeechbtn.setUp(question?.questiontext!!, mainView!!);
    }
}