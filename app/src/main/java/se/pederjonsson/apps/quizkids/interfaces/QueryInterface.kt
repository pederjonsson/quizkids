package se.pederjonsson.apps.quizkids.interfaces

import se.pederjonsson.apps.quizkids.model.DataHolderForQuerys
import se.pederjonsson.apps.quizkids.model.RoomQueryAsyncTasks

class QueryInterface {

    interface View {
        fun onStartTask(task: RoomQueryAsyncTasks.RoomQuery)
        fun onSuccess(dataHolder:DataHolderForQuerys?)
        fun onFail(dataHolder:DataHolderForQuerys?)
        fun onProgress(vararg values: Void?)
    }
}
