package com.example.demo.service.impl;

import com.example.demo.entity.Application;
import com.example.demo.entity.ApplicationCreatedEvent;
import com.example.demo.entity.MessageManager;
import com.example.demo.enums.MessageType;
import com.example.demo.producer.Producer;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by aliatakan on 16/11/17.
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationRepository repository;

    @Autowired
    private Producer applicationEventsProducer;

    @Override
    public void addApplication(Application application) {
        application = repository.save(application);

        //adding to the kinesis stream (application-stream)

        applicationEventsProducer.emit(
                Long.toString(application.getId()),
                MessageManager.create(
                        MessageType.EVT_APPLICATION_CREATED,
                        new ApplicationCreatedEvent(
                                application.getId(),
                                application.getClassId())).toByteArray());


    }
}
