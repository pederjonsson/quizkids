package se.pederjonsson.apps.quizkids.components.room

import android.os.AsyncTask
import se.pederjonsson.apps.quizkids.interfaces.QueryInterface
import java.lang.Exception

class RoomQueryAsyncTasks {

    class RoomQuery// only retain a weak reference to the activity

    internal constructor(private val dataholder: DataHolderForQuerys?, private val quizDatabase: QuizDatabase, private val queryInterface: QueryInterface.View) : AsyncTask<Void, Void, Boolean>() {



        init {
            queryInterface.onStartTask(this)
        }

        // doInBackground methods runs on a worker thread
        override fun doInBackground(vararg objs: Void): Boolean? {

            dataholder?.let { dh ->
                when (dh.requestType) {
                    DataHolderForQuerys.RequestType.INSERTQUESTIONS -> {
                        dh.questionEntityList?.let {
                            quizDatabase.questionDao.insert(it)
                            return true
                        } ?: run {
                            return false
                        }
                    }
                    DataHolderForQuerys.RequestType.GETALLQUESTIONS -> {
                        dh.questionEntityList = quizDatabase.questionDao.all
                        dh.questionEntityList?.let {
                            return true
                        } ?: run {
                            return false
                        }
                    }
                    DataHolderForQuerys.RequestType.SAVEPROFILE -> {
                        dh.profile?.let {
                            quizDatabase.profileDao.insert(dh.profile)
                            return true
                        }
                        return false
                    }
                    else -> {
                        return false
                    }
                }
            } ?: run { return false }
        }

        // onPostExecute runs on main thread
        override fun onPostExecute(bool: Boolean?) {
            if (bool!!) {
                queryInterface.onSuccess(dataholder)
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


