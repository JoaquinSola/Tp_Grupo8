package isi.deso.tp.grupo8;

public class Plato extends ItemMenu {
    private double calorias;
    private boolean aptoCeliacos;
    private boolean aptoVegetariano;

    public Plato(){
        this.calorias = 0;
        this.aptoCeliacos = false;
        this.aptoVegetariano = false;
    }


    public Plato(double c, boolean aC, boolean aV){
        this.calorias = c;
        this.aptoCeliacos = aC;
        this.aptoVegetariano = aV;

    }

    @Override
    public double peso(double p) {
        double peso;
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

}
