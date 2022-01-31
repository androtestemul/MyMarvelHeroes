package com.apska.mymarvelheroes.ui.screens.herolist

import android.app.Application
import android.content.Context
import android.net.*
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apska.mymarvelheroes.BuildConfig
import com.apska.mymarvelheroes.data.api.HeroApi
import com.apska.mymarvelheroes.data.model.Hero
import com.apska.mymarvelheroes.utils.Common.Companion.md5
import com.apska.mymarvelheroes.utils.NetworkUtils.Companion.isNetworkAvailable
import kotlinx.coroutines.*
import java.util.*

class HeroListViewModel(application: Application): AndroidViewModel(application) {

    private val TAG = this.javaClass.simpleName

    private val _status = MutableLiveData<HeroApiStatus>()
    val status: LiveData<HeroApiStatus>
        get() = _status

    private val _heroes = MutableLiveData<ArrayList<Hero>>()
    val heroes: LiveData<ArrayList<Hero>>
        get() = _heroes

    private var offset = 0L

    private val _navigateToSelectedHero = MutableLiveData<Hero>()
    val navigateToSelectedHero: LiveData<Hero>
        get() = _navigateToSelectedHero

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var needLoadData = true

    init {
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network : Network) {
                    if (needLoadData) {
                        fetchHeroList(false)
                    }
                }
            })
        } else {
            val networkRequest = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build()


                connectivityManager.registerNetworkCallback(networkRequest, object :
                    ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)

                        if (needLoadData) {
                            fetchHeroList(false)
                        }
                    }
                })

        }


        fetchHeroList()
    }

    fun fetchHeroList(needCheckInternet: Boolean = true) {
        needLoadData = true

        if (status.value == HeroApiStatus.LOADING) {
            return
        }

        if (needCheckInternet && !isNetworkAvailable(getApplication())) {
            _status.value = HeroApiStatus.NO_INTERNET
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
            } finally {
                needLoadData = false
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