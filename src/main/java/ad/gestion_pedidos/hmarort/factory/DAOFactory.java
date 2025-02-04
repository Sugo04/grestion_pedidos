package ad.gestion_pedidos.hmarort.factory;

import java.sql.Connection;

import ad.gestion_pedidos.hmarort.dao.interfaces.DAOCliente;
import ad.gestion_pedidos.hmarort.dao.interfaces.DAOPedido;
import ad.gestion_pedidos.hmarort.dao.interfaces.DAOZonaEnvio;
import ad.gestion_pedidos.hmarort.database_config.DatabaseConfig;
import ad.gestion_pedidos.hmarort.database_config.DatabaseType;

/**
 * Abstract factory para la creación de objetos DAO.
 */
public abstract class DAOFactory {
    
    protected DatabaseConfig dbConfig;

    /**
     * Constructor protegido que recibe la configuración de la base de datos.
     * @param databaseConfig
     */
    protected DAOFactory(DatabaseConfig databaseConfig) {
        this.dbConfig = databaseConfig;
    }

    /**
     * Obtiene una instancia de DAOFactory según el tipo de base de datos.
     * @param dbType
     * @param config
     * @return
     */
    public static DAOFactory getDAOFactory(DatabaseType dbType, DatabaseConfig config) {
        return switch (dbType) {
            case SQLITE -> new SQLiteDAOFactory(config);
            case MYSQL -> throw new UnsupportedOperationException("MySQL no implementado aún");
            case POSTGRESQL -> throw new UnsupportedOperationException("PostgreSQL no implementado aún");
        };
    }

    /**
     * Crea un objeto DAOCliente.
     * @return
     */
    public abstract DAOCliente createClienteDAO();

    /**
     * Crea un objeto DAOPedido.
     * @return
     */
    public abstract DAOPedido createPedidoDAO();

    /**
     * Crea un objeto DAOZonaEnvio.
     * @return
     */
    public abstract DAOZonaEnvio createZonaEnvioDAO();

    /**
     * Obtiene una conexión a la base de datos.
     * @return
     * @throws Exception
     */
    public Connection getConnection() throws Exception {
        return dbConfig.getConnection();
    }
}
