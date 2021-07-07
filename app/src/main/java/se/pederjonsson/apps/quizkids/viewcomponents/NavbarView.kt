package se.pederjonsson.apps.quizkids.viewcomponents

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.navbar.view.*
import se.pederjonsson.apps.quizkids.R

/**
 * Created by Gaming on 2018-04-01.
 */
class NavbarView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    private var dots:List<RoundedImageView>? = null
    private var mContext: Context? = null
    private var scoreVisible = false

    init{
        mContext = context
        inflate()

    }

    private fun inflate() {
        inflate(mContext, R.layout.navbar, this)
        dots = listOf(dot1, dot2, dot3, dot4, dot5, dot6, dot7, dot8, dot9, dot10)
    }

    fun setUp() {}
    fun showTitle(titlestring: String?) {
        title.text = titlestring;
    }

    fun show(visible: Boolean) {
        if (visible) {
            navbar_rootview.visibility = VISIBLE;
        } else {
            navbar_rootview.visibility = GONE;
        }
    }

    fun showScore(_val: Boolean) {
        scoreVisible = _val
        if (_val) {
            dotscontainer.visibility = VISIBLE;
        } else {
            dotscontainer.visibility = INVISIBLE;
        }
    }

    fun setScore(correct: Boolean, question: Int) {
        dots?.let {
            if (scoreVisible) {
                if (correct) {
                    it[question].setImageDrawable(mContext?.getDrawable(R.drawable.greenrectangle));
                } else {
                    it[question].setImageDrawable(mContext?.getDrawable(R.drawable.redrectangle));
                }
            }
        }
    }

    fun clearScore() {
        dots?.let {
            for (dot in it) {
                dot?.setImageDrawable(mContext?.getDrawable(R.drawable.whiterectangle))
            }
        }
    }

}