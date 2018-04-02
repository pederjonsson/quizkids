package se.pederjonsson.apps.quizkids;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.pederjonsson.apps.quizkids.Objects.Question;
import se.pederjonsson.apps.quizkids.Objects.QuestionAnswers;
import se.pederjonsson.apps.quizkids.db.Database;
import se.pederjonsson.apps.quizkids.fragments.QuestionFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnstart)
    public Button btnStart;

    private Unbinder unbinder;
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
        db = new Database();
        mFragmentManager = getSupportFragmentManager();
        slide.setDuration(500);
        slideout.setDuration(500);
        btnStart.setOnClickListener(v -> { startQuiz(); });
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
    }



    private void startQuiz(){



        qaList = db.getQuestionsByCategory(Question.Category.GEOGRAPHY);
        QuestionAnswers questionAnswers = qaList.get(0);

        showQuestionFragment(questionAnswers);
    }

    private void showQuestionFragment(QuestionAnswers questionAnswers){
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        QuestionFragment fragment = QuestionFragment.newInstance(questionAnswers);
        setSlideInOutTransition(fragment);
        fragmentTransaction.replace(R.id.fragmentcontainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setSlideInOutTransition(Fragment fragment){
        fragment.setEnterTransition(slide);
        fragment.setExitTransition(slideout);
        fragment.setAllowEnterTransitionOverlap(false);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void performTransition()
    {
        if (isDestroyed())
        {
            return;
        }
       /* Fragment previousFragment = mFragmentManager.findFragmentById(R.id.fragmentcontainer);
        Slide slide = new Slide(Gravity.RIGHT);
        slide.setDuration(500);
        Slide slideout = new Slide(Gravity.LEFT);
        slideout.setDuration(500);
        previousFragment.setAllowEnterTransitionOverlap(false);
        previousFragment.setExitTransition(slideout);

        Fragment nextFragment = QuestionFragment.newInstance(qaList.get(1));
        nextFragment.setEnterTransition(slide);
        nextFragment.setAllowEnterTransitionOverlap(false);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.hide(previousFragment);
        fragmentTransaction.replace(R.id.fragmentcontainer, nextFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();*/
       showQuestionFragment(qaList.get(1));
    }
}
