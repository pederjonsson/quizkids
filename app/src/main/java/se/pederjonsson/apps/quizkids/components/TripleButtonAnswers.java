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

/**
 * Created by Gaming on 2018-04-01.
 */

public class TripleButtonAnswers extends LinearLayout {

    @BindView(R.id.btnanswer1)
    public Button btnAnswer1;

    @BindView(R.id.btnanswer2)
    public Button btnAnswer2;

    @BindView(R.id.btnanswer3)
    public Button btnAnswer3;


    private List<Answer> mAnswers;
    private Context mContext;
    private Unbinder unbinder;


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

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
    }

    public void setUp(List<Answer> answers) {
        mAnswers = answers;
        setupListeners();
    }

    private void setupListeners() {
        /*backPressArea.setOnClickListener(v -> { mToolbarViewInterface.onBackBtnClicked(); });
        if(mShowBackBtn){
            backBtn.setVisibility(VISIBLE);
        }
        leftIcon.setOnClickListener(v -> { mToolbarViewInterface.onLeftIconClicked(); });
        */
    }

    public void setTitle(String text){


    }
}
