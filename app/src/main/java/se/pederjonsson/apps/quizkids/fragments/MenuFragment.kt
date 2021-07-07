package se.pederjonsson.apps.quizkids.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.menufragment.*
import se.pederjonsson.apps.quizkids.R
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract.MainActivityView

class MenuFragment : Fragment() {
    var mainActivityView: MainActivityView? = null
    var gameControllerMenuPresenter: GameControllerContract.MenuPresenter? = null

    /* @BindView(R.id.btnstart)
    Button btnStart;

    @BindView(R.id.btnjourney)
    Button btnJourney;

    @BindView(R.id.profilesettingsview)
    ProfileSettingView profileSettingView;

    @BindView(R.id.highscore)
    Button btnHighscore;

    */
    var journeyvisible = true
    var quickvisible = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.menufragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profilesettingsview.setUp(gameControllerMenuPresenter)

        btnjourney.setOnClickListener{
            profilesettingsview.startJourneyBtnClicked();
            if(quickvisible){
                btnstart.visibility = View.GONE
                quickvisible = false;
            } else {
                btnstart.visibility = View.VISIBLE
                quickvisible = true;
            }
        }

        btnstart.setOnClickListener{
            profilesettingsview.startQuickPlayBtnClicked();
            if(journeyvisible){
                btnjourney.visibility = View.GONE
                journeyvisible = false;
            } else {
                btnjourney.visibility = View.VISIBLE
                journeyvisible = true;
            }
        }

        (highscore as Button).setOnClickListener{
            Log.i("*bug", "highscore working")
            mainActivityView?.showHighscoreList()
        }
    }

    private fun showQuickBtn() {
        btnstart.visibility = View.VISIBLE
        quickvisible = true
    }

    private fun showJourneykBtn() {
        btnjourney.visibility = View.VISIBLE
        journeyvisible = true
    }

    var hasresumedonce = false
    override fun onResume() {
        super.onResume()
        gameControllerMenuPresenter!!.hideMainNavbar()
        if (hasresumedonce) {
            showQuickBtn()
            showJourneykBtn()
            profilesettingsview.cleanChoosePlayerContainer();
        }
        hasresumedonce = true
    }

    companion object {
        @JvmStatic
        fun newInstance(_mainActivityView: MainActivityView?, _gameControllerP: GameControllerContract.MenuPresenter?): MenuFragment {
            val menuFragment = MenuFragment()
            menuFragment.mainActivityView = _mainActivityView
            menuFragment.gameControllerMenuPresenter = _gameControllerP
            return menuFragment
        }
    }
}