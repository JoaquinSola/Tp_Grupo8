/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;
import java.util.Set;
public class ItemsMenuController {
    private final ItemMenuDAO itemsMenuMemory;

    public ItemsMenuController(ItemMenuDAO itemsMenuMemory) {
        this.itemsMenuMemory = itemsMenuMemory;
    }

    public void crearPlato(String nombre, String descripcion, double precio, double calorias, boolean celiacos, boolean vegetariano) {
        Plato plato = new Plato(calorias, celiacos, vegetariano, nombre, precio, descripcion);
        itemsMenuMemory.crearItem(plato);
    }

    public void crearBebida(String nombre, String descripcion, double precio, double volumen, double alcohol) {
        Bebida bebida = new Bebida(alcohol, volumen, nombre, precio, descripcion);
        itemsMenuMemory.crearItem(bebida);
    }

    public ItemMenu buscarItem(int id) {
        return itemsMenuMemory.buscarItem(id);
    }

    public void actualizarItem(ItemMenu item) {
        itemsMenuMemory.actualizarItem(item);
    }

    public void eliminarItem(int id) {
        itemsMenuMemory.eliminarItem(id);
    }

    public Set<ItemMenu> obtenerListaItems() {
        return itemsMenuMemory.listarItems();
    }
}