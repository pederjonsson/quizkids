package se.pederjonsson.apps.quizkids.highscore

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import se.pederjonsson.apps.quizkids.R

class HighscoreAdapter: BaseAdapter {

    private var mData:MutableList<HighscoreActivity.ProfilePoint> = mutableListOf()
    protected var mActivity: Activity? = null


    constructor(activity: Activity, _data: MutableList<HighscoreActivity.ProfilePoint>){
        mActivity = activity
        mData = _data
    }
    /*
    fun KeyValueAdapter(activity: Activity, data: List<Pair<String, String>>) {
        mActivity = activity

        mData = data
        if (mData == null) {
            mData = LinkedList()
        }
    }*/

    fun setData(_data: MutableList<HighscoreActivity.ProfilePoint>) {
        mData = _data
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return mData!!.size
    }

    override fun getItem(position: Int): HighscoreActivity.ProfilePoint? {

        return if (position >= 0 && position < mData!!.size) {
            mData.get(position)
        }
        else {
            null
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = mActivity?.layoutInflater?.inflate(R.layout.highscorelistelement, null)
        }

        val element = mData[position]

        val keyNameView = convertView!!.findViewById<View>(R.id.key) as TextView
        keyNameView.text = element.name

        val valueView = convertView.findViewById<View>(R.id.value) as TextView
        if (element.point != null) {
            valueView.text = element.point.toString()
            valueView.visibility = View.VISIBLE
        } else {
            valueView.text = ""
            valueView.visibility = View.INVISIBLE
        }

        return convertView
    }
}