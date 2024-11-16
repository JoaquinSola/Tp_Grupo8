/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;
import java.util.Set;

public interface ClienteDAO {
    void crearCliente(Cliente cliente);
    Cliente buscarCliente(long id);
    void actualizarCliente(Cliente cliente);
    void eliminarCliente(long id);
    Set<Cliente> listarClientes();
}