package se.pederjonsson.apps.quizkids.fragments.Question;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.pederjonsson.apps.quizkids.MainActivity;
import se.pederjonsson.apps.quizkids.Objects.Answer;
import se.pederjonsson.apps.quizkids.Objects.QuestionAnswers;
import se.pederjonsson.apps.quizkids.R;
import se.pederjonsson.apps.quizkids.components.QuestionView;
import se.pederjonsson.apps.quizkids.components.TripleButtonAnswers;

/**
 * Created by Gaming on 2018-04-01.
 */

public class QuestionFragment extends android.support.v4.app.Fragment implements QuestionAnswerContract.MainView {

    public static String QUESTION_DATA = "QUESTION_DATA";
    private Unbinder unbinder;

    @BindView(R.id.triplebtnanswers)
    TripleButtonAnswers tripleBtnAnswers;

    @BindView(R.id.questionview)
    QuestionView questionView;

    MediaPlayer mMediaPlayer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.question_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        QuestionAnswers questionAnswers = (QuestionAnswers) getArguments().getSerializable(QUESTION_DATA);

        questionView.setUp(questionAnswers.getQuestion(), this);
        tripleBtnAnswers.setUp(questionAnswers.getAnswers(), this);

        return view;
    }

    public static QuestionFragment newInstance(QuestionAnswers questionAnswers) {
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable(QUESTION_DATA, questionAnswers);
        questionFragment.setArguments(args);
        return questionFragment;
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
        if(stopLoadingAnimationHandler != null && stopLoadingAnimationRunnable != null){
            stopLoadingAnimationHandler.removeCallbacks(stopLoadingAnimationRunnable);
        }
    }

    @Override
    public Context getViewContext() {
        return this.getContext();
    }

    @Override
    public void publishChosenAnswer(Answer answer) {
        if(!tripleBtnAnswers.btnAnswer1.chosenAnswer){
            tripleBtnAnswers.btnAnswer1.hide();
        }
        if(!tripleBtnAnswers.btnAnswer2.chosenAnswer){
            tripleBtnAnswers.btnAnswer2.hide();
        }
        if(!tripleBtnAnswers.btnAnswer3.chosenAnswer){
            tripleBtnAnswers.btnAnswer3.hide();
        }
        if(answer.isCorrect()){
            System.out.print("** correct answer");
            playSound(R.raw.correct);
        } else {
            System.out.print("** wrong answer");
            playSound(R.raw.error);
        }
        tripleBtnAnswers.inactivateButtons();
        loadNewQuestion();

    }

    Runnable stopLoadingAnimationRunnable = new Runnable() {
        @Override
        public void run() {
            if(getActivity() != null){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((MainActivity)getActivity()).performTransition();
                    }
                });
            }
        }
    };

    Handler stopLoadingAnimationHandler = null;

    public void loadNewQuestion() {
        stopLoadingAnimationHandler = new Handler();
        stopLoadingAnimationHandler.postDelayed(stopLoadingAnimationRunnable, 1000);
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
