package com.zerogerc.todolist.profiling

import java.util.concurrent.ConcurrentHashMap

interface Profiler {
    fun reportMethodCall(
            fileName: String,
            declaringTypeName: String,
            methodName: String,
            timeNano: Long
    )
}

class MethodInfosProfiler : Profiler {

    private val methodProfilingInfos = ConcurrentHashMap<String, MethodProfilingInfo>()

    override fun reportMethodCall(fileName: String, declaringTypeName: String, methodName: String, timeNano: Long) {
        val methodId = getMethodId(declaringTypeName = declaringTypeName, methodName = methodName)

        System.nanoTime()
        val info = methodProfilingInfos[methodId] ?: MethodProfilingInfo(name = methodId, totalTime = 0L, totalCount = 0L)
        info.totalTime += timeNano
        info.totalCount += 1
        methodProfilingInfos[methodId] = info

//        android.util.Log.d("CALL", "File: $fileName, Method: $methodId, Time: ${timeNano / 1000}ms")
    }

    fun getMethodInfos(): List<MethodProfilingInfo> = methodProfilingInfos.values.toList()

    /**
     * Id to store method in map for future grouping of identical methods.
     */
    fun getMethodId(declaringTypeName: String, methodName: String) = "$declaringTypeName:$methodName"
}


data class MethodProfilingInfo(val name: String, var totalTime: Long, var totalCount: Long)
