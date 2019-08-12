package se.pederjonsson.apps.quizkids

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

import kotlinx.android.synthetic.main.activity_highscore.*

class HighscoreActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highscore)

        setupNavBar()
        loadList()

    }

    fun setupNavBar(){
        navbar.show(true)
        navbar.showScore(false)
        navbar.showTitle(getString(R.string.highscore_list))
    }

    fun loadList(){
       /* val profiles = db?.allProfiles
        profiles?.let{

            for (item in it) {
                val profile:Profile = item as Profile
                Log.i("HA", "profile name " + profile.name)
                Log.i("HA", "profile totalpoints " + profile.totalPoints)
            }
        }*/

    }

}
