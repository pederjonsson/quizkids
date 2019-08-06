package se.pederjonsson.apps.quizkids.components.room

import android.os.AsyncTask
import se.pederjonsson.apps.quizkids.interfaces.QueryInterface
import java.lang.Exception

class RoomQueryAsyncTasks {

    class RoomQuery// only retain a weak reference to the activity

    internal constructor(private val dataholder: DataHolderForQuerys?, private val quizDatabase: QuizDatabase, private val queryInterface: QueryInterface.View) : AsyncTask<Void, Void, Boolean>() {

        var allQuestionsFetched = mutableListOf<QuestionEntity>()

        init {
            queryInterface.onStartTask(this)
        }

        // doInBackground methods runs on a worker thread
        override fun doInBackground(vararg objs: Void): Boolean? {
            if (dataholder != null) {
                if (dataholder.requestType == DataHolderForQuerys.RequestType.INSERTQUESTIONS && dataholder.questionEntityList != null) {
                    try {
                        quizDatabase.questionDao.insert(dataholder.questionEntityList)
                    } catch (e: Exception) {
                        return false
                    }
                    return true
                } else if(dataholder.requestType == DataHolderForQuerys.RequestType.GETALLQUESTIONS){
                    try {
                        allQuestionsFetched = quizDatabase.questionDao.all

                    } catch (e: Exception) {
                        return false
                    }
                    return true
                }
            } else {
                return false
            }
            return false
        }

        // onPostExecute runs on main thread
        override fun onPostExecute(bool: Boolean?) {
            if (bool!!) {
                if(dataholder?.requestType == DataHolderForQuerys.RequestType.GETALLQUESTIONS){
                    queryInterface.onSuccess(allQuestionsFetched)
                } else{
                    queryInterface.onSuccess()
                }
            } else {
                queryInterface.onFail()
            }
        }

        override fun onProgressUpdate(vararg values: Void?) {
            super.onProgressUpdate(*values)
            queryInterface.onProgress(*values);
        }
    }
}


