package com.apska.mymarvelheroes.ui.screens.herodetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apska.mymarvelheroes.data.model.Hero
import com.apska.mymarvelheroes.databinding.FragmentHeroDetailBinding
import com.apska.mymarvelheroes.utils.Common.Companion.loadImageToView

class HeroDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        val binding = FragmentHeroDetailBinding.inflate(inflater)

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