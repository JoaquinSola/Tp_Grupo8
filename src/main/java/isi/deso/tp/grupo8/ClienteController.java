/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;
import java.util.Set;

public class ClienteController {
    private final ClienteDAO clienteMemory;
    private int contadorID = 1; // Generador de ID

    public ClienteController(ClienteDAO clienteMemory) {
        this.clienteMemory = clienteMemory;
    }

    private long generarIdCliente() {
        return contadorID++;
    }

    public void crearNuevoCliente(String cuit, String email, String direccion, Coordenada coordenadas, String alias, String cbu) {
        long id = generarIdCliente(); // Generación automática del ID
        Cliente cliente = new Cliente(id, cuit, email, direccion, coordenadas, alias, cbu);
        clienteMemory.crearCliente(cliente);
    }

    public Cliente buscarCliente(long id) {
        return clienteMemory.buscarCliente(id);
    }

    public void modificarCliente(long id, String cuit, String email, String direccion, Coordenada coordenadas, String alias, String cbu) {
        Cliente cliente = new Cliente(id, cuit, email, direccion, coordenadas, alias, cbu);
        clienteMemory.actualizarCliente(cliente);
    }

    public void eliminarCliente(long id) {
        clienteMemory.eliminarCliente(id);
    }

    public Set<Cliente> obtenerListaClientes() {
        return clienteMemory.listarClientes();
    }
}
