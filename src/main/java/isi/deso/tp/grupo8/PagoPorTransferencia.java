package isi.deso.tp.grupo8;

import java.time.LocalDate;

public class PagoPorTransferencia extends Pago implements EstrategiaDePago{
   
    /* Heredados
    private long id;
    private double monto;
    private LocalDateTime fecha;
    */
    
    final private double recargoEnTransferencia = 0.02;
    private String cbu;
    private String cuit;
    
   @Override
    public double calcularRecargo(double monto){
        this.monto = monto * this.recargoEnTransferencia + monto ;
        return monto;
    }
    public void setCbu(String cbu){
        this.cbu = cbu;
    }
    public void setCuit(String cuit){
        this.cuit = cuit;
    }
    public void setFecha(LocalDate fecha){
        this.fecha = fecha;
    }
}
