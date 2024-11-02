package isi.deso.tp.grupo8;

import java.util.Set;

public interface PedidoDAO {
    void crearPedido(Pedido pedido);
    Pedido buscarPedido(String id);
    void actualizarPedido(Pedido pedido);
    void eliminarPedido(String id);
    Set<Pedido> listarPedidos();
}