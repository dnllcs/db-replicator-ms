package org.daniellcs.dbReplicatorMs.infrastructure.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Source(
        String version,
        String connector,
        String name,
        long ts_ms,
        boolean snapshot,
        String db,
        String schema,
        String table,
        long txId,
        long lsn
) {
}
