package se.pederjonsson.apps.quizkids.Objects

import se.pederjonsson.apps.quizkids.fragments.Question.QuestionGameController
import java.io.Serializable
import java.util.ArrayList

/**
 * Created by Gaming on 2018-04-01.
 */

class Profile(val name: String) : Serializable {

    private var clearedCategories: MutableList<Question.Category>? = null
    var categorypoints = mutableMapOf<Question.Category,Int>()
    var age: Int? = null

    fun getClearedCategories(): List<Question.Category>? {
        return clearedCategories
    }

    fun addClearedCategory(clearedCategory: Question.Category) {
        if (clearedCategories == null)
            clearedCategories = ArrayList<Question.Category>()
        clearedCategories?.add(clearedCategory)
        setPointsOnCategory(clearedCategory, QuestionGameController.MAX_QUESTIONS_IN_CATEGORY)
    }

    fun setPointsOnCategory(category: Question.Category, points: Int){
        categorypoints.get(category)?.let{
            if(it < points){
                categorypoints.put(category, points)
            }
        }?:run{
            categorypoints.put(category, points)
        }
    }

    fun getPointsByCategory(category: Question.Category) : Int? {
        return categorypoints.get(category)
    }


}
