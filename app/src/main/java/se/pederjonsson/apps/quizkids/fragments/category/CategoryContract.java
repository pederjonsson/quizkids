package se.pederjonsson.apps.quizkids.fragments.category;

import android.content.Context;

import se.pederjonsson.apps.quizkids.data.CategoryItem;
import se.pederjonsson.apps.quizkids.model.profile.ProfileEntity;
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
