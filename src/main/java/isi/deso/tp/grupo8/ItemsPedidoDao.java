/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package isi.deso.tp.grupo8;

import java.util.Set;

public interface ItemsPedidoDao {
    void filtrado();
    Set<ItemPedido> ordenarPorCriterio();
    Set<ItemPedido> busquedaPorRangodePecios(double min, double max) throws ItemNoEncontradoException ;
    Set<ItemPedido> buscarPorRestaurante(Vendedor v) throws ItemNoEncontradoException;
}
