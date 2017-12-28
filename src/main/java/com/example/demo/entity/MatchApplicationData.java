package com.example.demo.entity;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by aliatakan on 15/12/17.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchApplicationData {
    private Long applicationId;

    private String status;

    public static MatchApplicationData fromJson(JsonNode json){
        return new MatchApplicationData(
                json.get("applicationId").asLong(),
                json.get("status").asText()
        );
    }

}
