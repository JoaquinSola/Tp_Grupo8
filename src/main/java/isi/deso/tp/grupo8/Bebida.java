package isi.deso.tp.grupo8;

public class Bebida extends ItemMenu {

    private double volumen;
    private TipoBebida tipoBebida;

    @Override
    public double peso(double p) {
        if (this.tipoBebida ==  TipoBebida.ALCOHOL){
        volumen = (p*0.99) + p*0.2;
        }else if(this.tipoBebida == TipoBebida.GACEOSA){
        volumen = (p*1.04) + p*0.2;
        }
    return volumen;
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
