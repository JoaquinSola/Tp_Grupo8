package isi.deso.tp.grupo8;

import java.time.LocalDateTime;

public class Cliente implements Observer {
    private String id;
    private String cuit;
    private String email;
    private String direccion;
    private Coordenada coordenadas;
    private String alias;
    private String cbu;
    
    public Cliente(String id, String cuit, String email, String direc, Coordenada coor, String alias, String cbu){
        this.id = id;
        this.cuit = cuit;
        this.email = email;
        this.direccion = direc;
        this.coordenadas = coor;
        this.alias = alias;
        this.cbu = cbu;
    }
    
    public String getId(){
        return id;
    }
    
     public String getCuit(){
        return cuit;
    }
     
     public String getEmail(){
        return email;
    }
     
     public String getDireccion(){
        return direccion;
    }
     
     public Coordenada getCoor(){
        return coordenadas;
    }
     
     public Pedido crearPedido(Vendedor v, String id){
        // public Pedido(Str ing id, Cliente c, ItemsPedidoMemory ip, Vendedor v)
        ItemsPedidoMemory im = new ItemsPedidoMemory();
        Pedido p = new Pedido(id, this, im, v); VER ESTOOOOO!!!!
         
        return p;
     }

     public Pedido agregarItem(Pedido p, ItemMenu im) throws ItemNoEncontradoException{
         p.agregarItem(im) ;
         return p;
     }

     @Override
    public void update(Pedido pedido) {
        System.out.println("Cliente " + this.id + " ha sido notificado: El estado del pedido es ahora " + pedido.getEstado());
        if (pedido.getEstado() == EstadoPedido.EN_ENVIO) {
            generarPago(pedido);
        }
    }

    private void generarPago(Pedido p) {
        System.out.println("Generando el pago para el pedido...");
        double monto;
        Pago pa;
        if(p.getMetodoDePago().equals("MP")){
            monto = p.pagarConMP(this.alias);
            pa = new Pago(monto,LocalDateTime.now(), "Pago hecho por Mercado Pago");
            p.guardarPago(pa);
        }
        else if(p.getMetodoDePago().equals("TRANS")){
           monto = p.pagarConTransferencia(this.cbu, this.cuit);
            pa = new Pago(monto, LocalDateTime.now(), "Pago hecho por Transferencia");
        }
        p.guardarPago(pa);
    }
}
