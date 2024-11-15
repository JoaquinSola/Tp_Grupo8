package isi.deso.tp.grupo8;

import java.time.LocalDateTime;


public class PagoPorMP extends Pago implements EstrategiaDePago {
   
    /* Heredados
    private long id;
    private double monto;
    private LocalDateTime fecha;
    */
    final private double recargoEnMP = 0.04;
    private String alias;
    private double monto_final;
    
    @Override
    public double calcularRecargo(double monto){
        monto_final = monto * this.recargoEnMP;
        return monto_final;
    }
    
    public void PagoPorMP(String a, long id,double monto,LocalDateTime fecha){
        
        super Pago(id, monto, fecha);
        this.alias = a;
    }
    
    
}
