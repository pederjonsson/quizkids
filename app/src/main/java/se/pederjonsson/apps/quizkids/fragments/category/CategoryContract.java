package se.pederjonsson.apps.quizkids.fragments.category;

import android.content.Context;

import se.pederjonsson.apps.quizkids.Objects.Answer;
import se.pederjonsson.apps.quizkids.Objects.CategoryItem;
import se.pederjonsson.apps.quizkids.Objects.Profile;

/**
 * Created by Gaming on 2018-04-01.
 */

public interface CategoryContract {

        interface View {
            Context getViewContext();
            void categoryClicked(CategoryItem categoryItem);
            Profile getCurrentProfile();
        }
        interface Presenter {

        }
        interface Interactor {

        }

}
