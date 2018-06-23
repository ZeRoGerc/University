package com.zerogerc.todolist.ui.lists

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
import com.zerogerc.todolist.dao.TaskList
import com.zerogerc.todolist.dao.ToDoListDao
import com.zerogerc.todolist.ui.tasks.TasksFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TaskListsFragment : Fragment(), TaskListsView, TaskListsAdapter.TaskListClickListener {

    @Inject
    lateinit var tasksListDao: ToDoListDao

    lateinit var presenter: TaskListsPresenter

    lateinit var listView: RecyclerView

    lateinit var newListTitleView: EditText

    lateinit var addButton: View

    lateinit var layoutManager: LinearLayoutManager

    var adapter: TaskListsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ToDoListApplication.getApplicationComponent(context).inject(this)
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
        presenter.reloadTaskLists()
    }

    private fun initListView(view: View) {
        listView = view.findViewById(R.id.task_lists_list)
        layoutManager = LinearLayoutManager(context)
        listView.setHasFixedSize(true)
        listView.layoutManager = layoutManager
    }

    private fun initFooter(view: View) {
        newListTitleView = view.findViewById(R.id.task_lists_new_list_title)
        newListTitleView.hint = resources.getString(R.string.task_lists_new_title_hint)

        addButton = view.findViewById(R.id.task_lists_add_button)
        addButton.setOnClickListener { addTaskListClicked() }
    }

    override fun onDestroyView() {
        presenter.unbindView(this)
        super.onDestroyView()
    }

    override fun onTaskListsLoaded(lists: List<TaskList>) {
        adapter = TaskListsAdapter(lists, this)
        listView.adapter = adapter
    }

    override fun onTaskListAdded() {
        presenter.reloadTaskLists()
        Toast.makeText(context, "New task list added.", Toast.LENGTH_SHORT).show()
    }

    override fun onTaskListRemoved() {
        presenter.reloadTaskLists()
        Toast.makeText(context, "Task list was removed.", Toast.LENGTH_SHORT).show()
    }

    override fun onTaskListClicked(taskList: TaskList) {
        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.main_activity_fragment_container, TasksFragment.newInstance(taskList._id()))
                .addToBackStack(null)
                .commit()
    }

    override fun onTaskListDeleteClicked(taskList: TaskList) {
        presenter.removeTaskList(taskList._id())
    }

    private fun addTaskListClicked() {
        val newTitle = newListTitleView.text.toString()
        if (newTitle.isEmpty()) {
            Toast.makeText(context, "Please enter title.", Toast.LENGTH_SHORT).show()
        } else {
            newListTitleView.text.clear()
            presenter.addTaskList(newTitle)
        }
    }

    private fun initPresenter() {
        this.presenter = TaskListsPresenter(
                tasksListDao,
                Schedulers.io(),
                AndroidSchedulers.mainThread()
        )
    }
}