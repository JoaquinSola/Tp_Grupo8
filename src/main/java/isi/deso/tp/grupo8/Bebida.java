package isi.deso.tp.grupo8;

public class Bebida extends ItemMenu {

    private double volumen;
    private double graduacionAlcoholica;

    public Bebida(double alc, double tam){
        this.graduacionAlcoholica = alc;
        this.volumen = tam;
    }

    @Override
    public double peso(double p) {              //NO VENDRIA A SER EL VOLUMEN ? 
        if (this.graduacionAlcoholica !=  0){
        volumen = (p*0.99) + p*0.2;
        }else{
        volumen = (p*1.04) + p*0.2;
        }
    return volumen;
    }

    @Override
    public boolean esComida() {
        return false;
    }

    @Override
    public boolean esBebida() {
        return true;
    }

    @Override
    public boolean aptoVegano() {
        return false;
    }

}
