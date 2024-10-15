package isi.deso.tp.grupo8;
import java.time.LocalDateTime;
public class Pago {
   private double monto;
   private LocalDateTime fecha;
   private String metodoDePago;
   
   public Pago(double monto, LocalDateTime fecha, String mdp){
       this.monto = monto;
       this.fecha = fecha;
       this.metodoDePago = mdp;
   }

   public void mensajeMDP(){
    System.out.println(this.metodoDePago);
   }
}
