package se.pederjonsson.apps.quizkids.fragments.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import se.pederjonsson.apps.quizkids.R
import se.pederjonsson.apps.quizkids.data.CategoryItem
import se.pederjonsson.apps.quizkids.viewcomponents.CategoryItemView
import kotlinx.android.synthetic.main.category_listitem.*

class CategoryAdapter(categoryItems: List<CategoryItem>?, private val mView: CategoryContract.View) : BaseAdapter() {
    private val layoutinflater: LayoutInflater = mView.viewContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var mCategoryItems: List<CategoryItem>?

    override fun getItemId(position: Int): Long {
        return mCategoryItems!![position].hashCode().toLong()
    }

    override fun getCount(): Int {
        return if (mCategoryItems == null) {
            0
        } else mCategoryItems!!.size
    }

    override fun getItem(position: Int): Any {
        return mCategoryItems!![position]
    }

    fun setData(categoryItems: List<CategoryItem>?) {
        mCategoryItems = categoryItems
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val listViewHolder: ViewHolder
        if (convertView == null) {
            convertView = layoutinflater.inflate(R.layout.category_listitem, parent, false)
            listViewHolder = ViewHolder(convertView)
            val categoryItem = getItem(position) as CategoryItem
            listViewHolder.setItem(categoryItem)
            convertView.tag = listViewHolder
        }
        return convertView!!
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var mCategoryItem: CategoryItem? = null
        private var mmView:View?

        init {
            mmView = view
        }

        // @BindView(R.id.categoryitemview)
        //CategoryItemView categoryItemView;
        fun setItem(categoryItem: CategoryItem?) {
            mCategoryItem = categoryItem
            val categoryitemview = mmView?.findViewById<CategoryItemView>(R.id.categoryitemview)
            categoryitemview?.setOnClickListener { mView.categoryClicked(mCategoryItem)}
            categoryitemview?.setUp(mCategoryItem, mView.currentProfile)
            categoryitemview?.showTitle()
        }
    }

    init {
        mCategoryItems = categoryItems
    }
}