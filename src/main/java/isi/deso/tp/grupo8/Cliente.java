package isi.deso.tp.grupo8;

public class Cliente implements Observer {
    private long id;
    private String cuit;
    private String email;
    private String direccion;
    private Coordenada coordenadas;
    private String alias;
    private String cbu;
    
    public Cliente(long id, String cuit, String email, String direc, Coordenada coor, String alias, String cbu){
        this.id = id;
        this.cuit = cuit;
        this.email = email;
        this.direccion = direc;
        this.coordenadas = coor;
        this.alias = alias;
        this.cbu = cbu;
    }
    public void setId(long id){
        this.id=id;
    }
    public long getId(){
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
     
     public String getAlias(){
        return alias;
    }
     
     public String getCbu(){
        return cbu;
    }


public void setCuit(String cuit) {
    this.cuit = cuit;
}

public void setEmail(String email) {
    this.email = email;
}

public void setDireccion(String direccion) {
    this.direccion = direccion;
}

public void setCoor(Coordenada coordenadas) {
    this.coordenadas = coordenadas;
}

public void setAlias(String alias) {
    this.alias = alias;
}

public void setCbu(String cbu) {
    this.cbu = cbu;
}
     
     public Pedido crearPedido(Vendedor v,long id, String metodoDePago){
        ItemsPedidoMemory im = new ItemsPedidoMemory();
        Pedido p = new Pedido(id, this, im, v, metodoDePago);
         
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
        if(p.getMetodoDePago().equals("Mercado Pago")){
            monto = p.pagarConMP(this.alias);

        }
        else if(p.getMetodoDePago().equals("Transferencia")){
           monto = p.pagarConTransferencia(this.cbu, this.cuit);
        
        }
    }
}
