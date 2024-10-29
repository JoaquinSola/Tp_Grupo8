/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package isi.deso.tp.grupo8;

import java.util.Set;

public interface VendedorDAO {
    void crearVendedor(Vendedor vendedor);
    Vendedor buscarVendedor(String id);
    void actualizarVendedor(Vendedor vendedor);
    void eliminarVendedor(String id);
    Set<Vendedor> listarVendedores();
}
