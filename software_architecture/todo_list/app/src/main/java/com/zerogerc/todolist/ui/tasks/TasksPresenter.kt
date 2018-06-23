package com.zerogerc.todolist.ui.tasks

import com.zerogerc.todolist.dao.ToDoListDao
import com.zerogerc.todolist.ui.Presenter
import io.reactivex.Scheduler

class TasksPresenter(
        val toDoListDao: ToDoListDao,
        val listId: Long,
        val ioScheduler: Scheduler,
        val uiScheduler: Scheduler
) : Presenter<TasksView>() {

    fun reloadTasks() {
        toDoListDao.selectTasksFromList(listId)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe {
                    tasks ->
                    executeIfViewBound { it.onTasksLoaded(tasks) }
                }
    }

    fun addTask(title: String) {
        toDoListDao.addTask(title, listId)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe { executeIfViewBound { it.onTaskAdded() } }
    }

    fun markDone(taskId: Long, done: Boolean) {
        toDoListDao.markTaskDone(taskId, done)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe()
    }
}
