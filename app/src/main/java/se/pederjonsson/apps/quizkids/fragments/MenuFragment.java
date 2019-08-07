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

import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract;
import se.pederjonsson.apps.quizkids.R;
import se.pederjonsson.apps.quizkids.components.ProfileSettingView;

public class MenuFragment extends android.support.v4.app.Fragment {

    private Unbinder unbinder;
    public GameControllerContract.MainActivityView mainActivityView;
    public GameControllerContract.MenuPresenter gameControllerMenuPresenter;

    @BindView(R.id.btnstart)
    Button btnStart;

    @BindView(R.id.btnjourney)
    Button btnJourney;

    @BindView(R.id.profilesettingsview)
    ProfileSettingView profileSettingView;

    @BindView(R.id.highscore)
    Button btnHighscore;


    boolean journeyvisible = true;
    boolean quickvisible = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menufragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        profileSettingView.setUp(gameControllerMenuPresenter);
        btnJourney.setOnClickListener(v -> {
            profileSettingView.startJourneyBtnClicked();
            if(quickvisible){
                btnStart.setVisibility(View.GONE);
                quickvisible = false;
            } else {
                btnStart.setVisibility(View.VISIBLE);
                quickvisible = true;
            }
        });
        btnStart.setOnClickListener(v -> {
            profileSettingView.startQuickPlayBtnClicked();
            if(journeyvisible){
                btnJourney.setVisibility(View.GONE);
                journeyvisible = false;
            } else {
                btnJourney.setVisibility(View.VISIBLE);
                journeyvisible = true;
            }
        });

        btnHighscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityView.showHighscoreList();
            }
        });

        return view;
    }

    private void showQuickBtn(){
        btnStart.setVisibility(View.VISIBLE);
        quickvisible = true;
    }

    private void showJourneykBtn(){
        btnJourney.setVisibility(View.VISIBLE);
        journeyvisible = true;
    }

    public static MenuFragment newInstance(GameControllerContract.MainActivityView _mainActivityView, GameControllerContract.MenuPresenter _gameControllerP) {
        MenuFragment menuFragment = new MenuFragment();
        menuFragment.mainActivityView = _mainActivityView;
        menuFragment.gameControllerMenuPresenter = _gameControllerP;
        return menuFragment;
    }

    boolean hasresumedonce = false;
    @Override
    public void onResume() {
        super.onResume();
        gameControllerMenuPresenter.hideMainNavbar();
        if(hasresumedonce){
            showQuickBtn();
            showJourneykBtn();
            profileSettingView.cleanChoosePlayerContainer();
        }
        hasresumedonce = true;

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
