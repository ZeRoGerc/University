package com.zerogerc.todolist.ui.tasks

import com.zerogerc.todolist.dao.Task

interface TasksView {

    fun onTasksLoaded(tasks: List<Task>)

    fun onTaskAdded()
}