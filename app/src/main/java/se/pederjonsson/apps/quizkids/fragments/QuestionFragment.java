package se.pederjonsson.apps.quizkids.fragments;

import android.app.Fragment;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.pederjonsson.apps.quizkids.Objects.Answer;
import se.pederjonsson.apps.quizkids.Objects.QuestionAnswers;
import se.pederjonsson.apps.quizkids.R;
import se.pederjonsson.apps.quizkids.components.TripleButtonAnswers;
import se.pederjonsson.apps.quizkids.db.Database;

/**
 * Created by Gaming on 2018-04-01.
 */

public class QuestionFragment extends android.support.v4.app.Fragment implements QuestionAnswerContract.MainView {

    private Unbinder unbinder;

    @BindView(R.id.triplebtnanswers)
    public TripleButtonAnswers tripleBtnAnswers;

    @BindView(R.id.text_question)
    public TextView textQuestion;

    MediaPlayer mMediaPlayer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.question_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        Database db = new Database();

        QuestionAnswers questionAnswers = db.getSampleQuestionAnswers();
        textQuestion.setText(questionAnswers.getTextQuestion().getQuestion());

        tripleBtnAnswers.setUp(questionAnswers.getAnswers(), this);

        return view;
    }

    public static android.support.v4.app.Fragment newInstance() {
        QuestionFragment questionFragment = new QuestionFragment();
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
    }

    @Override
    public Context getViewContext() {
        return this.getContext();
    }

    @Override
    public void publishChosenAnswer(Answer answer) {
        if(answer.isCorrect()){
            System.out.print("** correct answer");
            playSound(R.raw.correct);
        } else {
            System.out.print("** wrong answer");
            playSound(R.raw.error);
        }
        tripleBtnAnswers.inactivateButtons();
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
