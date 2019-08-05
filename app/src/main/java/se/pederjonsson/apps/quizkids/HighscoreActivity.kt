package se.pederjonsson.apps.quizkids

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import kotlinx.android.synthetic.main.activity_highscore.*
import se.pederjonsson.apps.quizkids.Objects.Profile
import se.pederjonsson.apps.quizkids.components.NavbarView
import se.pederjonsson.apps.quizkids.db.Database

class HighscoreActivity : AppCompatActivity() {

    internal var db: Database? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highscore)
        db = Database.getInstance(this)
        setupNavBar()
        loadList()

    }

    fun setupNavBar(){
        navbar.show(true)
        navbar.showScore(false)
        navbar.showTitle(getString(R.string.highscore_list))
    }

    fun loadList(){
        val profiles = db?.allProfiles
        profiles?.let{

            for (item in it) {
                val profile:Profile = item as Profile
                Log.i("HA", "profile name " + profile.name)
                Log.i("HA", "profile totalpoints " + profile.totalPoints)
            }
        }

    }

}
