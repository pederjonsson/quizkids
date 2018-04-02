package se.pederjonsson.apps.quizkids.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.pederjonsson.apps.quizkids.GameControllerContract;
import se.pederjonsson.apps.quizkids.Objects.Question;
import se.pederjonsson.apps.quizkids.R;

public class MenuFragment extends android.support.v4.app.Fragment {

    private Unbinder unbinder;
    public GameControllerContract.MainActivityView mainActivityView;

    @BindView(R.id.btnstart)
    Button btnStart;

    @BindView(R.id.btnjourney)
    Button btnJourney;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menufragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        btnStart.setOnClickListener(v -> { mainActivityView.startQuickQuiz();});
        btnJourney.setOnClickListener(v -> { mainActivityView.startQuizJourney(Question.Category.GEOGRAPHY);});
        return view;
    }

    public static MenuFragment newInstance(GameControllerContract.MainActivityView _mainActivityView) {
        MenuFragment menuFragment = new MenuFragment();
        menuFragment.mainActivityView = _mainActivityView;
        return menuFragment;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
