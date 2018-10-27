package se.pederjonsson.apps.quizkids;

import java.util.ArrayList;
import java.util.List;

import se.pederjonsson.apps.quizkids.Objects.CategoryItem;
import se.pederjonsson.apps.quizkids.Objects.Profile;
import se.pederjonsson.apps.quizkids.Objects.Question;
import se.pederjonsson.apps.quizkids.Objects.QuestionAnswers;
import se.pederjonsson.apps.quizkids.components.NavbarView;
import se.pederjonsson.apps.quizkids.db.Database;

public class GameController implements GameControllerContract.Presenter{

    private GameControllerContract.MainActivityView mainActivityView;
    private Database database;
    private List<QuestionAnswers> currentCategoryQAList;
    private Profile playingProfile;
    private Question.Category currentCategory;
    private CategoryItem currentCategoryItem;
    int currentQuestionInCategory = 0;
    private List<Boolean> currentAnswers;

    public static int GAMETYPE_JOURNEY = 1;
    public static int GAMETYPE_QUICK = 2;
    public static int MAX_QUESTIONS_IN_CATEGORY = 9;
    private NavbarView navbarView;

    public GameController(GameControllerContract.MainActivityView _mainActivityView, Database _database, NavbarView _navbar){
        mainActivityView = _mainActivityView;
        navbarView = _navbar;
        database = _database;
    }

    @Override
    public void answered(Boolean val){
        currentAnswers.add(val);
        navbarView.setScore(val, currentQuestionInCategory);
    }

    @Override
    public void nextQuestion() {
        if(currentQuestionInCategory == MAX_QUESTIONS_IN_CATEGORY){
            boolean allcorrect = true;
            for (Boolean answerCorrect: currentAnswers){
                if(!answerCorrect){
                    allcorrect = false;
                    break;
                }
            }

            navbarView.clearScore();
            hideMainNavbar();
            if(allcorrect){
                addClearedCategory(currentCategory);
                mainActivityView.showResultView(currentCategoryItem, playingProfile);
            }
            startGame(GAMETYPE_JOURNEY, playingProfile);
        } else {
            currentQuestionInCategory++;
            mainActivityView.showQuestionFragment(currentCategoryQAList.get(currentQuestionInCategory), false);
        }
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
    public void saveProfile(Profile profile) {
        database.insertProfile(profile.getName(), profile);
    }

    @Override
    public void addClearedCategory(Question.Category clearedCategory) {
        playingProfile.addClearedCategory(clearedCategory);
        saveProfile(playingProfile);
    }

    @Override
    public void startGame(int gametype, Profile profile) {
        playingProfile = profile;
        if(gametype == GAMETYPE_JOURNEY){
            //first show view for choose category;
            //pretend choose geo
           // loadQuestionsByCategory(Question.Category.GEOGRAPHY);
            mainActivityView.showCategories();
        } else if(gametype == GAMETYPE_QUICK){
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
    public void loadQuestionsByCategory(CategoryItem categoryItem){
        currentCategoryItem = categoryItem;
        currentAnswers = new ArrayList<>();
        currentCategory = categoryItem.getCategory();
        currentQuestionInCategory = 0;
        currentCategoryQAList = database.getQuestionsByCategory(categoryItem.getCategory());
        QuestionAnswers questionAnswers = currentCategoryQAList.get(currentQuestionInCategory);
        mainActivityView.showQuestionFragment(questionAnswers, false);
        questionHasBeenShownTo(questionAnswers, playingProfile);
        navbarView.showTitle(currentCategory.name());
        navbarView.show(true);
        navbarView.clearScore();
        navbarView.showScore();
    }

    private void questionHasBeenShownTo(QuestionAnswers qa, Profile profile){
        qa.setHasBeenShownTo(profile.getName());
        updateOrSaveQA(qa);
    }

    private void updateOrSaveQA(QuestionAnswers qa){
        database.insertQa(qa.getQuestion().getCategoryString(), qa.getQuestion().getDifficultyLevel(), qa.getQuestion().getQuestionResId(), qa);
    }
}
