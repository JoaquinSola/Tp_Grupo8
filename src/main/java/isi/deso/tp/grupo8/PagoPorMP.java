package isi.deso.tp.grupo8;


public class PagoPorMP implements EstrategiaDePago{
   
    final private double recargoEnMP = 0.04;
    private String alias;
    
    public PagoPorMP(String a){
        this.alias = a;
    }
    
    @Override
    public double calcularRecargo(double monto){
        return monto * this.recargoEnMP;
    }
}
