/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;
import java.util.HashSet;
import java.util.Set;

public class VendedorController {
    private final VendedorDAO vendedorMemory;
    private int contadorID = 1; // Para generar IDs únicos

    public VendedorController(VendedorDAO vendedorMemory) {
        this.vendedorMemory = vendedorMemory;
    }

    private long generarIdVendedor() {
        return contadorID++; // Ejemplo: VEN-001
    }

public void crearNuevoVendedor(String nombre, String direccion, Coordenada coordenadas) {
    if (coordenadas.getId() <= 0) {
        throw new IllegalArgumentException("La coordenada debe tener un ID válido antes de crear el vendedor.");
    }
    long id = generarIdVendedor(); // Genera el ID automáticamente para el vendedor
    Vendedor vendedor = new Vendedor(id, nombre, direccion, coordenadas, new HashSet<>());
    vendedorMemory.crearVendedor(vendedor); // Guarda el vendedor en la base de datos
}

    public Vendedor buscarVendedor(long id) {
        return vendedorMemory.buscarVendedor(id);
    }

    public void modificarVendedor(long id, String nombre, String direccion, Coordenada coordenadas) {
        Vendedor vendedor = new Vendedor(id, nombre, direccion, coordenadas, new HashSet<>());
        vendedorMemory.actualizarVendedor(vendedor);
    }

    public void eliminarVendedor(long id) {
        vendedorMemory.eliminarVendedor(id);
    }

    public Set<Vendedor> obtenerListaVendedores() {
        return vendedorMemory.listarVendedores();
    }
}
