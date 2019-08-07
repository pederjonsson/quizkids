package se.pederjonsson.apps.quizkids.interfaces;

import android.content.Context;

import java.util.List;

import se.pederjonsson.apps.quizkids.Objects.Answer;
import se.pederjonsson.apps.quizkids.Objects.CategoryItem;
import se.pederjonsson.apps.quizkids.Objects.Profile;
import se.pederjonsson.apps.quizkids.Objects.Question;
import se.pederjonsson.apps.quizkids.Objects.QuestionAnswers;
import se.pederjonsson.apps.quizkids.components.room.profile.ProfileEntity;

/**
 * Created by Gaming on 2018-04-01.
 */

public interface GameControllerContract {


    interface MainActivityView extends QueryInterface.View {
        Context getViewContext();

        void showCategories();

        void startQuickQuiz();

        void showMenu();

        void showHighscoreList();
    }

    interface QuestionActivityView extends MainActivityView {

        void showQuestionFragment(QuestionAnswers questionAnswers, boolean addToBackstack);

        void showResultView(CategoryItem categoryItem, Profile profile, int amountCorrect, Boolean allCorrect);

        void speekText(String speechString);
    }

    interface MenuPresenter {

        void saveProfile(ProfileEntity profileEntity);

        void startGame(int gametype, ProfileEntity profileEntity);

        List<ProfileEntity> getProfiles();

        void setProfiles(List<ProfileEntity> profiles);

        boolean playerNameIsAvailable();

        ProfileEntity getPlayingProfile();

        void setPlayingProfile(ProfileEntity profileEntity);

        void hideMainNavbar();
    }

    interface QuestionPresenter {

        void nextQuestion();

        void saveProfile(Profile profile);

        List<Profile> getProfiles();

        void loadQuestionsByCategory(CategoryItem category);

        boolean playerNameIsAvailable();

        void addClearedCategory(Question.Category clearedCategory);

        void addPointsOnCategory(Question.Category clearedCategory, Integer points);

        void answered(Boolean val);

        Profile getPlayingProfile();

        void setPlayingProfile(Profile profile);

        void hideMainNavbar();

        void startGame(int gametype, Profile profile);

    }

    interface Interactor {

    }

}
