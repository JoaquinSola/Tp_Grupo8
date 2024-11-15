package isi.deso.tp.grupo8;

public class PagoPorTransferencia implements EstrategiaDePago{
   
    /* Heredados
    private long id;
    private double monto;
    private LocalDateTime fecha;
    */
    
    final private double recargoEnTransferencia = 0.02;
    private String cbu;
    private String cuit;
    
    public PagoPorTransferencia(String cbu, String cuit){
        this.cbu = cbu;
        this.cuit = cuit;
    }
    
    @Override
    public double calcularRecargo(double monto){
        return monto * this.recargoEnTransferencia;
    }
}
