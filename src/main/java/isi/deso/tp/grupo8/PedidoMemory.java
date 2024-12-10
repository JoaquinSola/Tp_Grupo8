package isi.deso.tp.grupo8;

import java.util.HashSet;
import java.util.Set;

public class PedidoMemory implements PedidoDAO {
    private Set<Pedido> pedidos = new HashSet<>();
    

    @Override
    public void crearPedido(Pedido pedido) {
        if (pedido != null) {
            pedidos.add(pedido);
        }
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
        Pedido existente = buscarPedido(pedido.getId());
        if (existente == null) {
            throw new IllegalArgumentException("El pedido con ID " + pedido.getId() + " no existe.");
        }
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