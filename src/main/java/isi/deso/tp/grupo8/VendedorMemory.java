/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;

import java.util.*;

public class VendedorMemory implements VendedorDAO {
    private Set<Vendedor> vendedores = new HashSet<>();

        @Override
      public void crearVendedor(Vendedor vendedor) {
        vendedores.add(vendedor);
    }
        @Override
    public Vendedor buscarVendedor(String id) {
        return vendedores.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    @Override
    public void actualizarVendedor(Vendedor vendedor) {
        eliminarVendedor(vendedor.getId());
        vendedores.add(vendedor);
    }
    @Override
    public void eliminarVendedor(String id) {
        vendedores.removeIf(v -> v.getId().equals(id));
    }
    @Override
    public Set<Vendedor> listarVendedores() {
        return vendedores;
    }
}