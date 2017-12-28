package com.example.demo.entity;

import com.example.demo.enums.MessageType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.Data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by aliatakan on 22/11/17.
 */
@Data
public class MessageManager {
    private final String id;
    private final String name;
    private final Long timestamp;
    private final JsonNode payload;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ObjectReader OBJECT_READER = OBJECT_MAPPER.reader();

    public MessageManager(String id, String messageType, Long timestamp, JsonNode payload) {
        this.id = id;
        this.name = messageType;
        this.timestamp = timestamp;
        this.payload = payload;
    }

    public MessageManager(JsonNode message) {
        this.id = message.get("id").asText();
        this.name = message.get("name").asText();
        this.timestamp = message.get("timestamp").asLong();
        this.payload = message.get("payload");
    }

    public static MessageManager create(MessageType messageType, final Object value) {
        return new MessageManager(UUID.randomUUID().toString(), messageType.name(), System.currentTimeMillis(), OBJECT_MAPPER.valueToTree(value));
    }

    public static MessageManager parse(final byte[] json) {
        try {
            JsonNode root = OBJECT_READER.readTree(new ByteArrayInputStream(json));
            return new MessageManager(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] toByteArray() {
        try {
            return OBJECT_MAPPER.writeValueAsBytes(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
