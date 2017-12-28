package com.example.demo.service;

import com.example.demo.entity.Application;

/**
 * Created by aliatakan on 25/12/17.
 */
public interface ApplicationDataService {
    Application findApplication(Long id);

    void updateApplication(Application application);
}
