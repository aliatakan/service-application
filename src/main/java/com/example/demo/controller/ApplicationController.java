package com.example.demo.controller;

import com.example.demo.entity.Application;
import com.example.demo.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by aliatakan on 16/11/17.
 */
@RestController
@RequestMapping(value = "/application")
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    @RequestMapping(method = RequestMethod.POST)
    @PostMapping("application")
    public ResponseEntity addApplication(@RequestBody Application application){
        applicationService.addApplication(application);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
