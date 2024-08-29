/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;

public class Coordenada {
    private float longitud;
    private float latitud;
    
    public Coordenada (float latitud, float longitud){
        this.longitud = longitud;
        this.latitud = latitud;
    }
    
    public float getLongitud(){
        return longitud;
    }
    
     public float getLatitud(){
        return latitud;
    }
}
