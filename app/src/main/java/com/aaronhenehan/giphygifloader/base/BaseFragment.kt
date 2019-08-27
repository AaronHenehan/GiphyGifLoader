package com.aaronhenehan.giphygifloader.base

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : Fragment() {
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    protected fun addToDisposables(disposable: CompositeDisposable) {
        compositeDisposable.add(disposable)
    }
}