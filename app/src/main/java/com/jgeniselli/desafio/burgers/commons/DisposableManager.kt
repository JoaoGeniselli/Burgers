package com.jgeniselli.desafio.burgers.commons

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class DisposableManager {

    companion object {
        private var compositeInstance: CompositeDisposable? = null

        private fun getSharedComposite(): CompositeDisposable {
            compositeInstance ?: synchronized(this) {
                compositeInstance = CompositeDisposable()
            }
            return compositeInstance!!
        }

        fun add(disposable: Disposable) {
            getSharedComposite().add(disposable)
        }

        fun dispose() {
            getSharedComposite().dispose()
        }
    }
}