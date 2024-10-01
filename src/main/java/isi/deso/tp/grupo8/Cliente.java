/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;

public class Cliente {
    private String id;
    private String cuit;
    private String email;
    private String direccion;
    private Coordenada coordenadas;
    
    public Cliente(String id, String cuit, String email, String direc, Coordenada coor){
        this.id = id;
        this.cuit = cuit;
        this.email = email;
        this.direccion = direc;
        this.coordenadas = coor;
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
        Pedido p = new Pedido(id, this, im, v);
         
        return p;
     }

     public Pedido agregarItem(Pedido p, ItemMenu im) throws ItemNoEncontradoException{
         p.agregarItem(im) ;
         return p;
     }
}
