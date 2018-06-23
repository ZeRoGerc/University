package com.zerogerc.todolist.ui.tasks

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.zerogerc.todolist.R
import com.zerogerc.todolist.app.ToDoListApplication
import com.zerogerc.todolist.dao.Task
import com.zerogerc.todolist.dao.ToDoListDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TasksFragment : Fragment(), TasksView, TasksAdapter.TaskClickListener {

    @Inject
    lateinit var tasksListDao: ToDoListDao

    lateinit var presenter: TasksPresenter

    var listId = 0L

    lateinit var listView: RecyclerView

    lateinit var newTaskTitleView: EditText

    lateinit var addButton: View

    lateinit var layoutManager: LinearLayoutManager

    var adapter: TasksAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ToDoListApplication.getApplicationComponent(context).inject(this)

        listId = arguments.getLong(LIST_ID, 0L)
        initPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.task_lists_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListView(view)
        initFooter(view)

        presenter.bindView(this)
        presenter.reloadTasks()
    }

    private fun initListView(view: View) {
        listView = view.findViewById(R.id.task_lists_list)
        layoutManager = LinearLayoutManager(context)
        listView.setHasFixedSize(true)
        listView.layoutManager = layoutManager
    }

    private fun initFooter(view: View) {
        newTaskTitleView = view.findViewById(R.id.task_lists_new_list_title)
        newTaskTitleView.hint = resources.getString(R.string.tasks_new_title_hint)

        addButton = view.findViewById(R.id.task_lists_add_button)
        addButton.setOnClickListener { addTaskClicked() }
    }

    override fun onDestroyView() {
        presenter.unbindView(this)
        super.onDestroyView()
    }

    override fun onTasksLoaded(tasks: List<Task>) {
        adapter = TasksAdapter(tasks, this)
        listView.adapter = adapter
    }

    override fun onTaskAdded() {
        Toast.makeText(context, "Task added", Toast.LENGTH_SHORT).show()
        presenter.reloadTasks()
    }

    override fun onTaskClicked(task: Task, done: Boolean) {
        presenter.markDone(task._id(), done)
    }

    private fun addTaskClicked() {
        val newTitle = newTaskTitleView.text.toString()
        if (newTitle.isEmpty()) {
            Toast.makeText(context, "Please enter title.", Toast.LENGTH_SHORT).show()
        } else {
            newTaskTitleView.text.clear()
            presenter.addTask(newTitle)
        }
    }

    private fun initPresenter() {
        this.presenter = TasksPresenter(
                tasksListDao,
                listId,
                Schedulers.io(),
                AndroidSchedulers.mainThread()
        )
    }

    companion object {

        val LIST_ID = "ARG_TASKS_FRAGMENT_LIST_ID"

        fun newInstance(listId: Long): TasksFragment {
            val args = Bundle()
            args.putLong(LIST_ID, listId)
            val fragment = TasksFragment()
            fragment.arguments = args
            return fragment
        }
    }
}