package org.example;

import org.apache.catalina.core.ApplicationContext;
import org.example.controller.IncidentController;
import org.example.entity.IncidentEntity;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Thread)
@Fork(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
//@Warmup(iterations = 3)
@Measurement(iterations = 1)
public class JmhBenchMark {

    StringBuilder stringBuilder = new StringBuilder();

    //springBoot容器
    private ConfigurableApplicationContext context;

    //待测试的TestUserController
    private IncidentController incidentController;

    /**
     * 初始化，获取springBoot容器，run即可，同时得到相关的测试对象
     */
    @Setup
    public void init() {
        //容器获取
        context = SpringApplication.run(MainApp.class);
        //获取对象
        incidentController = context.getBean(IncidentController.class);
    }

    @Benchmark
    public void add() {
        IncidentEntity incident = new IncidentEntity();
        incident.setName("name");
        incident.setStatus("END");
        incident.setDescription("benchMark");
        incidentController.createIncident(incident);
    }

    @Benchmark
    public void query() {
        incidentController.queryPage(0,1);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JmhBenchMark.class.getSimpleName()).measurementTime(TimeValue.valueOf("1s"))
                .build();
        new Runner(opt).run();
    }

}
