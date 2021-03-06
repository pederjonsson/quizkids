package se.pederjonsson.apps.quizkids.model

import se.pederjonsson.apps.quizkids.model.categorypoints.CategoryPointsEntity
import se.pederjonsson.apps.quizkids.model.profile.ProfileEntity
import se.pederjonsson.apps.quizkids.model.question.QuestionEntity

class DataHolderForQuerys {

    var requestType:RequestType ? = null

    var errorMsg = ""
    var profile : ProfileEntity? = null
    var profileid = ""
    var profileEntityList: MutableList<ProfileEntity>? = null
    var category = ""
    var categoryid = 0
    var points = 0
    var categoryPointsEntity:CategoryPointsEntity? = null
    var categoryPointsEntityList: MutableList<CategoryPointsEntity>? = null
    var questionEntityList: MutableList<QuestionEntity>? = null

    enum class RequestType private constructor(val requestType: String) {
        INSERTQUESTIONS("insertquestions"),
        GETALLQUESTIONS("getallquestions"),
        GETQUESTIONSBYCATEGORY("getquestionsbycategory"),

        SAVEPROFILE("SAVEPROFILE"),
        GETALLPROFILES("GETALLPROFILES"),
        GETPROFILEBYNAME("GETPROFILEBYNAME"),

        INSERTCATEGORYPOINTS("insertcategorypoints"),
        GETCATEGORYPOINTSFORUSER("GETCATEGORYPOINTSFORUSER")


    }

    constructor (requestType: RequestType){
        this.requestType = requestType
    }

}

