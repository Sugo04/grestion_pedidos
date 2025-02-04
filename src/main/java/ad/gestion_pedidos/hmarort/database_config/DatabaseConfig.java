package ad.gestion_pedidos.hmarort.database_config;

import java.sql.Connection;
import javax.sql.DataSource;

public interface DatabaseConfig {
    Connection getConnection() throws Exception;
    DataSource getDataSource();
    void closeP();
    String getUrl();
    String getClienName();
    String getPassword();
    int getMaxPoolSize();
    int getMinPoolSize();

    static DatabaseConfig forType(DatabaseType type, String url, String username, String password) {
        return switch (type) {
            case SQLITE -> new SQLiteConfig(url);
            case MYSQL -> throw new UnsupportedOperationException("MySQL not implemented yet");
            case POSTGRESQL -> throw new UnsupportedOperationException("PostgreSQL not implemented yet");
        };
    }

}
