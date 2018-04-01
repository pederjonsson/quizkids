package se.pederjonsson.apps.quizkids.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.pederjonsson.apps.quizkids.Objects.Answer;
import se.pederjonsson.apps.quizkids.R;
import se.pederjonsson.apps.quizkids.fragments.QuestionAnswerContract;

/**
 * Created by Gaming on 2018-04-01.
 */

public class ButtonAnswer extends LinearLayout {

    @BindView(R.id.btnanswer)
    public Button btnAnswer;

    private Context mContext;
    private Unbinder unbinder;
    private Answer mAnswer;
    private QuestionAnswerContract.MainView mainView;

    public ButtonAnswer(Context context, AttributeSet attrs) {
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
        inflate(mContext, R.layout.btnanswer, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        unbinder = ButterKnife.bind(this);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
    }

    public void setUp(Answer answer, QuestionAnswerContract.MainView _mainView) {
        mAnswer = answer;
        btnAnswer.setText(mAnswer.getTextAnswer());
        mainView = _mainView;

        setupListener();
    }

    private void setupListener() {
        btnAnswer.setOnClickListener(v -> { mainView.publishChosenAnswer(mAnswer); });
    }
}
