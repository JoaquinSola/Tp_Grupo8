package isi.deso.tp.grupo8;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GestionPedidos extends JFrame {
    private PedidoController controlador;
    private ClienteController clienteController;
    private VendedorController vendedorController;
    private ItemsMenuController itemsMenuController;
    private JTextField txtIdPedido, txtIdCliente, txtIdVendedor, txtMetodoPago, txtIdItem;
    private JTextArea areaResultados;
    private JButton btnCrearPedido, btnModificarPedido, btnEliminarPedido, btnListarPedidos;
    private JButton btnAbrirGestionCliente, btnAbrirGestionVendedor, btnAbrirGestionItem;
    private Set<Integer> idsItemsSeleccionados = new HashSet<>();

    public GestionPedidos(PedidoController pedidoController,
                          ClienteController clienteController,
                          VendedorController vendedorController,
                          ItemsMenuController itemsMenuController) {
        this.controlador = pedidoController;
        this.clienteController = clienteController;
        this.vendedorController = vendedorController;
        this.itemsMenuController = itemsMenuController;

        setTitle("Gestión de Pedidos");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        txtIdPedido = new JTextField(10);
        txtIdCliente = new JTextField(10);
        txtIdVendedor = new JTextField(10);
        txtMetodoPago = new JTextField(10);
        txtIdItem = new JTextField(10);
        areaResultados = new JTextArea(20, 40);
        areaResultados.setEditable(false);

        btnCrearPedido = new JButton("Crear Pedido");
        btnModificarPedido = new JButton("Modificar Pedido");
        btnEliminarPedido = new JButton("Eliminar Pedido");
        btnListarPedidos = new JButton("Listar Pedidos");

        btnAbrirGestionCliente = new JButton("Abrir Gestión Cliente");
        btnAbrirGestionVendedor = new JButton("Abrir Gestión Vendedor");
        btnAbrirGestionItem = new JButton("Abrir Gestión ItemMenu");

        // Agregar componentes
        add(new JLabel("ID Pedido:"));
        add(txtIdPedido);
        add(new JLabel("ID Cliente:"));
        add(txtIdCliente);
        add(new JLabel("ID Vendedor:"));
        add(txtIdVendedor);
        add(new JLabel("Método de Pago:"));
        add(txtMetodoPago);
        add(new JLabel("ID Item Menu:"));
        add(txtIdItem);

        add(btnCrearPedido);
        add(btnModificarPedido);
        add(btnEliminarPedido);
        add(btnListarPedidos);
        add(new JScrollPane(areaResultados));

        add(btnAbrirGestionCliente);
        add(btnAbrirGestionVendedor);
        add(btnAbrirGestionItem);

        btnCrearPedido.addActionListener(this::crearPedido);
        btnModificarPedido.addActionListener(this::modificarPedido);
        btnEliminarPedido.addActionListener(this::eliminarPedido);
        btnListarPedidos.addActionListener(e -> listarPedidos());

        btnAbrirGestionCliente.addActionListener(e -> new GestionClientes(this.clienteController));
        btnAbrirGestionVendedor.addActionListener(e -> new GestionVendedores(this.vendedorController));
        btnAbrirGestionItem.addActionListener(e -> new GestionItemsMenu(this.itemsMenuController));

        setVisible(true);
    }

    private void crearPedido(ActionEvent e) {
        // Lógica para crear el pedido
        try {
            long idPedido = Long.parseLong(txtIdPedido.getText());
            long idCliente = Long.parseLong(txtIdCliente.getText());
            long idVendedor = Long.parseLong(txtIdVendedor.getText());
            String metodoPago = txtMetodoPago.getText();
            Set<Integer> idsItems = new HashSet<>();
            idsItems.add(Integer.parseInt(txtIdItem.getText()));

            if (idCliente <= 0 || idVendedor <= 0 || metodoPago.isEmpty() || idsItems.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            controlador.crearPedido(idPedido, idCliente, idVendedor, metodoPago, idsItems);
            JOptionPane.showMessageDialog(this, "Pedido creado exitosamente.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID debe ser un número válido.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear el pedido: " + ex.getMessage());
        }
    }

    private void modificarPedido(ActionEvent e) {
        long idPedido = Long.parseLong(txtIdPedido.getText());
        Pedido pedido = controlador.buscarPedido(idPedido);
        if (pedido != null) {
            pedido.setEstado(EstadoPedido.RECIBIDO);
            controlador.actualizarPedido(pedido);
            areaResultados.setText("Pedido modificado.");
        } else {
            areaResultados.setText("Pedido no encontrado.");
        }
    }

    private void eliminarPedido(ActionEvent e) {
        long idPedido = Long.parseLong(txtIdPedido.getText());
        controlador.eliminarPedido(idPedido);
        areaResultados.setText("Pedido eliminado.");
    }

    private void listarPedidos() {
        Set<Pedido> pedidos = controlador.listarPedidos();
        StringBuilder sb = new StringBuilder("Lista de Pedidos:\n");
        for (Pedido p : pedidos) {
            sb.append("ID: ").append(p.getId()).append(", Cliente: ").append(p.getCliente().getId())
              .append(", Vendedor: ").append(p.getVendedor().getId()).append(", Estado: ").append(p.getEstado())
              .append(", Método de Pago: ").append(p.getMetodoDePago()).append("\n");
        }
        areaResultados.setText(sb.toString());
    }

    public static void main(String[] args) throws SQLException {
        // Crear las instancias de memoria
        ClienteMemory clienteMemory = new ClienteMemory();
        VendedorMemory vendedorMemory = new VendedorMemory();
        ItemsMenuMemory itemsMenuMemory = new ItemsMenuMemory();
        
        // Crear controladores
        ClienteController clienteController = new ClienteController(clienteMemory);
        VendedorController vendedorController = new VendedorController(vendedorMemory);
        ItemsMenuController itemsMenuController = new ItemsMenuController(itemsMenuMemory);
        
        // Crear el controlador de pedidos
        PedidoController pedidoController = new PedidoController(new PedidoMemory(), clienteMemory, vendedorMemory, itemsMenuMemory);
        
        // Crear y mostrar la ventana de gestión de pedidos
        new GestionPedidos(pedidoController, clienteController, vendedorController, itemsMenuController);
    }
}
