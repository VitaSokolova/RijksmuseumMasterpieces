package com.rijksmuseum.masterpieces.features.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> LiveData<T>.debounce(duration: Long = 1000L, coroutineScope: CoroutineScope) =
    MediatorLiveData<T>().also { mld ->

        val source = this
        var job: Job? = null

        mld.addSource(source) {
            if (job == null) {
                job = coroutineScope.launch {
                    mld.value = source.value
                }
            } else {
                job?.cancel()
                job = coroutineScope.launch {
                    delay(duration)
                    mld.value = source.value
                }
            }

        }
    }