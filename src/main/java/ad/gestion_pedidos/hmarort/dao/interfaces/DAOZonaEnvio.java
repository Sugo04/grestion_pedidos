package ad.gestion_pedidos.hmarort.dao.interfaces;

import java.util.List;
import ad.gestion_pedidos.hmarort.models.ZonaEnvio;

/**
 * Interfaz para la gestión de operaciones CRUD de zonas de envío.
 */
public interface DAOZonaEnvio {

    void agregarZonaEnvio(ZonaEnvio zonaEnvio) throws Exception;

    ZonaEnvio obtenerZonaEnvioPorId(int id) throws Exception;

    List<ZonaEnvio> obtenerTodasLasZonas() throws Exception;

    void actualizarZonaEnvio(ZonaEnvio zonaEnvio) throws Exception;

    void eliminarZonaEnvioPorId(int id) throws Exception;
}