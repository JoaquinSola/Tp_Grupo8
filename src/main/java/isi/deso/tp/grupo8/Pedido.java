
package isi.deso.tp.grupo8;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private String id;
    private Cliente cliente;
    private ItemsPedidoMemory itemsPedidoMemory;
    private Pago pago;
    //private Estado estado;
    private Vendedor vendedor;
    private EstadoPedido estado;
    private List<Observer> observers = new ArrayList<>();
    private String metodoDePago ;

    public Pedido(String id, Cliente c, ItemsPedidoMemory ip, Vendedor v, String metodo){
        this.id = id;
        this.cliente = c;
        this.itemsPedidoMemory= ip;
        this.vendedor=v;
        this.estado = EstadoPedido.PENDIENTE;
         // Agregar el pedido al vendedor
        this.vendedor.agregarPedido(this); // Esta línea debería ser ejecutada
        this.metodoDePago = metodo;
    }

    public String getId(){
        return id;
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
       PagoPorMP mp = new PagoPorMP(alias);
       double monto = this.itemsPedidoMemory.calcularTotal();
       this.estado = EstadoPedido.RECIBIDO;
       return (mp.calcularRecargo(monto) + monto);
   }
   
   public double pagarConTransferencia(String cbu, String cuit){
       PagoPorTransferencia t = new PagoPorTransferencia(cbu, cuit);
       double monto = this.itemsPedidoMemory.calcularTotal();
       this.estado = EstadoPedido.RECIBIDO;
       return (t.calcularRecargo(monto) + monto);
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
