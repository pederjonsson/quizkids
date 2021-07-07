package se.pederjonsson.apps.quizkids.viewcomponents

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.resultview.view.*
import se.pederjonsson.apps.quizkids.R
import se.pederjonsson.apps.quizkids.data.CategoryItem
import se.pederjonsson.apps.quizkids.fragments.Question.QuestionGameController
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract.QuestionActivityView
import se.pederjonsson.apps.quizkids.model.profile.ProfileEntity

/**
 * Created by Gaming on 2018-04-01.
 */
class ResultView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {
    private var mContext: Context? = null
    private var questionActivityView: QuestionActivityView? = null

    init{
        mContext = context
        inflate()
    }

    protected fun inflate() {
        inflate(mContext, R.layout.resultview, this)
    }

    fun setUp(categoryItem: CategoryItem?, profileEntity: ProfileEntity?, _questionActivityView: QuestionActivityView?) {
        questionActivityView = _questionActivityView
        resultcategoryview.setUp(categoryItem, profileEntity)
        resultcategoryview.showTitle()
        showTitle(mContext?.getString(R.string.congratulations) + " " + profileEntity?.profilename + "!");
        results_subtitle.text = mContext?.getString(R.string.youclearedcategory);
        btnresultcontinue.setOnClickListener{
            questionActivityView?.showCategories();
        }
        btnresultcontinue.text = mContext?.getString(R.string.continuegame);
    }

    fun setUpNotAllCorrect(categoryItem: CategoryItem?, profileEntity: ProfileEntity?, _questionActivityView: QuestionActivityView?, amountCorrect: Int) {
        questionActivityView = _questionActivityView
        resultcategoryview.setUp(categoryItem, profileEntity)
        resultcategoryview.showTitle()
        val title = mContext?.getString(R.string.nice_work) + " " + profileEntity?.profilename
        showTitle(title + "!");
        var amountquestions = amountCorrect.toString() + " " + mContext?.getString(R.string.questions);
        if(amountCorrect == 1){
            amountquestions = mContext?.getString(R.string.one_question)!!
        }
        var of = mContext?.getString(R.string.of)
        var subString = mContext?.getString(R.string.you_answered_correctly_on) + " " + amountquestions + " " + of + " " + QuestionGameController.MAX_QUESTIONS_IN_CATEGORY
        results_subtitle.text = subString
        val speechstring = title + ". " + mContext?.getString(R.string.you_answered_correctly_on) + ". " + amountquestions + ". " + of + " " + QuestionGameController.MAX_QUESTIONS_IN_CATEGORY +
                ". " + mContext?.getString(R.string.better_luck_next_time)

        btnresultcontinue.setOnClickListener{
            questionActivityView?.showCategories()
        }
        btnresultcontinue.text = mContext?.getString(R.string.continuegame);

        questionActivityView?.speekText(speechstring);
    }

    fun showTitle(titlestring: String?) {
        resultstitle.text = titlestring;
    }

    fun show(visible: Boolean) {
        if (visible) {
            resultview_rootview.visibility = VISIBLE;
        } else {
            resultview_rootview.visibility = GONE;
        }
    }
}