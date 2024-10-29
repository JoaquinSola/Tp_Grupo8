/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;
import java.util.Set;
import java.util.HashSet;

public class VendedorController {
    private final VendedorDAO vendedorMemory;
    private int contadorID = 1; // Para generar IDs únicos

    public VendedorController(VendedorDAO vendedorMemory) {
        this.vendedorMemory = vendedorMemory;
    }

    private String generarIdVendedor() {
        return "VEN-" + String.format("%03d", contadorID++); // Ejemplo: VEN-001
    }

    public void crearNuevoVendedor(String nombre, String direccion, Coordenada coordenadas) {
        String id = generarIdVendedor(); // Genera el ID automáticamente
        Vendedor vendedor = new Vendedor(id, nombre, direccion, coordenadas, new HashSet<>());
        vendedorMemory.crearVendedor(vendedor);
    }

    public Vendedor buscarVendedor(String id) {
        return vendedorMemory.buscarVendedor(id);
    }

    public void modificarVendedor(String id, String nombre, String direccion, Coordenada coordenadas) {
        Vendedor vendedor = new Vendedor(id, nombre, direccion, coordenadas, new HashSet<>());
        vendedorMemory.actualizarVendedor(vendedor);
    }

    public void eliminarVendedor(String id) {
        vendedorMemory.eliminarVendedor(id);
    }

    public Set<Vendedor> obtenerListaVendedores() {
        return vendedorMemory.listarVendedores();
    }
}
