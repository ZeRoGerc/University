package com.zerogerc.todolist.profiling

class ProfilersProvider {

    val methodInfosProfiler: MethodInfosProfiler by lazy { MethodInfosProfiler() }

    private object Holder {
        val INSTANCE = ProfilersProvider()
    }

    companion object {
        @JvmStatic
        val instance: ProfilersProvider by lazy { Holder.INSTANCE }
    }
}
