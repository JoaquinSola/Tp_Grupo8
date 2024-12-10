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
public void modificarItem(long id, String nombre, String descripcion, double precio) {
    ItemMenu item = buscarItem(id); // Buscar el ítem por ID

    if (item != null) {
        item.nombre = nombre;  // Modificar el nombre
        item.setDesc(descripcion);  // Usar el setter para descripción
        item.precio = precio;  // Modificar el precio
        itemsMenuMemory.actualizarItem(item);  // Guardar los cambios
    } else {
        throw new IllegalArgumentException("El ítem con ID " + id + " no existe.");
    }
}
    public ItemMenu buscarItem(long id) {
        return itemsMenuMemory.buscarItem(id);
    }

    public void actualizarItem(ItemMenu item) {
        itemsMenuMemory.actualizarItem(item);
    }

    public void eliminarItem(long id) {
        itemsMenuMemory.eliminarItem(id);
    }

    public Set<ItemMenu> obtenerListaItems() {
        return itemsMenuMemory.listarItems();
    }
}