package com.zerogerc.todolist.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zerogerc.todolist.R
import com.zerogerc.todolist.ui.lists.TaskListsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(R.id.main_activity_fragment_container, TaskListsFragment())
                .commit()
    }
}
