package com.example.similarphotos.presentation.screens.photos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.similarphotos.R
import com.example.similarphotos.presentation.PhotoItem

class RecyclersAdapter: ListAdapter<List<PhotoItem>, RecyclersVH>(RecyclersDU()) {

    private var list: List<List<PhotoItem>> = ArrayList()
    private var rootListener: RecyclersVH.RootListener? = null

    fun setData(list: List<List<PhotoItem>>) {
        this.list = list
        submitList(this.list)
    }

    fun setListener(rootListener: RecyclersVH.RootListener){
        this.rootListener = rootListener
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclersVH {
        return RecyclersVH.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclersVH, position: Int) {
        return holder.bind(list[position], rootListener)
    }
}

class RecyclersVH(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(items: List<PhotoItem>, listener: RootListener?) {
        val photos = view.findViewById<RecyclerView>(R.id.photos_rv)
        val separator = view.findViewById<View>(R.id.separator)

        var photoAdapter = PhotosAdapter()
        photoAdapter.setData(items)
        photoAdapter.setListener(object: PhotosVH.Listener{
            override fun onCLick(item: PhotoItem) {
                for (i in items){
                    if(i.Uri == item.Uri){
                        i.isCheck = !i.isCheck
                        break
                    }
                }
                photoAdapter.notifyDataSetChanged()
                listener?.update(items)
            }

        })

        photos.layoutManager = GridLayoutManager(view.context, 3)
        photos.adapter = photoAdapter

    }

    interface RootListener{
        fun update(list: List<PhotoItem>)
    }

    companion object {
        fun create(parentView: ViewGroup): RecyclersVH {
            return RecyclersVH(
                LayoutInflater.from(parentView.context)
                    .inflate(R.layout.item_recycler_with_photos, parentView, false)
            )
        }
    }
}

class RecyclersDU : DiffUtil.ItemCallback<List<PhotoItem>>(){
    override fun areItemsTheSame(oldItem: List<PhotoItem>, newItem: List<PhotoItem>): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: List<PhotoItem>, newItem: List<PhotoItem>): Boolean {
        for(i in 0..oldItem.lastIndex){
            if(oldItem[i].id != newItem[i].id ||
                oldItem[i].isCheck != newItem[i].isCheck ||
                oldItem[i].Uri != newItem[i].Uri ) return false
        }
        return true
    }



}