package se.pederjonsson.apps.quizkids.components

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast

import java.util.Locale

import se.pederjonsson.apps.quizkids.R
import se.pederjonsson.apps.quizkids.fragments.Question.QuestionAnswerContract
import se.pederjonsson.apps.quizkids.interfaces.LifecycleInterface
import kotlinx.android.synthetic.main.texttospeechbtn_view.view.*

open class TextToSpeechBtnView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs), LifecycleInterface {

    var speakString = ""
    var mainView: QuestionAnswerContract.MainView? = null
    var textToSpeech: TextToSpeech? = null
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

        textToSpeech = TextToSpeech(mainView?.viewContext, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                val ttsLang = textToSpeech?.setLanguage(Locale.getDefault())

                if (ttsLang == TextToSpeech.LANG_MISSING_DATA || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                    println("The Language is not supported!")
                } else {
                    println("Language Supported.")
                }
                println("Initialization success.")
            } else {
                Toast.makeText(mainView?.viewContext, "ERROR", Toast.LENGTH_SHORT).show()
            }
        })

        texttospeechbtnimageview.setOnClickListener {
            Log.i("TTS", "clicked btn")
            val speechStatus = textToSpeech?.speak(speakString, TextToSpeech.QUEUE_FLUSH, null, null)
            if (speechStatus == TextToSpeech.ERROR) {
                Log.i("TTS", "Error in converting Text to Speech!")
            }
            Log.i("TTS", "should speak now")
        }
    }

    override fun onPause() {
        if (textToSpeech != null) {
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
            textToSpeech = null
        }
    }

    override fun onResume() {
        if (textToSpeech == null && setupHasBeenRun) {
            setupTextToSpeech()
        }
    }
}
