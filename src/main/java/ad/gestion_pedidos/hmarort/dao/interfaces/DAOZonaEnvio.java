package ad.gestion_pedidos.hmarort.dao.interfaces;

import java.util.List;
import ad.gestion_pedidos.hmarort.models.ZonaEnvio;

/**
 * Interfaz que define las operaciones CRUD para la gestión de zonas de envío
 * en el sistema de gestión de pedidos.
 */
public interface DAOZonaEnvio {
    /**
     * Agrega una nueva zona de envío al sistema.
     * @param zonaEnvio Objeto ZonaEnvio con la información a insertar
     * @throws Exception Si ocurre un error durante la inserción
     */
    void agregarZonaEnvio(ZonaEnvio zonaEnvio) throws Exception;

    /**
     * Recupera una zona de envío específica por su ID.
     * @param id Identificador único de la zona de envío
     * @return Objeto ZonaEnvio si existe, null en caso contrario
     * @throws Exception Si ocurre un error durante la consulta
     */
    ZonaEnvio obtenerZonaEnvioPorId(int id) throws Exception;

    /**
     * Obtiene todas las zonas de envío registradas en el sistema.
     * @return Lista de todas las zonas de envío
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<ZonaEnvio> obtenerTodasLasZonas() throws Exception;

    /**
     * Actualiza la información de una zona de envío existente.
     * @param zonaEnvio Objeto ZonaEnvio con la información actualizada
     * @throws Exception Si ocurre un error durante la actualización
     */
    void actualizarZonaEnvio(ZonaEnvio zonaEnvio) throws Exception;

    /**
     * Elimina una zona de envío del sistema.
     * @param id Identificador único de la zona a eliminar
     * @throws Exception Si ocurre un error durante la eliminación
     */
    void eliminarZonaEnvioPorId(int id) throws Exception;
}