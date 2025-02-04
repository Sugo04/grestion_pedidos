package ad.gestion_pedidos.hmarort.dao.interfaces;

import java.time.LocalDate;
import java.util.List;

import ad.gestion_pedidos.hmarort.models.Pedido;

public interface DAOPedido {

    void insertarPedido(Pedido pedido) throws Exception;

    void eliminarPedido(Pedido pedido) throws Exception;

    void modificarPedido(Pedido pedido) throws Exception;

    Pedido buscarPedido(int id) throws Exception;

    List<Pedido> listarPedidos() throws Exception;

    List<Pedido> listarPedidosPorCliente(int idCliente) throws Exception;

    List<Pedido> listarPedidosPorFecha(LocalDate fecha) throws Exception;

    double calcularTotalPedidosPorCliente(int idCliente) throws Exception;
}
