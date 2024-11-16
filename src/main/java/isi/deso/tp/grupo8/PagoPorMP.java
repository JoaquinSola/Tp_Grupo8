package isi.deso.tp.grupo8;

import java.time.LocalDate;


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
        this.monto = monto;
       this.monto_final = monto * this.recargoEnMP + monto ;
        return monto_final;
    }
    public void setAlias(String alias){
        this.alias = alias;
    }
    public void setFecha(LocalDate fecha){
        this.fecha = fecha;
    }
    
    
    
}
