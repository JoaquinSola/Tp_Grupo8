
package isi.deso.tp.grupo8;

public class Pedido {
    private String id;
    private Cliente cliente;
    private ItemsPedidoMemory itemsPedidoMemory;
    //private Pago pago;
    //private Estado estado;
    
    //falta agregar pago y estado
    public Pedido(String id, Cliente c, ItemsPedidoMemory ip){
        this.id = id;
        this.cliente = c;
        this.itemsPedidoMemory= ip;
        //this.pago=p;
        //this.estado = e;
        
    }
}
