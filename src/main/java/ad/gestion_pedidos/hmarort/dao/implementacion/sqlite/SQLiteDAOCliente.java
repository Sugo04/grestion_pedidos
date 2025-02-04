package ad.gestion_pedidos.hmarort.dao.implementacion.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.util.QueryUtils;

import ad.gestion_pedidos.hmarort.dao.interfaces.DAOCliente;
import ad.gestion_pedidos.hmarort.database_config.DatabaseConfig;
import ad.gestion_pedidos.hmarort.models.Cliente;
import ad.gestion_pedidos.hmarort.utils.QueryUtil;

public class SQLiteDAOCliente implements DAOCliente {

    private static final Logger logger = LoggerFactory.getLogger(SQLiteDAOCliente.class);
    private final DatabaseConfig databaseConfig;

    public SQLiteDAOCliente(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
        logger.debug("SQLiteClienteDAO inicializado");
    }

    @Override
    public void actualizarCliente(Cliente cliente) throws Exception {
        try (Connection conn = databaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(QueryUtil.UPDATE_CLIENTE)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefono());
            stmt.setInt(4, cliente.getIdZonaEnvio());
            stmt.setInt(5, cliente.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("La actualización del cliente falló, ninguna fila afectada.");
            }
        }
    }

    @Override
    public Cliente buscarCliente(int id) throws Exception {
        logger.debug("Buscando cliente con ID: {}", id);
        try (Connection conn = databaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(QueryUtil.SELECT_CLIENTE_BY_ID)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = extractClienteFromResultSet(rs);
                    logger.debug("Cliente encontrado: {}", cliente.getNombre());
                    return cliente;
                }
            }
            logger.debug("No se encontró cliente con ID: {}", id);
            return null;
        } catch (SQLException e) {
            logger.error("Error al buscar cliente por ID: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Cliente buscarClientePorZona(int idZona) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double calcularFacturacionCliente(int idCliente) throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void eliminarCliente(int id) throws Exception {
        try (Connection conn = databaseConfig.getConnection()) {

            try (PreparedStatement stmt = conn.prepareStatement(QueryUtil.DELETE_CLIENTE)) {
                stmt.setInt(1, id);
                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new Exception("El borrado del cliente falló, ninguna fila afectada.");
                }
            }
        }
    }

    @Override
    public void insertarCliente(Cliente cliente) throws Exception {
        logger.debug("Intentando insertar cliente: {}", cliente.getNombre());
        try (Connection conn = databaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(QueryUtil.INSERT_CLIENTE,
                        Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefono());
            stmt.setInt(4, cliente.getIdZonaEnvio());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Fallo al crear cliente: ninguna fila afectada");
                throw new Exception("La creación del cliente falló, ninguna fila afectada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cliente.setId(generatedKeys.getInt(1));
                    logger.info("Cliente insertado correctamente con ID: {}", cliente.getId());
                } else {
                    logger.error("Fallo al crear cliente: no se obtuvo ID");
                    throw new Exception("La creación del cliente falló, no se obtuvo el ID.");
                }
            }
        } catch (SQLException e) {
            logger.error("Error al insertar cliente: {}", e.getMessage());
            throw e;
        }

    }

    @Override
    public List<Cliente> listarClientes() throws Exception {
        List<Cliente> clientes = new ArrayList<>();

        try (Connection conn = databaseConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(QueryUtil.SELECT_ALL_CLIENTES)) {

            while (rs.next()) {
                clientes.add(extractClienteFromResultSet(rs));
            }
        }

        return clientes;
    }

    private Cliente extractClienteFromResultSet(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id_cliente"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setEmail(rs.getString("email"));
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setIdZonaEnvio(rs.getInt("id_zona"));
        return cliente;
    }
}
