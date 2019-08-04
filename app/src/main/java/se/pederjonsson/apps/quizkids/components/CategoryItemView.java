package se.pederjonsson.apps.quizkids.components;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.pederjonsson.apps.quizkids.Objects.CategoryItem;
import se.pederjonsson.apps.quizkids.Objects.Profile;
import se.pederjonsson.apps.quizkids.R;

/**
 * Created by Gaming on 2018-04-01.
 */

public class CategoryItemView extends RelativeLayout {

    @BindView(R.id.icon)
    RoundedImageView icon;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.checkmark)
    ImageView checkMark;

    private CategoryItem categoryItem;
    private Context mContext;
    private Unbinder unbinder;
    private Profile playingProfile;

    public CategoryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    protected void init(Context context) {
        mContext = context;
        inflate();
    }

    protected void inflate() {
        inflate(mContext, R.layout.categoryitemview, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        unbinder = ButterKnife.bind(this);
    }

    public void setUp(CategoryItem _categoryItem, Profile profile) {
        categoryItem = _categoryItem;
        playingProfile = profile;
        title.setText(categoryItem.getCategory().getCategoryTranslated(mContext));
        Integer point = playingProfile.getPointsByCategory(categoryItem.getCategory());
        Log.i("CIV", "points for category " + categoryItem.getCategory() + " = " + point);
        setIcon();
        setCheckMark();
    }

    private void setCheckMark(){
        if(playingProfile.getClearedCategories() != null && playingProfile.getClearedCategories().contains(categoryItem.getCategory())){
            checkMark.setVisibility(VISIBLE);
        }
    }

    private void setIcon(){
       
        switch (categoryItem.getCategory().name().toString()){
            case "BUILDINGS"  :
                icon.setImageDrawable(mContext.getDrawable(R.drawable.icon_pyramids_64));
                break;
            case "SCIENCE"  :
                icon.setImageDrawable(mContext.getDrawable(R.drawable.icon_atom_64));
                break;
            case "GEOGRAPHY"  :
                icon.setImageDrawable(mContext.getDrawable(R.drawable.icon_geo_64));
                break;
            case "MATH"  :
                icon.setImageDrawable(mContext.getDrawable(R.drawable.icon_math_64));
                break;
            case "ABC"  :
                icon.setImageDrawable(mContext.getDrawable(R.drawable.icon_abc_64));
                break;
            case "OCEAN"  :
                icon.setImageDrawable(mContext.getDrawable(R.drawable.icon_ocean_64));
                break;
            case "ANIMALS"  :
                icon.setImageDrawable(mContext.getDrawable(R.drawable.icon_animals_64));
                break;
            case "SUPERHEROES"  :
                icon.setImageDrawable(mContext.getDrawable(R.drawable.icon_super_64));
                break;
            case "SPORT"  :
                icon.setImageDrawable(mContext.getDrawable(R.drawable.icon_sport_64));
                break;

            default:
                icon.setImageDrawable(mContext.getDrawable(R.drawable.icon_pyramids_64));
                break;
        }
    }

    public void showTitle(){
        title.setVisibility(VISIBLE);
    }

}
