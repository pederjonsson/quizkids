package se.pederjonsson.apps.quizkids;

import java.util.ArrayList;
import java.util.List;

import se.pederjonsson.apps.quizkids.Objects.Profile;
import se.pederjonsson.apps.quizkids.Objects.Question;
import se.pederjonsson.apps.quizkids.Objects.QuestionAnswers;
import se.pederjonsson.apps.quizkids.db.Database;

public class GameController implements GameControllerContract.Presenter{

    private GameControllerContract.MainActivityView mainActivityView;
    private Database database;
    private List<QuestionAnswers> currentCategoryQAList;
    private Profile playingProfile;
    private Question.Category currentCategory;
    int currentQuestionInCategory = 0;
    private List<Boolean> currentAnswers;

    public static int GAMETYPE_JOURNEY = 1;
    public static int GAMETYPE_QUICK = 2;
    public static int MAX_QUESTIONS_IN_CATEGORY = 9;

    public GameController(GameControllerContract.MainActivityView _mainActivityView, Database _database){
        mainActivityView = _mainActivityView;
        database = _database;

    }

    @Override
    public void answered(Boolean val){
        currentAnswers.add(val);
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
            if(allcorrect){
                addClearedCategory(currentCategory);
            }
            startGame(GAMETYPE_JOURNEY, playingProfile);
        } else {
            currentQuestionInCategory++;
            mainActivityView.showQuestionFragment(currentCategoryQAList.get(currentQuestionInCategory), false);
        }
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
            loadQuestionsByCategory(Question.Category.GEOGRAPHY);
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
    public void loadQuestionsByCategory(Question.Category category){
        currentAnswers = new ArrayList<>();
        currentCategory = category;
        currentQuestionInCategory = 0;
        currentCategoryQAList = database.getQuestionsByCategory(category);
        QuestionAnswers questionAnswers = currentCategoryQAList.get(currentQuestionInCategory);
        mainActivityView.showQuestionFragment(questionAnswers, false);
        questionHasBeenShownTo(questionAnswers, playingProfile);
    }

    private void questionHasBeenShownTo(QuestionAnswers qa, Profile profile){
        qa.setHasBeenShownTo(profile.getName());
        updateOrSaveQA(qa);
    }

    private void updateOrSaveQA(QuestionAnswers qa){
        database.insertQa(qa.getQuestion().getCategoryString(), qa.getQuestion().getDifficultyLevel(), qa.getQuestion().getQuestionResId(), qa);
    }
}
