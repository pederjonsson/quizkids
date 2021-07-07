package se.pederjonsson.apps.quizkids.viewcomponents

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.categoryitemview.view.*
import se.pederjonsson.apps.quizkids.R
import se.pederjonsson.apps.quizkids.data.CategoryItem
import se.pederjonsson.apps.quizkids.model.profile.ProfileEntity


/**
 * Created by Gaming on 2018-04-01.
 */
class CategoryItemView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    private var categoryItem: CategoryItem? = null
    private var mContext: Context? = null
    private var playingProfile: ProfileEntity? = null

    init {
        mContext = context
        inflate()
    }

    private fun inflate() {
        inflate(mContext, R.layout.categoryitemview, this)
    }

    fun setUp(_categoryItem: CategoryItem?, profile: ProfileEntity?) {
        categoryItem = _categoryItem
        playingProfile = profile
        title.text = categoryItem?.category?.getCategoryTranslated(mContext)
        val point = playingProfile!!.getPointsForCategory(categoryItem!!.category.category)
        Log.i("CIV", "points for category " + categoryItem!!.category + " = " + point)
        setIcon()
        setCheckMark(point)
    }

    private fun setCheckMark(points: Int) {
        //if(playingProfile.getClearedCategories() != null && playingProfile.getClearedCategories().contains(categoryItem.getCategory())){
        if (points == 10) {
            checkmark.visibility = VISIBLE
        }
    }

    private fun setIcon() {
        when (categoryItem!!.category.name) {
            "BUILDINGS" -> icon.setImageDrawable(mContext!!.getDrawable(R.drawable.icon_pyramids_64))
            "SCIENCE" -> icon.setImageDrawable(mContext!!.getDrawable(R.drawable.icon_atom_64))
            "GEOGRAPHY" -> icon.setImageDrawable(mContext!!.getDrawable(R.drawable.icon_geo_64))
            "MATH" -> icon.setImageDrawable(mContext!!.getDrawable(R.drawable.icon_math_64))
            "ABC" -> icon.setImageDrawable(mContext!!.getDrawable(R.drawable.icon_abc_64))
            "OCEAN" -> icon.setImageDrawable(mContext!!.getDrawable(R.drawable.icon_ocean_64))
            "ANIMALS" -> icon.setImageDrawable(mContext!!.getDrawable(R.drawable.icon_animals_64))
            "SUPERHEROES" -> icon.setImageDrawable(mContext!!.getDrawable(R.drawable.icon_super_64))
            "SPORT" -> icon.setImageDrawable(mContext!!.getDrawable(R.drawable.icon_sport_64))
            else -> icon.setImageDrawable(mContext!!.getDrawable(R.drawable.icon_pyramids_64))
        }
    }

    fun showTitle() {
        title.visibility = VISIBLE;
    }
}