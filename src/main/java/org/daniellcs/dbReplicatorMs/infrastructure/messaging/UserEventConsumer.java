package org.daniellcs.dbReplicatorMs.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.daniellcs.dbReplicatorMs.infrastructure.Enum.DbEnum;
import org.daniellcs.dbReplicatorMs.infrastructure.config.DynamicJdbcTemplateProvider;
import org.daniellcs.dbReplicatorMs.infrastructure.event.DebeziumEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class UserEventConsumer {

    private final DynamicJdbcTemplateProvider provider;

    @KafkaListener(topics = "dbserver1.public.customers")
    public void consume(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        DebeziumEvent event = mapper.readValue(message, DebeziumEvent.class);

        String eventTable = event.source().table();
        String eventSchema = event.source().schema();
        String eventDb = event.source().db();
        String eventOp = event.op();
        Map<String,Object> eventAfter = event.after();

        for (DbEnum database : DbEnum.values()) {
            String databaseName = database.getName();
            if (!databaseName.equals(eventDb)) {
                switch(eventOp) {
                    case "d" -> applyDelete(databaseName, eventTable, eventOp);
                    default -> applyCreate(databaseName, eventTable, eventOp);
                }
            }
        }
    }


    private void applyDelete(String database, String table, String op) {

    }

    private void applyCreate(String database, String table, String op) {

    }

}
