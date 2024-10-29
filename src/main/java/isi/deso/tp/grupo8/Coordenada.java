/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;

public class Coordenada {
    private double longitud;
    private double latitud;
    
    public Coordenada (double latitud, double longitud){
        this.longitud = longitud;
        this.latitud = latitud;
    }
    
    public double getLongitud(){
        return longitud;
    }
    
     public double getLatitud(){
        return latitud;
    }
}
