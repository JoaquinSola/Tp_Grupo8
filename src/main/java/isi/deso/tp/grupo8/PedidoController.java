package isi.deso.tp.grupo8;

import java.util.Set;

public class PedidoController {
    private final PedidoDAO pedidoDAO;
    private final ClienteDAO clienteDAO;
    private final VendedorDAO vendedorDAO;
    private final ItemMenuDAO itemMenuDAO;

    public PedidoController(PedidoDAO pedidoDAO, ClienteDAO clienteDAO, VendedorDAO vendedorDAO, ItemMenuDAO itemMenuDAO) {
        this.pedidoDAO = pedidoDAO;
        this.clienteDAO = clienteDAO;
        this.vendedorDAO = vendedorDAO;
        this.itemMenuDAO = itemMenuDAO;
    }

    public void crearPedido(long idPedido, long idCliente, long idVendedor, String metodoPago, Set<Long> idsItems) {
        Cliente cliente = clienteDAO.buscarCliente(idCliente);
        Vendedor vendedor = vendedorDAO.buscarVendedor(idVendedor);

        // Inicializar una instancia de ItemsPedidoMemory
        ItemsPedidoMemory itemsPedido = new ItemsPedidoMemory();

        for (Long itemId : idsItems) {
            ItemMenu item = itemMenuDAO.buscarItem(itemId);
            if (item != null) {
                itemsPedido.agregarItem(item); // Añadir ítem al pedido
            }
        }

        // Crear el pedido con los datos proporcionados
        Pedido pedido = new Pedido(idPedido, cliente, itemsPedido, vendedor, metodoPago);
        // Open the payment window with the correct amount
        new GestionPago(pedido);
    }

    public Pedido buscarPedido(long id) {
        return pedidoDAO.buscarPedido(id);
    }

    public void actualizarPedido(Pedido pedido) {
        pedidoDAO.actualizarPedido(pedido);
    }

    public void eliminarPedido(long id) {
        pedidoDAO.eliminarPedido(id);
    }

    public Set<Pedido> listarPedidos() {
        return pedidoDAO.listarPedidos();
    }
}

