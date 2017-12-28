# service-application
This Java service accepts applications from students

## Getting Started

This repository contains example project for using AWS kinesis with Java Spring Boot. I used PostgreSQL as a RDBMS.

### Prerequisites

You need to install Postgres in localhost and create a db which name is deneme_db. You will see the configuration parameters in the resources/application.properties file

```
spring.datasource.url=jdbc:postgresql://localhost/deneme_db
spring.datasource.username=ali_user
spring.datasource.password=123
spring.jpa.generate-ddl=true
```

Create application table.
```
CREATE TABLE public.application
(
    id bigint NOT NULL DEFAULT nextval('application_id_seq'::regclass),
    studentid bigint NOT NULL,
    classid smallint NOT NULL,
    status character varying(10) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT application_pkey PRIMARY KEY (id)
);
```

Also You need to have an AWS account to create a aws kinesis stream and you will see the configuration parameters in the resources/application.properties file

```
kinesis.region=us-east-1
kinesis.streamName=stream-application
aws.accessKey=****
aws.secretKey=****
aws.appName=APPLICATION_APP
```

### Summary

Open a postman and send a post request by using this json body. By doing this, you will make a request to apply a lesson. This lesson status will be "NEW" and another service (service-curriculum) will change its status to "APPROVED" or "REJECTED".

Then check the application table in the postgres. You should find a new application in the table. This code add a new application to the application table by using spring data.

```
application = repository.save(application);
```

Then code emits an event to the stream-application stream in the aws kinesis. There are applicaionId and classId (actually lessonId) in the payLoad.

```
applicationEventsProducer.emit(
                    Long.toString(application.getId()),
                    MessageManager.create(
                            MessageType.EVT_APPLICATION_CREATED,
                            new ApplicationCreatedEvent(
                                    application.getId(),
                                    application.getClassId())).toByteArray());
```

End with an example of getting some data out of the system or using it for a little demo

### service-application as a consumer

You need to check the service-curriculum because it emits EVT_APPLICATION_APPROVED or EVT_APPLICATION_REJECTED events and service-application consumes these events to modify application status.

```
 @Override
    public void processRecords(ProcessRecordsInput processRecordsInput) {
        List<Record> records = processRecordsInput.getRecords();

        records.stream()
                .map(r -> MessageManager.parse(r.getData().array()))
                .filter(m -> m.getName().equals(MessageType.EVT_APPLICATION_APPROVED.name()) 
                        || m.getName().equals(MessageType.EVT_APPLICATION_REJECTED.name()))
                .map(m -> MatchApplicationData.fromJson(m.getPayload()))
                .forEach(data -> {
                    //find the related application
                    application = applicationDataService.findApplication(data.getApplicationId());

                    //change its status
                    application.setStatus(data.getStatus());

                    //update application status
                    applicationDataService.updateApplication(application);
                });

        updateCheckpoint(processRecordsInput);
    }
```

There is an article about Event-driven Data Management in Microservices in the Cimri tech-blog.
