/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;
import java.util.Set;
import java.util.HashSet;


public class Vendedor {
    private String id;
    private String nombre;
    private String direccion;
    private Coordenada coordenadas;
    private Set<ItemMenu> listaProductos;
    
    public Vendedor (String id, String nombre, String direc, Coordenada coor){
        this.id = id;
        this.nombre = nombre;
        this.direccion = direc;
        this.coordenadas = coor;
    }
    
    public String getId(){
        return id;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public String getDireccion(){
        return direccion;
    }
    
    public Coordenada getCoor(){
        return coordenadas;
    }
    
    public double distancia(Cliente cliente){
        Coordenada coordVend = this.coordenadas; //ESTAS SON COORDENADAS EN GRADOS
        Coordenada coordClient = cliente.getCoor(); //ESTAS SON COORDENADAS EN GRADOS
        double phi1 = Math.toRadians(coordVend.getLatitud()); //ESTAS SON COORDENADAS EN RADIANES
        double lamb1 = Math.toRadians(coordVend.getLongitud()); //ESTAS SON COORDENADAS EN RADIANES
        double phi2 = Math.toRadians(coordClient.getLatitud()); //ESTAS SON COORDENADAS EN RADIANES
        double lamb2 = Math.toRadians(coordClient.getLongitud()); //ESTAS SON COORDENADAS EN RADIANES
        //System.out.println("phi1: "+phi1+" ,phi2: "+phi2+" ,lam1: "+lamb1+" ,lamb2:"+lamb2);
        double r = 6371;
        double deltaPhi = (phi2 - phi1);
        double deltaLam = (lamb2 - lamb1);
        double distancia;
        double a = Math.pow(Math.sin(deltaPhi / 2), 2) +
                   Math.cos(phi1) * Math.cos(phi2) * Math.pow(Math.sin(deltaLam / 2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        // Calcular distancia Total en KM.
        distancia = r * c;
        return distancia;
    }
    
    public Set getComidas(){
        Set<ItemMenu> comidas = new HashSet<>();
        for(ItemMenu producto: this.listaProductos){
            if(producto.esComida()){
                comidas.add(producto);
            }
        }
        return comidas;
    }
       
      public Set getBebidas(){
        Set<ItemMenu> bebidas = new HashSet<>();
        for(ItemMenu producto: this.listaProductos){
            if(producto.esBebida()){
                bebidas.add(producto);
            }
        }
        return bebidas;
    }
    
}
