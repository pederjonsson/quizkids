package se.pederjonsson.apps.quizkids.viewcomponents;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
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
import se.pederjonsson.apps.quizkids.MenuGameController;
import se.pederjonsson.apps.quizkids.model.profile.ProfileEntity;
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract;
import se.pederjonsson.apps.quizkids.R;

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
    private List<ProfileEntity> profiles;
    private GameControllerContract.MenuPresenter gameControllerMenuPresenter;
    private boolean journeyChosen;

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

    public void setUp(GameControllerContract.MenuPresenter _gameControllerMenuPresenter) {
        gameControllerMenuPresenter = _gameControllerMenuPresenter;
        loadProfilesList();

    }

    private void loadProfilesList(){
        profiles = gameControllerMenuPresenter.getProfiles();

    }

    public void startJourneyBtnClicked() {
        profiles = gameControllerMenuPresenter.getProfiles();
        journeyChosen = true;
        if (profiles != null && profiles.size() > 0) {
            showChoosePlayerOrCreateNew();
        } else {
            if(shownChoosePlayerContainer){
                cleanChoosePlayerContainer();
            } else {
                showEnterName();
            }

        }

        setupListener();
        Log.i("PSV", "HIDE QUICKBTN");
    }

    public void startQuickPlayBtnClicked() {
        profiles = gameControllerMenuPresenter.getProfiles();
        journeyChosen = false;
        if (profiles != null && profiles.size() > 0) {
            showChoosePlayerOrCreateNew();
        } else {
            if(shownChoosePlayerContainer){
                cleanChoosePlayerContainer();
            } else {
                showEnterName();
            }
        }

        setupListener();
        Log.i("PSV", "HIDE JOURNEYBTN");
    }

    public void cleanChoosePlayerContainer() {
        choosePlayerContainer.removeAllViews();
        shownChoosePlayerContainer = false;
        choosePlayerContainer.setVisibility(GONE);
        enterNewPlayerContainer.setVisibility(GONE);

        Log.i("PSV", "SHOW ALL BTNS");

    }

    private boolean shownChoosePlayerContainer = false;

    private void showChoosePlayerOrCreateNew() {
        if (shownChoosePlayerContainer) {
            cleanChoosePlayerContainer();
        } else {
            LayoutInflater factory = LayoutInflater.from(mContext);
            for (ProfileEntity profile : profiles) {
                if (profile != null && profile.getProfilename() != null) {
                    View view = factory.inflate(R.layout.playerlistitem, choosePlayerContainer, false);
                    Button mBtn = ((Button) view.findViewById(R.id.btnplayername));
                    String name = "";

                    name = profile.getProfilename();

                    mBtn.setText(mContext.getString(R.string.playas) + " " + name + " >");
                    mBtn.setTag(profile);
                    mBtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ProfileEntity p = (ProfileEntity) v.getTag();
                            if(journeyChosen){
                                gameControllerMenuPresenter.startGame(MenuGameController.GAMETYPE_JOURNEY, p);
                            } else {
                                gameControllerMenuPresenter.startGame(MenuGameController.GAMETYPE_QUICK, p);
                            }
                            cleanChoosePlayerContainer();
                        }
                    });
                    choosePlayerContainer.addView(view);
                }
            }

            View view = factory.inflate(R.layout.playerlistitem, choosePlayerContainer, false);

            Button mBtn = view.findViewById(R.id.btnplayername);
            mBtn.setText(mContext.getString(R.string.create_new_player) + " >");
            mBtn.setTag(new ProfileEntity("newplayer"));
            mBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProfileEntity p = (ProfileEntity) v.getTag();
                    if (p.getProfilename().equals("newplayer")) {
                        //show enter name view
                        cleanChoosePlayerContainer();
                        showEnterName();
                    } else {
                        if(journeyChosen){
                            gameControllerMenuPresenter.startGame(MenuGameController.GAMETYPE_JOURNEY, p);
                        } else {
                            gameControllerMenuPresenter.startGame(MenuGameController.GAMETYPE_QUICK, p);
                        }
                        cleanChoosePlayerContainer();
                    }
                }
            });
            choosePlayerContainer.addView(view);
            choosePlayerContainer.setVisibility(VISIBLE);
            enterNewPlayerContainer.setVisibility(GONE);
            shownChoosePlayerContainer = true;
        }


    }

    private void showEnterName() {
        enterNewPlayerContainer.setVisibility(VISIBLE);
        choosePlayerContainer.setVisibility(GONE);
        shownChoosePlayerContainer = true;
    }

    private void setupListener() {
        //edittextlistener here
        startGameButton.setOnClickListener(v -> {
            profiles = gameControllerMenuPresenter.getProfiles();
            if (editTextName.getText() == null || editTextName.getText().toString().isEmpty()) {
                Toast.makeText(mContext, R.string.toast_pls_enter_name, Toast.LENGTH_SHORT).show();
            } else {
                String nameEntered = editTextName.getText().toString();
                //checkPlayerNameAvailable();
                ProfileEntity newPlayer = new ProfileEntity(nameEntered);
                gameControllerMenuPresenter.saveProfile(newPlayer);
                loadProfilesList();
                if(journeyChosen){
                    gameControllerMenuPresenter.startGame(MenuGameController.GAMETYPE_JOURNEY, newPlayer);
                } else {
                    gameControllerMenuPresenter.startGame(MenuGameController.GAMETYPE_QUICK, newPlayer);
                }
            }
        });
    }


    public void hideEditText() {
        editTextName.setVisibility(GONE);
    }
}
