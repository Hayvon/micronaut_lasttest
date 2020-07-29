package com.example;

import io.micronaut.http.annotation.Get;
import org.camunda.bpm.engine.RuntimeService;

import javax.inject.Inject;
import java.util.List;

@io.micronaut.http.annotation.Controller("/test")
public class Controller {

    @Inject
    private RuntimeService runtimeService;

    @Get(uri = "/", produces = {"application/json"})
    void startInstance(){
        runtimeService.startProcessInstanceByKey("HelloWorld");
        //System.out.println("Instance started");
    }

}
