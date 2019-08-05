package se.pederjonsson.apps.quizkids.fragments.Question;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract;
import se.pederjonsson.apps.quizkids.Objects.CategoryItem;
import se.pederjonsson.apps.quizkids.Objects.Profile;
import se.pederjonsson.apps.quizkids.Objects.Question;
import se.pederjonsson.apps.quizkids.Objects.QuestionAnswers;
import se.pederjonsson.apps.quizkids.components.NavbarView;
import se.pederjonsson.apps.quizkids.db.Database;

public class QuestionGameController implements GameControllerContract.QuestionPresenter {

    private GameControllerContract.QuestionActivityView questionActivityView;
    private Database database;
    private List<QuestionAnswers> currentCategoryQAList;
    private Profile playingProfile;
    private Question.Category currentCategory;
    private CategoryItem currentCategoryItem;
    int currentQuestionInCategory = 0;
    private List<Boolean> currentAnswers;

    public static int GAMETYPE_JOURNEY = 1;
    public static int GAMETYPE_QUICK = 2;
    public static int MAX_QUESTIONS_IN_CATEGORY = 10;
    public static int MAX_QUESTIONS_TOTAL = 20;
    private NavbarView navbarView;

    public QuestionGameController(GameControllerContract.QuestionActivityView _questionActivityView, Database _database, NavbarView _navbar) {
        questionActivityView = _questionActivityView;
        navbarView = _navbar;
        database = _database;
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

    private void countAndShowResult(){
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
        if (allcorrect) {
            addClearedCategory(currentCategory);
        } else {
            addPointsOnCategory(currentCategory, correctCounter);
        }
        questionActivityView.showResultView(currentCategoryItem, playingProfile, correctCounter, allcorrect);
    }

    @Override
    public void hideMainNavbar() {
        navbarView.show(false);
    }

    @Override
    public Profile getPlayingProfile() {
        return playingProfile;
    }

    @Override
    public void setPlayingProfile(Profile profile) {
        playingProfile = profile;
    }

    @Override
    public void saveProfile(Profile profile) {
        database.insertProfile(profile.getName(), profile);
    }

    @Override
    public void addClearedCategory(Question.Category clearedCategory) {
        playingProfile.addClearedCategory(clearedCategory);
        saveProfile(playingProfile);
    }

    @Override
    public void addPointsOnCategory(Question.Category category, Integer points) {
        playingProfile.setPointsOnCategory(category, points);
        saveProfile(playingProfile);
    }

    @Override
    public void startGame(int gametype, Profile profile) {
        playingProfile = profile;
        if (gametype == GAMETYPE_JOURNEY) {
            //first show view for choose category;
            //pretend choose geo
            // loadQuestionsByCategory(Question.Category.GEOGRAPHY);
            // questionActivityView.showCategories();
        } else if (gametype == GAMETYPE_QUICK) {
            //mixaafrågor sen
            loadQuestionsByCategory(new CategoryItem(Question.Category.GEOGRAPHY));
        }
    }

    @Override
    public List<Profile> getProfiles() {
        return database.getAllProfiles();
    }

    @Override
    public boolean playerNameIsAvailable() {
        return false;
    }

    @Override
    public void loadQuestionsByCategory(CategoryItem categoryItem) {
        currentCategoryItem = categoryItem;
        currentAnswers = new ArrayList<>();
        currentCategory = categoryItem.getCategory();
        currentQuestionInCategory = 0;
        currentCategoryQAList = database.getQuestionsByCategory(categoryItem.getCategory());
        Collections.shuffle(currentCategoryQAList);
        if (currentCategoryQAList != null) {
            Log.i("QGC", "currentCategoryQAList size " + currentCategoryQAList.size());

            QuestionAnswers questionAnswers = currentCategoryQAList.get(currentQuestionInCategory);
            questionActivityView.showQuestionFragment(questionAnswers, false);
            questionHasBeenShownTo(questionAnswers, playingProfile);
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

    private void questionHasBeenShownTo(QuestionAnswers qa, Profile profile) {
        qa.setHasBeenShownTo(profile.getName());
        updateOrSaveQA(qa);
    }

    private void updateOrSaveQA(QuestionAnswers qa) {
        database.insertQa(qa.getQuestion().getCategoryString(), qa.getQuestion().getDifficultyLevel(), qa.getQuestion().getQuestionResId(), qa);
    }
}
