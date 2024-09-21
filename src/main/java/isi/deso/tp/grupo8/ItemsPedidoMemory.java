/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemsPedidoMemory implements ItemsPedidoDao{
    private Set<ItemPedido> listaItems;
     
    public ItemsPedidoMemory(){
      this.listaItems = new HashSet<>();
  }
  
    public Set<ItemPedido> getLista(){
      return listaItems;
  }
    public Set<ItemPedido> agregarItem(ItemPedido ip ){
      this.listaItems.add(ip);
      return listaItems;
  }


     @Override
    public Set<ItemPedido> ordenarPorCriterio(){
    Set<ItemPedido> setAux = this.listaItems.stream()
        .sorted(Comparator.comparing(item -> item.getItemPedido().toString())).collect(Collectors.toSet());
        
            StringBuilder resultado = new StringBuilder("El orden alfabetico de los items es: [");
            for (ItemPedido item : setAux) {
            resultado.append(item.getItemPedido()).append(", ");
        }
        if (!setAux.isEmpty()) {
            resultado.setLength(resultado.length() - 2); // Elimina la última coma y el espacio
        }
        resultado.append("]");
        System.out.println(resultado.toString());
        
    return setAux;
    }
    
     @Override
    // POR EL MENSAJE DE AVISO QUE SE DIO EN CLASES, SE ENTIENDE QUE LA BUSQUEDA POR RANGO DE PRECIO SE HACE SOBRE ITEMS, Y NO SOBRE PEDIDOS COMO LO INDICABA EL ENUNCIADO.
    public Set<ItemPedido> busquedaPorRangodePecios(double min, double max) throws ItemNoEncontradoException {  
        Set<ItemPedido> setAux = this.listaItems.stream().filter(item -> item.getItemPedido().getPrecio() > min && item.getItemPedido().getPrecio() < max).collect(Collectors.toSet());
        if(setAux.isEmpty()){
            throw new ItemNoEncontradoException("Item No encontrado");
        }else{
            StringBuilder resultado = new StringBuilder("El/Lost item que cumplen la condicion: [");
            for (ItemPedido item : setAux) {
            resultado.append(item.getItemPedido()).append(", ");
        }
        if (!setAux.isEmpty()) {
            resultado.setLength(resultado.length() - 2); // Elimina la última coma y el espacio
        }
        resultado.append("]");
        System.out.println(resultado.toString());

        }
        return setAux;
    } 
    
     @Override
    public Set<ItemPedido> buscarPorRestaurante(Vendedor v) throws ItemNoEncontradoException {
       Set<ItemPedido> set1 = this.listaItems.stream().filter(items -> v.getList().contains(items.getItemPedido())).collect(Collectors.toSet());
       if(set1.isEmpty()){
           throw new ItemNoEncontradoException("Item No encontrado");
       }else{
        StringBuilder resultado = new StringBuilder("El vendedor "+ v.getNombre() +" dispone de: [");
        for (ItemPedido item : set1) {
        resultado.append(item.getItemPedido()).append(", ");
    }
    if (!set1.isEmpty()) {
        resultado.setLength(resultado.length() - 2); // Elimina la última coma y el espacio
    }
    resultado.append("]");
    System.out.println(resultado.toString());

    }
       return set1;
    }
}
// clase Pedido la cual tiene una lista de Items Pedido