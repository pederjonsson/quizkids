package se.pederjonsson.apps.quizkids.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.pederjonsson.apps.quizkids.GameControllerContract;
import se.pederjonsson.apps.quizkids.Objects.CategoryItem;
import se.pederjonsson.apps.quizkids.Objects.Profile;
import se.pederjonsson.apps.quizkids.R;

/**
 * Created by Gaming on 2018-04-01.
 */

public class ResultView extends RelativeLayout {

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

    private CategoryItem categoryItem;
    private Context mContext;
    private Unbinder unbinder;
    private Profile playingProfile;
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
        unbinder = ButterKnife.bind(this);
    }

    public void setUp(CategoryItem categoryItem, Profile profile, GameControllerContract.QuestionActivityView _questionActivityView) {
        questionActivityView = _questionActivityView;
        categoryItemView.setUp(categoryItem, profile);
        categoryItemView.showTitle();
        showTitle(mContext.getString(R.string.congratulations) + " " + profile.getName() + "!");
        subtitle.setText(mContext.getString(R.string.youclearedcategory));
        btnContinue.setOnClickListener(v -> {
            questionActivityView.showCategories();
        });
        btnContinue.setText(mContext.getString(R.string.continuegame));
    }

    public void showTitle(String titlestring){
        title.setText(titlestring);
    }

    public void show(boolean visible){
        if(visible){
            rootView.setVisibility(VISIBLE);
        } else {
            rootView.setVisibility(GONE);
        }
    }

}
