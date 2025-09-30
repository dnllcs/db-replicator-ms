package org.daniellcs.dbReplicatorMs.infrastructure.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DebeziumEvent(
        Map<String, Object> before,
        Map<String, Object> after,
        Source source,
        String op,
        long ts_ms
) {}

