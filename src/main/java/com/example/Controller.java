package com.example;

import io.micronaut.http.annotation.Get;
import org.apache.ibatis.datasource.pooled.PoolState;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.List;

@io.micronaut.http.annotation.Controller("/test")
public class Controller {

    static int maxPoolCount;

    @Inject
    private RuntimeService runtimeService;

    @Inject
    private HistoryService historyService;

    @Inject
    private ProcessEngine processEngine;

    @Get(uri = "/", produces = {"application/json"})
    void startInstance() throws InterruptedException {
        DataSource dataSource = processEngine.getProcessEngineConfiguration().getDataSource();
        PoolState poolState = ((PooledDataSource) dataSource).getPoolState();
        int activeConnectionCount = poolState.getActiveConnectionCount();
        System.out.println("Active Connections: " + activeConnectionCount);
        if(activeConnectionCount > maxPoolCount) {
            maxPoolCount = activeConnectionCount;
            System.out.println(poolState.toString());
        }
        runtimeService.startProcessInstanceByKey("HelloWorld");
        System.out.println("MaxPoolCount: " + maxPoolCount);
    }

    @Get(uri = "/count", produces = {"application/json"})
    public String count() {
        return historyService.createHistoricProcessInstanceQuery().count() + " ";
    }

}
