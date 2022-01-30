package com.apska.mymarvelheroes.ui.screens.herolist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.apska.mymarvelheroes.R
import com.apska.mymarvelheroes.data.model.Hero
import com.apska.mymarvelheroes.databinding.FragmentHeroListBinding
import com.apska.mymarvelheroes.utils.Common.Companion.getColumnCount


class HeroListFragment : Fragment() {
    private val TAG = this.javaClass.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        val heroListViewModel = ViewModelProvider(this)[HeroListViewModel::class.java]

        val binding = FragmentHeroListBinding.inflate(inflater)

        binding.lifecycleOwner = this


        binding.heroRecyclerView.layoutManager = GridLayoutManager(this.context, getColumnCount(requireActivity()))

        val heroAdapter = HeroAdapter()
        binding.heroRecyclerView.adapter = heroAdapter

        heroAdapter.onReachEndListener = object : HeroAdapter.OnReachEndListener {
            override fun onReachEnd() {
                Log.i(TAG,"ReachEnd: Конец списка.")

                heroListViewModel.fetchHeroList()
            }
        }

        heroAdapter.onItemClickListener = object : HeroAdapter.OnItemClickListener {
            override fun onClick(hero: Hero) {
                heroListViewModel.displayHeroDetails(hero)
            }
        }

        heroListViewModel.heroes.observe(viewLifecycleOwner) {
            Log.d(TAG, "onCreateView: submitList")

            heroAdapter.submitMyList(it)
        }

        heroListViewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                HeroApiStatus.LOADING -> {
                    binding.statusBar.statusView.visibility = View.VISIBLE
                    binding.statusBar.imageViewLoading
                        .setImageDrawable(AppCompatResources.getDrawable(requireActivity(), R.drawable.loading_animation))
                    binding.statusBar.textViewLoading.text = getText(R.string.loading)

                }
                HeroApiStatus.ERROR -> {
                    binding.statusBar.statusView.visibility = View.VISIBLE
                    binding.statusBar.imageViewLoading
                        .setImageDrawable(AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_baseline_error_24))
                    binding.statusBar.textViewLoading.text = getText(R.string.loading_error_try_again)
                }
                HeroApiStatus.NO_INTERNET -> {
                    binding.statusBar.statusView.visibility = View.VISIBLE
                    binding.statusBar.imageViewLoading
                        .setImageDrawable(AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_baseline_error_24))
                    binding.statusBar.textViewLoading.text = getText(R.string.no_internet)
                }
                else -> {
                    binding.statusBar.statusView.visibility = View.GONE
                }
            }
        }

        heroListViewModel.navigateToSelectedHero.observe(viewLifecycleOwner) {
            if (it != null) {
                this.findNavController().navigate(HeroListFragmentDirections.actionHeroListFragmentToHeroDetailFragment(it))
                heroListViewModel.displayHeroDetailsComplete()
            }
        }

        return binding.root
    }


}