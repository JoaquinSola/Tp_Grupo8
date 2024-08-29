/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;


public class Vendedor {
    private String id;
    private String nombre;
    private String direccion;
    private Coordenada coordenadas;
    
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
}
