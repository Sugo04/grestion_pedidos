package ad.gestion_pedidos.hmarort.database_config;

import java.sql.Connection;
import javax.sql.DataSource;

public interface DatabaseConfig {
    /**
     * Obtiene una conexión a la base de datos.
     * @return
     * @throws Exception
     */
    Connection getConnection() throws Exception;
    /**
     * Obtiene el DataSource de la base de datos.
     * @return
     */
    DataSource getDataSource();
    /**
     * Cierra la conexión a la base de datos.
     */
    void closeP();
    /**
     * Obtiene la URL de la base de datos.
     * @return
     */
    String getUrl();
    /**
     * Obtiene el nombre de usuario de la base de datos.
     * @return
     */
    String getClienName();
    /**
     * Obtiene la contraseña de la base de datos.
     * @return
     */
    String getPassword();
    /**
     * Obtiene el tamaño máximo del pool de conexiones.
     * @return
     */
    int getMaxPoolSize();
    /**
     *  Obtiene el tamaño mínimo del pool de conexiones.
     * @return
     */
    int getMinPoolSize();

    /**
     * Crea una instancia de DatabaseConfig según el tipo de base de datos.
     * @param type
     * @param url
     * @param username
     * @param password
     * @return
     */
    static DatabaseConfig forType(DatabaseType type, String url, String username, String password) {
        return switch (type) {
            case SQLITE -> new SQLiteConfig(url);
            case MYSQL -> throw new UnsupportedOperationException("MySQL not implemented yet");
            case POSTGRESQL -> throw new UnsupportedOperationException("PostgreSQL not implemented yet");
        };
    }

}
