package com.apska.mymarvelheroes.ui.screens.herodetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import com.apska.mymarvelheroes.R
import com.apska.mymarvelheroes.data.model.Hero
import com.apska.mymarvelheroes.databinding.FragmentHeroDetailBinding
import com.apska.mymarvelheroes.utils.Common.Companion.loadImageToView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.annotations.NotNull

class HeroDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val binding = FragmentHeroDetailBinding.inflate(inflater)

        //val args = HeroDetailFragmentArgs.fromBundle(this.arguments)
        val hero: Hero

        arguments?.let {
            hero = HeroDetailFragmentArgs.fromBundle(it).selectedHero

            binding.textViewHeroName.text = hero.name
            binding.textViewHeroDescr.text = hero.description

            loadImageToView(binding.heroImageView, hero.thumbnail.imageUrl)

            binding.executePendingBindings()
        }

        return binding.root
    }
}