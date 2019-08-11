package se.pederjonsson.apps.quizkids.Objects

import se.pederjonsson.apps.quizkids.fragments.Question.QuestionGameController
import java.io.Serializable
import java.util.ArrayList

/**
 * Created by Gaming on 2018-04-01.
 */

class Profile(val name: String) : Serializable {

    var categorypoints = mutableMapOf<Question.Category, Int>()
    var totalPoints = 0

    fun setPointsOnCategory(category: Question.Category, points: Int) {
        categorypoints.get(category)?.let {
            if (it < points) {
                categorypoints.put(category, points)
            }
        } ?: run {
            categorypoints.put(category, points)
        }
        setTotalPoints()
    }

    fun setTotalPoints() {
        totalPoints = 0
        for (value: Int in categorypoints.values) {
            totalPoints += value
        }
    }

    fun getPointsByCategory(category: Question.Category): Int? {
        return categorypoints.get(category)
    }


}
