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

    public void crearPlato(String nombre, String descripcion, double precio, double calorias, boolean celiacos, boolean vegetariano, double peso, Categoria categoria) {
        Plato plato = new Plato(calorias, celiacos, vegetariano, nombre, precio, descripcion,peso);
        itemsMenuMemory.crearItem(plato, categoria);
    }

    public void crearBebida(String nombre, String descripcion, double precio, double volumen, double alcohol, Categoria categoria) {
        Bebida bebida = new Bebida(alcohol, volumen, nombre, precio, descripcion);
        itemsMenuMemory.crearItem(bebida, categoria);
    }

    public void modificarItem(long id, String nombre, String descripcion, double precio, Categoria categoria) {
        ItemMenu item = itemsMenuMemory.buscarItem(id);
        if (item != null) {
            item.setNombre(nombre);
            item.setDesc(descripcion);
            item.setPrecio(precio);
            itemsMenuMemory.actualizarItem(item, categoria);
        }
    }

    public ItemMenu buscarItem(long id) {
        return itemsMenuMemory.buscarItem(id);
    }

    public void eliminarItem(long id) {
        itemsMenuMemory.eliminarItem(id);
    }

    public Set<ItemMenu> obtenerListaItems() {
        return itemsMenuMemory.listarItems();
    }
}