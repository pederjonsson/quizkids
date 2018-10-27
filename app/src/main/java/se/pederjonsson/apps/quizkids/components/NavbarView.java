package se.pederjonsson.apps.quizkids.components;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.pederjonsson.apps.quizkids.Objects.CategoryItem;
import se.pederjonsson.apps.quizkids.Objects.Profile;
import se.pederjonsson.apps.quizkids.R;

/**
 * Created by Gaming on 2018-04-01.
 */

public class NavbarView extends RelativeLayout {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.rootview)
    RelativeLayout rootView;

    @BindView(R.id.dotscontainer)
    LinearLayout dotsContainer;

    @BindViews({R.id.dot1, R.id.dot2, R.id.dot3, R.id.dot4, R.id.dot5, R.id.dot6, R.id.dot7, R.id.dot8, R.id.dot9, R.id.dot10})
    List<RoundedImageView> dots;

    private CategoryItem categoryItem;
    private Context mContext;
    private Unbinder unbinder;
    private Profile playingProfile;

    public NavbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }



    protected void init(Context context) {
        mContext = context;
        inflate();
    }

    protected void inflate() {
        inflate(mContext, R.layout.navbar, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        unbinder = ButterKnife.bind(this);
    }

    public void setUp() {
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

    public void showScore(){
        dotsContainer.setVisibility(VISIBLE);
    }

    public void setScore(boolean correct, int question){
        if(correct){
            dots.get(question).setImageDrawable(mContext.getDrawable(R.drawable.greenrectangle));
        } else {
            dots.get(question).setImageDrawable(mContext.getDrawable(R.drawable.redrectangle));
        }
    }

    public void clearScore(){
        for (RoundedImageView dot: dots){
            dot.setImageDrawable(mContext.getDrawable(R.drawable.whiterectangle));
        }
    }

}
