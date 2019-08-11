package se.pederjonsson.apps.quizkids.fragments.Question;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.pederjonsson.apps.quizkids.components.room.RoomDBUtil;
import se.pederjonsson.apps.quizkids.components.room.categorypoints.CategoryPointsEntity;
import se.pederjonsson.apps.quizkids.components.room.profile.ProfileEntity;
import se.pederjonsson.apps.quizkids.components.room.question.QuestionEntity;
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract;
import se.pederjonsson.apps.quizkids.Objects.CategoryItem;
import se.pederjonsson.apps.quizkids.Objects.Question;
import se.pederjonsson.apps.quizkids.components.NavbarView;

public class QuestionGameController implements GameControllerContract.QuestionPresenter {

    private GameControllerContract.QuestionActivityView questionActivityView;

    private List<QuestionEntity> currentCategoryQAList;
    private ProfileEntity playingProfile;
    private Question.Category currentCategory;
    private CategoryItem currentCategoryItem;
    int currentQuestionInCategory = 0;
    private List<Boolean> currentAnswers;

    public static int GAMETYPE_JOURNEY = 1;
    public static int GAMETYPE_QUICK = 2;
    public static int MAX_QUESTIONS_IN_CATEGORY = 10;
    public static int MAX_QUESTIONS_TOTAL = 20;
    private NavbarView navbarView;
    RoomDBUtil dbUtil;

    public QuestionGameController(GameControllerContract.QuestionActivityView _questionActivityView, NavbarView _navbar) {
        questionActivityView = _questionActivityView;
        navbarView = _navbar;
        dbUtil = new RoomDBUtil();
    }

    @Override
    public void answered(Boolean val) {
        currentAnswers.add(val);
        navbarView.setScore(val, currentQuestionInCategory);
    }

    @Override
    public void nextQuestion() {

        if (currentCategory == Question.Category.QUICKPLAY) {
            Log.i("QGC", "yes it is quickplay");
            if (currentQuestionInCategory == MAX_QUESTIONS_TOTAL - 1) {
                countAndShowResult();
            } else {
                currentQuestionInCategory++;
                questionActivityView.showQuestionFragment(currentCategoryQAList.get(currentQuestionInCategory), false);
            }
        } else {
            if (currentQuestionInCategory == MAX_QUESTIONS_IN_CATEGORY - 1) {
                countAndShowResult();
            } else {
                currentQuestionInCategory++;
                questionActivityView.showQuestionFragment(currentCategoryQAList.get(currentQuestionInCategory), false);
            }
        }
    }

    private void countAndShowResult() {
        boolean allcorrect = true;
        int correctCounter = 0;
        for (Boolean answerCorrect : currentAnswers) {
            if (!answerCorrect) {
                allcorrect = false;
            } else {
                correctCounter++;
            }
        }

        navbarView.clearScore();
        hideMainNavbar();
        addPointsOnCategory(currentCategory, correctCounter);
        questionActivityView.showResultView(currentCategoryItem, playingProfile, correctCounter, allcorrect);
    }

    @Override
    public void hideMainNavbar() {
        navbarView.show(false);
    }

    @Override
    public ProfileEntity getPlayingProfile() {
        return playingProfile;
    }

    @Override
    public void setPlayingProfile(ProfileEntity profile) {
        playingProfile = profile;
    }

    @Override
    public void addPointsOnCategory(Question.Category category, Integer points) {
        if(playingProfile.getPointsForCategory(category.getCategory()) < points ){
            Log.i("ROOM", "addPointsOnCategory from qcontroller " + points + " for " + category);
            dbUtil.insertCategoryPoints(questionActivityView.getViewContext(),questionActivityView, new CategoryPointsEntity(category.getCategory(), playingProfile.getProfilename(), points));
        } else {
            Log.i("ROOM", "no new record with " + points + " for " + category);
        }
    }

    @Override
    public void startGame(int gametype, ProfileEntity profile) {
        playingProfile = profile;
        if (gametype == GAMETYPE_JOURNEY) {
            //first show view for choose category;
            //pretend choose geo
            // loadQuestionsByCategory(Question.Category.GEOGRAPHY);
            // questionActivityView.showCategories();
        } else if (gametype == GAMETYPE_QUICK) {
            //mixaafrågor sen
            //loadQuestionsByCategory(new CategoryItem(Question.Category.GEOGRAPHY));
        }
    }

    @Override
    public void questionsLoadedByCategory(CategoryItem categoryItem, List<QuestionEntity> questions) {
        currentCategoryItem = categoryItem;
        currentAnswers = new ArrayList<>();
        currentCategory = categoryItem.getCategory();
        currentQuestionInCategory = 0;
        currentCategoryQAList = questions;
        Collections.shuffle(currentCategoryQAList);
        if (currentCategoryQAList != null) {
            Log.i("QGC", "currentCategoryQAList size " + currentCategoryQAList.size());

            QuestionEntity questionEntity = currentCategoryQAList.get(currentQuestionInCategory);
            questionActivityView.showQuestionFragment(questionEntity, false);
            //questionHasBeenShownTo(questionAnswers, playingProfile);
            navbarView.showTitle(currentCategory.getCategoryTranslated(questionActivityView.getViewContext()));
            navbarView.show(true);
            navbarView.clearScore();
            if (currentCategory != Question.Category.QUICKPLAY) {
                navbarView.showScore(true);
            } else {
                navbarView.showScore(false);
            }
        } else {
            Toast.makeText(questionActivityView.getViewContext(), "not ready yet", Toast.LENGTH_LONG).show();
        }
    }
}
