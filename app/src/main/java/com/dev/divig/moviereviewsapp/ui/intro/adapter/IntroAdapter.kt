package com.dev.divig.moviereviewsapp.ui.intro.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dev.divig.moviereviewsapp.databinding.ItemIntroContainerBinding
import com.dev.divig.moviereviewsapp.ui.intro.model.Intro

class IntroAdapter(private val intro: List<Intro>) :
    RecyclerView.Adapter<IntroAdapter.IntroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroViewHolder {
        val binding =
            ItemIntroContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IntroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) {
        holder.bind(intro[position])
    }

    override fun getItemCount(): Int {
        return intro.size
    }

    inner class IntroViewHolder(private val binding: ItemIntroContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(intro: Intro) {
            binding.ivImageIntro.load(intro.introDataImage)
            binding.tvDescIntro.text = intro.introDataDesc
        }
    }
}