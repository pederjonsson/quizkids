package se.pederjonsson.apps.quizkids.model

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
                            try {
                                quizDatabase.questionDao.insert(it)
                                return true
                            } catch (e: Throwable) {
                                e.message?.let { dh.errorMsg = it }
                                return false
                            }
                        } ?: run {
                            return false
                        }
                    }

                    DataHolderForQuerys.RequestType.GETALLQUESTIONS -> {
                        try {
                            dh.questionEntityList = quizDatabase.questionDao.all
                            dh.questionEntityList?.let {
                                return true
                            } ?: run {
                                return false
                            }
                        } catch (e: Exception) {
                            e.message?.let { dh.errorMsg = it }
                            return false
                        }
                    }

                    DataHolderForQuerys.RequestType.GETQUESTIONSBYCATEGORY -> {
                        try {
                            dh.questionEntityList = quizDatabase.questionDao.getQuestionsByCategory(dh.category)
                            dh.questionEntityList?.let {
                                return true
                            } ?: run {
                                return false
                            }
                        } catch (e: Exception) {
                            e.message?.let { dh.errorMsg = it }
                            return false
                        }
                    }

                    DataHolderForQuerys.RequestType.SAVEPROFILE -> {
                        dh.profile?.let {
                            try {
                                quizDatabase.profileDao.insert(dh.profile)
                                return true
                            } catch (e: Exception) {
                                e.message?.let { dh.errorMsg = it }
                                return false
                            }
                        }
                        return false
                    }

                    DataHolderForQuerys.RequestType.GETALLPROFILES -> {
                        try {
                            dh.profileEntityList = quizDatabase.profileDao.all
                            return true
                        } catch (e: Exception) {
                            e.message?.let { dh.errorMsg = it }
                            return false
                        }
                    }

                    DataHolderForQuerys.RequestType.GETPROFILEBYNAME -> {
                        try {
                            dh.profile = quizDatabase.profileDao.getProfileByName(dh.profileid)
                            return true
                        } catch (e: Exception) {
                            e.message?.let { dh.errorMsg = it }
                            return false
                        }
                    }

                    DataHolderForQuerys.RequestType.INSERTCATEGORYPOINTS -> {
                        dh.categoryPointsEntity?.let {
                            try {
                                quizDatabase.categoryPointsDao.insert(dh.categoryPointsEntity)
                                return true
                            } catch (e: Exception) {
                                e.message?.let { dh.errorMsg = it }
                                return false
                            }
                        }
                        return false
                    }

                    DataHolderForQuerys.RequestType.GETCATEGORYPOINTSFORUSER -> {

                        try {
                            dh.categoryPointsEntityList = quizDatabase.categoryPointsDao.getAllByProfile(dh.profileid)
                            return true
                        } catch (e: Exception) {
                            e.message?.let { dh.errorMsg = it }
                            return false
                        }
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
                queryInterface.onFail(dataholder)
            }
        }

        override fun onProgressUpdate(vararg values: Void?) {
            super.onProgressUpdate(*values)
            queryInterface.onProgress(*values);
        }
    }
}


