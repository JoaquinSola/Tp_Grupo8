package isi.deso.tp.grupo8;

public class Bebida extends ItemMenu {

    private double volumen;
    private double graduacionAlcoholica;

    @Override
    public double peso(double p) {
        if (this.graduacionAlcoholica !=  0){
        volumen = (p*0.99) + p*0.2;
        }else{
        volumen = (p*1.04) + p*0.2;
        }
    return volumen;
    }

    @Override
    public boolean esComida() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean esBebida() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean aptoVegano() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
