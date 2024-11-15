package isi.deso.tp.grupo8;
import java.time.LocalDateTime;
public abstract class Pago {
   private long id;
   private double monto;
   private LocalDateTime fecha;
   
   //generar el id
   //FALTAAAAAAAAAAA
   
   public Pago(double monto, LocalDateTime fecha){
       this.monto = monto;
       this.fecha = fecha;
   }
}
