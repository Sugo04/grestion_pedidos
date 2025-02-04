package ad.gestion_pedidos.hmarort.database_config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class SQLiteConfig implements DatabaseConfig{
     private static final int DEFAULT_MAX_POOL_SIZE = 10;
    private static final int DEFAULT_MIN_POOL_SIZE = 1;
    private final String url;
    private HikariDataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(SQLiteConfig.class);

    /**
     * Constructor que recibe la URL de la base de datos.
     * @param url
     */
    public SQLiteConfig(String url) {
        this.url = url;
        initializeDataSource();
    }

    /**
     * Inicializa el DataSource de HikariCP.
     */
    private void initializeDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlite:" + url);
        config.setMaximumPoolSize(DEFAULT_MAX_POOL_SIZE);
        config.setMinimumIdle(DEFAULT_MIN_POOL_SIZE);
        config.setAutoCommit(true);

        config.addDataSourceProperty("foreign_keys", "true");
        config.addDataSourceProperty("synchronous", "normal");
        config.addDataSourceProperty("journal_mode", "WAL");

        dataSource = new HikariDataSource(config);

        initializeDatabase();
    }

    /**
     * Inicializa la base de datos SQLite.
     */
    private void initializeDatabase() {
        logger.info("Iniciando inicialización de base de datos SQLite");
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            logger.debug("Leyendo script SQL de inicialización");
            String sqlScript = new String(Files.readAllBytes(Paths.get("src/main/resources/pedidos.sql")));

            String[] statements = sqlScript.split(";");

            for (String statement : statements) {
                statement = statement.trim();
                if (!statement.isEmpty()) {
                    try {
                        if (statement.startsWith("CREATE TABLE")) {
                            logger.debug("Ejecutando creación de tabla: {}", 
                                statement.substring(0, statement.indexOf("(")).trim());
                            statement = statement.replace("CREATE TABLE", "CREATE TABLE IF NOT EXISTS");
                        }
                        stmt.execute(statement);
                    } catch (SQLException e) {
                        logger.warn("Advertencia al ejecutar statement: {}", e.getMessage()); 
                    }
                }
            }
            logger.info("Base de datos inicializada correctamente");
        } catch (SQLException | IOException e) {
            logger.error("Error fatal al inicializar base de datos", e);
        }
    }

    /**
     * Obtiene una conexión a la base de datos.
     */
    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Obtiene el DataSource de la base de datos.
     */
    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * Obtiene la URL de la base de datos.
     */
    @Override
    public String getUrl() {
        return url;
    }

    /**
     * Obtiene la contraseña de la base de datos.
     */
    @Override
    public String getPassword() {
        return ""; 
    }

    /**
     * Obtiene el tamaño máximo del pool de conexiones.
     */
    @Override
    public int getMaxPoolSize() {
        return DEFAULT_MAX_POOL_SIZE;
    }

    /**
     * Obtiene el tamaño mínimo del pool de conexiones.
     */
    @Override
    public int getMinPoolSize() {
        return DEFAULT_MIN_POOL_SIZE;
    }

    /**
     * Cierra la conexión a la base de datos.
     */
    @Override
    public void closeP() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    /**
     * Obtiene el nombre de usuario de la base de datos.
     */
    @Override
    public String getClienName() {
        return "";
    }
}
