package isi.deso.tp.grupo8;

public class Plato extends ItemMenu {

    @Override
    public double peso(double p) {
        double peso;
        peso = p + (p*0.1);
        return peso;
    }

    @Override
    public void esComida() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void esBebida() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void aptoVegano() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
