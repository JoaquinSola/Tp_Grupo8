package isi.deso.tp.grupo8;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoordenadaDAO {
    private static final String INSERT_SQL = "INSERT INTO coordenadas (latitud, longitud) VALUES (?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT id, latitud, longitud FROM coordenadas WHERE id = ?";

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

    public Coordenada findById(long id) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                double latitud = rs.getDouble("latitud");
                double longitud = rs.getDouble("longitud");
                Coordenada coordenada = new Coordenada(latitud, longitud);
                coordenada.setId(rs.getLong("id"));
                return coordenada;
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar la coordenada: " + e.getMessage());
        }
        return null;
    }
}
