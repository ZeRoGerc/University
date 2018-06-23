package com.zerogerc.todolist.profiling

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zerogerc.todolist.R


class ProfilingResultsActivity : AppCompatActivity() {

    lateinit var list: RecyclerView

    lateinit var adapter: ProfilingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profiling)
    }

    override fun onResume() {
        super.onResume()
        val methodsProfiler = ProfilersProvider.instance.methodInfosProfiler
        val methodInfos = methodsProfiler.getMethodInfos()

        adapter = ProfilingAdapter(this)
        adapter.updateData(methodInfos.sortedBy { it.totalTime }.reversed())

        list = findViewById(R.id.activity_profiling_list)
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)
    }

    class ProfilingAdapter(context: Context) : RecyclerView.Adapter<ProfilingResultsViewHolder>() {

        private val inflater = LayoutInflater.from(context)!!

        private var data: List<MethodProfilingInfo> = emptyList()

        fun updateData(infos: List<MethodProfilingInfo>) {
            data = infos
            notifyDataSetChanged()
        }

        override fun onBindViewHolder(holder: ProfilingResultsViewHolder, position: Int) =
                holder.onBind(data[position])

        override fun getItemCount() = data.size

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
                ProfilingResultsViewHolder(inflater.inflate(R.layout.item_profiling, parent, false))
    }

    class ProfilingResultsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nameView: TextView = itemView.findViewById(R.id.item_profiling_title)

        val infoView: TextView = itemView.findViewById(R.id.item_profiling_info)

        @SuppressLint("SetTextI18n")
        fun onBind(info: MethodProfilingInfo) {
            val time = when {
                info.totalTime < 1000 -> "${info.totalTime}ns"
                info.totalTime < 1e6 -> "${info.totalTime / 1000}mcs"
                info.totalTime < 1e9 -> "${info.totalTime / 1e6}ms"
                else -> "${info.totalTime / 1e9}s"
            }

            nameView.text = info.name
            infoView.text = "Time: $time, Count: ${info.totalCount}"
        }
    }
}
