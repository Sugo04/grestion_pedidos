package ad.gestion_pedidos.hmarort.dao.implementacion.sqlite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ad.gestion_pedidos.hmarort.dao.interfaces.DAOPedido;
import ad.gestion_pedidos.hmarort.database_config.DatabaseConfig;
import ad.gestion_pedidos.hmarort.models.Pedido;
import ad.gestion_pedidos.hmarort.utils.QueryUtil;

public class SQLiteDAOPedido implements DAOPedido {

    private final DatabaseConfig dbConfig;

    /**
     * Constructor que recibe la configuración de la base de datos.
     * 
     * @param databaseConfig
     */
    public SQLiteDAOPedido(DatabaseConfig databaseConfig) {
        this.dbConfig = databaseConfig;
    }

    /**
     * Agrega un nuevo pedido al sistema.
     * 
     * @param pedido Objeto Pedido con la información a insertar
     * @throws Exception Si ocurre un error durante la inserción
     */
    @Override
    public void agregarPedido(Pedido pedido) throws Exception {
        try (Connection conn = dbConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(QueryUtil.INSERT_PEDIDO,
                        Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, Date.valueOf(pedido.getFecha()));
            stmt.setDouble(2, pedido.getImporte());
            stmt.setInt(3, pedido.getIdCliente());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("La creación del pedido falló, ninguna fila afectada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pedido.setId(generatedKeys.getInt(1));
                } else {
                    throw new Exception("La creación del pedido falló, no se obtuvo el ID.");
                }
            }
        }
    }

    /**
     * Recupera un pedido específico por su ID.
     * 
     * @param id Identificador único del pedido
     * @return Objeto Pedido si existe, null en caso contrario
     * @throws Exception Si ocurre un error durante la consulta
     */
    @Override
    public Pedido obtenerPedidoPorId(int id) throws Exception {
        try (Connection conn = dbConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(QueryUtil.SELECT_PEDIDO_BY_ID)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractPedidoFromResultSet(rs);
                }
            }
        }
        return null;
    }

    /**
     * Obtiene todos los pedidos registrados en el sistema.
     */
    @Override
    public List<Pedido> obtenerTodosLosPedidos() throws Exception {
        List<Pedido> pedidos = new ArrayList<>();

        try (Connection conn = dbConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(QueryUtil.SELECT_ALL_PEDIDOS)) {

            while (rs.next()) {
                pedidos.add(extractPedidoFromResultSet(rs));
            }
        }

        return pedidos;
    }

    /**
     * Actualiza la información de un pedido existente.
     */
    @Override
    public void actualizarPedido(Pedido pedido) throws Exception {
        try (Connection conn = dbConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(QueryUtil.UPDATE_PEDIDO)) {

            stmt.setDate(1, Date.valueOf(pedido.getFecha()));
            stmt.setDouble(2, pedido.getImporte());
            stmt.setInt(3, pedido.getIdCliente());
            stmt.setInt(4, pedido.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("La actualización del pedido falló, ninguna fila afectada.");
            }
        }
    }

    /**
     * Elimina un pedido existente del sistema.
     */
    @Override
    public void eliminarPedido(int id) throws Exception {
        try (Connection conn = dbConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(QueryUtil.DELETE_PEDIDO)) {

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("El borrado del pedido falló, ninguna fila afectada.");
            }
        }
    }

    /**
     * Recupera todos los pedidos asociados a un cliente específico.
     */
    @Override
    public List<Pedido> obtenerPedidosPorCliente(int idCliente) throws Exception {
        List<Pedido> pedidos = new ArrayList<>();

        try (Connection conn = dbConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(QueryUtil.SELECT_PEDIDOS_BY_CLIENTE)) {

            stmt.setInt(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(extractPedidoFromResultSet(rs));
                }
            }
        }

        return pedidos;
    }

    /**
     * Obtiene los pedidos realizados en una fecha específica.
     */
    @Override
    public List<Pedido> obtenerPedidosPorFecha(LocalDate fecha) throws Exception {
        List<Pedido> pedidos = new ArrayList<>();

        try (Connection conn = dbConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(QueryUtil.SELECT_PEDIDOS_BY_FECHA)) {

            stmt.setDate(1, Date.valueOf(fecha));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(extractPedidoFromResultSet(rs));
                }
            }
        }

        return pedidos;
    }

    /**
     * Calcula el total facturado para un cliente específico.
     */
    @Override
    public double calcularTotalFacturadoPorCliente(int idCliente) throws Exception {
        try (Connection conn = dbConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT SUM(importe_total) as total FROM Pedidos WHERE id_cliente = ?")) {

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
     * Extrae un objeto Pedido de un ResultSet.
     * 
     * @param rs
     * @return
     * @throws Exception
     */
    private Pedido extractPedidoFromResultSet(ResultSet rs) throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId(rs.getInt("id_pedido"));
        pedido.setFecha(rs.getDate("fecha").toLocalDate());
        pedido.setImporte(rs.getDouble("importe_total"));
        pedido.setIdCliente(rs.getInt("id_cliente"));
        return pedido;
    }
}
