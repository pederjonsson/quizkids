package se.pederjonsson.apps.quizkids.interfaces;

import android.content.Context;

import java.util.List;

import se.pederjonsson.apps.quizkids.Objects.CategoryItem;
import se.pederjonsson.apps.quizkids.Objects.Profile;
import se.pederjonsson.apps.quizkids.Objects.Question;
import se.pederjonsson.apps.quizkids.Objects.QuestionAnswers;

/**
 * Created by Gaming on 2018-04-01.
 */

public interface LifecycleInterface {
    void onResume();
    void onPause();
}
