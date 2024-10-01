
package isi.deso.tp.grupo8;

public class Pedido {
    private String id;
    private Cliente cliente;
    private ItemsPedidoMemory itemsPedidoMemory;
    //private Pago pago;
    //private Estado estado;
    private Vendedor vendedor;
    
    //falta agregar pago y estado
    public Pedido(String id, Cliente c, ItemsPedidoMemory ip, Vendedor v){
        this.id = id;
        this.cliente = c;
        this.itemsPedidoMemory= ip;
        //this.pago=p;
        //this.estado = e;
        this.vendedor=v;
    }
   public Pedido agregarItem(ItemMenu im) throws ItemNoEncontradoException {
       if(this.vendedor.getList().contains(im)){
       this.itemsPedidoMemory.agregarItem(im);
       } else {
           throw new ItemNoEncontradoException("Item No encontrado");
       }
       return this;
   }
}
