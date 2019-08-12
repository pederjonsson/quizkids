package se.pederjonsson.apps.quizkids.model

import android.content.Context
import se.pederjonsson.apps.quizkids.data.CategoryItem
import se.pederjonsson.apps.quizkids.R
import se.pederjonsson.apps.quizkids.model.question.QuestionEntity
import se.pederjonsson.apps.quizkids.model.categorypoints.CategoryPointsEntity
import se.pederjonsson.apps.quizkids.model.profile.ProfileEntity
import se.pederjonsson.apps.quizkids.interfaces.QueryInterface
import java.util.ArrayList


class RoomDBUtil {

    /********************************* QUESTIONS *********************************/

    data class Ddbquestion(val arrayid: Int, val drawableid: Int, val context: Context, val category: String = CategoryItem.Category.BUILDINGS.category) {
        var stringArray: Array<String> = context.getResources().getStringArray(arrayid)
        var q: String = stringArray.get(0)
        var a1: String = stringArray.get(1)
        var a2: String = stringArray.get(2)
        var a3: String = stringArray.get(3)
    }

    fun generateQABuildings(c: Context): ArrayList<QuestionEntity> {
        val qaList = ArrayList<QuestionEntity>()
        val dbArr = arrayOf(
                Ddbquestion(R.array.q_buildings_paris_strings, R.drawable.question_eiffel200, c),
                Ddbquestion(R.array.q_buildings_mounteverest_strings, R.drawable.question_mnteverest, c),
                Ddbquestion(R.array.q_buildings_burjikhalifa_strings, R.drawable.q_burjikhalifa, c),
                Ddbquestion(R.array.q_buildings_stbasil_strings, R.drawable.q_stbasilscathedral, c),
                Ddbquestion(R.array.q_buildings_sphinx_strings, R.drawable.q_sphinx, c),
                Ddbquestion(R.array.q_buildings_chinesewall_strings, R.drawable.q_chinesewall, c),
                Ddbquestion(R.array.q_buildings_bigben_strings, R.drawable.q_big_ben, c),
                Ddbquestion(R.array.q_buildings_colosseum_strings, R.drawable.q_colosseum, c),
                Ddbquestion(R.array.q_buildings_angkorwat_strings, R.drawable.q_angkor_wat, c),
                Ddbquestion(R.array.q_buildings_liberty_strings, R.drawable.q_liberty, c)
        )
        for (d in dbArr){
            qaList.add(QuestionEntity(d.q, d.drawableid, d.category, d.a1, d.a2, d.a3))
        }
        return qaList
    }

    fun populateDbWithQuestions(context: Context, queryInterface: QueryInterface.View, questions: MutableList<QuestionEntity>) {
        val quizDatabase = QuizDatabase.getInstance(context)
        var dataHolder = DataHolderForQuerys(DataHolderForQuerys.RequestType.INSERTQUESTIONS)
        dataHolder.questionEntityList = questions
        RoomQueryAsyncTasks.RoomQuery(dataHolder, quizDatabase, queryInterface).execute()
    }

    fun getAllQuestions(context: Context, queryInterface: QueryInterface.View) {
        val quizDatabase = QuizDatabase.getInstance(context)
        var dataHolder = DataHolderForQuerys(DataHolderForQuerys.RequestType.GETALLQUESTIONS)
        RoomQueryAsyncTasks.RoomQuery(dataHolder, quizDatabase, queryInterface).execute()
    }

    fun getQuestionsByCategory(context: Context, queryInterface: QueryInterface.View, category: String) {
        val quizDatabase = QuizDatabase.getInstance(context)
        var dataHolder = DataHolderForQuerys(DataHolderForQuerys.RequestType.GETQUESTIONSBYCATEGORY)
        dataHolder.category = category
        RoomQueryAsyncTasks.RoomQuery(dataHolder, quizDatabase, queryInterface).execute()
    }

    /********************************* PROFILE *********************************/

    fun saveProfile(context: Context, queryInterface: QueryInterface.View, profile: ProfileEntity) {
        val quizDatabase = QuizDatabase.getInstance(context)

        // fetch data and create note object
        var dataHolder = DataHolderForQuerys(DataHolderForQuerys.RequestType.SAVEPROFILE)
        dataHolder.profile = profile
        RoomQueryAsyncTasks.RoomQuery(dataHolder, quizDatabase, queryInterface).execute()
    }

    fun getAllProfiles(context: Context, queryInterface: QueryInterface.View) {
        val quizDatabase = QuizDatabase.getInstance(context)

        // fetch data and create note object

        var dataHolder = DataHolderForQuerys(DataHolderForQuerys.RequestType.GETALLPROFILES)

        RoomQueryAsyncTasks.RoomQuery(dataHolder, quizDatabase, queryInterface).execute()
    }

    fun getProfileByName(context: Context, queryInterface: QueryInterface.View, profileId: String) {
        val quizDatabase = QuizDatabase.getInstance(context)
        var dataHolder = DataHolderForQuerys(DataHolderForQuerys.RequestType.GETPROFILEBYNAME)
        dataHolder.profileid = profileId
        RoomQueryAsyncTasks.RoomQuery(dataHolder, quizDatabase, queryInterface).execute()
    }

    /********************************* CATEGORIES *********************************/


    /********************************* CATEGORYPOINTS *********************************/

    fun insertCategoryPoints(context: Context, queryInterface: QueryInterface.View, categoryPointsEntity: CategoryPointsEntity) {
        val quizDatabase = QuizDatabase.getInstance(context)
        var dataHolder = DataHolderForQuerys(DataHolderForQuerys.RequestType.INSERTCATEGORYPOINTS)
        dataHolder.categoryPointsEntity = categoryPointsEntity
        RoomQueryAsyncTasks.RoomQuery(dataHolder, quizDatabase, queryInterface).execute()
    }

    fun getAllCategoryPointsForUser(context: Context, queryInterface: QueryInterface.View, profileId: String) {
        val quizDatabase = QuizDatabase.getInstance(context)
        var dataHolder = DataHolderForQuerys(DataHolderForQuerys.RequestType.GETCATEGORYPOINTSFORUSER)
        dataHolder.profileid = profileId
        RoomQueryAsyncTasks.RoomQuery(dataHolder, quizDatabase, queryInterface).execute()
    }
}
