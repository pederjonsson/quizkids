package se.pederjonsson.apps.quizkids.viewcomponents

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.profilesettingsview.view.*
import se.pederjonsson.apps.quizkids.MenuGameController
import se.pederjonsson.apps.quizkids.R
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract
import se.pederjonsson.apps.quizkids.model.profile.ProfileEntity

/**
 * Created by Gaming on 2018-04-01.
 */
class ProfileSettingView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private var mContext: Context? = null
    private var profiles: List<ProfileEntity>? = null
    private var gameControllerMenuPresenter: GameControllerContract.MenuPresenter? = null
    private var journeyChosen = false
    private var shownChoosePlayerContainer = false


    init{
        mContext = context
        inflate()
    }

    private fun inflate() {
        inflate(mContext, R.layout.profilesettingsview, this)
    }

    fun setUp(_gameControllerMenuPresenter: GameControllerContract.MenuPresenter?) {
        gameControllerMenuPresenter = _gameControllerMenuPresenter
        loadProfilesList()
    }

    private fun loadProfilesList() {
        profiles = gameControllerMenuPresenter!!.profiles
    }

    fun startJourneyBtnClicked() {
        profiles = gameControllerMenuPresenter!!.profiles
        journeyChosen = true
        if (profiles != null && profiles!!.size > 0) {
            showChoosePlayerOrCreateNew()
        } else {
            if (shownChoosePlayerContainer) {
                cleanChoosePlayerContainer()
            } else {
                showEnterName()
            }
        }
        setupListener()
        Log.i("PSV", "HIDE QUICKBTN")
    }

    fun startQuickPlayBtnClicked() {
        profiles = gameControllerMenuPresenter!!.profiles
        journeyChosen = false
        if (profiles != null && profiles!!.isNotEmpty()) {
            showChoosePlayerOrCreateNew()
        } else {
            if (shownChoosePlayerContainer) {
                cleanChoosePlayerContainer()
            } else {
                showEnterName()
            }
        }
        setupListener()
        Log.i("PSV", "HIDE JOURNEYBTN")
    }

    fun cleanChoosePlayerContainer() {
        chooseplayercontainer.removeAllViews();
        shownChoosePlayerContainer = false
        chooseplayercontainer.visibility = GONE;
        enter_new_player_container.visibility = GONE;
        Log.i("PSV", "SHOW ALL BTNS")
    }

    private fun showChoosePlayerOrCreateNew() {
        if (shownChoosePlayerContainer) {
            cleanChoosePlayerContainer()
        } else {
            val factory = LayoutInflater.from(mContext)
            for (profile in profiles!!) {
                if (profile.profilename != null) {
                    val view: View? = factory.inflate(R.layout.playerlistitem, chooseplayercontainer, false)
                    val btnPlayerName = view?.findViewById<View>(R.id.btnplayername)
                    var name = ""
                    name = profile.profilename
                    (btnPlayerName as Button)?.text = mContext!!.getString(R.string.playas) + " " + name + " >"
                    btnPlayerName.tag = profile
                    btnPlayerName.setOnClickListener { v ->
                        val p = v.tag as ProfileEntity
                        if (journeyChosen) {
                            gameControllerMenuPresenter!!.startGame(MenuGameController.GAMETYPE_JOURNEY, p)
                        } else {
                            gameControllerMenuPresenter!!.startGame(MenuGameController.GAMETYPE_QUICK, p)
                        }
                        cleanChoosePlayerContainer()
                    }
                    chooseplayercontainer.addView(view);
                }
            }
            val view: View? = factory.inflate(R.layout.playerlistitem, chooseplayercontainer, false)
            val btnPlayerName = view?.findViewById<View>(R.id.btnplayername)
            (btnPlayerName as Button).text = mContext!!.getString(R.string.create_new_player) + " >"
            btnPlayerName.tag = ProfileEntity("newplayer")
            btnPlayerName.setOnClickListener { v ->
                val p = v.tag as ProfileEntity
                if (p.profilename == "newplayer") {
                    //show enter name view
                    cleanChoosePlayerContainer()
                    showEnterName()
                } else {
                    if (journeyChosen) {
                        gameControllerMenuPresenter!!.startGame(MenuGameController.GAMETYPE_JOURNEY, p)
                    } else {
                        gameControllerMenuPresenter!!.startGame(MenuGameController.GAMETYPE_QUICK, p)
                    }
                    cleanChoosePlayerContainer()
                }
            }
            chooseplayercontainer.addView(view);
            chooseplayercontainer.visibility = VISIBLE;
            enter_new_player_container.visibility = GONE;
            shownChoosePlayerContainer = true
        }
    }

    private fun showEnterName() {
        enter_new_player_container.visibility = VISIBLE;
        chooseplayercontainer.visibility = GONE;
        shownChoosePlayerContainer = true
    }

    private fun setupListener() {
        //edittextlistener here
        startgamebtn.setOnClickListener {
            profiles = gameControllerMenuPresenter?.profiles;
            if (edittextname.text == null || edittextname.text.toString().isEmpty()) {
                Toast.makeText(mContext, R.string.toast_pls_enter_name, Toast.LENGTH_SHORT).show();
            } else {
                val nameEntered = edittextname.text.toString();
                //checkPlayerNameAvailable();
                val newPlayer = ProfileEntity(nameEntered);
                gameControllerMenuPresenter?.saveProfile(newPlayer);
                loadProfilesList();
                if(journeyChosen){
                    gameControllerMenuPresenter?.startGame(MenuGameController.GAMETYPE_JOURNEY, newPlayer);
                } else {
                    gameControllerMenuPresenter?.startGame(MenuGameController.GAMETYPE_QUICK, newPlayer);
                }
            }
        }
    }

    fun hideEditText() {
        edittextname.visibility = GONE;
    }
}