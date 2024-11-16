package isi.deso.tp.grupo8;

import java.util.HashSet;
import java.util.Set;

public class PedidoMemory implements PedidoDAO {
    private Set<Pedido> pedidos = new HashSet<>();

    @Override
    public void crearPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    @Override
    public Pedido buscarPedido(long id) {
        return pedidos.stream()
                .filter(p -> p.getId()==(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void actualizarPedido(Pedido pedido) {
        eliminarPedido(pedido.getId());
        pedidos.add(pedido);
    }

    @Override
    public void eliminarPedido(long id) {
        pedidos.removeIf(p -> p.getId()==(id));
    }

    @Override
    public Set<Pedido> listarPedidos() {
        return pedidos;
    }
}