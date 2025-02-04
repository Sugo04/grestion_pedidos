package ad.gestion_pedidos.hmarort.dao.interfaces;

import java.util.List;
import ad.gestion_pedidos.hmarort.models.Cliente;

/**
 * Interfaz para la gestión de operaciones CRUD de clientes.
 */
public interface DAOCliente {

    /**
     * Inserta un nuevo cliente en la base de datos.
     * @param cliente Cliente a insertar.
     * @throws Exception Si ocurre un error en la inserción.
     */
    void agregarCliente(Cliente cliente) throws Exception;

    /**
     * Busca un cliente por su ID.
     * @param id Identificador del cliente.
     * @return Cliente encontrado o null si no existe.
     * @throws Exception Si ocurre un error en la búsqueda.
     */
    Cliente obtenerClientePorId(int id) throws Exception;

    /**
     * Lista todos los clientes registrados.
     * @return Lista de clientes.
     * @throws Exception Si ocurre un error en la consulta.
     */
    List<Cliente> obtenerTodosLosClientes() throws Exception;

    /**
     * Actualiza la información de un cliente.
     * @param cliente Cliente con la información actualizada.
     * @throws Exception Si ocurre un error en la actualización.
     */
    void actualizarInformacionCliente(Cliente cliente) throws Exception;

    /**
     * Elimina un cliente de la base de datos.
     * @param id Identificador del cliente a eliminar.
     * @throws Exception Si ocurre un error en la eliminación.
     */
    void eliminarClientePorId(int id) throws Exception;

    /**
     * Busca clientes por zona de envío.
     * @param idZona Identificador de la zona de envío.
     * @return Cliente encontrado o null si no existe.
     * @throws Exception Si ocurre un error en la búsqueda.
     */
    List<Cliente> obtenerClientePorZona(int idZona) throws Exception;

    /**
     * Calcula la facturación total de un cliente.
     * @param idCliente Identificador del cliente.
     * @return Monto total de facturación.
     * @throws Exception Si ocurre un error en el cálculo. 
     */
    double calcularFacturacionTotalCliente(int idCliente) throws Exception;
}