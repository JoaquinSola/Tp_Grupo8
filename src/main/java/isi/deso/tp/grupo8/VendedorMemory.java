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
        return null;
    }
    @Override
    public void actualizarVendedor(Vendedor vendedor) {
        
    }
    @Override
    public void eliminarVendedor(long id) {
        
    }
    @Override
    public Set<Vendedor> listarVendedores() {
        return null;
    }
}