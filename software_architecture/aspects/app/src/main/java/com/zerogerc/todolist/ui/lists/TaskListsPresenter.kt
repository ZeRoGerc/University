package com.zerogerc.todolist.ui.lists

import com.zerogerc.todolist.dao.ToDoListDao
import com.zerogerc.todolist.ui.Presenter
import io.reactivex.Scheduler

class TaskListsPresenter(
        val toDoListDao: ToDoListDao,
        val ioScheduler: Scheduler,
        val uiScheduler: Scheduler
) : Presenter<TaskListsView>() {

    fun reloadTaskLists() {
        toDoListDao.selectAllTaskLists()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe { tasks -> executeIfViewBound { it.onTaskListsLoaded(tasks) } }
    }

    fun addTaskList(title: String) {
        toDoListDao.addTaskList(title)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe { executeIfViewBound { it.onTaskListAdded() } }
    }

    fun removeTaskList(taskListId: Long) {
        toDoListDao.removeTaskList(taskListId)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe { executeIfViewBound { it.onTaskListRemoved() } }
    }
}
