package ad.gestion_pedidos.hmarort.dao.implementacion.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ad.gestion_pedidos.hmarort.dao.interfaces.DAOZonaEnvio;
import ad.gestion_pedidos.hmarort.database_config.DatabaseConfig;
import ad.gestion_pedidos.hmarort.models.ZonaEnvio;
import ad.gestion_pedidos.hmarort.utils.QueryUtil;

public class SQLiteDAOZonaEnvio implements DAOZonaEnvio{
    private final DatabaseConfig databaseConfig;

    public SQLiteDAOZonaEnvio(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Override
    public void insert(ZonaEnvio zonaEnvio) throws Exception {
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(QueryUtil.INSERT_ZONA, 
                     Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, zonaEnvio.getNombre());
            stmt.setDouble(2, zonaEnvio.getPrecio());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("La creación de la zona falló, ninguna fila afectada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    zonaEnvio.setId(generatedKeys.getInt(1));
                } else {
                    throw new Exception("La creación de la zona falló, no se obtuvo el ID.");
                }
            }
        }
    }

    @Override
    public ZonaEnvio getById(int id) throws Exception {
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(QueryUtil.SELECT_ZONA_BY_ID)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractZonaFromResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<ZonaEnvio> getAll() throws Exception {
        List<ZonaEnvio> zonas = new ArrayList<>();
        
        try (Connection conn = databaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(QueryUtil.SELECT_ALL_ZONAS)) {
            
            while (rs.next()) {
                zonas.add(extractZonaFromResultSet(rs));
            }
        }
        
        return zonas;
    }

    @Override
    public void update(ZonaEnvio zonaEnvio) throws Exception {
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(QueryUtil.UPDATE_ZONA)) {
            
            stmt.setString(1, zonaEnvio.getNombre());
            stmt.setDouble(2, zonaEnvio.getPrecio());
            stmt.setInt(3, zonaEnvio.getId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("La actualización de la zona falló, ninguna fila afectada.");
            }
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(QueryUtil.DELETE_ZONA)) {
            
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("El borrado de la zona falló, ninguna fila afectada.");
            }
        }
    }

    private ZonaEnvio extractZonaFromResultSet(ResultSet rs) throws SQLException {
        ZonaEnvio zona = new ZonaEnvio();
        zona.setId(rs.getInt("id_zona"));
        zona.setNombre(rs.getString("nombre_zona"));
        zona.setPrecio(rs.getDouble("tarifa_envio"));
        return zona;
    }
}
