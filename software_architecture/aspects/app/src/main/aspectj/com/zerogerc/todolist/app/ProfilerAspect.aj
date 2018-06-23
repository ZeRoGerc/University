package com.zerogerc.todolist.app;

import com.zerogerc.todolist.profiling.Profiler;
import com.zerogerc.todolist.profiling.ProfilersProvider;

public aspect ProfilerAspect {

    pointcut allInPackage(): execution(* *(..)) && (
            within(com.zerogerc.todolist..dao..*)
            );

    Object around(Object o): target(o) && allInPackage() {
        long start = System.nanoTime();
        Object res = proceed(o);
        long nanoTime = System.nanoTime() - start;

        if ("method-execution".equals(thisJoinPointStaticPart.getKind())) {
            String fileName = thisJoinPointStaticPart.getSourceLocation().getFileName();
            String declTypeName = thisJoinPointStaticPart.getSignature().getDeclaringTypeName();
            String name = thisEnclosingJoinPointStaticPart.getSignature().getName();

            Profiler methodInfosProfiler = ProfilersProvider.Companion.getInstance().getMethodInfosProfiler();
            methodInfosProfiler.reportMethodCall(
                    fileName,
                    declTypeName,
                    name,
                    nanoTime
            );
        }

        return res;
    }
}
