package com.example.koinkotlinmvvm.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.koinkotlinmvvm.R
import com.example.koinkotlinmvvm.models.Cat
import kotlinx.android.synthetic.main.item_cat.view.*

class CatAdapter(var catsList: List<Cat>) : RecyclerView.Adapter<CatAdapter.CatViewHolder>() {

    // This is a method that create the viewHolder which will have the card that will contain
    // the imageView which is our component that will appear to the user
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_cat, parent, false)
        return CatViewHolder(view)
    }

    // this function used to know what is the number elements
    override fun getItemCount(): Int = catsList.size

    // This is a method that will bind the data from each element list into its respective place in
    // the recyclerView
    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        // Verify if position exists in list to avoid IndexOutOfBoundException
        if (position != RecyclerView.NO_POSITION) {
            val cat: Cat = catsList[position]
            holder.bind(cat)
        }
    }

    // Update recyclerView's data when we get it from our API
    fun updateData(newCatsList: List<Cat>) {
        catsList = newCatsList
        // tell the recyclerView that it's list has been changed and it needs to be updated
        notifyDataSetChanged()
    }

    // ViewHolder that would have an ImageView that would changed depending on which item needs to be drawn in it
    class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cat: Cat) {
            // Load images into ImageView using Glide library
            Glide.with(itemView.context)
                // .load loads the url from the internet
                .load(cat.imageUrl)
                    // .centerCrop crops the image after fetching it to fit the imageView
                .centerCrop()
                    // .thumbnail allows thumbnail for the image
                .thumbnail()
                // .into tells glide where to put the image
                .into(itemView.itemCatImageView)
        }
    }
}