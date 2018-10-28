package se.pederjonsson.apps.quizkids;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.pederjonsson.apps.quizkids.Objects.CategoryItem;
import se.pederjonsson.apps.quizkids.Objects.Profile;
import se.pederjonsson.apps.quizkids.Objects.Question;
import se.pederjonsson.apps.quizkids.Objects.QuestionAnswers;
import se.pederjonsson.apps.quizkids.components.NavbarView;
import se.pederjonsson.apps.quizkids.components.ResultView;
import se.pederjonsson.apps.quizkids.db.Database;
import se.pederjonsson.apps.quizkids.fragments.MenuFragment;
import se.pederjonsson.apps.quizkids.fragments.Question.QuestionFragment;
import se.pederjonsson.apps.quizkids.fragments.category.CategoryFragment;

public class MainActivity extends AppCompatActivity implements GameControllerContract.MainActivityView {

    private static int SLIDE_TIME = 300;
    private Unbinder unbinder;
    private GameControllerContract.Presenter gameControllerPresenter;
    Database db;
    MediaPlayer mMediaPlayer;

    @BindView(R.id.navbar)
    NavbarView navbarView;

    @BindView(R.id.resultview)
    ResultView resultView;

    List<QuestionAnswers> qaList;
    private FragmentManager mFragmentManager;

    Slide slide = new Slide(Gravity.RIGHT);
    Slide slideout = new Slide(Gravity.LEFT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        resultView.show(false);
        db = new Database(this);
        gameControllerPresenter = new GameController(this, db, navbarView);
        mFragmentManager = getSupportFragmentManager();
        slide.setDuration(SLIDE_TIME);
        slideout.setDuration(SLIDE_TIME);
        showMenu();

        db.populate(this);

    }

    @Override
    public void showResultView(CategoryItem categoryItem, Profile profile) {
        playSound(R.raw.drumroll);
        resultView.setUp(categoryItem, profile);
        resultView.show(true);
    }

    private void playSound(int resId){
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
        MenuFragment fragment = MenuFragment.newInstance(this, gameControllerPresenter);
        //setSlideOutTransition(fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragmentcontainer, fragment);

        commitTransaction(fragmentTransaction);

    }

    private void commitTransaction(FragmentTransaction fragmentTransaction){
        if(!mFragmentManager.isStateSaved()) {
            fragmentTransaction.commit();
        } else {
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void showCategories() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        CategoryFragment fragment = CategoryFragment.newInstance(this, gameControllerPresenter);
        setSlideInOutTransition(fragment);
        fragmentTransaction.replace(R.id.fragmentcontainer, fragment);
      //  if(addToBackstack){
            fragmentTransaction.addToBackStack(null);
        //}
        commitTransaction(fragmentTransaction);
    }

    @Override
    public void showQuestionFragment(QuestionAnswers questionAnswers, boolean addToBackstack){
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        QuestionFragment fragment = QuestionFragment.newInstance(questionAnswers, gameControllerPresenter);
        setSlideInOutTransition(fragment);
        fragmentTransaction.replace(R.id.fragmentcontainer, fragment);
        if(addToBackstack){
            fragmentTransaction.addToBackStack(null);
        }
        commitTransaction(fragmentTransaction);
    }

    private void setSlideInOutTransition(Fragment fragment){
        fragment.setEnterTransition(slide);
        setSlideOutTransition(fragment);
    }

    private void setSlideOutTransition(Fragment fragment){
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
}
