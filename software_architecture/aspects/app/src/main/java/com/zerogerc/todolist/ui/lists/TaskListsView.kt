package com.zerogerc.todolist.ui.lists

import android.support.annotation.UiThread
import com.zerogerc.todolist.dao.TaskList

@UiThread
interface TaskListsView {

    fun onTaskListsLoaded(lists: List<TaskList>)

    fun onTaskListAdded()

    fun onTaskListRemoved()
}