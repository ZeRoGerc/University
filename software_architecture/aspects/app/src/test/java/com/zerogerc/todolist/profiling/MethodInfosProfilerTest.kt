package com.zerogerc.todolist.profiling

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MethodInfosProfilerTest {

    @Test
    fun `getMethodInfos should return zero by default`() {
        val profiler = MethodInfosProfiler()
        assertThat(profiler.getMethodInfos()).isEmpty()
    }


    @Test
    fun `reportMethodCall should report single`() {
        val profiler = MethodInfosProfiler()

        profiler.reportMethodCall(
                fileName = "file",
                declaringTypeName = "type",
                methodName = "method",
                timeNano = 100L
        )

        val expected = MethodProfilingInfo(
                name = profiler.getMethodId("type", "method"),
                totalTime = 100L,
                totalCount = 1
        )

        assertThat(profiler.getMethodInfos()).hasSize(1)
        assertThat(profiler.getMethodInfos()[0]).isEqualTo(expected)
    }

    @Test
    fun `reportMethodCall should report complex`() {
        val profiler = MethodInfosProfiler()

        profiler.reportMethodCall(
                fileName = "file",
                declaringTypeName = "type",
                methodName = "method1",
                timeNano = 100L
        )


        profiler.reportMethodCall(
                fileName = "file",
                declaringTypeName = "type",
                methodName = "method2",
                timeNano = 10L
        )

        profiler.reportMethodCall(
                fileName = "file",
                declaringTypeName = "type",
                methodName = "method1",
                timeNano = 1L
        )

        val expected1 = MethodProfilingInfo(
                name = profiler.getMethodId("type", "method1"),
                totalTime = 101L,
                totalCount = 2
        )

        val expected2 = MethodProfilingInfo(
                name = profiler.getMethodId("type", "method2"),
                totalTime = 10L,
                totalCount = 1
        )

        assertThat(profiler.getMethodInfos()).containsExactlyInAnyOrder(expected1, expected2)
    }
}
