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

    /**
     * Crea un nuevo pedido con los datos proporcionados.
     *
     * @param idPedido    ID único del pedido.
     * @param idCliente   ID del cliente que realiza el pedido.
     * @param idVendedor  ID del vendedor que atiende el pedido.
     * @param metodoPago  Método de pago utilizado.
     * @param idsItems    Conjunto de IDs de los ítems del menú seleccionados.
     */
    public void crearPedido(long idPedido, long idCliente, long idVendedor, String metodoPago, Set<Integer> idsItems) {
        // Buscar cliente y vendedor en los DAOs correspondientes
        Cliente cliente = clienteDAO.buscarCliente(idCliente);
        Vendedor vendedor = vendedorDAO.buscarVendedor(idVendedor);

        // Inicializar una instancia de ItemsPedidoMemory
        ItemsPedidoMemory itemsPedido = new ItemsPedidoMemory();

        // Añadir ítems al pedido utilizando los IDs proporcionados
        for (int itemId : idsItems) {
            ItemMenu item = itemMenuDAO.buscarItem(itemId);
            if (item != null) {
                itemsPedido.agregarItem(item); // Añadir ítem al pedido
            }
        }

        // Crear el pedido con los datos proporcionados
        Pedido pedido = new Pedido(idPedido, cliente, itemsPedido, vendedor, metodoPago);

        try {
            // Guardar el pedido utilizando el DAO
            pedidoDAO.crearPedido(pedido);
        } catch (Exception e) {
            // Manejo de excepciones en la creación del pedido
            System.err.println("Error al crear el pedido: " + e.getMessage());
        }
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
