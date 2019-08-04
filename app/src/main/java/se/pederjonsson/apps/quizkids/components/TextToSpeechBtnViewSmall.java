package se.pederjonsson.apps.quizkids.components;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.pederjonsson.apps.quizkids.R;
import se.pederjonsson.apps.quizkids.fragments.Question.QuestionAnswerContract;
import se.pederjonsson.apps.quizkids.interfaces.LifecycleInterface;

/**
 * Created by Gaming on 2018-04-01.
 */

public class TextToSpeechBtnViewSmall extends TextToSpeechBtnView implements LifecycleInterface {



    public TextToSpeechBtnViewSmall(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void init(Context context) {
        super.init(context);
    }

    @Override
    protected void inflate() {
        inflate(mContext, R.layout.texttospeechbtn_view_small, this);
    }

}
