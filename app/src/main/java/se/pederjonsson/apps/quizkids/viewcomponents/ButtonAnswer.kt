package se.pederjonsson.apps.quizkids.viewcomponents

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.btnanswer.view.*
import se.pederjonsson.apps.quizkids.R
import se.pederjonsson.apps.quizkids.data.Answer
import se.pederjonsson.apps.quizkids.fragments.Question.QuestionAnswerContract.MainView

/**
 * Created by Gaming on 2018-04-01.
 */
class ButtonAnswer(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    /*@BindView(R.id.btnanswer)
    public Button btnAnswer;

    @BindView(R.id.response)
    public TextView response;

    @BindView(R.id.texttospeechbtn)
    public TextToSpeechBtnViewSmall textToSpeechBtnView;
*/
    private var mContext: Context? = null
    private var mAnswer: Answer? = null
    private var mainView: MainView? = null
    var chosenAnswer = false

    init {
        mContext = context
        inflate()
    }

    protected fun inflate() {
        inflate(mContext, R.layout.btnanswer, this)
    }

    fun setUp(answer: Answer?, _mainView: MainView?) {
        mAnswer = answer
        btnanswer.setText(mAnswer!!.textAnswer);
        mainView = _mainView
        texttospeechbtn.setUp(mAnswer!!.textAnswer, mainView!!);
        setupListener()
    }

    private fun setupListener() {
        btnanswer.setOnClickListener {
            chosenAnswer = true
            showResponse()
            mainView?.publishChosenAnswer(mAnswer)
        }
    }

    private fun showResponse() {
        if (mAnswer!!.isCorrect) {
            response.text = resources.getString(R.string.correct_answer)
            response.setTextColor(resources.getColor(R.color.colorCorrect))
        } else {
            response.text = resources.getString(R.string.wrong_answer)
            response.setTextColor(resources.getColor(R.color.colorError))
        }
        response.visibility = VISIBLE
    }

    fun inactivate() {
        btnanswer.isEnabled = false
    }

    fun hide() {
        texttospeechbtn.visibility = GONE
        btnanswer.visibility = GONE
    }
}