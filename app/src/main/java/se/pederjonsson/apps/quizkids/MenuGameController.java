package se.pederjonsson.apps.quizkids;

import java.util.List;

import se.pederjonsson.apps.quizkids.Objects.Profile;
import se.pederjonsson.apps.quizkids.components.NavbarView;
import se.pederjonsson.apps.quizkids.db.Database;

public class MenuGameController implements GameControllerContract.MenuPresenter {

    private GameControllerContract.MainActivityView mainActivityView;
    private Database database;
    private Profile playingProfile;
    public static int GAMETYPE_JOURNEY = 1;
    public static int GAMETYPE_QUICK = 2;
    private NavbarView navbarView;

    public MenuGameController(GameControllerContract.MainActivityView _mainActivityView, Database _database, NavbarView _navbar){
        mainActivityView = _mainActivityView;
        navbarView = _navbar;
        database = _database;
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
    public void startGame(int gametype, Profile profile) {
        playingProfile = profile;
        if(gametype == GAMETYPE_JOURNEY){
            //first show view for choose category;
            //pretend choose geo
           // loadQuestionsByCategory(Question.Category.GEOGRAPHY);
            mainActivityView.showCategories();
        } else if(gametype == GAMETYPE_QUICK){
            //mixaafrågor sen
           // loadQuestionsByCategory(new CategoryItem(Question.Category.GEOGRAPHY));
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
}
