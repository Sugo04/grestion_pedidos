package ad.gestion_pedidos.hmarort.dao.implementacion.sqlite;

import java.time.LocalDate;
import java.util.List;

import ad.gestion_pedidos.hmarort.dao.interfaces.DAOPedido;
import ad.gestion_pedidos.hmarort.models.Pedido;

public class SQLiteDAOPedido implements DAOPedido{

    @Override
    public Pedido buscarPedido(int id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double calcularTotalPedidosPorCliente(int idCliente) throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void eliminarPedido(Pedido pedido) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void insertarPedido(Pedido pedido) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<Pedido> listarPedidos() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Pedido> listarPedidosPorCliente(int idCliente) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Pedido> listarPedidosPorFecha(LocalDate fecha) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void modificarPedido(Pedido pedido) throws Exception {
        // TODO Auto-generated method stub
        
    }
    
}
