package com.zerogerc.todolist.ui

abstract class Presenter<T> {

    var view: T? = null

    fun bindView(view: T) {
        if (this.view != null) {
            throw RuntimeException("Previous view is not unbounded!")
        }
        this.view = view
    }

    fun unbindView(view: T) {
        if (this.view != view) {
            throw RuntimeException("Passed view is not equal to currently bounded view!")
        }
        this.view = null
    }

    fun executeIfViewBound(action: (T) -> Unit) {
        this.view?.let { action(it) }
    }
}