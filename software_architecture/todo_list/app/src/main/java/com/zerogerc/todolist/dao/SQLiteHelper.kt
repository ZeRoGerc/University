package com.zerogerc.todolist.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.zerogerc.todolist.app.ToDoListApplication
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SQLiteHelper @Inject constructor(context: ToDoListApplication)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        val DATABASE_VERSION = 1

        val DATABASE_NAME = "ToDoList.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TaskList.CREATE_TABLE)
        db.execSQL(Task.CREATE_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // no - op
    }

}