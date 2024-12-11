package isi.deso.tp.grupo8;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class GestionPedidos extends JFrame {
    private PedidoController controlador;
    private ClienteController clienteController;
    private VendedorController vendedorController;
    private ItemsMenuController itemsMenuController;
    private JTextField txtIdPedido, txtIdCliente, txtIdVendedor;
    private JTextArea areaResultados;
    private JButton btnCrearPedido, btnModificarPedido, btnEliminarPedido, btnListarPedidos, btnMostrarItems;
    private JComboBox<String> comboMetodoPago;
    private JList<ItemMenu> listItemsMenu;
    private Set<ItemMenu> itemsSeleccionados;

    public GestionPedidos(PedidoController pedidoController,
                          ClienteController clienteController,
                          VendedorController vendedorController,
                          ItemsMenuController itemsMenuController) {
        this.controlador = pedidoController;
        this.clienteController = clienteController;
        this.vendedorController = vendedorController;
        this.itemsMenuController = itemsMenuController;
        this.itemsSeleccionados = new HashSet<>();

        setTitle("Gestión de Pedidos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        txtIdPedido = new JTextField(10);
        txtIdCliente = new JTextField(10);
        txtIdVendedor = new JTextField(10);
        comboMetodoPago = new JComboBox<>(new String[]{"MP", "Por Transferencia"});
        listItemsMenu = new JList<>(); // JList para seleccionar múltiples items del menú
        listItemsMenu.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        areaResultados = new JTextArea(20, 40);
        areaResultados.setEditable(false);

        btnCrearPedido = new JButton("Crear Pedido");
        btnModificarPedido = new JButton("Modificar Pedido");
        btnEliminarPedido = new JButton("Eliminar Pedido");
        btnListarPedidos = new JButton("Listar Pedidos");
        btnMostrarItems = new JButton("Mostrar Items");

        add(new JLabel("ID Pedido:"));
        add(txtIdPedido);
        add(new JLabel("ID Cliente:"));
        add(txtIdCliente);
        add(new JLabel("ID Vendedor:"));
        add(txtIdVendedor);
        add(new JLabel("Método de Pago:"));
        add(comboMetodoPago);
        add(new JLabel("Items del Menú:"));
        add(new JScrollPane(listItemsMenu));

        add(btnMostrarItems);
        add(btnCrearPedido);
        add(btnModificarPedido);
        add(btnEliminarPedido);
        add(btnListarPedidos);
        add(new JScrollPane(areaResultados));

        btnMostrarItems.addActionListener(this::mostrarItemsVendedor);
        btnCrearPedido.addActionListener(this::abrirVentanaPago);
        btnModificarPedido.addActionListener(this::modificarPedido);
        btnEliminarPedido.addActionListener(this::eliminarPedido);
        btnListarPedidos.addActionListener(e -> listarPedidos());

        setVisible(true);
    }

    private void mostrarItemsVendedor(ActionEvent e) {
        try {
            long idVendedor = Long.parseLong(txtIdVendedor.getText());
            Set<ItemMenu> items = vendedorController.obtenerListaProductosVendedor(idVendedor);
            listItemsMenu.setListData(items.toArray(new ItemMenu[0]));
            areaResultados.setText("Items del vendedor cargados.");
        } catch (NumberFormatException ex) {
            areaResultados.setText("Por favor, ingresa un ID de vendedor válido.");
        } catch (Exception ex) {
            areaResultados.setText("Error al cargar items: " + ex.getMessage());
        }
    }

    private void abrirVentanaPago(ActionEvent e) {
        try {
            long idPedido = Long.parseLong(txtIdPedido.getText());
            long idCliente = Long.parseLong(txtIdCliente.getText());
            long idVendedor = Long.parseLong(txtIdVendedor.getText());
            String metodoPago = (String) comboMetodoPago.getSelectedItem();

            double montoTotal = 0.0;
            itemsSeleccionados.clear();
            for (ItemMenu item : listItemsMenu.getSelectedValuesList()) {
                montoTotal += item.getPrecio();
                itemsSeleccionados.add(item);
            }

            LocalDate fechaActual = LocalDate.now();
            Pago pago;
            if (metodoPago.equals("MP")) {
                pago = new PagoPorMP(0, montoTotal, fechaActual, "", montoTotal * 0.05); // ID autogenerado
            } else {
                pago = new PagoPorTransferencia(0, montoTotal, fechaActual, "", "", 0.0); // ID autogenerado
            }

            

            Pedido pedido = new Pedido(idPedido, clienteController.buscarCliente(idCliente), new ItemsPedidoMemory(), vendedorController.buscarVendedor(idVendedor), metodoPago);
            pedido.setPago(pago);
            for (ItemMenu item : itemsSeleccionados) {
                pedido.getItemsPedidoMemory().agregarItem(item);
            }

            controlador.crearPedido(pedido.getId(), pedido.getCliente().getId(), pedido.getVendedor().getId(), metodoPago, getIdsItemsSeleccionados());
            areaResultados.setText("Pedido creado exitosamente.");
        } catch (NumberFormatException ex) {
            areaResultados.setText("Error: Por favor, ingresa valores numéricos válidos.");
        } catch (Exception ex) {
            areaResultados.setText("Error al crear el pedido: " + ex.getMessage());
        }
    }

    private Set<Long> getIdsItemsSeleccionados() {
        Set<Long> ids = new HashSet<>();
        for (ItemMenu item : itemsSeleccionados) {
            ids.add(item.getId());
        }
        return ids;
    }

    private void modificarPedido(ActionEvent e) {
        try {
            long idPedido = Long.parseLong(txtIdPedido.getText());
            Pedido pedido = controlador.buscarPedido(idPedido);
            if (pedido != null) {
                pedido.setEstado(EstadoPedido.RECIBIDO);
                controlador.actualizarPedido(pedido);
                areaResultados.setText("Pedido modificado exitosamente.");
            } else {
                areaResultados.setText("Pedido no encontrado.");
            }
        } catch (NumberFormatException ex) {
            areaResultados.setText("Error: Por favor, ingresa un ID de pedido válido.");
        } catch (Exception ex) {
            areaResultados.setText("Error al modificar el pedido: " + ex.getMessage());
        }
    }

    private void eliminarPedido(ActionEvent e) {
        try {
            long idPedido = Long.parseLong(txtIdPedido.getText());
            controlador.eliminarPedido(idPedido);
            areaResultados.setText("Pedido eliminado correctamente.");
        } catch (NumberFormatException ex) {
            areaResultados.setText("Error: Por favor, ingresa un ID de pedido válido.");
        } catch (Exception ex) {
            areaResultados.setText("Error al eliminar el pedido: " + ex.getMessage());
        }
    }

    private void listarPedidos() {
        try {
            Set<Pedido> pedidos = controlador.listarPedidos();
            if (pedidos.isEmpty()) {
                areaResultados.setText("No hay pedidos registrados.");
            } else {
                StringBuilder sb = new StringBuilder("Lista de Pedidos:\n");
                for (Pedido p : pedidos) {
                    sb.append("ID: ").append(p.getId()).append(", Cliente: ").append(p.getCliente().getId())
                      .append(", Vendedor: ").append(p.getVendedor().getId()).append(", Estado: ").append(p.getEstado())
                      .append(", Método de Pago: ").append(p.getMetodoDePago()).append("\n");
                }
                areaResultados.setText(sb.toString());
            }
        } catch (Exception ex) {
            areaResultados.setText("Error al listar los pedidos: " + ex.getMessage());
        }
    }

    public void guardarPedidoConPago(Pedido pedido) {
        try {
            controlador.crearPedido(pedido.getId(), pedido.getCliente().getId(), pedido.getVendedor().getId(), pedido.getMetodoDePago(), getIdsItemsSeleccionados());
            areaResultados.setText("Pedido creado y pago confirmado exitosamente.");
        } catch (Exception ex) {
            areaResultados.setText("Error al guardar el pedido: " + ex.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
        ClienteMemory clienteMemory = new ClienteMemory();
        VendedorMemory vendedorMemory = new VendedorMemory();
        ItemsMenuMemory itemsMenuMemory = new ItemsMenuMemory();

        ClienteController clienteController = new ClienteController(clienteMemory);
        VendedorController vendedorController = new VendedorController(vendedorMemory, itemsMenuMemory);
        ItemsMenuController itemsMenuController = new ItemsMenuController(itemsMenuMemory);

        PedidoController pedidoController = new PedidoController(new PedidoMemory(), clienteMemory, vendedorMemory, itemsMenuMemory);

        new GestionPedidos(pedidoController, clienteController, vendedorController, itemsMenuController);
    }
}


