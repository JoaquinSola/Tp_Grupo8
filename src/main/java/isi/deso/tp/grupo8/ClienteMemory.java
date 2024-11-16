/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ClienteMemory implements ClienteDAO {
    private Set<Cliente> clientes = new HashSet<>();

    @Override
    public void crearCliente(Cliente cliente) {
        // Primero, agregamos el cliente a la lista en memoria (si lo necesitas)
        clientes.add(cliente);

        // Ahora, agregamos el cliente a la base de datos
        Connection conexion = null;
        PreparedStatement ps = null;

        try {
            // Obtener la conexión
            conexion = ConexionDB.getConnection();
            
            // Sentencia SQL para insertar un nuevo cliente
            String sql = "INSERT INTO Cliente (cuit, email, direccion, alias, cbu) VALUES (?, ?, ?, ?, ?)";
            ps = conexion.prepareStatement(sql);
            
            // Asignamos los valores del cliente a la consulta
            ps.setString(1, cliente.getCuit());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getDireccion());
            ps.setString(4, cliente.getAlias());
            ps.setString(5, cliente.getCbu());
            
            // Asegúrate de obtener el ID de la coordenada correctamente

            // Ejecutamos la consulta
            ps.executeUpdate();  // No es necesario pasar la consulta aquí, ya está en el PreparedStatement
            
        } catch (SQLException e) {
            // Manejo de excepciones, puedes mejorar el mensaje de error
            System.err.println("Error al insertar cliente en la base de datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerramos recursos
            try {
                if (ps != null) ps.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                // Si ocurre un error al cerrar los recursos
                System.err.println("Error al cerrar los recursos: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    //DEBEMOS VER QUE HACER CON COORDEDANAS
public Cliente buscarCliente(long id) {
    Cliente cliente = null;
    Connection conexion = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        conexion = ConexionDB.getConnection();
        String sql = "SELECT * FROM Cliente WHERE id_cliente = ?";
        ps = conexion.prepareStatement(sql);
        ps.setLong(1, id);

        rs = ps.executeQuery();

        if (rs.next()) {
            // Crear un objeto Cliente con los datos obtenidos, sin coordenada
            cliente = new Cliente(
                rs.getLong("id_cliente"),
                rs.getString("cuit"),
                rs.getString("email"),
                rs.getString("direccion"),
                null,  // Ya no necesitas la coordenada, pon null o ajusta si tienes otro valor.
                rs.getString("alias"),
                rs.getString("cbu")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conexion != null) conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return cliente;
}


    @Override
    public void actualizarCliente(Cliente cliente) {
        eliminarCliente(cliente.getId());
        clientes.add(cliente);
    }

    @Override
    public void eliminarCliente(long id) {
        clientes.removeIf(c -> c.getId()==(id));
    }

    @Override
    public Set<Cliente> listarClientes() {
        return clientes;
    }
}
