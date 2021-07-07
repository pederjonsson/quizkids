package se.pederjonsson.apps.quizkids.highscore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_highscore.*
import se.pederjonsson.apps.quizkids.R
import se.pederjonsson.apps.quizkids.interfaces.QueryInterface
import se.pederjonsson.apps.quizkids.model.DataHolderForQuerys
import se.pederjonsson.apps.quizkids.model.RoomDBUtil
import se.pederjonsson.apps.quizkids.model.RoomQueryAsyncTasks
import se.pederjonsson.apps.quizkids.model.categorypoints.CategoryPointsEntity
import se.pederjonsson.apps.quizkids.model.profile.ProfileEntity

class HighscoreActivity : AppCompatActivity(), QueryInterface.View {

    private var profileEntityList: MutableList<ProfileEntity>? = null
    //var categoryPointsEntityList: MutableList<CategoryPointsEntity>? = null
    private var profilePointMap:MutableMap<String, Int> = mutableMapOf<String, Int>()
    private var profilePointList:MutableList<ProfilePoint> = mutableListOf()
    private lateinit var dbUtil:RoomDBUtil

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
        dbUtil = RoomDBUtil()
        dbUtil.getAllProfiles(this, this)
       /* val profiles = db?.allProfiles
        profiles?.let{

            for (item in it) {
                val profile:Profile = item as Profile
                Log.i("HA", "profile name " + profile.name)
                Log.i("HA", "profile totalpoints " + profile.totalPoints)
            }
        }*/

    }

    override fun onFail(dataHolder: DataHolderForQuerys?) {
        profileRequestCount ++
        if(profileRequestCount == expectedRequests){
            //udpate UI
            Log.i("highscore", "expectedrequests reached " + expectedRequests)
            Log.i("highscore", "profilePointMap size " + profilePointMap?.size)
            for ((key, value) in profilePointMap) {
                println("$key = $value")
                Log.i("highscore", "$key = $value")
            }
        }
    }

    override fun onProgress(vararg values: Void?) {

    }

    override fun onStartTask(task: RoomQueryAsyncTasks.RoomQuery) {

    }

    var profileRequestCount = 0
    var expectedRequests = 0

    override fun onSuccess(dataHolder: DataHolderForQuerys?) {
        if(dataHolder?.requestType == DataHolderForQuerys.RequestType.GETALLPROFILES){
            profileEntityList = dataHolder.profileEntityList
            profileEntityList?.let{
                expectedRequests = it.size
                for (pr:ProfileEntity in it){
                    dbUtil.getAllCategoryPointsForUser(this, this, pr.profilename)
                }
            }
        } else if(dataHolder?.requestType == DataHolderForQuerys.RequestType.GETCATEGORYPOINTSFORUSER){
            profileRequestCount ++
            dataHolder.categoryPointsEntityList?.let{
                for (cpe:CategoryPointsEntity in it){
                    if(profilePointMap.containsKey(cpe.profileid)){
                        val oldPoints:Int? = profilePointMap.get(cpe.profileid)
                        var newPoints = 0
                        oldPoints?.let{
                            newPoints = oldPoints + cpe.points
                        }
                        profilePointMap.put(cpe.profileid, newPoints)
                    } else {
                        profilePointMap.put(cpe.profileid, cpe.points)
                    }
                }
            }
            if(profileRequestCount == expectedRequests){
                //udpate UI
                Log.i("highscore", "expectedrequests reached " + expectedRequests)

                Log.i("highscore", "profilePointMap size " + profilePointMap.size)

                for ((key, value) in profilePointMap) {
                    println("$key = $value")
                    Log.i("highscore", "$key = $value")
                    profilePointList.add(ProfilePoint(key, value))
                }

                val mAdapter = HighscoreAdapter(this, profilePointList)
                listView.adapter = mAdapter
                mAdapter.notifyDataSetChanged()
                listView.setOnItemClickListener({ adapterView, view, i, l ->
                    val nameclicked = profilePointList.get(i).name
                    Log.i("highscore" , "name clicked $nameclicked")
                })
            }
        }
    }

    data class ProfilePoint(val name: String, val point: Int)



}
