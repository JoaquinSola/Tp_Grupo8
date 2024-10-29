/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;

import java.util.*;

public class ClienteMemory implements ClienteDAO {
    private Set<Cliente> clientes = new HashSet<>();

    @Override
    public void crearCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    @Override
    public Cliente buscarCliente(String id) {
        return clientes.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void actualizarCliente(Cliente cliente) {
        eliminarCliente(cliente.getId());
        clientes.add(cliente);
    }

    @Override
    public void eliminarCliente(String id) {
        clientes.removeIf(c -> c.getId().equals(id));
    }

    @Override
    public Set<Cliente> listarClientes() {
        return clientes;
    }
}
