/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemsPedidoMemory implements ItemsPedidoDao{
     private Set<ItemPedido> listaItems;
     
  public void ItemsPedidoMemory(){
      Set<ItemPedido> listaItems = new HashSet<>();
  }
  
  public Set<ItemPedido> getLista(){
      return listaItems;
  }
  
  //REVISARRRRRRRRRRRRRRRRRRRRRR
  public Set<ItemPedido> agregarItem(ItemPedido ip ){
      this.listaItems.add(ip);
      return listaItems;
  }
    //void filtrado(){
    //}
    //void ordenarPorCriterio(){   
    //}
    //void busquedaPorRangodePecios(double min, double max, ItemsPedidoMemory ip){    
    //}
  
    public Set<ItemPedido> buscarPorRestaurante(Vendedor v) throws ItemNoEncontradoException {
       Set<ItemPedido> set1 = this.listaItems.stream().filter(items -> v.getList().contains(items.getItemPedido())).collect(Collectors.toSet());
       if(set1.isEmpty()){
           throw new ItemNoEncontradoException("Item No encontrado");
       }
       return set1;
    }
}
// clase Pedido la cual tiene una lista de Items Pedido