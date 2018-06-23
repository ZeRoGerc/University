package com.zerogerc.todolist.app

import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ApplicationModule(val application: ToDoListApplication) {

    @Provides
    @Singleton
    fun application(): ToDoListApplication = application
}