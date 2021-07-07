package se.pederjonsson.apps.quizkids.ktextensions

import android.animation.*
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.LinearInterpolator

fun View.expandXY(delay:Long = 0, alphaVal:Float = 1f, expandFrom:Float = 0f, duration: Long = 0): Animator {

    val floatArray = floatArrayOf(expandFrom,1f)
    val anim = ObjectAnimator.ofPropertyValuesHolder(
            this,
            PropertyValuesHolder.ofFloat("scaleX",*floatArray),
            PropertyValuesHolder.ofFloat("scaleY",*floatArray),
            PropertyValuesHolder.ofFloat("alpha", alphaVal))
    if(duration > 0){
        anim.duration = duration
    }
    anim.setStartDelay(delay)
    return anim;
}

fun View.collapseXY(collapseVal: Float = 0f, alphaVal: Float = 0f, duration: Long = 0): Animator {

    val floatArray = floatArrayOf(1f,collapseVal)
    val anim = ObjectAnimator.ofPropertyValuesHolder(
            this,
            PropertyValuesHolder.ofFloat("scaleX",*floatArray),
            PropertyValuesHolder.ofFloat("scaleY",*floatArray),
            PropertyValuesHolder.ofFloat("alpha", alphaVal))
    if(duration > 0){
        anim.duration = duration
    }
    return anim
}

fun View.collapseY(collapseVal: Float = 0f, alphaVal: Float = 0f, duration: Long = 0): Animator {

    val floatArray = floatArrayOf(1f,collapseVal)
    val anim = ObjectAnimator.ofPropertyValuesHolder(
            this,
            PropertyValuesHolder.ofFloat("scaleY",*floatArray),
            PropertyValuesHolder.ofFloat("alpha", alphaVal))
    if(duration > 0){
        anim.duration = duration
    }
    return anim
}

fun View.expandY(delay:Long = 0, alphaVal:Float = 1f, expandFrom:Float = 0f, duration: Long = 0): Animator {

    val floatArray = floatArrayOf(expandFrom,1f)
    val anim = ObjectAnimator.ofPropertyValuesHolder(
            this,
            PropertyValuesHolder.ofFloat("scaleY",*floatArray),
            PropertyValuesHolder.ofFloat("alpha", alphaVal))
    if(duration > 0){
        anim.duration = duration
    }
    anim.setStartDelay(delay)
    return anim;
}

fun View.animateAlpha(toAlphaVal: Float = 0f,dur:Long = 130, delay:Long = 0, hide:Boolean = false): Animator {

    val anim = ObjectAnimator.ofPropertyValuesHolder(
            this,
            PropertyValuesHolder.ofFloat("alpha", toAlphaVal))
    anim.setDuration(dur)
    anim.setStartDelay(delay)
    anim.addListener(object : AnimatorListenerAdapter(){
        override fun onAnimationEnd(animation: Animator?) {
            if(hide){
                visibility = View.GONE
            } else {
                visibility = View.VISIBLE
            }
        }
    })
    return anim;
}

fun View.rotate(degrees: Float = 0.0f,dur:Long = 200) {
    animate().rotation(degrees).setDuration(dur)
}

fun View.animateY(ypos: Float = 0.0f, dur:Long = 200) {
    animate().y(ypos).setDuration(dur)
}

fun View.setVisibilityBy(b:Boolean?, useInvisible: Boolean = false){
    val hidden = when (useInvisible) {
        true -> View.INVISIBLE
        else -> View.GONE
    }
    if (b == null)
        visibility = hidden
    else if (b)
        visibility = View.VISIBLE
    else
        visibility = hidden
}

fun View.toggleVisibility(){
    if (visibility == View.GONE || visibility == View.INVISIBLE)
        visibility = View.VISIBLE
    else {
        visibility = View.GONE
    }
}

fun View.animateYWithListener(startingpos: Float = 0.0f, ypos: Float = 0.0f, dur:Long = 200, animatorListener: AnimatorListenerAdapter) {
    //1
    val valueAnimator = ValueAnimator.ofFloat(startingpos, ypos)

//2
    valueAnimator.addUpdateListener {
        // 3
        val value = it.animatedValue as Float
        // 4
        translationY = value
    }

    animatorListener?.let{
        valueAnimator.addListener(animatorListener)
    }

//5
    valueAnimator.interpolator = LinearInterpolator()
    valueAnimator.duration = dur

//6
    valueAnimator.start()
}

/**
 * can be used by any object that extends View
 * removing the listener is handled here so we dont forget
 * it triggers only when the width and height is no longer 0
 * you can use the properties and public methods of the view directly inside the lambda
 */

inline fun <T: View> T.afterMeasured(crossinline f: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f()
            }
        }
    })
}