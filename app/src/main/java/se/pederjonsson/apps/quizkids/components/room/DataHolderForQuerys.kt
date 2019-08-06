package se.pederjonsson.apps.quizkids.components.room

class DataHolderForQuerys {

    var requestType:RequestType ? = null

    enum class RequestType private constructor(val requestType: String) {
        INSERTQUESTIONS("insertquestions"), GETALLQUESTIONS("getallquestions")
    }

    constructor (requestType: RequestType){
        this.requestType = requestType
    }

    var questionEntityList: MutableList<QuestionEntity>? = null

}

