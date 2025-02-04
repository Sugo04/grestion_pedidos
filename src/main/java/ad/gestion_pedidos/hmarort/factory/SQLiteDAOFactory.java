package ad.gestion_pedidos.hmarort.factory;

import ad.gestion_pedidos.hmarort.dao.implementacion.sqlite.SQLiteDAOCliente;
import ad.gestion_pedidos.hmarort.dao.implementacion.sqlite.SQLiteDAOPedido;
import ad.gestion_pedidos.hmarort.dao.implementacion.sqlite.SQLiteDAOZonaEnvio;
import ad.gestion_pedidos.hmarort.dao.interfaces.DAOCliente;
import ad.gestion_pedidos.hmarort.dao.interfaces.DAOPedido;
import ad.gestion_pedidos.hmarort.dao.interfaces.DAOZonaEnvio;
import ad.gestion_pedidos.hmarort.database_config.DatabaseConfig;

public class SQLiteDAOFactory extends DAOFactory{

    public SQLiteDAOFactory(DatabaseConfig databaseConfig) {
        super(databaseConfig);
    }

    @Override
    public DAOCliente createClienteDAO() {
        return new SQLiteDAOCliente(this.dbConfig);
    }

    @Override
    public DAOPedido createPedidoDAO() {
        return new SQLiteDAOPedido(this.dbConfig);
    }

    @Override
    public DAOZonaEnvio createZonaEnvioDAO() {
        return new SQLiteDAOZonaEnvio(this.dbConfig);
    }
}
