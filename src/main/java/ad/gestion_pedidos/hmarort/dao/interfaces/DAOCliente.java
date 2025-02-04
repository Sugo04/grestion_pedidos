package ad.gestion_pedidos.hmarort.dao.interfaces;

import java.util.List;

import ad.gestion_pedidos.hmarort.models.Cliente;

public interface DAOCliente {

    void insertarCliente(Cliente cliente) throws Exception;

    Cliente buscarCliente(int id) throws Exception;

    List<Cliente> listarClientes() throws Exception;

    void actualizarCliente(Cliente cliente) throws Exception;

    void eliminarCliente(int id) throws Exception;

    Cliente buscarClientePorZona(int idZona) throws Exception;

    double calcularFacturacionCliente(int idCliente) throws Exception;
}
