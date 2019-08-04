package se.pederjonsson.apps.quizkids.components;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.pederjonsson.apps.quizkids.Objects.Question;
import se.pederjonsson.apps.quizkids.R;
import se.pederjonsson.apps.quizkids.fragments.Question.QuestionAnswerContract;
import se.pederjonsson.apps.quizkids.interfaces.LifecycleInterface;

/**
 * Created by Gaming on 2018-04-01.
 */

public class TextToSpeechBtnView extends LinearLayout implements LifecycleInterface {

    @BindView(R.id.texttospeechbtnimageview)
    ImageView textToSpeechBtn;

    protected Context mContext;
    protected String speakString = "";
    protected QuestionAnswerContract.MainView mainView;
    protected TextToSpeech textToSpeech;
    protected Unbinder unbinder;
    protected boolean setupHasBeenRun = false;

    public TextToSpeechBtnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    protected void init(Context context) {
        mContext = context;
        inflate();
    }

    protected void inflate() {
        inflate(mContext, R.layout.texttospeechbtn_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        unbinder = ButterKnife.bind(this);
    }

    public void setUp(String _speakString, QuestionAnswerContract.MainView _mainView) {
        mainView = _mainView;
        setupHasBeenRun = true;
        speakString = _speakString;
        setupTextToSpeech();
    }

    private void setupTextToSpeech() {

        textToSpeech = new TextToSpeech(mainView.getViewContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(Locale.getDefault());

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        System.out.println("The Language is not supported!");
                    } else {
                        System.out.println("Language Supported.");
                    }
                    System.out.println("Initialization success.");
                } else {
                    Toast.makeText(mainView.getViewContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textToSpeechBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TTS", "clicked btn");
                int speechStatus = textToSpeech.speak(speakString, TextToSpeech.QUEUE_FLUSH, null, null);
                if (speechStatus == TextToSpeech.ERROR) {
                    System.out.println("Error in converting Text to Speech!");
                }
                Log.i("TTS", "should speak now");
            }
        });
    }

    @Override
    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
    }

    @Override
    public void onResume() {
        if (textToSpeech == null && setupHasBeenRun) {
            setupTextToSpeech();
        }
    }
}
