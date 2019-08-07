package se.pederjonsson.apps.quizkids.fragments.category;

import android.content.Context;

import se.pederjonsson.apps.quizkids.Objects.Answer;
import se.pederjonsson.apps.quizkids.Objects.CategoryItem;
import se.pederjonsson.apps.quizkids.Objects.Profile;
import se.pederjonsson.apps.quizkids.components.room.profile.ProfileEntity;
import se.pederjonsson.apps.quizkids.interfaces.QueryInterface;

/**
 * Created by Gaming on 2018-04-01.
 */

public interface CategoryContract {

    interface View extends QueryInterface.View {
        Context getViewContext();
        void categoryClicked(CategoryItem categoryItem);
        ProfileEntity getCurrentProfile();
    }
}
