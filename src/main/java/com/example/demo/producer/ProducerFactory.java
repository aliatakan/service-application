package com.example.demo.producer;

import com.amazonaws.auth.BasicAWSCredentials;
import com.example.demo.config.KinesisConfig;
import com.example.demo.producer.impl.KinesisProducerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by aliatakan on 20/11/17.
 */
@Component
public class ProducerFactory {

    @Autowired
    private KinesisConfig kinesisConfig;

    @Bean
    public Producer applicationEventsProducer(){
        return new KinesisProducerImpl(new BasicAWSCredentials(
                kinesisConfig.getAccessKey(),
                kinesisConfig.getSecretKey()),
                kinesisConfig.getStreamName(),
                kinesisConfig.getAwsRegion());
    }
}
