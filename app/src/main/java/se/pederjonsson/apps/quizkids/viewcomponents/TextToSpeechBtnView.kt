package se.pederjonsson.apps.quizkids.viewcomponents

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout

import se.pederjonsson.apps.quizkids.R
import se.pederjonsson.apps.quizkids.fragments.Question.QuestionAnswerContract
import kotlinx.android.synthetic.main.texttospeechbtn_view.view.*

open class TextToSpeechBtnView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    var speakString = ""
    var mainView: QuestionAnswerContract.MainView? = null
    var setupHasBeenRun = false

    init {
        inflate()
    }

    open fun inflate(){
        LayoutInflater.from(context).inflate(R.layout.texttospeechbtn_view, this, true)
    }

    fun setUp(_speakString: String, _mainView: QuestionAnswerContract.MainView) {
        mainView = _mainView
        setupHasBeenRun = true
        speakString = _speakString
        setupTextToSpeech()
    }

    private fun setupTextToSpeech() {
        texttospeechbtnimageview.setOnClickListener {
            mainView?.qActivityView?.speekText(speakString)
        }
    }
}
