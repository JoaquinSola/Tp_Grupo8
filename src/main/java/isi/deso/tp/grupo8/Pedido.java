
package isi.deso.tp.grupo8;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private long id;
    private Cliente cliente;
    private ItemsPedidoMemory itemsPedidoMemory;
    private Pago pago;
    //private Estado estado;
    private Vendedor vendedor;
    private EstadoPedido estado;
    private List<Observer> observers = new ArrayList<>();
    private String metodoDePago ;

    public Pedido(long id, Cliente c, ItemsPedidoMemory ip, Vendedor v, String metodo){
        this.id = id;
        this.cliente = c;
        this.itemsPedidoMemory= ip;
        this.vendedor=v;
        this.estado = EstadoPedido.PENDIENTE;
         // Agregar el pedido al vendedor
        this.vendedor.agregarPedido(this); // Esta línea debería ser ejecutada
        this.metodoDePago = metodo;
    }

    public long getId(){
        return id;
    }
 public Vendedor getVendedor(){
        return vendedor;
    } public Cliente getCliente(){
        return cliente;
    }
    // Método para agregar observadores
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // Método para notificar a los observadores
    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public void setEstado(EstadoPedido nuevoEstado) {
        this.estado = nuevoEstado;
        notifyObservers();  // Notificar cuando cambie el estado
    }

    public EstadoPedido getEstado() {
        return this.estado;
    }

   public Pedido agregarItem(ItemMenu im) throws ItemNoEncontradoException {
       if(this.vendedor.getList().contains(im)){
       this.itemsPedidoMemory.agregarItem(im);
       } else {
           throw new ItemNoEncontradoException("Item No encontrado");
       }
       return this;
   }
   
  
   
   public double pagarConMP(String alias){
    PagoPorMP pagoMP = new PagoPorMP();
       double monto = this.itemsPedidoMemory.calcularTotal();
       this.estado = EstadoPedido.RECIBIDO;
       double valorT = (pagoMP.calcularRecargo(monto));
       LocalDate fechaActual = LocalDate.now();
       pagoMP.setAlias(alias);
       pagoMP.setFecha(fechaActual);
       this.pago=pagoMP;
       return valorT;
   }
   
   public double pagarConTransferencia(String cbu, String cuit){
       PagoPorTransferencia t = new PagoPorTransferencia();
       double monto = this.itemsPedidoMemory.calcularTotal();
       this.estado = EstadoPedido.RECIBIDO;
       double valorT = (t.calcularRecargo(monto));
       LocalDate fechaActual = LocalDate.now();
       t.setCbu(cbu);
       t.setCuit(cuit);
       t.setFecha(fechaActual);
       this.pago=t;
       return valorT;
   }

   public void estadoDelPedido(){
    System.out.println("El estado del pedido es: $"+this.estado);
   }
   
   public String getMetodoDePago(){
      return this.metodoDePago;
   }
   
   public void guardarPago(Pago pa){
       this.pago = pa;
   }
}
