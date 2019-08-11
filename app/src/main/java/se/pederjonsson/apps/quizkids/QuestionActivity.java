package se.pederjonsson.apps.quizkids;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.pederjonsson.apps.quizkids.Objects.CategoryItem;
import se.pederjonsson.apps.quizkids.components.NavbarView;
import se.pederjonsson.apps.quizkids.components.ResultView;
import se.pederjonsson.apps.quizkids.components.room.DataHolderForQuerys;
import se.pederjonsson.apps.quizkids.components.room.RoomDBUtil;
import se.pederjonsson.apps.quizkids.components.room.RoomQueryAsyncTasks;
import se.pederjonsson.apps.quizkids.components.room.profile.ProfileEntity;
import se.pederjonsson.apps.quizkids.components.room.question.QuestionEntity;
import se.pederjonsson.apps.quizkids.fragments.Question.QuestionFragment;
import se.pederjonsson.apps.quizkids.fragments.Question.QuestionGameController;
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract;

public class QuestionActivity extends AppCompatActivity implements GameControllerContract.QuestionActivityView {

    private static int SLIDE_TIME = 300;
    private Unbinder unbinder;
    private GameControllerContract.QuestionPresenter gameControllerPresenter;
    MediaPlayer mMediaPlayer;

    @BindView(R.id.navbar)
    NavbarView navbarView;

    @BindView(R.id.resultview)
    ResultView resultView;

    private FragmentManager mFragmentManager;
    public static int SHOW_CATEGORIES = 101;
    public static String CATEGORY_ITEM = "CATEGORY_ITEM";
    public static String PROFILE_ITEM = "PROFILE_ITEM";

    CategoryItem categoryItem;
    Slide slide = new Slide(Gravity.RIGHT);
    Slide slideout = new Slide(Gravity.LEFT);
    RoomDBUtil roomDBUtil;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        unbinder = ButterKnife.bind(this);
        resultView.show(false);

        gameControllerPresenter = new QuestionGameController(this, navbarView);
        mFragmentManager = getSupportFragmentManager();
        slide.setDuration(SLIDE_TIME);
        slideout.setDuration(SLIDE_TIME);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            categoryItem = (CategoryItem) extras.get(CATEGORY_ITEM);
            ProfileEntity playingProfile = (ProfileEntity) extras.get(PROFILE_ITEM);
            gameControllerPresenter.setPlayingProfile(playingProfile);
           // gameControllerPresenter.loadQuestionsByCategory(categoryItem);
            roomDBUtil = new RoomDBUtil();
            roomDBUtil.getQuestionsByCategory(this, this, categoryItem.getCategory().getCategory());

        } else {
            finish();
        }
    }

    @Override
    public void showResultView(CategoryItem categoryItem, ProfileEntity profileEntity, int amountCorrect, Boolean allCorrect) {
        if (allCorrect) {
            playSound(R.raw.drumroll);
            resultView.setUp(categoryItem, profileEntity, this);
        } else {
            resultView.setUpNotAllCorrect(categoryItem, profileEntity, this, amountCorrect);
        }
        resultView.show(true);
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
        //not used here
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
        //setResult(SHOW_CATEGORIES); no need because thats the view we came from
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showCategories();
    }

    @Override
    public void showQuestionFragment(QuestionEntity questionEntity, boolean addToBackstack) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        QuestionFragment fragment = QuestionFragment.newInstance(questionEntity, gameControllerPresenter);
        setSlideInOutTransition(fragment);
        fragmentTransaction.replace(R.id.fragmentcontainer, fragment);
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(null);
        }
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
    public void showHighscoreList() {

    }

    @Override
    public void onStartTask(@NotNull RoomQueryAsyncTasks.RoomQuery task) {

    }

    @Override
    public void onProgress(@Nullable Void... values) {

    }

    @Override
    public void onSuccess(@Nullable DataHolderForQuerys dh) {
        if(dh.getRequestType() == DataHolderForQuerys.RequestType.GETQUESTIONSBYCATEGORY){
            Log.i("ROOM","questions by category " + dh.getCategory() + " fetched: " + dh.getQuestionEntityList().size());
            gameControllerPresenter.questionsLoadedByCategory(categoryItem, dh.getQuestionEntityList());
        } else if(dh.getRequestType() == DataHolderForQuerys.RequestType.INSERTCATEGORYPOINTS){
            Log.i("ROOM","inserted categorypoints " + dh.getCategoryPointsEntity().getPoints() + " for " + dh.getCategoryPointsEntity().getCategoryid() + " name: " + dh.getCategoryPointsEntity().getProfileid());
            //dbUtil.getAllCategoryPointsForUser(this, this, "testcreateuser");
           // gameControllerPresenter.getPlayingProfile().
            roomDBUtil.getAllCategoryPointsForUser(this, this, gameControllerPresenter.getPlayingProfile().getProfilename());
        } else  if(dh.getRequestType() == DataHolderForQuerys.RequestType.GETCATEGORYPOINTSFORUSER){
            Log.i("ROOM","categorypoints for user " + dh.getCategoryPointsEntityList()+ " for " + dh.getProfileid());
            if(gameControllerPresenter.getPlayingProfile() != null){
                gameControllerPresenter.getPlayingProfile().setCategoryPointsList(dh.getCategoryPointsEntityList());
            }
        }
    }

    @Override
    public void onFail(@Nullable DataHolderForQuerys dataHolder) {
        
    }

    @Override
    public void speekText(String speechString) {

        if(textToSpeech != null){
            int speechStatus = textToSpeech.speak(speechString, TextToSpeech.QUEUE_FLUSH, null, null);
            if (speechStatus == TextToSpeech.ERROR) {
                Log.i("TTS", "Error in converting Text to Speech!");
            } else {
                Log.i("TTS", "should speak now");
            }
        }
    }

    private void setupTextToSpeech(){

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.getDefault());
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.i("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported");
                    }
                    Log.i("TTS", "Initialization success.");
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (textToSpeech == null) {
            setupTextToSpeech();
        }
    }
}
