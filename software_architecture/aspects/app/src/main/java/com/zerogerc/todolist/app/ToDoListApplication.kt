package com.zerogerc.todolist.app

import android.app.Application
import android.content.Context

open class ToDoListApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = initApplicationComponent().build()
    }

    open fun initApplicationComponent(): DaggerApplicationComponent.Builder =
            DaggerApplicationComponent
                    .builder()
                    .applicationModule(ApplicationModule(this))

    companion object {
        fun getApplication(context: Context): ToDoListApplication =
                context.applicationContext as ToDoListApplication

        fun getApplicationComponent(context: Context): ApplicationComponent =
                getApplication(context).applicationComponent
    }
}
