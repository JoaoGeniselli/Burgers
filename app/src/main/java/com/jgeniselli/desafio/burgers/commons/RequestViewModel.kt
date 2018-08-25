package com.jgeniselli.desafio.burgers.commons

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

abstract class RequestViewModel<T, B : RequestBundle> : ViewModel() {

    private val _result = MutableLiveData<T>()
    private val _error = MutableLiveData<Event<String?>>()
    private val _loading = MutableLiveData<Event<Boolean>>()

    protected fun postError(error: String?) {
        _error.value = Event(error)
    }

    val result: LiveData<T>
        get() = _result

    val errorMessage: LiveData<Event<String?>>
        get() = _error

    val loadingState: LiveData<Event<Boolean>>
        get() = _loading

    fun start(bundle: B) {
        if (locksResult() && result.value != null) return
        val single = makeRequest(bundle)
        attachThreadAndLoading(single)
                .subscribe({ _result.value = it }, { _error.value = Event(it.message) })
    }

    fun notifyResult() {
        _result.value = _result.value
    }

    fun <G> attachThreadAndLoading(single: Single<G>): Single<G> =
            single.observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { _loading.value = Event(true) }
                    .doAfterTerminate { _loading.value = Event(false) }

    abstract fun makeRequest(bundle: B): Single<T>

    open fun locksResult(): Boolean = false

    override fun onCleared() {
//        DisposableManager.dispose()
        super.onCleared()
    }

}


