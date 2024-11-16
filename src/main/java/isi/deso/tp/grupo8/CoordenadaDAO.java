package isi.deso.tp.grupo8;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoordenadaDAO {
    private static final String INSERT_SQL = "INSERT INTO coordenada (latitud, longitud) VALUES (?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT id, latitud, longitud FROM coordenada WHERE id = ?";
    private static final String SELECT_BY_COORDS_SQL = "SELECT id FROM coordenadas WHERE latitud = ? AND longitud = ?";

    public void save(Coordenada coordenada) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            stmt.setDouble(1, coordenada.getLatitud());
            stmt.setDouble(2, coordenada.getLongitud());
            stmt.executeUpdate();

            // Obtén el ID generado automáticamente
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    coordenada.setId(generatedKeys.getLong(1)); // Asigna el ID generado a la instancia
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar la coordenada: " + e.getMessage());
        }
    }

     public Coordenada findByLatLong(double latitud, double longitud) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_COORDS_SQL)) {
            
            stmt.setDouble(1, latitud);
            stmt.setDouble(2, longitud);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                long id = rs.getLong("id");
                return new Coordenada(latitud, longitud, id);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar la coordenada: " + e.getMessage());
        }
        return null;
    }

    public Coordenada findById(long id) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                double latitud = rs.getDouble("latitud");
                double longitud = rs.getDouble("longitud");
                Coordenada coordenada = new Coordenada(latitud, longitud,1);
                coordenada.setId(rs.getLong("id"));
                return coordenada;
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar la coordenada: " + e.getMessage());
        }
        return null;
    }
    
}
