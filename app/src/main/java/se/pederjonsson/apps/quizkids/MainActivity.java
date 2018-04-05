package se.pederjonsson.apps.quizkids;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.pederjonsson.apps.quizkids.Objects.Question;
import se.pederjonsson.apps.quizkids.Objects.QuestionAnswers;
import se.pederjonsson.apps.quizkids.db.Database;
import se.pederjonsson.apps.quizkids.fragments.MenuFragment;
import se.pederjonsson.apps.quizkids.fragments.QuestionFragment;

public class MainActivity extends AppCompatActivity implements GameControllerContract.MainActivityView {

    private static int SLIDE_TIME = 300;
    private Unbinder unbinder;
    private GameControllerContract.Presenter gameControllerPresenter;
    Database db;
    List<QuestionAnswers> qaList;
    private FragmentManager mFragmentManager;

    Slide slide = new Slide(Gravity.RIGHT);
    Slide slideout = new Slide(Gravity.LEFT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);
        db = new Database(this);
        gameControllerPresenter = new GameController(this, db);
        mFragmentManager = getSupportFragmentManager();
        slide.setDuration(SLIDE_TIME);
        slideout.setDuration(SLIDE_TIME);
        showMenu();

        db.populate();

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
    public void startQuizJourney(Question.Category category) {
        qaList = db.getQuestionsByCategory(Question.Category.GEOGRAPHY);
        QuestionAnswers questionAnswers = qaList.get(0);
        showQuestionFragment(questionAnswers, true);
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





}
