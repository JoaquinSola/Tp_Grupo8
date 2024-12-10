/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class VendedorMemory implements VendedorDAO {
    private Connection connection;
     public VendedorMemory() throws SQLException {
        this.connection = ConexionDB.getConnection();
    }

        @Override
     public void crearVendedor(Vendedor vendedor) {
    String sql = "INSERT INTO Vendedor (nombre, direccion, id_coordenada) VALUES (?, ?, ?)";
    try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setString(1, vendedor.getNombre());
        stmt.setString(2, vendedor.getDireccion());
        stmt.setLong(3, vendedor.getCoor().getId()); // Usar el ID de la coordenada
        stmt.executeUpdate();

        // Obtener el ID generado automáticamente para el vendedor
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                vendedor.setId(generatedKeys.getLong(1));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
      @Override
public Vendedor buscarVendedor(long id) {
    String sql = "SELECT v.id_vendedor, v.nombre, v.direccion, v.id_coordenada, " +
                 "c.latitud, c.longitud " +
                 "FROM Vendedor v " +
                 "LEFT JOIN Coordenadas c ON v.id_coordenada = c.id_coordenada " +
                 "WHERE v.id_vendedor = ?";
    Vendedor vendedor = null;

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setLong(1, id);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                vendedor = mapearVendedor(rs);
            }
        }
    } catch (SQLException e) {
        // Manejo del error: puedes usar un logger aquí
        System.err.println("Error al buscar el vendedor con ID: " + id);
        e.printStackTrace();
    }

    return vendedor;
}

// Método auxiliar para mapear el ResultSet a un objeto Vendedor
private Vendedor mapearVendedor(ResultSet rs) throws SQLException {
    Vendedor vendedor = new Vendedor();
    vendedor.setId(rs.getLong("id_vendedor"));
    vendedor.setNombre(rs.getString("nombre"));
    vendedor.setDireccion(rs.getString("direccion"));

    Coordenada coordenada = new Coordenada();
    coordenada.setId(rs.getLong("id_coordenada"));
    coordenada.setLatitud(rs.getDouble("latitud"));
    coordenada.setLongitud(rs.getDouble("longitud"));
    vendedor.setCoordenada(coordenada);

    return vendedor;
}
    

// Método para verificar si una coordenada existe en la base de datos
private boolean existeCoordenada(long idCoordenada) {
    String sql = "SELECT 1 FROM Coordenadas WHERE id_coordenada = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setLong(1, idCoordenada);
        try (ResultSet rs = stmt.executeQuery()) {
            return rs.next(); // Retorna true si encuentra la coordenada
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Error al verificar la existencia de la coordenada: " + e.getMessage());
    }
}


// Método para insertar una nueva coordenada
private void insertarCoordenada(Coordenada coordenada) {
    String sql = "INSERT INTO Coordenadas (id_coordenada, latitud, longitud) VALUES (?, ?, ?)";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setLong(1, coordenada.getId());
        stmt.setDouble(2, coordenada.getLatitud());
        stmt.setDouble(3, coordenada.getLongitud());
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Error al insertar la coordenada: " + e.getMessage());
    }
}


@Override
public boolean modificarVendedor(Vendedor vendedor) {
    boolean vendedorActualizado = false;
    boolean coordenadaActualizada = true; // Por defecto asumimos éxito

    try {
        connection.setAutoCommit(false);
    
        // Actualizar coordenada (si existe)
        if (vendedor.getCoor() != null) {
            coordenadaActualizada = modificarCoordenada(vendedor.getCoor());
        }

        // Actualizar vendedor
        String sql = "UPDATE Vendedor SET nombre = ?, direccion = ? WHERE id_vendedor = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, vendedor.getNombre());
            stmt.setString(2, vendedor.getDireccion());
            stmt.setLong(3, vendedor.getId());

            int filasActualizadas = stmt.executeUpdate();
            vendedorActualizado = (filasActualizadas > 0);
        }

        // Confirmar transacción si todo fue exitoso
        if (vendedorActualizado && coordenadaActualizada) {
            connection.commit();
        } else {
            connection.rollback();
        }

    } catch (SQLException e) {
        try {
            connection.rollback();
        } catch (SQLException rollbackEx) {
            System.err.println("Error al hacer rollback");
            rollbackEx.printStackTrace();
        }
        System.err.println("Error al modificar el vendedor con ID: " + vendedor.getId());
        e.printStackTrace();
    } finally {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return vendedorActualizado && coordenadaActualizada;
}
public boolean modificarCoordenada(Coordenada coordenada) {
    
    if (coordenada == null) {
        throw new IllegalArgumentException("La coordenada no puede ser nula.");
    }

    if (Double.isNaN(coordenada.getLatitud()) || Double.isNaN(coordenada.getLongitud())) {
        throw new IllegalArgumentException("Latitud o longitud no son válidos.");
    }

    String sql = "UPDATE Coordenadas SET latitud = ?, longitud = ? WHERE id_coordenada = ?";
    boolean actualizada = false;

    try {
        if (connection == null || connection.isClosed()) {
            throw new IllegalStateException("La conexión a la base de datos no está disponible.");
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, coordenada.getLatitud());
            stmt.setDouble(2, coordenada.getLongitud());
            stmt.setLong(3, coordenada.getId());

            int filasActualizadas = stmt.executeUpdate();
            actualizada = (filasActualizadas > 0);

            if (!actualizada) {
                System.err.println("No se encontró ninguna coordenada con ID: " + coordenada.getId());
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al modificar la coordenada con ID: " + coordenada.getId());
        e.printStackTrace();
    }

    return actualizada;
}

public void agregarItemMenu(long idVendedor, long idItemMenu) {
    String sql = "INSERT INTO vendedor_itemmenu (id_vendedor, id_itemMenu) VALUES (?, ?)";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setLong(1, idVendedor);
        stmt.setLong(2, idItemMenu);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    @Override
   public void eliminarVendedor(long id) {
    String sql = "DELETE FROM Vendedor WHERE id_vendedor = ?";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setLong(1, id);

        int filasAfectadas = stmt.executeUpdate(); 

        if (filasAfectadas == 0) {
            throw new SQLException("No se encontró ningún vendedor con ID: " + id);
        } else {
            System.out.println("Vendedor con ID " + id + " eliminado correctamente.");
        }
    } catch (SQLException e) {
        System.err.println("Error al eliminar el vendedor con ID: " + id);
        e.printStackTrace();
    }
}
@Override
public Set<Vendedor> listarVendedores() {
    String sql = "SELECT v.id_vendedor, v.nombre, v.direccion, v.id_coordenada, " +
                 "c.latitud, c.longitud " +
                 "FROM Vendedor v " +
                 "LEFT JOIN Coordenadas c ON v.id_coordenada = c.id_coordenada";
    Set<Vendedor> vendedores = new HashSet<>(); // Usamos HashSet para garantizar unicidad

    try (PreparedStatement stmt = connection.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Vendedor vendedor = mapearVendedor(rs);
            vendedores.add(vendedor); // HashSet se encarga de evitar duplicados
        }
    } catch (SQLException e) {
        // Manejo del error: log o mensaje de consola
        System.err.println("Error al listar los vendedores");
        e.printStackTrace();
    }

    return vendedores;
}


}