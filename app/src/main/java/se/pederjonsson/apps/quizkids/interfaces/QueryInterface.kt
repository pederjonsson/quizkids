package se.pederjonsson.apps.quizkids.interfaces

import se.pederjonsson.apps.quizkids.components.room.DataHolderForQuerys
import se.pederjonsson.apps.quizkids.components.room.RoomQueryAsyncTasks

class QueryInterface {

    interface View {
        fun onStartTask(task: RoomQueryAsyncTasks.RoomQuery)
        fun onSuccess(dataHolder:DataHolderForQuerys?)
        fun onFail(dataHolder:DataHolderForQuerys?)
        fun onProgress(vararg values: Void?)
    }
}
