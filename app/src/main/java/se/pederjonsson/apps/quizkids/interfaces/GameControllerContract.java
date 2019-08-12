package se.pederjonsson.apps.quizkids.interfaces;

import android.content.Context;

import java.util.List;

import se.pederjonsson.apps.quizkids.Objects.CategoryItem;
import se.pederjonsson.apps.quizkids.components.room.profile.ProfileEntity;
import se.pederjonsson.apps.quizkids.components.room.question.QuestionEntity;

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

        void showQuestionFragment(QuestionEntity questionEntity, boolean addToBackstack);

        void showResultView(CategoryItem categoryItem, ProfileEntity profileEntity, int amountCorrect, Boolean allCorrect);

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

        void questionsLoadedByCategory(CategoryItem category, List<QuestionEntity> questions);

        void addPointsOnCategory(CategoryItem.Category clearedCategory, Integer points);

        void answered(Boolean val);

        ProfileEntity getPlayingProfile();

        void setPlayingProfile(ProfileEntity profileEntity);

        void hideMainNavbar();

        void startGame(int gametype, ProfileEntity profileEntity);

    }

    interface Interactor {

    }

}
