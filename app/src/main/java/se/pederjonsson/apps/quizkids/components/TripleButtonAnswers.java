package se.pederjonsson.apps.quizkids.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.pederjonsson.apps.quizkids.Objects.Answer;
import se.pederjonsson.apps.quizkids.R;
import se.pederjonsson.apps.quizkids.fragments.Question.QuestionAnswerContract;
import se.pederjonsson.apps.quizkids.interfaces.LifecycleInterface;

/**
 * Created by Gaming on 2018-04-01.
 */

public class TripleButtonAnswers extends LinearLayout implements LifecycleInterface {

    @BindView(R.id.btnanswer1)
    public ButtonAnswer btnAnswer1;

    @BindView(R.id.btnanswer2)
    public ButtonAnswer btnAnswer2;

    @BindView(R.id.btnanswer3)
    public ButtonAnswer btnAnswer3;


    private List<Answer> mAnswers;
    private Context mContext;
    private Unbinder unbinder;
    private QuestionAnswerContract.MainView mainView;


    public TripleButtonAnswers(Context context, AttributeSet attrs) {
        super(context, attrs);

        //TypedArray typedArrayTitle = context.obtainStyledAttributes(attrs, R.styleable.telavox_view_property);
       // getTitle(typedArrayTitle);

        init(context);
    }

   /* private void getTitle(TypedArray typedArray) {
        CharSequence s = typedArray.getString(R.styleable.telavox_view_property_title);
        if (s != null) {
            mTitleText = s.toString();
        }
        typedArray.recycle();
    }*/

    protected void init(Context context) {
        mContext = context;
        inflate();
    }

    protected void inflate() {
        inflate(mContext, R.layout.triplebtnanswers, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        unbinder = ButterKnife.bind(this);
    }

    public void setUp(List<Answer> answers, QuestionAnswerContract.MainView _mainView) {

        List<Answer> answersCopy = new ArrayList<>();
        answersCopy.add(answers.get(0));
        answersCopy.add(answers.get(1));
        answersCopy.add(answers.get(2));
        Collections.shuffle(answersCopy);
        mAnswers = answersCopy;
        mainView = _mainView;
        setupBtns();
    }

    private void setupBtns(){
       btnAnswer1.setUp(mAnswers.get(0), mainView);
       btnAnswer2.setUp(mAnswers.get(1), mainView);
       btnAnswer3.setUp(mAnswers.get(2), mainView);
    }

    public void inactivateButtons(){
        btnAnswer1.inactivate();
        btnAnswer2.inactivate();
        btnAnswer3.inactivate();
    }

    @Override
    public void onPause() {
        btnAnswer1.onPause();
        btnAnswer2.onPause();
        btnAnswer3.onPause();
    }

    @Override
    public void onResume() {
        btnAnswer1.onResume();
        btnAnswer2.onResume();
        btnAnswer3.onResume();
    }
}
