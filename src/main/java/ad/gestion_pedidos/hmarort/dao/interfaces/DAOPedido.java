package ad.gestion_pedidos.hmarort.dao.interfaces;

import java.time.LocalDate;
import java.util.List;
import ad.gestion_pedidos.hmarort.models.Pedido;

/**
 * Interfaz que define las operaciones CRUD y consultas específicas para la gestión de pedidos
 * en el sistema de gestión de pedidos.
 */
public interface DAOPedido {
    /**
     * Agrega un nuevo pedido al sistema.
     * @param pedido Objeto Pedido con la información a insertar
     * @throws Exception Si ocurre un error durante la inserción
     */
    void agregarPedido(Pedido pedido) throws Exception;

    /**
     * Elimina un pedido existente del sistema.
     * @param id Identificador único del pedido a eliminar
     * @throws Exception Si ocurre un error durante la eliminación
     */
    void eliminarPedido(int id) throws Exception;

    /**
     * Actualiza la información de un pedido existente.
     * @param pedido Objeto Pedido con la información actualizada
     * @throws Exception Si ocurre un error durante la actualización
     */
    void actualizarPedido(Pedido pedido) throws Exception;

    /**
     * Recupera un pedido específico por su ID.
     * @param id Identificador único del pedido
     * @return Objeto Pedido si existe, null en caso contrario
     * @throws Exception Si ocurre un error durante la consulta
     */
    Pedido obtenerPedidoPorId(int id) throws Exception;

    /**
     * Obtiene todos los pedidos registrados en el sistema.
     * @return Lista de todos los pedidos
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Pedido> obtenerTodosLosPedidos() throws Exception;

    /**
     * Recupera todos los pedidos asociados a un cliente específico.
     * @param idCliente Identificador único del cliente
     * @return Lista de pedidos del cliente
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Pedido> obtenerPedidosPorCliente(int idCliente) throws Exception;

    /**
     * Obtiene los pedidos realizados en una fecha específica.
     * @param fecha Fecha de los pedidos a buscar
     * @return Lista de pedidos de la fecha especificada
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Pedido> obtenerPedidosPorFecha(LocalDate fecha) throws Exception;

    /**
     * Calcula el total facturado para un cliente específico.
     * @param idCliente Identificador único del cliente
     * @return Monto total facturado
     * @throws Exception Si ocurre un error durante el cálculo
     */
    double calcularTotalFacturadoPorCliente(int idCliente) throws Exception;
}