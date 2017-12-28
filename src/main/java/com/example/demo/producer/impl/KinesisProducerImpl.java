package com.example.demo.producer.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.example.demo.producer.Producer;

import java.nio.ByteBuffer;

/**
 * Created by aliatakan on 20/11/17.
 */
public class KinesisProducerImpl implements Producer {

    private final AmazonKinesisClient client;
    private final String streamName;

    public KinesisProducerImpl(final AWSCredentials awsCredentials,
                               final String streamName,
                               final String regionId) {
        this.streamName = streamName;
        this.client = new AmazonKinesisClient(awsCredentials)
                .withRegion(Regions.fromName(regionId));
    }

    @Override
    public void emit(String partitionKey, byte[] data) {
        this.client.putRecord(this.streamName, ByteBuffer.wrap(data), partitionKey);
    }


}
