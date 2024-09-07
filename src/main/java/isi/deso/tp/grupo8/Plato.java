package isi.deso.tp.grupo8;

public class Plato extends ItemMenu {
    private double calorias;
    private boolean aptoCeliacos;
    private boolean aptoVegetariano;
    private double peso;

    public Plato(){
        this.calorias = 0;
        this.aptoCeliacos = false;
        this.aptoVegetariano = false;
        this.nombre=null;
    }


    public Plato(double c, boolean aC, boolean aV, String nom){
        this.calorias = c;
        this.aptoCeliacos = aC;
        this.aptoVegetariano = aV;
        this.nombre = nom;

    }

    @Override
    public double peso(double p) {
        peso = p + (p*0.1);
        return peso;
    }

    @Override
    public boolean esComida() {
        boolean veg = this.aptoVegano();
        if(veg == true){
            System.out.println("Es comida vegana");
        }else{
            System.out.println("Es comida no vegana");
        }
        return true;
    }

    @Override
    public boolean esBebida() {
        return false;
    }

    @Override
    public boolean aptoVegano() {
        if (this.aptoVegetariano == true){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean esAlcoholica(){
        return false;
    }

    @Override
    public String toString() {
        return this.nombre;
    }


}
