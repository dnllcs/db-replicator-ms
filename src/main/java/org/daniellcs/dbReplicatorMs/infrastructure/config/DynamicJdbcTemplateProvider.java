package org.daniellcs.dbReplicatorMs.infrastructure.config;

import org.daniellcs.dbReplicatorMs.infrastructure.Enum.DbEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DynamicJdbcTemplateProvider {

    @Value("${database.url}")
    private String baseUrl;
    @Value("${database.username}")
    private String username;
    @Value("${database.password}")
    private String password;

    private final Map<String, JdbcTemplate> templates = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        for (DbEnum db : DbEnum.values()) {
            createJdbcTemplate(db.getName());
        }
    }

    public JdbcTemplate getJdbcTemplate(String databaseName) {
        return templates.computeIfAbsent(databaseName, this::createJdbcTemplate);
    }

    private JdbcTemplate createJdbcTemplate(String databaseName) {
        DataSource ds = DataSourceBuilder.create()
                .url(baseUrl + databaseName)
                .username(username)
                .password(password)
                .driverClassName("org.postgresql.Driver")
                .build();

        return new JdbcTemplate(ds);
    }

}
