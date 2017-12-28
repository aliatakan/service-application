package com.example.demo.consumer;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessorFactory;
import com.example.demo.service.ApplicationDataService;

public class EventRecordProcessorFactory implements IRecordProcessorFactory {

    private ApplicationDataService applicationDataService;

    public EventRecordProcessorFactory(ApplicationDataService applicationDataService){
        this.applicationDataService = applicationDataService;
    }

    @Override
    public IRecordProcessor createProcessor() {
        return new EventRecordProcessor(applicationDataService);
    }
}
