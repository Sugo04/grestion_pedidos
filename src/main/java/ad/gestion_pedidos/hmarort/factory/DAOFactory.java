package ad.gestion_pedidos.hmarort.factory;

import java.sql.Connection;

import ad.gestion_pedidos.hmarort.dao.interfaces.DAOCliente;
import ad.gestion_pedidos.hmarort.dao.interfaces.DAOPedido;
import ad.gestion_pedidos.hmarort.dao.interfaces.DAOZonaEnvio;
import ad.gestion_pedidos.hmarort.database_config.DatabaseConfig;
import ad.gestion_pedidos.hmarort.database_config.DatabaseType;

public abstract class DAOFactory {
    protected DatabaseConfig dbConfig;

    protected DAOFactory(DatabaseConfig databaseConfig) {
        this.dbConfig = databaseConfig;
    }

    public static DAOFactory getDAOFactory(DatabaseType dbType, DatabaseConfig config) {
        return switch (dbType) {
            case SQLITE -> new SQLiteDAOFactory(config);
            case MYSQL -> throw new UnsupportedOperationException("MySQL no implementado aún");
            case POSTGRESQL -> throw new UnsupportedOperationException("PostgreSQL no implementado aún");
        };
    }

    public abstract DAOCliente createClienteDAO();

    public abstract DAOPedido createPedidoDAO();

    public abstract DAOZonaEnvio createZonaEnvioDAO();

    public Connection getConnection() throws Exception {
        return dbConfig.getConnection();
    }
}
