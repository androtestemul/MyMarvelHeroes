package com.apska.mymarvelheroes.ui.screens.herolist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apska.mymarvelheroes.BuildConfig
import com.apska.mymarvelheroes.data.api.HeroApi
import com.apska.mymarvelheroes.data.model.Hero
import com.apska.mymarvelheroes.utils.Common.Companion.md5
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class HeroListViewModel(): ViewModel() {

    private val TAG = this.javaClass.simpleName

    private val _status = MutableLiveData<HeroApiStatus>()
    val status: LiveData<HeroApiStatus>
        get() = _status

    private val _heroes = MutableLiveData<ArrayList<Hero>>()
    val heroes: LiveData<ArrayList<Hero>>
        get() = _heroes

    var offset = 0L

    private val _navigateToSelectedHero = MutableLiveData<Hero>()
    val navigateToSelectedHero: LiveData<Hero>
        get() = _navigateToSelectedHero

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        fetchHeroList()
    }

    fun fetchHeroList() {
        if (status.value == HeroApiStatus.LOADING) {
            return
        }

        coroutineScope.launch {
            Log.d(TAG, "fetchHeroList: start")
            
            val ts = Date().time
            val hash = md5(ts.toString() + BuildConfig.PRIVATE_API_KEY + HeroApi.PUBLIC_API_KEY)
            val getHeroesDeferred = HeroApi.heroApiService.getHeroesList(offset = offset, ts = ts, hash = hash)
            try {
                _status.value = HeroApiStatus.LOADING

                val heroResponse = getHeroesDeferred.await()

                offset += HeroApi.LIMIT

                _status.value = HeroApiStatus.DONE

                val heroList = _heroes.value ?: ArrayList()

                heroList.addAll(heroResponse.data.results)

                _heroes.value = heroList

            } catch (t:Throwable) {
                _status.value = HeroApiStatus.ERROR
            }
        }
    }

    fun displayHeroDetails(hero: Hero) {
        _navigateToSelectedHero.value = hero
    }

    fun displayHeroDetailsComplete() {
        _navigateToSelectedHero.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}