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

public class QuestionView extends LinearLayout implements LifecycleInterface {

    @BindView(R.id.imageView1)
    RoundedImageView imageView;

    @BindView(R.id.text_question)
    TextView textQuestion;

    @BindView(R.id.texttospeechbtn)
    TextToSpeechBtnView textToSpeechBtn;

    private Question question;
    private Context mContext;
    private Unbinder unbinder;
    private QuestionAnswerContract.MainView mainView;

    public QuestionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    protected void init(Context context) {
        mContext = context;
        inflate();
    }

    protected void inflate() {
        inflate(mContext, R.layout.question_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        unbinder = ButterKnife.bind(this);
    }

    public void setUp(Question _question, QuestionAnswerContract.MainView _mainView) {
        question = _question;
        mainView = _mainView;
        textQuestion.setText(mContext.getText(question.getQuestionResId()));
        if (question.getDrawableResID() > 0) {
            imageView.setImageDrawable(getResources().getDrawable(question.getDrawableResID()));
        }
        textToSpeechBtn.setUp(mContext.getText(question.getQuestionResId()).toString(), mainView);
    }

    @Override
    public void onPause() {
        textToSpeechBtn.onPause();
    }

    @Override
    public void onResume() {
        textToSpeechBtn.onResume();
    }
}
