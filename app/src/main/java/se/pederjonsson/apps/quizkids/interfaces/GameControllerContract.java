package se.pederjonsson.apps.quizkids.interfaces;

import android.content.Context;

import java.util.List;

import se.pederjonsson.apps.quizkids.Objects.Answer;
import se.pederjonsson.apps.quizkids.Objects.CategoryItem;
import se.pederjonsson.apps.quizkids.Objects.Profile;
import se.pederjonsson.apps.quizkids.Objects.Question;
import se.pederjonsson.apps.quizkids.Objects.QuestionAnswers;

/**
 * Created by Gaming on 2018-04-01.
 */

public interface GameControllerContract {


    interface MainActivityView {
        Context getViewContext();

        void showCategories();

        void startQuickQuiz();

        void showMenu();
    }

    interface QuestionActivityView extends MainActivityView {

        void showQuestionFragment(QuestionAnswers questionAnswers, boolean addToBackstack);

        void showResultView(CategoryItem categoryItem, Profile profile, int amountCorrect, Boolean allCorrect);
    }

    interface MenuPresenter {

        void saveProfile(Profile profile);

        void startGame(int gametype, Profile profile);

        List<Profile> getProfiles();

        boolean playerNameIsAvailable();

        Profile getPlayingProfile();

        void setPlayingProfile(Profile profile);

        void loadPlayingProfile(String name);

        void hideMainNavbar();
    }

    interface QuestionPresenter {

        void nextQuestion();

        void saveProfile(Profile profile);

        List<Profile> getProfiles();

        void loadQuestionsByCategory(CategoryItem category);

        boolean playerNameIsAvailable();

        void addClearedCategory(Question.Category clearedCategory);

        void answered(Boolean val);

        Profile getPlayingProfile();

        void setPlayingProfile(Profile profile);

        void hideMainNavbar();

        void startGame(int gametype, Profile profile);

    }

    interface Interactor {

    }

}
