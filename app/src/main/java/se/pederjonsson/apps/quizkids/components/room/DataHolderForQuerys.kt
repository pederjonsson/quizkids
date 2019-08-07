package se.pederjonsson.apps.quizkids.components.room

import se.pederjonsson.apps.quizkids.components.room.profile.ProfileEntity
import se.pederjonsson.apps.quizkids.components.room.question.QuestionEntity

class DataHolderForQuerys {

    var requestType:RequestType ? = null

    var errorMsg = ""
    var profile : ProfileEntity? = null
    var profileEntityList: MutableList<ProfileEntity>? = null

    var questionEntityList: MutableList<QuestionEntity>? = null

    enum class RequestType private constructor(val requestType: String) {
        INSERTQUESTIONS("insertquestions"), GETALLQUESTIONS("getallquestions"),
        SAVEPROFILE("SAVEPROFILE"), GETALLPROFILES("GETALLPROFILES")
    }

    constructor (requestType: RequestType){
        this.requestType = requestType
    }

}

