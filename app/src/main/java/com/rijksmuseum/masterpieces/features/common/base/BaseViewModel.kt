package com.rijksmuseum.masterpieces.features.common.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.rijksmuseum.masterpieces.infrastructure.SchedulersProvider
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

/**
 * Base class for ViewModels
 *
 * Helps to reduce boilerplate code
 */
open class BaseViewModel(private val schedulersProvider: SchedulersProvider) : ViewModel() {

    private val disposables = CompositeDisposable()

    @CallSuper
    override fun onCleared() {
        disposables.clear()
    }

    /**
     * Method to subscribe on IO thread and receive results on UI thread
     */
    protected fun <T> subscribeIo(
        single: Single<T>,
        onSubscribe: () -> Unit,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit
    ): Disposable {
        return single.subscribeOn(schedulersProvider.worker())
            .observeOn(schedulersProvider.main())
            .doOnSubscribe { onSubscribe() }
            .subscribe(
                { onSuccess(it) },
                { onError(it) }
            ).also { disposables.add(it) }
    }
}