package com.kathir.newsreader.adapter

import android.content.Context
import com.kathir.newsreader.entity.Newsdb
import androidx.recyclerview.widget.RecyclerView
import com.kathir.newsreader.adapter.NewsAdapter.MyviewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.kathir.newsreader.R
import android.widget.TextView

/**
 * Created by KATHIR on 12-01-2022.
 */
class NewsAdapter(private var list: List<Newsdb>) : RecyclerView.Adapter<MyviewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        context = parent.context
        return MyviewHolder(view)
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        holder.title.text = list[position].desc
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById<View>(R.id.textview) as TextView
    }

    fun filterList(filteredList: List<Newsdb>) {
        list = filteredList
        notifyDataSetChanged()
    }

}