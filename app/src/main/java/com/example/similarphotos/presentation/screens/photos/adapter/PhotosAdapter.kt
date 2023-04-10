package com.example.similarphotos.presentation.screens.photos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.similarphotos.R
import com.example.similarphotos.domain.setImage
import com.example.similarphotos.presentation.PhotoItem
import com.google.android.material.card.MaterialCardView

class PhotosAdapter : ListAdapter<PhotoItem, PhotosVH>(PhotoItemDU()) {

    private var list: List<PhotoItem> = ArrayList()
    private var listener: PhotosVH.Listener? = null

    fun setData(list: List<PhotoItem>) {
        this.list = list
        submitList(this.list)
    }

    fun setListener(listener: PhotosVH.Listener){
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosVH {
        return PhotosVH.create(parent)
    }

    override fun onBindViewHolder(holder: PhotosVH, position: Int) {
        return holder.bind(list[position], listener)
    }
}

class PhotosVH(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(item: PhotoItem, listener: Listener?) {
        val photo = view.findViewById<ImageView>(R.id.photo)
        val checkBox = view.findViewById<ImageView>(R.id.checkbox)
        val photoChecker = view.findViewById<View>(R.id.photo_checker)
        val photosBorderCv = view.findViewById<MaterialCardView>(R.id.photos_border_cv)

        if (item.isCheck) {
            checkBox.visibility = VISIBLE
            photoChecker.visibility = VISIBLE
            photosBorderCv.strokeColor = view.context.getColor(R.color.strokeBlue)
        } else {
            checkBox.visibility = GONE
            photoChecker.visibility = GONE
            photosBorderCv.strokeColor = view.context.getColor(R.color.strokeDark)
        }

        view.rootView.setOnClickListener {
            listener?.onCLick(item)
        }
        photo.setImage(item.Uri)
    }

    interface Listener{
        fun onCLick(item: PhotoItem)
    }

    companion object {
        fun create(parentView: ViewGroup): PhotosVH {
            return PhotosVH(
                LayoutInflater.from(parentView.context)
                    .inflate(R.layout.item_photo, parentView, false)
            )
        }
    }
}

class PhotoItemDU : DiffUtil.ItemCallback<PhotoItem>() {
    override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean =
        oldItem.isCheck == newItem.isCheck

}