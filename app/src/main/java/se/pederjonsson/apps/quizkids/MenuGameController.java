package se.pederjonsson.apps.quizkids;

import android.util.Log;

import java.util.List;

import se.pederjonsson.apps.quizkids.components.NavbarView;
import se.pederjonsson.apps.quizkids.components.room.RoomDBUtil;
import se.pederjonsson.apps.quizkids.components.room.profile.ProfileEntity;
import se.pederjonsson.apps.quizkids.interfaces.GameControllerContract;

public class MenuGameController implements GameControllerContract.MenuPresenter {

    private GameControllerContract.MainActivityView mainActivityView;
    private RoomDBUtil dbUtil;
    private ProfileEntity playingProfile;
    public static int GAMETYPE_JOURNEY = 1;
    public static int GAMETYPE_QUICK = 2;
    private NavbarView navbarView;
    private List<ProfileEntity> profiles;

    public MenuGameController(GameControllerContract.MainActivityView _mainActivityView, NavbarView _navbar){
        mainActivityView = _mainActivityView;
        navbarView = _navbar;
        dbUtil = new RoomDBUtil();
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
    public void setPlayingProfile(ProfileEntity profileEntity) {
        playingProfile = profileEntity;
        Log.i("categorypoints", "do i have points in setPlayingProfile? " + profileEntity.getCategoryPointsList());

    }

    @Override
    public void saveProfile(ProfileEntity profileEntity) {
        dbUtil.saveProfile(mainActivityView.getViewContext(), mainActivityView, profileEntity);
    }

    @Override
    public void startGame(int gametype, ProfileEntity profileEntity) {
        playingProfile = profileEntity;
        if(gametype == GAMETYPE_JOURNEY){
            //first show view for choose category;
            //pretend choose geo
           // loadQuestionsByCategory(Question.Category.GEOGRAPHY);
            mainActivityView.showCategories();
        } else if(gametype == GAMETYPE_QUICK){
            //mixaafrågor sen
           // loadQuestionsByCategory(new CategoryItem(Question.Category.GEOGRAPHY));
            mainActivityView.startQuickQuiz();
        }
    }

    @Override
    public List<ProfileEntity> getProfiles() {
        dbUtil.getAllProfiles(mainActivityView.getViewContext(), mainActivityView);
        return profiles;
    }

    @Override
    public void setProfiles(List<ProfileEntity> profiles) {
        this.profiles = profiles;
    }

    @Override
    public boolean playerNameIsAvailable() {
        return false;
    }
}
