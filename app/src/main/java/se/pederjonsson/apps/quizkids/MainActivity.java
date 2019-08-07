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
import android.util.Log;
import android.view.Gravity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.pederjonsson.apps.quizkids.Objects.CategoryItem;
import se.pederjonsson.apps.quizkids.Objects.Question;
import se.pederjonsson.apps.quizkids.components.NavbarView;

import se.pederjonsson.apps.quizkids.components.room.DataHolderForQuerys;
import se.pederjonsson.apps.quizkids.components.room.RoomQueryAsyncTasks;
import se.pederjonsson.apps.quizkids.components.room.categorypoints.CategoryPointsEntity;
import se.pederjonsson.apps.quizkids.components.room.profile.ProfileEntity;
import se.pederjonsson.apps.quizkids.db.Database;
import se.pederjonsson.apps.quizkids.components.room.RoomDBUtil;
import se.pederjonsson.apps.quizkids.fragments.MenuFragment;
import se.pederjonsson.apps.quizkids.fragments.category.CategoryFragment;
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract;

public class MainActivity extends AppCompatActivity implements GameControllerContract.MainActivityView {


    public static final String TABLE_NAME_QUESTION = "QUESTIONS";
    public static final String TABLE_NAME_PROFILE = "PROFILES";
    public static final String TABLE_NAME_CATEGORY = "CATEGORIES";
    public static final String TABLE_NAME_CATEGORYPOINTS = "CATEGORYPOINTS";
    public static final String DB_NAME = "QUIZ_DB";

    private static int SLIDE_TIME = 300;
    private Unbinder unbinder;
    private GameControllerContract.MenuPresenter gameControllerMenuPresenter;
  //  Database db;
    MediaPlayer mMediaPlayer;

    @BindView(R.id.navbar)
    NavbarView navbarView;

    private FragmentManager mFragmentManager;
    RoomDBUtil dbUtil;

    Slide slide = new Slide(Gravity.RIGHT);
    Slide slideout = new Slide(Gravity.LEFT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        gameControllerMenuPresenter = new MenuGameController(this, navbarView);
        mFragmentManager = getSupportFragmentManager();
        slide.setDuration(SLIDE_TIME);
        slideout.setDuration(SLIDE_TIME);
        showMenu();

        dbUtil = new RoomDBUtil();
        dbUtil.generateQABuildingsRoom(this, this);
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
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra(QuestionActivity.CATEGORY_ITEM, new CategoryItem(Question.Category.QUICKPLAY));
        intent.putExtra(QuestionActivity.PROFILE_ITEM, gameControllerMenuPresenter.getPlayingProfile());
        startActivityForResult(intent, 1);
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

    @Override
    public void showHighscoreList() {
        Intent intent = new Intent(this, HighscoreActivity.class);
        //intent.putExtra(QuestionActivity.CATEGORY_ITEM, new CategoryItem(Question.Category.QUICKPLAY));
        //intent.putExtra(QuestionActivity.PROFILE_ITEM, gameControllerMenuPresenter.getPlayingProfile());
        startActivity(intent);
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
        if(task != null && !task.isCancelled()){
            task.cancel(true);
            task = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QuestionActivity.SHOW_CATEGORIES) {
            showCategories();
        }
    }

    @Override
    public void onProgress(@Nullable Void... values) {
        Log.i("ROOM", "ONPROGRESS");
    }

    RoomQueryAsyncTasks.RoomQuery task = null;

    @Override
    public void onStartTask(@NotNull RoomQueryAsyncTasks.RoomQuery task) {
        Log.i("ROOM", "onStartTask");
        this.task = task;
    }

    @Override
    public void onSuccess(DataHolderForQuerys dh) {
        if(dh != null){
        Log.i("ROOM", "ONSUCCESS FOR REQUEST " + dh.getRequestType().getRequestType());
            if(dh.getRequestType() == DataHolderForQuerys.RequestType.INSERTQUESTIONS){
                dbUtil.getAllQuestions(this, this);
                dbUtil.saveProfile(this, this, new ProfileEntity("testcreateuser"));
            } else if(dh.getRequestType() == DataHolderForQuerys.RequestType.GETALLQUESTIONS){
                Log.i("ROOM","questions fetched: " + dh.getQuestionEntityList().size());
                dbUtil.getQuestionsByCategory(this, this, Question.Category.BUILDINGS.getCategory());
            } else if(dh.getRequestType() == DataHolderForQuerys.RequestType.SAVEPROFILE){
                Log.i("ROOM","profilesaved for: " + dh.getProfile().getProfilename());
                dbUtil.insertCategoryPoints(this, this, new CategoryPointsEntity(Question.Category.BUILDINGS.getCategory(), "testcreateuser", 5));
                dbUtil.getAllProfiles(this, this);
            } else if(dh.getRequestType() == DataHolderForQuerys.RequestType.GETALLPROFILES){
                Log.i("ROOM","all profiles = " + dh.getProfileEntityList());
                gameControllerMenuPresenter.setProfiles(dh.getProfileEntityList());
            } else if(dh.getRequestType() == DataHolderForQuerys.RequestType.GETQUESTIONSBYCATEGORY){
                Log.i("ROOM","questions by category " + dh.getCategory() + " fetched: " + dh.getQuestionEntityList().size());
            } else if(dh.getRequestType() == DataHolderForQuerys.RequestType.INSERTCATEGORYPOINTS){
                Log.i("ROOM","inserted categorypoints " + dh.getCategoryPointsEntity().getPoints() + " for " + dh.getCategoryPointsEntity().getCategoryid() + " name: " + dh.getCategoryPointsEntity().getProfileid());
                dbUtil.getAllCategoryPointsForUser(this, this, "testcreateuser");
            } else if(dh.getRequestType() == DataHolderForQuerys.RequestType.GETCATEGORYPOINTSFORUSER){
                Log.i("ROOM","categorypoints for user " + dh.getCategoryPointsEntityList()+ " for " + dh.getProfileid());
                if(gameControllerMenuPresenter.getPlayingProfile() != null){
                    gameControllerMenuPresenter.getPlayingProfile().setCategoryPointsList(dh.getCategoryPointsEntityList());
                }
            }
        } else {
            Log.i("ERROR", "No data returned frmo request");
        }
    }

    @Override
    public void onFail(DataHolderForQuerys dh) {
        if(dh != null){
            Log.i("ROOM", "ONFAIL FOR REQUEST " + dh.getRequestType() + " error " + dh.getErrorMsg());
        } else {
            Log.i("ROOM", "ONFAIL");
        }
    }
}
