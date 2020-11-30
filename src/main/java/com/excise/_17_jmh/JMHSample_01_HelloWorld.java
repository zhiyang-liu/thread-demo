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
 * JMH是专用于性能测试的框架，其精度可以达到毫秒级
 * 本例子测量方法的平均响应时间
 * 首先预热，然后测试20次平均值
 */
@BenchmarkMode(Mode.AverageTime) // 指定模式为平均响应时间
@OutputTimeUnit(TimeUnit.MILLISECONDS) // 指定时间单位
public class JMHSample_01_HelloWorld {

    // 被度量函数用@Benchmark标注
    @Benchmark
    public void wellHelloThere() {
        // this method was intentionally left blank.
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHSample_01_HelloWorld.class.getSimpleName())
                .forks(1).build();
        new Runner(opt).run();
    }
}
