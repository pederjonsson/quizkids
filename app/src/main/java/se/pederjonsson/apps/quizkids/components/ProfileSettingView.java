package se.pederjonsson.apps.quizkids.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.pederjonsson.apps.quizkids.GameController;
import se.pederjonsson.apps.quizkids.GameControllerContract;
import se.pederjonsson.apps.quizkids.Objects.Answer;
import se.pederjonsson.apps.quizkids.Objects.Profile;
import se.pederjonsson.apps.quizkids.R;
import se.pederjonsson.apps.quizkids.fragments.QuestionAnswerContract;

/**
 * Created by Gaming on 2018-04-01.
 */

public class ProfileSettingView extends LinearLayout {

    @BindView(R.id.nametitle)
    TextView nameTitle;

    @BindView(R.id.edittextname)
    EditText editTextName;

    @BindView(R.id.chooseplayercontainer)
    LinearLayout choosePlayerContainer;

    @BindView(R.id.enter_new_player_container)
    LinearLayout enterNewPlayerContainer;

    @BindView(R.id.startgamebtn)
    Button startGameButton;

    private Context mContext;
    private Unbinder unbinder;
    private List<Profile> profiles;
    private GameControllerContract.Presenter gameControllerPresenter;

    public ProfileSettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    protected void init(Context context) {
        mContext = context;
        inflate();
    }

    protected void inflate() {
        inflate(mContext, R.layout.profilesettingsview, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        unbinder = ButterKnife.bind(this);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
    }

    public void setUp(GameControllerContract.Presenter _gameControllerPresenter) {
        gameControllerPresenter = _gameControllerPresenter;
        profiles = gameControllerPresenter.getProfiles();

    }

    public void startJourneyBtnClicked(){
        if(profiles != null && profiles.size() > 0){
            showChoosePlayerOrCreateNew();
        } else {
            showEnterName();
        }

        setupListener();
    }

    private boolean shownChoosePlayerContainer = false;
    private void showChoosePlayerOrCreateNew(){
        if(shownChoosePlayerContainer){
            choosePlayerContainer.removeAllViews();
            shownChoosePlayerContainer = false;

        } else {
            LayoutInflater factory = LayoutInflater.from(mContext);
            for (Profile profile:profiles) {
                View view = factory.inflate(R.layout.playerlistitem, choosePlayerContainer, false);
                ((TextView) view.findViewById(R.id.playername)).setText(profile.getName());
                choosePlayerContainer.addView(view);
            }

            View view = factory.inflate(R.layout.playerlistitem, choosePlayerContainer, false);
            ((TextView) view.findViewById(R.id.playername)).setText("create new player");
            choosePlayerContainer.addView(view);
            choosePlayerContainer.setVisibility(VISIBLE);
            enterNewPlayerContainer.setVisibility(GONE);
            shownChoosePlayerContainer = true;
        }


    }

    private void showEnterName(){
        enterNewPlayerContainer.setVisibility(VISIBLE);
        choosePlayerContainer.setVisibility(GONE);
    }

    private void setupListener() {
        //edittextlistener here
        startGameButton.setOnClickListener(v -> {

            if(editTextName.getText() == null || editTextName.getText().toString().isEmpty()){
                Toast.makeText(mContext, R.string.toast_pls_enter_name, Toast.LENGTH_SHORT);
            } else {
                String nameEntered = editTextName.getText().toString();
                Profile newPlayer = new Profile(nameEntered);
                gameControllerPresenter.saveProfile(newPlayer);
                gameControllerPresenter.startGame(GameController.GAMETYPE_JOURNEY, newPlayer);
            }
        });
    }

    public void hideEditText(){
        editTextName.setVisibility(GONE);
    }
}
