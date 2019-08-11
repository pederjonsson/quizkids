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
import se.pederjonsson.apps.quizkids.components.TripleButtonAnswers;
import se.pederjonsson.apps.quizkids.components.room.question.QuestionEntity;
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract;
import se.pederjonsson.apps.quizkids.Objects.Answer;
import se.pederjonsson.apps.quizkids.R;
import se.pederjonsson.apps.quizkids.components.QuestionView;

/**
 * Created by Gaming on 2018-04-01.
 */

public class QuestionFragment extends android.support.v4.app.Fragment implements QuestionAnswerContract.MainView {

    public static String QUESTION_DATA = "QUESTION_DATA";
    private Unbinder unbinder;
    private TripleButtonAnswers tripleBtnAnswers;

    @BindView(R.id.questionview)
    QuestionView questionView;

    MediaPlayer mMediaPlayer;
    public GameControllerContract.QuestionPresenter gameControllerPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.question_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        QuestionEntity questionEntity = (QuestionEntity) getArguments().getSerializable(QUESTION_DATA);

        questionView.setUp(questionEntity, this);
        tripleBtnAnswers = view.findViewById(R.id.triplebtnanswers);
        tripleBtnAnswers.setUp(questionEntity, this);

        return view;
    }

    public static QuestionFragment newInstance(QuestionEntity questionEntity, GameControllerContract.QuestionPresenter _gameControllerP) {
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable(QUESTION_DATA, questionEntity);
        questionFragment.setArguments(args);
        questionFragment.gameControllerPresenter = _gameControllerP;
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
        questionView.onPause();
        tripleBtnAnswers.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        questionView.onResume();
        tripleBtnAnswers.onResume();
    }

    @Override
    public Context getViewContext() {
        return this.getContext();
    }

    @Override
    public void publishChosenAnswer(Answer answer) {
        if(!tripleBtnAnswers.getMAnswerBtns().get(0).chosenAnswer){
            tripleBtnAnswers.getMAnswerBtns().get(0).hide();
        }
        if(!tripleBtnAnswers.getMAnswerBtns().get(1).chosenAnswer){
            tripleBtnAnswers.getMAnswerBtns().get(1).hide();
        }
        if(!tripleBtnAnswers.getMAnswerBtns().get(2).chosenAnswer){
            tripleBtnAnswers.getMAnswerBtns().get(2).hide();
        }
        if(answer.isCorrect()){
            System.out.print("** correct answer");
            gameControllerPresenter.answered(true);
            playSound(R.raw.correct);
        } else {
            System.out.print("** wrong answer");
            gameControllerPresenter.answered(false);
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
                        gameControllerPresenter.nextQuestion();
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
