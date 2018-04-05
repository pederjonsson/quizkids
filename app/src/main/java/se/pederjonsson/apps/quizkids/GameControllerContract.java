package se.pederjonsson.apps.quizkids;

import android.content.Context;

import java.util.List;

import se.pederjonsson.apps.quizkids.Objects.Answer;
import se.pederjonsson.apps.quizkids.Objects.Profile;
import se.pederjonsson.apps.quizkids.Objects.Question;
import se.pederjonsson.apps.quizkids.Objects.QuestionAnswers;

/**
 * Created by Gaming on 2018-04-01.
 */

public interface GameControllerContract {


        interface MainActivityView {
            Context getViewContext();
            void startQuizJourney(Question.Category category);
            void startQuickQuiz();
            void showQuestionFragment(QuestionAnswers questionAnswers,boolean addToBackstack);
            void showMenu();
        }
        interface Presenter {
            void nextQuestion();
            void saveProfile(Profile profile);
            void startGame(int gametype, Profile profile);
            List<Profile> getProfiles();
        }
        interface Interactor {

        }

}
