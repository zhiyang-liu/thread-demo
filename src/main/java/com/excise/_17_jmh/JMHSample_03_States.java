package com.excise._17_jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * JMH中的State可以理解为变量或者数据模型的作用域，通常包括整个Benchmark 和 Thread级别
 * 对于measureShared所有的线程共享一份数据，对于measureUnshared每个不同的测试线程都有自己的数据复制
 *
 * Benchmark                             Mode  Cnt          Score         Error  Units
 * JMHSample_03_States.measureShared    thrpt   20   50321074.475 ±  951972.110  ops/s
 * JMHSample_03_States.measureUnshared  thrpt   20  427972370.262 ± 5032619.981  ops/s
 */
public class JMHSample_03_States {

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        volatile double x = Math.PI;
    }

    @State(Scope.Thread)
    public static class ThreadState {
        volatile double x = Math.PI;
    }


    @Benchmark
    public void measureUnshared(ThreadState state) {
        state.x++;
    }

    @Benchmark
    public void measureShared(BenchmarkState state) {
        state.x++;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHSample_03_States.class.getSimpleName())
                .threads(4)
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}