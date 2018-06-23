package com.zerogerc.todolist.app

import com.zerogerc.todolist.ui.MainActivity
import com.zerogerc.todolist.ui.lists.TaskListsFragment
import com.zerogerc.todolist.ui.tasks.TasksFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(taskListsFragment: TaskListsFragment)

    fun inject(tasksFragment: TasksFragment)
}
