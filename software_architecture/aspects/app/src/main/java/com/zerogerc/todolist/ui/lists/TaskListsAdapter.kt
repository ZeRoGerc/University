package com.zerogerc.todolist.ui.lists

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zerogerc.todolist.R
import com.zerogerc.todolist.dao.TaskList

class TaskListsAdapter(
        val data: List<TaskList>,
        val listener: TaskListClickListener
) : RecyclerView.Adapter<TaskListsAdapter.TaskListViewHolder>() {

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) = holder.bindData(data[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            TaskListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_task_list, parent, false))

    override fun getItemCount() = data.size

    inner class TaskListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleView: TextView = itemView.findViewById(R.id.item_task_list_title)

        val deleteButton: View = itemView.findViewById(R.id.item_task_list_delete_button)

        var taskList: TaskList? = null

        init {
            titleView.setOnClickListener { taskList?.let { listener.onTaskListClicked(it) } }
            deleteButton.setOnClickListener { taskList?.let { listener.onTaskListDeleteClicked(it) } }
        }

        fun bindData(taskList: TaskList) {
            this.taskList = taskList
            this.titleView.text = taskList.title()
        }
    }

    interface TaskListClickListener {

        fun onTaskListClicked(taskList: TaskList)

        fun onTaskListDeleteClicked(taskList: TaskList)
    }
}