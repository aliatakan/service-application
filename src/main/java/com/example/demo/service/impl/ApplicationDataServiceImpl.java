package com.example.demo.service.impl;

import com.example.demo.entity.Application;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.service.ApplicationDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by aliatakan on 25/12/17.
 */
@Service
public class ApplicationDataServiceImpl implements ApplicationDataService {

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public Application findApplication(Long id) {
        return applicationRepository.findOne(id);
    }

    @Override
    public void updateApplication(Application application) {
        applicationRepository.save(application);
    }

}
