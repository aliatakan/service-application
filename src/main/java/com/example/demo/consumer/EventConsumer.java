package com.example.demo.consumer;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.amazonaws.services.kinesis.metrics.interfaces.MetricsLevel;
import com.example.demo.config.KinesisConfig;
import com.example.demo.service.ApplicationDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class EventConsumer implements CommandLineRunner {

    @Autowired
    private ApplicationDataService applicationDataService;

    @Autowired
    private KinesisConfig kinesisConfig;

    private List<Worker> workers = new ArrayList<>();

    @Override
    public void run(String... strings) throws Exception {
        String workerId = UUID.randomUUID().toString();

        KinesisClientLibConfiguration config = new KinesisClientLibConfiguration(
                kinesisConfig.getAppName(),
                kinesisConfig.getStreamName(),
                new StaticCredentialsProvider(new BasicAWSCredentials(kinesisConfig.getAccessKey(), kinesisConfig.getSecretKey())), workerId)
            .withRegionName(kinesisConfig.getAwsRegion())
            .withMaxRecords(kinesisConfig.getBufferSizeLimit())
            .withMetricsLevel(MetricsLevel.NONE)
            .withInitialPositionInStream(InitialPositionInStream.TRIM_HORIZON);

        Worker worker = new Worker.Builder()
                .recordProcessorFactory(new EventRecordProcessorFactory(applicationDataService))
                .config(config)
                .build();

        workers.add(worker);

        worker.run();

    }

    @PreDestroy
    public void destroy() {
        workers.forEach(Worker::shutdown);
    }

}
