/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;
import java.util.Set;
public interface ItemMenuDAO {
    void crearItem(ItemMenu item);
    ItemMenu buscarItem(int id);
    void actualizarItem(ItemMenu item);
    void eliminarItem(int id);
    Set<ItemMenu> listarItems();
}