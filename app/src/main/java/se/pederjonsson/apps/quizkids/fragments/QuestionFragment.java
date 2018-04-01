package se.pederjonsson.apps.quizkids.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.pederjonsson.apps.quizkids.Objects.QuestionAnswers;
import se.pederjonsson.apps.quizkids.R;
import se.pederjonsson.apps.quizkids.components.TripleButtonAnswers;
import se.pederjonsson.apps.quizkids.db.Database;

/**
 * Created by Gaming on 2018-04-01.
 */

public class QuestionFragment extends Fragment {

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
}
