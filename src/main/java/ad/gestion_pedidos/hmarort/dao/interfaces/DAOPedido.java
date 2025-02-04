package ad.gestion_pedidos.hmarort.dao.interfaces;

import java.time.LocalDate;
import java.util.List;
import ad.gestion_pedidos.hmarort.models.Pedido;

/**
 * Interfaz para la gestión de operaciones CRUD de pedidos.
 */
public interface DAOPedido {

    void agregarPedido(Pedido pedido) throws Exception;

    void eliminarPedido(int id) throws Exception;

    void actualizarPedido(Pedido pedido) throws Exception;

    Pedido obtenerPedidoPorId(int id) throws Exception;

    List<Pedido> obtenerTodosLosPedidos() throws Exception;

    List<Pedido> obtenerPedidosPorCliente(int idCliente) throws Exception;

    List<Pedido> obtenerPedidosPorFecha(LocalDate fecha) throws Exception;

    double calcularTotalFacturadoPorCliente(int idCliente) throws Exception;
}