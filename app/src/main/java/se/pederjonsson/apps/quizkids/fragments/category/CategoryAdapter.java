package se.pederjonsson.apps.quizkids.fragments.category;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.pederjonsson.apps.quizkids.Objects.CategoryItem;
import se.pederjonsson.apps.quizkids.Objects.Question;
import se.pederjonsson.apps.quizkids.R;

public class CategoryAdapter extends BaseAdapter {

    private LayoutInflater layoutinflater;
    private List<CategoryItem> mCategoryItems;
    private CategoryContract.View mView;

    public CategoryAdapter(List<CategoryItem> categoryItems, CategoryContract.View view) {
        mView = view;
        layoutinflater = (LayoutInflater) mView.getViewContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCategoryItems = categoryItems;
    }

    @Override
    public long getItemId(int position) {
        return mCategoryItems.get(position).hashCode();
    }

    @Override
    public int getCount() {
        if (mCategoryItems == null) {
            return 0;
        }
        return mCategoryItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mCategoryItems.get(position);
    }

    public void setData(List<CategoryItem> categoryItems) {
        this.mCategoryItems = categoryItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder listViewHolder;
        if (convertView == null) {
            convertView = layoutinflater.inflate(R.layout.category_listitem, parent, false);
           listViewHolder = new ViewHolder(convertView);
            CategoryItem categoryItem = (CategoryItem) getItem(position);
            listViewHolder.setItem(categoryItem);
            convertView.setTag(listViewHolder);
        }

        return convertView;
    }


  public class ViewHolder extends RecyclerView.ViewHolder {
        private CategoryItem mCategoryItem;

        @BindView(R.id.icon)
        RoundedImageView icon;

        public void setItem(CategoryItem categoryItem) {
            mCategoryItem = categoryItem;
            icon.setOnClickListener(v -> mView.categoryClicked(mCategoryItem));
            setIcon();
        }

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setIcon(){
            switch (mCategoryItem.getCategory().name().toString()){
                case "GEOGRAPHY"  : icon.setImageDrawable(mView.getViewContext().getDrawable(R.drawable.pyramids));
                case "SCIENCE"  : icon.setImageDrawable(mView.getViewContext().getDrawable(R.drawable.pyramids));
                case "MATH"  : icon.setImageDrawable(mView.getViewContext().getDrawable(R.drawable.pyramids));
                case "ABC"  : icon.setImageDrawable(mView.getViewContext().getDrawable(R.drawable.pyramids));
                default:  icon.setImageDrawable(mView.getViewContext().getDrawable(R.drawable.pyramids));
            }
        }
    }


}
