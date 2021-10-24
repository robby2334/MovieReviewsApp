package com.dev.divig.moviereviewsapp.ui.intro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.divig.moviereviewsapp.R

class IntroAdapter(private val intro: List<IntroData>) :
    RecyclerView.Adapter<IntroAdapter.IntroViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroViewHolder {
        return IntroViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.intro_container,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) {
        holder.bind(intro[position])
    }

    override fun getItemCount(): Int {
        return intro.size
    }

    inner class IntroViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageIntro = view.findViewById<ImageView>(R.id.iv_image_intro)
        private val descIntro = view.findViewById<TextView>(R.id.tv_desc_intro)

        fun bind(intro: IntroData) {
            imageIntro.setImageResource(intro.introDataImage)
            descIntro.text = intro.introDataDesc
        }
    }
}