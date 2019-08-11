package se.pederjonsson.apps.quizkids.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.pederjonsson.apps.quizkids.components.room.profile.ProfileEntity;
import se.pederjonsson.apps.quizkids.fragments.Question.QuestionGameController;
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract;
import se.pederjonsson.apps.quizkids.Objects.CategoryItem;
import se.pederjonsson.apps.quizkids.R;

/**
 * Created by Gaming on 2018-04-01.
 */

public class ResultView extends RelativeLayout{

    @BindView(R.id.resultstitle)
    TextView title;

    @BindView(R.id.results_subtitle)
    TextView subtitle;

    @BindView(R.id.rootview)
    RelativeLayout rootView;

    @BindView(R.id.resultcategoryview)
    CategoryItemView categoryItemView;

    @BindView(R.id.btnresultcontinue)
    Button btnContinue;

    private Context mContext;
    private GameControllerContract.QuestionActivityView questionActivityView;

    public ResultView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    protected void init(Context context) {
        mContext = context;
        inflate();
    }

    protected void inflate() {
        inflate(mContext, R.layout.resultview, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void setUp(CategoryItem categoryItem, ProfileEntity profileEntity, GameControllerContract.QuestionActivityView _questionActivityView) {
        questionActivityView = _questionActivityView;
        categoryItemView.setUp(categoryItem, profileEntity);
        categoryItemView.showTitle();
        showTitle(mContext.getString(R.string.congratulations) + " " + profileEntity.getProfilename() + "!");
        subtitle.setText(mContext.getString(R.string.youclearedcategory));
        btnContinue.setOnClickListener(v -> {
            questionActivityView.showCategories();
        });
        btnContinue.setText(mContext.getString(R.string.continuegame));
    }

    public void setUpNotAllCorrect(CategoryItem categoryItem, ProfileEntity profileEntity, GameControllerContract.QuestionActivityView _questionActivityView, int amountCorrect) {
        questionActivityView = _questionActivityView;
        categoryItemView.setUp(categoryItem, profileEntity);
        categoryItemView.showTitle();
        String title = mContext.getString(R.string.nice_work) + " " + profileEntity.getProfilename();
        showTitle(title + "!");
        String amountquestions = amountCorrect + " " + mContext.getString(R.string.questions);
        if(amountCorrect == 1){
            amountquestions = mContext.getString(R.string.one_question);
        }
        String of = mContext.getString(R.string.of);
        String subString = mContext.getString(R.string.you_answered_correctly_on) + " " + amountquestions + " " + of + " " + QuestionGameController.MAX_QUESTIONS_IN_CATEGORY;
        subtitle.setText(subString);
        String speechstring = title + ". " + mContext.getString(R.string.you_answered_correctly_on) + ". " + amountquestions + ". " + of + " " + QuestionGameController.MAX_QUESTIONS_IN_CATEGORY +
                ". " + mContext.getString(R.string.better_luck_next_time);

        btnContinue.setOnClickListener(v -> {
            questionActivityView.showCategories();
        });
        btnContinue.setText(mContext.getString(R.string.continuegame));

        questionActivityView.speekText(speechstring);
    }

    public void showTitle(String titlestring) {
        title.setText(titlestring);
    }

    public void show(boolean visible) {
        if (visible) {
            rootView.setVisibility(VISIBLE);
        } else {
            rootView.setVisibility(GONE);
        }
    }
}
