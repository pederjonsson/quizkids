package se.pederjonsson.apps.quizkids.fragments.category;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.pederjonsson.apps.quizkids.model.DataHolderForQuerys;
import se.pederjonsson.apps.quizkids.model.RoomDBUtil;
import se.pederjonsson.apps.quizkids.model.RoomQueryAsyncTasks;
import se.pederjonsson.apps.quizkids.model.profile.ProfileEntity;
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract;
import se.pederjonsson.apps.quizkids.data.CategoryItem;
import se.pederjonsson.apps.quizkids.QuestionActivity;
import se.pederjonsson.apps.quizkids.R;
import se.pederjonsson.apps.quizkids.viewcomponents.NavbarView;

/**
 * Created by Gaming on 2018-04-01.
 */

public class CategoryFragment extends android.support.v4.app.Fragment implements CategoryContract.View {

    public static String CATEGORY_DATA = "CATEGORY_DATA";
    private Unbinder unbinder;
    public GameControllerContract.MainActivityView mainActivityView;
    public GameControllerContract.MenuPresenter mGameControllerMenuPresenter;

    MediaPlayer mMediaPlayer;

    @BindView(R.id.gridview)
    GridView gridView;

    @BindView(R.id.navbar)
    NavbarView navbarView;

    private CategoryAdapter mAdapter;
    List<CategoryItem> categories;

    RoomDBUtil dbUtil;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        navbarView.showTitle(getString(R.string.categories));
        navbarView.show(true);
        dbUtil = new RoomDBUtil();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        dbUtil.getAllCategoryPointsForUser(getViewContext(), this, mGameControllerMenuPresenter.getPlayingProfile().getProfilename());
    }

    private void setupAdapter(){
        mAdapter = new CategoryAdapter(null, this);
        gridView.setAdapter(mAdapter);
        getCategoryData();
    }

    @Override
    public ProfileEntity getCurrentProfile() {
        return mGameControllerMenuPresenter.getPlayingProfile();
    }

    private void getCategoryData() {
        categories = new ArrayList<>();
        for (CategoryItem.Category category : CategoryItem.Category.values()) {
            categories.add(new CategoryItem(category));
        }
        if (categories != null) {
            mAdapter.setData(categories);
            mAdapter.notifyDataSetChanged();
        }
    }

    public static CategoryFragment newInstance(GameControllerContract.MainActivityView _mainActivityView, GameControllerContract.MenuPresenter _gamecontrollerMenuPresenter) {
        CategoryFragment categoryFragment = new CategoryFragment();
        categoryFragment.mainActivityView = _mainActivityView;
        categoryFragment.mGameControllerMenuPresenter = _gamecontrollerMenuPresenter;
        return categoryFragment;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseMediaPlayer();
        if(task != null && !task.isCancelled()){
            task.cancel(true);
            task = null;
        }
    }

    @Override
    public Context getViewContext() {
        return this.getContext();
    }

    @Override
    public void categoryClicked(CategoryItem categoryItem) {
        Intent intent = new Intent(getActivity(), QuestionActivity.class);
        intent.putExtra(QuestionActivity.CATEGORY_ITEM, categoryItem);
        intent.putExtra(QuestionActivity.PROFILE_ITEM, mGameControllerMenuPresenter.getPlayingProfile());
        getActivity().startActivityForResult(intent, 1);
    }

    private void playSound(int resId) {
        releaseMediaPlayer();
        mMediaPlayer = MediaPlayer.create(getContext(), resId);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    RoomQueryAsyncTasks.RoomQuery task;
    @Override
    public void onStartTask(@NotNull RoomQueryAsyncTasks.RoomQuery _task) {
        task = _task;
    }

    @Override
    public void onProgress(@org.jetbrains.annotations.Nullable Void... values) {

    }

    @Override
    public void onFail(@org.jetbrains.annotations.Nullable DataHolderForQuerys dh) {
        if(dh != null){
            Log.i("ROOM", "ONFAIL FOR REQUEST " + dh.getRequestType() + " error " + dh.getErrorMsg());
        } else {
            Log.i("ROOM", "ONFAIL");
        }
    }

    @Override
    public void onSuccess(@org.jetbrains.annotations.Nullable DataHolderForQuerys dh) {
        if(dh != null) {
            if(dh.getRequestType() == DataHolderForQuerys.RequestType.GETCATEGORYPOINTSFORUSER){
                Log.i("ROOM","categorypoints for user " + dh.getCategoryPointsEntityList()+ " for " + dh.getProfileid());
                if(mGameControllerMenuPresenter.getPlayingProfile() != null){
                    mGameControllerMenuPresenter.getPlayingProfile().setCategoryPointsList(dh.getCategoryPointsEntityList());
                    setupAdapter();
                } else {
                    Log.i("ROOM","profile was null");
                }
            }
        }
    }
}
