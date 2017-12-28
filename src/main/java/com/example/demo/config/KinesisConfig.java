package com.example.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by aliatakan on 20/11/17.
 */
@Service
@Data
public class KinesisConfig {
    @Value("${kinesis.region}")
    private String awsRegion;

    @Value("${kinesis.streamName}")
    private String streamName;

    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.appName}")
    private String appName;

    @Value("${CONSUMER_BUFFER_SIZE_LIMIT:100}")
    private int bufferSizeLimit;

}
