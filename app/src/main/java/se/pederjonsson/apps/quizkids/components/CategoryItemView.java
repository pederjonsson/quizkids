package se.pederjonsson.apps.quizkids.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void setUp(CategoryItem _categoryItem, Profile profile) {
        categoryItem = _categoryItem;
        playingProfile = profile;
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
                icon.setImageDrawable(mContext.getDrawable(R.drawable.icon_pyramids_24));
                break;
            case "SCIENCE"  :
                icon.setImageDrawable(mContext.getDrawable(R.drawable.icon_atom_64));
                break;
            case "GEOGRAPHY"  :
                icon.setImageDrawable(mContext.getDrawable(R.drawable.icon_pyramids_64));
                break;
            case "MATH"  :
                icon.setImageDrawable(mContext.getDrawable(R.drawable.icon_pyramids_24));
                break;
            case "ABC"  :
                icon.setImageDrawable(mContext.getDrawable(R.drawable.icon_pyramids_24));
                break;

            default:
                icon.setImageDrawable(null);
                break;
        }
    }

}
