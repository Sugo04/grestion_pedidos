package ad.gestion_pedidos.hmarort.database_config;

public class DatabaseConfigFactory {
    /**
     * Crea una instancia de DatabaseConfig según el tipo de base de datos.
     * @param type
     * @param properties
     * @return
     */
    public static DatabaseConfig createConfig(DatabaseType type, DatabaseProperties properties) {
        return switch (type) {
            case SQLITE -> createSQLiteConfig(properties);
            case MYSQL -> createMySQLConfig(properties);
            case POSTGRESQL -> createPostgreSQLConfig(properties);
        };
    }

    /**
     * Crea una instancia de SQLiteConfig.
     * @param properties
     * @return
     */
    private static DatabaseConfig createSQLiteConfig(DatabaseProperties properties) {
        return new SQLiteConfig(properties.getUrl());
    }

    /**
     * Crea una instancia de MySQLConfig.
     * @param properties
     * @return
     */
    private static DatabaseConfig createMySQLConfig(DatabaseProperties properties) {
        throw new UnsupportedOperationException("MySQL not implemented yet");
    }

    /**
     * Crea una instancia de PostgreSQLConfig.
     * @param properties
     * @return
     */
    private static DatabaseConfig createPostgreSQLConfig(DatabaseProperties properties) {
        throw new UnsupportedOperationException("PostgreSQL not implemented yet");
    }
}
