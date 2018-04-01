package se.pederjonsson.apps.quizkids.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.question_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        Database db = new Database();


        QuestionAnswers questionAnswers = db.getSampleQuestionAnswers();
        tripleBtnAnswers.setUp(questionAnswers.getAnswers());

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }

    @Override
    public Context getViewContext() {
        return this.getContext();
    }

    @Override
    public void publishChosenAnswer(Answer answer) {
        if(answer.isCorrect()){
            System.out.print("** correct answer");
        } else {
            System.out.print("** wrong answer");
        }
    }
}
