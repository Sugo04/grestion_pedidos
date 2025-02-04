package ad.gestion_pedidos.hmarort.dao.interfaces;

import java.util.List;

import ad.gestion_pedidos.hmarort.models.ZonaEnvio;

public interface DAOZonaEnvio {

    void insert(ZonaEnvio zonaEnvio) throws Exception;

    ZonaEnvio getById(int id) throws Exception;

    List<ZonaEnvio> getAll() throws Exception;

    void update(ZonaEnvio zonaEnvio) throws Exception;

    void delete(int id) throws Exception;
}
