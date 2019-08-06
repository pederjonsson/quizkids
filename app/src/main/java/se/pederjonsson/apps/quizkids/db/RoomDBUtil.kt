package se.pederjonsson.apps.quizkids.db

import android.content.Context
import se.pederjonsson.apps.quizkids.Objects.Question
import se.pederjonsson.apps.quizkids.R
import se.pederjonsson.apps.quizkids.components.room.DataHolderForQuerys
import se.pederjonsson.apps.quizkids.components.room.QuestionEntity
import se.pederjonsson.apps.quizkids.components.room.QuizDatabase
import se.pederjonsson.apps.quizkids.components.room.RoomQueryAsyncTasks
import se.pederjonsson.apps.quizkids.interfaces.QueryInterface

class RoomDBUtil {

    fun generateQABuildingsRoom(context: Context, queryInterface: QueryInterface.View) {
        val quizDatabase = QuizDatabase.getInstance(context)

        // fetch data and create note object
        val questionEntity = QuestionEntity(context.getString(R.string.q_buildings_paris),
                R.drawable.question_eiffel200, Question.Category.BUILDINGS.category, "Eiffel", "Big Ben", "Falafel")

        var qList = mutableListOf<QuestionEntity>()
        qList.add(questionEntity)

        var dataHolder = DataHolderForQuerys(DataHolderForQuerys.RequestType.INSERTQUESTIONS)
        dataHolder.questionEntityList = qList
        RoomQueryAsyncTasks.RoomQuery(dataHolder, quizDatabase, queryInterface).execute()
    }

    fun getAllQuestions(context: Context, queryInterface: QueryInterface.View) {
        val quizDatabase = QuizDatabase.getInstance(context)
        var dataHolder = DataHolderForQuerys(DataHolderForQuerys.RequestType.GETALLQUESTIONS)
        RoomQueryAsyncTasks.RoomQuery(dataHolder, quizDatabase, queryInterface).execute()
    }
}
