package com.excise._17_jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 整体吞吐量，1秒内可以执行多少次调用
 */
public class JMHSample_02_BenchmarkModes {

    @Benchmark
    @BenchmarkMode(Mode.Throughput) // 整体吞吐量
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void measureThroughput() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime) // 调用的平均时间
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void measureAvgTime() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime) // 设置模式为随机取样，比如99%的调用在多少毫秒以内
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void measureSamples() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime) // 指定模式为冷启动，往往预热次数为0，即冷启动执行一次的性能
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void measureSingleShot() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Benchmark
    @BenchmarkMode({ Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime })
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void measureMultiple() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void measureAll() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(JMHSample_02_BenchmarkModes.class.getSimpleName()).forks(1).build();
        new Runner(opt).run();
    }

}