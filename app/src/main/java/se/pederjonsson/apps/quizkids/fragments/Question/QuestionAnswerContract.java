package se.pederjonsson.apps.quizkids.fragments.Question;

import android.content.Context;

import se.pederjonsson.apps.quizkids.Objects.Answer;

/**
 * Created by Gaming on 2018-04-01.
 */

public interface QuestionAnswerContract {


        interface MainView {
            Context getViewContext();
            void publishChosenAnswer(Answer answer);
        }
        interface Presenter {

        }
        interface Interactor {

        }

}
