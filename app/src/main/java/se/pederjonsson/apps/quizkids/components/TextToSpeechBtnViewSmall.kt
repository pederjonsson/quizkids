package se.pederjonsson.apps.quizkids.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import se.pederjonsson.apps.quizkids.R
import se.pederjonsson.apps.quizkids.interfaces.LifecycleInterface

class TextToSpeechBtnViewSmall(context: Context, attrs: AttributeSet) : TextToSpeechBtnView(context, attrs), LifecycleInterface {

    override fun inflate() {
        LayoutInflater.from(context).inflate(R.layout.texttospeechbtn_view_small, this, true)
    }
}
