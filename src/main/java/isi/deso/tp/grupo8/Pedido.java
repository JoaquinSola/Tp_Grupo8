
package isi.deso.tp.grupo8;

public class Pedido {
    private String id;
    private Cliente cliente;
    private ItemsPedidoMemory itemsPedidoMemory;
    //private Pago pago;
    //private Estado estado;
    private Vendedor vendedor;
    private EstadoPedido estado;
    
    //falta agregar pago y estado
    public Pedido(String id, Cliente c, ItemsPedidoMemory ip, Vendedor v){
        this.id = id;
        this.cliente = c;
        this.itemsPedidoMemory= ip;
        //this.pago=p;
        //this.estado = e;
        this.vendedor=v;
        this.estado = EstadoPedido.PENDIENTE;
    }
   public Pedido agregarItem(ItemMenu im) throws ItemNoEncontradoException {
       if(this.vendedor.getList().contains(im)){
       this.itemsPedidoMemory.agregarItem(im);
       } else {
           throw new ItemNoEncontradoException("Item No encontrado");
       }
       return this;
   }
   
   
   public double pagarConMP(String alias){
       PagoPorMP mp = new PagoPorMP(alias);
       double monto = this.itemsPedidoMemory.calcularTotal();
       this.estado = EstadoPedido.RECIBIDO;
       return (mp.calcularRecargo(monto) + monto);
   }
   
   public double pagarConTransferencia(String cbu, String cuit){
       PagoPorTransferencia t = new PagoPorTransferencia(cbu, cuit);
       double monto = this.itemsPedidoMemory.calcularTotal();
       this.estado = EstadoPedido.RECIBIDO;
       return (t.calcularRecargo(monto) + monto);
   }

   public void estadoDelPedido(){
    System.out.println("El estado del pedido es: $"+this.estado);
   }
}
