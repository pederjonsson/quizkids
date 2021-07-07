package se.pederjonsson.apps.quizkids.fragments.category

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.category_fragment.*
import se.pederjonsson.apps.quizkids.QuestionActivity
import se.pederjonsson.apps.quizkids.R
import se.pederjonsson.apps.quizkids.data.CategoryItem
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract.MainActivityView
import se.pederjonsson.apps.quizkids.model.DataHolderForQuerys
import se.pederjonsson.apps.quizkids.model.RoomDBUtil
import se.pederjonsson.apps.quizkids.model.RoomQueryAsyncTasks.RoomQuery
import se.pederjonsson.apps.quizkids.model.profile.ProfileEntity
import java.util.*

/**
 * Created by Gaming on 2018-04-01.
 */
class CategoryFragment : Fragment(), CategoryContract.View {
    var mainActivityView: MainActivityView? = null
    var mGameControllerMenuPresenter: GameControllerContract.MenuPresenter? = null
    var mMediaPlayer: MediaPlayer? = null

    private var mAdapter: CategoryAdapter? = null
    var categories: MutableList<CategoryItem>? = null
    var dbUtil: RoomDBUtil? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.category_fragment, container, false)
        dbUtil = RoomDBUtil()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navbar.showTitle(getString(R.string.categories))
        navbar.show(true);
    }

    override fun onResume() {
        super.onResume()
        dbUtil!!.getAllCategoryPointsForUser(viewContext, this, mGameControllerMenuPresenter!!.playingProfile.profilename)
    }

    private fun setupAdapter() {
        mAdapter = CategoryAdapter(null, this)
        gridview.adapter = mAdapter
        setCategoryData()
    }

    override fun getCurrentProfile(): ProfileEntity {
        return mGameControllerMenuPresenter!!.playingProfile
    }

    fun setCategoryData(){
        categories = ArrayList()
        for (category in CategoryItem.Category.values()) {
            categories?.add(CategoryItem(category))
        }
        if (categories != null) {
            Log.i("ROOM", "success categories found " + categories!!.size)
            mAdapter!!.setData(categories)
            mAdapter!!.notifyDataSetChanged()
        } else {
            Log.i("ROOM", "ONFAIL no categories found")
        }
    }

    override fun onPause() {
        super.onPause()
        releaseMediaPlayer()
        if (task != null && !task!!.isCancelled) {
            task!!.cancel(true)
            task = null
        }
    }

    override fun getViewContext(): Context {
        return this.context!!
    }

    override fun categoryClicked(categoryItem: CategoryItem) {
        val intent = Intent(activity, QuestionActivity::class.java)
        intent.putExtra(QuestionActivity.CATEGORY_ITEM, categoryItem)
        intent.putExtra(QuestionActivity.PROFILE_ITEM, mGameControllerMenuPresenter!!.playingProfile)
        activity!!.startActivityForResult(intent, 1)
    }

    private fun playSound(resId: Int) {
        releaseMediaPlayer()
        mMediaPlayer = MediaPlayer.create(context, resId)
        mMediaPlayer?.setLooping(false)
        mMediaPlayer?.start()
    }

    private fun releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer!!.isPlaying) {
                mMediaPlayer!!.stop()
            }
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    var task: RoomQuery? = null
    override fun onStartTask(_task: RoomQuery) {
        task = _task
    }

    override fun onProgress(vararg values: Void?) {}
    override fun onFail(dh: DataHolderForQuerys?) {
        if (dh != null) {
            Log.i("ROOM", "ONFAIL FOR REQUEST " + dh.requestType + " error " + dh.errorMsg)
        } else {
            Log.i("ROOM", "ONFAIL")
        }
    }

    override fun onSuccess(dh: DataHolderForQuerys?) {
        if (dh != null) {
            if (dh.requestType === DataHolderForQuerys.RequestType.GETCATEGORYPOINTSFORUSER) {
                Log.i("ROOM", "categorypoints for user " + dh.categoryPointsEntityList + " for " + dh.profileid)
                dh.categoryPointsEntityList?.let {
                    for(c in it){
                        Log.i("CIV", "categoryfragment catpoint " + c.points)
                    }
                }

                if (mGameControllerMenuPresenter!!.playingProfile != null) {
                    mGameControllerMenuPresenter!!.playingProfile.categoryPointsList = dh.categoryPointsEntityList
                    setupAdapter()
                } else {
                    Log.i("ROOM", "profile was null")
                }
            }
        }
    }

    companion object {
        var CATEGORY_DATA = "CATEGORY_DATA"
        @JvmStatic
        fun newInstance(_mainActivityView: MainActivityView?, _gamecontrollerMenuPresenter: GameControllerContract.MenuPresenter?): CategoryFragment {
            val categoryFragment = CategoryFragment()
            categoryFragment.mainActivityView = _mainActivityView
            categoryFragment.mGameControllerMenuPresenter = _gamecontrollerMenuPresenter
            return categoryFragment
        }
    }
}