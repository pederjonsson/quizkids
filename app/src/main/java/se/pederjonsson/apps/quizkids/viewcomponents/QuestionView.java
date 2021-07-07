package se.pederjonsson.apps.quizkids.viewcomponents;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.pederjonsson.apps.quizkids.R;
import se.pederjonsson.apps.quizkids.model.question.QuestionEntity;
import se.pederjonsson.apps.quizkids.fragments.Question.QuestionAnswerContract;

/**
 * Created by Gaming on 2018-04-01.
 */

public class QuestionView extends LinearLayout {

    @BindView(R.id.imageView1)
    RoundedImageView imageView;

    @BindView(R.id.text_question)
    TextView textQuestion;

    @BindView(R.id.texttospeechbtn)
    TextToSpeechBtnView textToSpeechBtn;

    private QuestionEntity question;
    private Context mContext;
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
        ButterKnife.bind(this);
    }

    public void setUp(QuestionEntity _question, QuestionAnswerContract.MainView _mainView) {
        question = _question;
        mainView = _mainView;
        textQuestion.setText(question.getQuestiontext());
        if (question.getDrawableid() > 0) {
            imageView.setImageDrawable(getResources().getDrawable(question.getDrawableid()));
        }
        textToSpeechBtn.setUp(question.getQuestiontext(), mainView);
    }
}