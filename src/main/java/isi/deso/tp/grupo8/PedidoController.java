package isi.deso.tp.grupo8;

import java.util.Set;

public class PedidoController {
    private PedidoDAO pedidoDAO;
    private ClienteDAO clienteDAO;
    private VendedorDAO vendedorDAO;
    private ItemMenuDAO itemMenuDAO;

    public PedidoController(PedidoDAO pedidoDAO, ClienteDAO clienteDAO, VendedorDAO vendedorDAO, ItemMenuDAO itemMenuDAO) {
        this.pedidoDAO = pedidoDAO;
        this.clienteDAO = clienteDAO;
        this.vendedorDAO = vendedorDAO;
        this.itemMenuDAO = itemMenuDAO;
    }

    public void crearPedido(long idPedido, long idCliente, long idVendedor, String metodoPago, Set<Integer> idsItems) {
        Cliente cliente = clienteDAO.buscarCliente(idCliente);
        Vendedor vendedor = vendedorDAO.buscarVendedor(idVendedor);
        ItemsPedidoMemory itemsPedido = new ItemsPedidoMemory();

        for (int itemId : idsItems) {
            ItemMenu item = itemMenuDAO.buscarItem(itemId);
            if (item != null) {
                itemsPedido.agregarItem(item);
            }
        }

        Pedido pedido = new Pedido(idPedido, cliente, itemsPedido, vendedor, metodoPago);
        pedidoDAO.crearPedido(pedido);
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