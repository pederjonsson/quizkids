package se.pederjonsson.apps.quizkids.interfaces

import se.pederjonsson.apps.quizkids.components.room.RoomQueryAsyncTasks

class QueryInterface {

    interface View {
        fun onStartTask(task: RoomQueryAsyncTasks.RoomQuery)
        fun onSuccess()
        fun onSuccess(any:Any)
        fun onFail()
        fun onProgress(vararg values: Void?)
    }
}
