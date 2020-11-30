package com.excise._17_jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 不同Map实现类的性能测试
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class MapTest {

    static Map hashMap = new HashMap();
    static Map syncHashMap = Collections.synchronizedMap(new HashMap());
    static Map concurrentHashMap = new ConcurrentHashMap();

    @Setup
    public void setup() {
        for (int i = 0; i < 10000; i++) {
            hashMap.put(Integer.toString(i), Integer.toString(i));
            syncHashMap.put(Integer.toString(i), Integer.toString(i));
            concurrentHashMap.put(Integer.toString(i), Integer.toString(i));
        }

    }

    @Benchmark
    public void hashMapGet() {
        hashMap.get("4");
    }

    @Benchmark
    public void syncHashMapGet() {
        syncHashMap.get("4");
    }

    @Benchmark
    public void concurrentHashMapGet() {
        concurrentHashMap.get("4");
    }

    @Benchmark
    public void hashMapSize() {
        hashMap.size();
    }

    @Benchmark
    public void syncHashMapSize() {
        syncHashMap.size();
    }

    @Benchmark
    public void concurrentHashMapSize() {
        concurrentHashMap.size();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(MapTest.class.getSimpleName()).forks(1).warmupIterations(5)
                .measurementIterations(5).threads(2).build();
        new Runner(opt).run();
    }
}
