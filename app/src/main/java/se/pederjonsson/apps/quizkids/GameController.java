package se.pederjonsson.apps.quizkids;

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

    public static int GAMETYPE_JOURNEY = 1;
    public static int GAMETYPE_QUICK = 2;

    public GameController(GameControllerContract.MainActivityView _mainActivityView, Database _database){
        mainActivityView = _mainActivityView;
        database = _database;

    }

    @Override
    public void nextQuestion() {
        mainActivityView.showQuestionFragment(currentCategoryQAList.get(1), false);
    }

    @Override
    public void saveProfile(Profile profile) {
        database.insertProfile(profile.getName(), profile);
    }

    @Override
    public void startGame(int gametype, Profile profile) {
        playingProfile = profile;
        if(gametype == GAMETYPE_JOURNEY){
            //first show view for choose category;
            //pretend choose geo
            loadQuestionsByCategory(Question.Category.GEOGRAPHY);
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

    private void loadQuestionsByCategory(Question.Category category){
        currentCategoryQAList = database.getQuestionsByCategory(category);
        QuestionAnswers questionAnswers = currentCategoryQAList.get(0);
        mainActivityView.showQuestionFragment(questionAnswers, true);
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
