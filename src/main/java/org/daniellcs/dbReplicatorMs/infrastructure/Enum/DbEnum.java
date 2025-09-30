package org.daniellcs.dbReplicatorMs.infrastructure.Enum;

public enum DbEnum {
    DB1("db1"),
    DB2("db2"),
    DB3("db3");

    private final String name;
    DbEnum(String name) { this.name = name; }
    public String getName() { return name; }
}
