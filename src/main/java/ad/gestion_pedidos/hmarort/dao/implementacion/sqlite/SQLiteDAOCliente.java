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

/**
 * Implementación de DAOCliente para SQLite.
 */
public class SQLiteDAOCliente implements DAOCliente {

    private static final Logger LOGGER = LoggerFactory.getLogger(SQLiteDAOCliente.class);
    private final DatabaseConfig dbConfig;

    /**
     * Constructor que recibe la configuración de la base de datos.
     * @param dbConfig Configuración de la base de datos.
     */
    public SQLiteDAOCliente(DatabaseConfig dbConfig) {
        this.dbConfig = dbConfig;
        LOGGER.debug("ClienteDAOSQLite inicializado");
    }

    /**
     * Actualiza la información de un cliente en la base de datos.
     * @param cliente Cliente con la información actualizada.
     * @throws Exception Si ocurre un error en la actualización.
     */
    @Override
    public void actualizarInformacionCliente(Cliente cliente) throws Exception {
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(QueryUtil.UPDATE_CLIENTE)) {
            
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefono());
            stmt.setInt(4, cliente.getIdZonaEnvio());
            stmt.setInt(5, cliente.getId());
            
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new Exception("La actualización del cliente falló, ninguna fila afectada.");
            }
        }
    }

    /**
     * Busca un cliente por su ID.
     * @param id ID del cliente a buscar.
     * @return Cliente encontrado o null si no existe.
     * @throws Exception Si ocurre un error en la búsqueda.
     */
    @Override
    public Cliente obtenerClientePorId(int id) throws Exception {
        LOGGER.debug("Buscando cliente con ID: {}", id);
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(QueryUtil.SELECT_CLIENTE_BY_ID)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = extraerDesdeResultSet(rs);
                    LOGGER.debug("Cliente encontrado: {}", cliente.getNombre());
                    return cliente;
                }
            }
            LOGGER.debug("No se encontró cliente con ID: {}", id);
            return null;
        } catch (SQLException e) {
            LOGGER.error("Error al buscar cliente por ID: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Cliente> obtenerClientePorZona(int idZona) throws Exception {
        List<Cliente> clientes = new ArrayList<>();

        try (Connection conn = dbConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(QueryUtil.SELECT_CLIENTES_BY_ZONA)) {

            stmt.setInt(1, idZona);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    clientes.add(extraerDesdeResultSet(rs));
                }
            }
        }

        return clientes;
    }

    @Override
    public double calcularFacturacionTotalCliente(int idCliente) throws Exception {
        try (Connection conn = dbConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(QueryUtil.SELECT_TOTAL_CLIENTE)) {

            stmt.setInt(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        }
        return 0.0;
    }

    /**
     * Elimina un cliente por su ID.
     * @param id ID del cliente a eliminar.
     * @throws Exception Si ocurre un error en la eliminación.
     */
    @Override
    public void eliminarClientePorId(int id) throws Exception {
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(QueryUtil.DELETE_CLIENTE)) {
            
            stmt.setInt(1, id);
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new Exception("El borrado del cliente falló, ninguna fila afectada.");
            }
        }
    }

    /**
     * Inserta un nuevo cliente en la base de datos.
     * @param cliente Cliente a insertar.
     * @throws Exception Si ocurre un error en la inserción.
     */
    @Override
    public void agregarCliente(Cliente cliente) throws Exception {
        LOGGER.debug("Intentando insertar cliente: {}", cliente.getNombre());
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(QueryUtil.INSERT_CLIENTE,
                     Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefono());
            stmt.setInt(4, cliente.getIdZonaEnvio());
            
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas == 0) {
                LOGGER.error("Fallo al crear cliente: ninguna fila afectada");
                throw new Exception("La creación del cliente falló, ninguna fila afectada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cliente.setId(generatedKeys.getInt(1));
                    LOGGER.info("Cliente insertado correctamente con ID: {}", cliente.getId());
                } else {
                    LOGGER.error("Fallo al crear cliente: no se obtuvo ID");
                    throw new Exception("La creación del cliente falló, no se obtuvo el ID.");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error al insertar cliente: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Obtiene la lista de todos los clientes.
     * @return Lista de clientes.
     * @throws Exception Si ocurre un error al obtener la lista.
     */
    @Override
    public List<Cliente> obtenerTodosLosClientes() throws Exception {
        List<Cliente> clientes = new ArrayList<>();
        
        try (Connection conn = dbConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(QueryUtil.SELECT_ALL_CLIENTES)) {
            
            while (rs.next()) {
                clientes.add(extraerDesdeResultSet(rs));
            }
        }
        return clientes;
    }

    /**
     * Extrae un objeto Cliente desde un ResultSet.
     * @param rs ResultSet con la información del cliente.
     * @return Objeto Cliente.
     * @throws SQLException Si ocurre un error en la extracción.
     */
    private Cliente extraerDesdeResultSet(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id_cliente"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setEmail(rs.getString("email"));
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setIdZonaEnvio(rs.getInt("id_zona"));
        return cliente;
    }
}