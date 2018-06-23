package com.zerogerc.todolist.ui.tasks

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.zerogerc.todolist.R
import com.zerogerc.todolist.dao.Task

class TasksAdapter(
        val data: List<Task>,
        val listener: TaskClickListener
) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) = holder.bindData(data[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            TaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false))

    override fun getItemCount() = data.size

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleView: TextView = itemView.findViewById(R.id.item_task_title)

        val checkView: CheckBox = itemView.findViewById(R.id.item_task_check)

        var task: Task? = null

        init {
            itemView.setOnClickListener { task?.let {
                checkView.isChecked = !checkView.isChecked
                listener.onTaskClicked(it, checkView.isChecked)
            } }
        }

        fun bindData(task: Task) {
            this.task = task
            this.checkView.isChecked = task.done() != 0L
            this.titleView.text = task.text()
        }
    }

    interface TaskClickListener {
        fun onTaskClicked(task: Task, done: Boolean)
    }
}