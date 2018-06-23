package com.zerogerc.todolist.dao

import com.zerogerc.todolist.data.TaskListModel
import com.zerogerc.todolist.data.TaskModel
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ToDoListDao @Inject constructor(val dbHelper: SQLiteHelper) {

    fun addTaskList(title: String): Completable = Completable.fromAction {
        val statement = TaskListModel.AddTaskList(dbHelper.writableDatabase)
        statement.bind(title)
        statement.program.execute()
    }

    fun removeTaskList(taskListId: Long): Completable = Completable.fromCallable {
        val statement = TaskListModel.DeleteTaskList(dbHelper.writableDatabase)
        statement.bind(taskListId)
        statement.program.execute()
    }

    fun selectAllTaskLists(): Single<List<TaskList>> = Single.fromCallable {
        val query = TaskList.FACTORY.selectAll()
        dbHelper.writableDatabase.rawQuery(query.statement, query.args).use { cursor ->
            generateSequence { if (cursor.moveToNext()) cursor else null }
                    .map { it -> TaskList.SELECT_ALL_MAPPER.map(it) }
                    .toList()
        }
    }

    fun addTask(text: String, listId: Long): Completable = Completable.fromAction {
        val statement = TaskModel.AddTask(dbHelper.writableDatabase)
        statement.bind(text, listId)
        statement.program.execute()
    }

    fun markTaskDone(taskId: Long, done: Boolean): Completable = Completable.fromAction {
        val statement = TaskModel.MarkDone(dbHelper.writableDatabase)
        if (done) {
            statement.bind(1L, taskId)
        } else {
            statement.bind(0L, taskId)
        }
        statement.program.execute()
    }

    fun selectTasksFromList(listId: Long): Single<List<Task>> = Single.fromCallable {
        val query = Task.FACTORY.selectByList(listId)
        dbHelper.writableDatabase.rawQuery(query.statement, query.args).use { cursor ->
            generateSequence { if (cursor.moveToNext()) cursor else null }
                    .map { Task.SELECT_ALL_MAPPER.map(it) }
                    .toList()
        }
    }
}