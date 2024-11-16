/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;
import java.util.HashSet;
import java.util.Set;


public class ItemsMenuMemory implements ItemMenuDAO {
    private final Set<ItemMenu> items = new HashSet<>();
    private int ultimoID = 1;  // Para generar IDs únicos

    @Override
    public void crearItem(ItemMenu item) {
        item.setId(ultimoID++);  // Asignar ID utilizando el setter
        items.add(item);
    }

    @Override
    public ItemMenu buscarItem(long id) {
        return items.stream()
                .filter(item -> item.getId() == id)  // Usar el getter
                .findFirst()
                .orElse(null);
    }

    @Override
    public void actualizarItem(ItemMenu item) {
        eliminarItem(item.getId());  // Usar el getter
        items.add(item);
    }

    @Override
    public void eliminarItem(long id) {
        items.removeIf(item -> item.getId() == id);  // Usar el getter
    }

    @Override
    public Set<ItemMenu> listarItems() {
        return items;
    }
}
