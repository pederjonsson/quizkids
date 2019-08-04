package se.pederjonsson.apps.quizkids.components;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract;
import se.pederjonsson.apps.quizkids.Objects.CategoryItem;
import se.pederjonsson.apps.quizkids.Objects.Profile;
import se.pederjonsson.apps.quizkids.R;
import se.pederjonsson.apps.quizkids.interfaces.LifecycleInterface;

/**
 * Created by Gaming on 2018-04-01.
 */

public class ResultView extends RelativeLayout implements LifecycleInterface {

    @BindView(R.id.resultstitle)
    TextView title;

    @BindView(R.id.results_subtitle)
    TextView subtitle;

    @BindView(R.id.rootview)
    RelativeLayout rootView;

    @BindView(R.id.resultcategoryview)
    CategoryItemView categoryItemView;

    @BindView(R.id.btnresultcontinue)
    Button btnContinue;

    private CategoryItem categoryItem;
    private Context mContext;
    private Unbinder unbinder;
    private Profile playingProfile;
    private GameControllerContract.QuestionActivityView questionActivityView;
    private TextToSpeech textToSpeech;

    public ResultView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    protected void init(Context context) {
        mContext = context;
        inflate();
    }

    protected void inflate() {
        inflate(mContext, R.layout.resultview, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        unbinder = ButterKnife.bind(this);
    }

    public void setUp(CategoryItem categoryItem, Profile profile, GameControllerContract.QuestionActivityView _questionActivityView) {
        questionActivityView = _questionActivityView;
        categoryItemView.setUp(categoryItem, profile);
        categoryItemView.showTitle();
        showTitle(mContext.getString(R.string.congratulations) + " " + profile.getName() + "!");
        subtitle.setText(mContext.getString(R.string.youclearedcategory));
        btnContinue.setOnClickListener(v -> {
            questionActivityView.showCategories();
        });
        btnContinue.setText(mContext.getString(R.string.continuegame));
    }

    public void setUpNotAllCorrect(CategoryItem categoryItem, Profile profile, GameControllerContract.QuestionActivityView _questionActivityView, int amountCorrect) {
        questionActivityView = _questionActivityView;
        categoryItemView.setUp(categoryItem, profile);
        categoryItemView.showTitle();
        String title = mContext.getString(R.string.nice_work) + " " + profile.getName();
        showTitle(title + "!");
        String amountquestions = amountCorrect + " " + mContext.getString(R.string.questions);
        if(amountCorrect == 1){
            amountquestions = mContext.getString(R.string.one_question);
        }
        String of = mContext.getString(R.string.of);
        String subString = mContext.getString(R.string.you_answered_correctly_on) + " " + amountquestions + " " + of + " " + 10;
        subtitle.setText(subString);
        String speechstring = title + ". " + mContext.getString(R.string.you_answered_correctly_on) + ". " + amountquestions + ". " + of + " " + 10 +
                ". " + mContext.getString(R.string.better_luck_next_time);

        btnContinue.setOnClickListener(v -> {
            questionActivityView.showCategories();
        });
        btnContinue.setText(mContext.getString(R.string.continuegame));

        textToSpeech = new TextToSpeech(_questionActivityView.getViewContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(Locale.getDefault());

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        System.out.println("The Language is not supported!");
                    } else {
                        System.out.println("Language Supported.");
                    }
                    System.out.println("Initialization success.");
                    int speechStatus = textToSpeech.speak(speechstring, TextToSpeech.QUEUE_FLUSH, null, null);

                    if (speechStatus == TextToSpeech.ERROR) {
                        System.out.println("Error in converting Text to Speech!");
                    }
                } else {
                    Toast.makeText(_questionActivityView.getViewContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showTitle(String titlestring) {
        title.setText(titlestring);
    }

    public void show(boolean visible) {
        if (visible) {
            rootView.setVisibility(VISIBLE);
        } else {
            rootView.setVisibility(GONE);
        }
    }

    @Override
    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
    }

    @Override
    public void onResume() {

    }
}
