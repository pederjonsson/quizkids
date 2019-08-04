package se.pederjonsson.apps.quizkids;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.pederjonsson.apps.quizkids.components.NavbarView;

import se.pederjonsson.apps.quizkids.db.Database;
import se.pederjonsson.apps.quizkids.fragments.MenuFragment;
import se.pederjonsson.apps.quizkids.fragments.category.CategoryFragment;
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract;

public class MainActivity extends AppCompatActivity implements GameControllerContract.MainActivityView {

    private static int SLIDE_TIME = 300;
    private Unbinder unbinder;
    private GameControllerContract.MenuPresenter gameControllerMenuPresenter;
    Database db;
    MediaPlayer mMediaPlayer;

    @BindView(R.id.navbar)
    NavbarView navbarView;

    private FragmentManager mFragmentManager;

    Slide slide = new Slide(Gravity.RIGHT);
    Slide slideout = new Slide(Gravity.LEFT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        db = Database.getInstance(this);
        gameControllerMenuPresenter = new MenuGameController(this, db, navbarView);
        mFragmentManager = getSupportFragmentManager();
        slide.setDuration(SLIDE_TIME);
        slideout.setDuration(SLIDE_TIME);
        showMenu();

        db.populate(this);

    }

    private void playSound(int resId) {
        releaseMediaPlayer();
        mMediaPlayer = MediaPlayer.create(this, resId);
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

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
    }

    @Override
    public void startQuickQuiz() {

    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void showMenu() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        MenuFragment fragment = MenuFragment.newInstance(this, gameControllerMenuPresenter);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragmentcontainer, fragment);
        commitTransaction(fragmentTransaction);
    }

    private void commitTransaction(FragmentTransaction fragmentTransaction) {
        if (!mFragmentManager.isStateSaved()) {
            fragmentTransaction.commit();
        } else {
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void showCategories() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        CategoryFragment fragment = CategoryFragment.newInstance(this, gameControllerMenuPresenter);
        setSlideInOutTransition(fragment);
        fragmentTransaction.replace(R.id.fragmentcontainer, fragment);
        //  if(addToBackstack){
        fragmentTransaction.addToBackStack(null);
        //}
        commitTransaction(fragmentTransaction);
    }

    private void setSlideInOutTransition(Fragment fragment) {
        fragment.setEnterTransition(slide);
        setSlideOutTransition(fragment);
    }

    private void setSlideOutTransition(Fragment fragment) {
        fragment.setExitTransition(slideout);
        fragment.setAllowEnterTransitionOverlap(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaPlayer();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QuestionActivity.SHOW_CATEGORIES) {
            showCategories();
        }
    }
}
