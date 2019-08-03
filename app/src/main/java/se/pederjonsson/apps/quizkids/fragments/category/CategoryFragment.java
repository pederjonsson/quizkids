package se.pederjonsson.apps.quizkids.fragments.category;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract;
import se.pederjonsson.apps.quizkids.Objects.CategoryItem;
import se.pederjonsson.apps.quizkids.Objects.Profile;
import se.pederjonsson.apps.quizkids.Objects.Question;
import se.pederjonsson.apps.quizkids.QuestionActivity;
import se.pederjonsson.apps.quizkids.R;
import se.pederjonsson.apps.quizkids.components.NavbarView;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.category_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        navbarView.showTitle(getString(R.string.categories));
        navbarView.show(true);
      //  QuestionAnswers questionAnswers = (QuestionAnswers) getArguments().getSerializable(CATEGORY_DATA);
        mAdapter = new CategoryAdapter(null, this);

        gridView.setAdapter(mAdapter);

        getCategoryData();
        if(categories != null && mAdapter.getCount() == 0){
            mAdapter.setData(categories);
            mAdapter.notifyDataSetChanged();
        }
        return view;
    }

    @Override
    public Profile getCurrentProfile() {
        return mGameControllerMenuPresenter.getPlayingProfile();
    }

    private void getCategoryData(){
        categories = new ArrayList<>();
        for (Question.Category category : Question.Category.values()){
            categories.add(new CategoryItem(category));
        }
    }

    public static CategoryFragment newInstance(GameControllerContract.MainActivityView _mainActivityView, GameControllerContract.MenuPresenter _gamecontrollerMenuPresenter) {
        CategoryFragment categoryFragment = new CategoryFragment();
        categoryFragment.mainActivityView = _mainActivityView;
        categoryFragment.mGameControllerMenuPresenter = _gamecontrollerMenuPresenter;
      /*  Bundle args = new Bundle();
        args.putSerializable(CATEGORY_DATA, questionAnswers);
        questionFragment.setArguments(args);*/
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
    }

    @Override
    public Context getViewContext() {
        return this.getContext();
    }

    @Override
    public void categoryClicked(CategoryItem categoryItem) {
        //mGameControllerPresenter.loadQuestionsByCategory(categoryItem);
        Intent intent = new Intent(getActivity(), QuestionActivity.class);
        intent.putExtra(QuestionActivity.CATEGORY_ITEM, categoryItem);
        intent.putExtra(QuestionActivity.PROFILE_ITEM, mGameControllerMenuPresenter.getPlayingProfile());
        getActivity().startActivityForResult(intent, 1);
    }

    private void playSound(int resId){
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

}
