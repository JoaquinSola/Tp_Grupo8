/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GestionPedidos extends JFrame {
    private final PedidoController controlador;
    private JTextField txtBuscar, txtID, txtCliente, txtVendedor, txtMp, txtIpm;
    private JButton btnBuscar, btnCrear, btnEditar, btnEliminar,btnListar;
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private JComboBox<Cliente> todosClientes;
    private JTextArea areaResultados;

    private ClienteMemory clienteMemory;
    
    public GestionPedidos(PedidoController controlador) {
        this.controlador = controlador;

        setTitle("Gestión de Pedidos");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        txtID = new JTextField(10);
        txtMp = new JTextField(15);
        areaResultados = new JTextArea(15, 40);
        areaResultados.setEditable(false);

        btnCrear = new JButton("Crear");
        btnBuscar = new JButton("Buscar");
        btnEliminar = new JButton("Eliminar");
        btnEditar = new JButton("Editar");
        btnListar = new JButton("Listar");

        add(new JLabel("ID:"));
        add(txtID);
        add(new JLabel("Metodo de Pago"));
        add(txtMp);
        add(btnCrear);
        add(btnBuscar);
        add(btnEliminar);
        add(btnEditar);
        add(btnListar);
        add(new JScrollPane(areaResultados));

        btnCrear.addActionListener(this::crearPedido);
        btnBuscar.addActionListener(this::buscarPedido);
        btnEditar.addActionListener(this::actualizarPedido);
        btnEliminar.addActionListener(this::eliminarPedido);
        btnListar.addActionListener(e -> listarPedidos());
       

        setVisible(true);
    }

    public GestionPedidos() {
        // Configuración del JFrame
        setTitle("Gestión de Pedidos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Inicializar componentes
        inicializarComponentes();
        
        // Hacer visible el JFrame
        setVisible(true);
    }

    private void inicializarComponentes() {
        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout()); // Usar BorderLayout para organizar el panel

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new FlowLayout());
        txtBuscar = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        btnCrear = new JButton("Crear Pedido");
        btnEditar = new JButton("Editar Pedido");
        btnEliminar = new JButton("Eliminar Pedido");

        // Añadir componentes de búsqueda al panel de búsqueda
        panelBusqueda.add(new JLabel("Buscar:"));
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(btnCrear);
        panelBusqueda.add(btnEditar);
        panelBusqueda.add(btnEliminar);

        // Tabla para mostrar pedidos
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Cantidad"}, 0);
        tablaPedidos = new JTable(modeloTabla);
        
        // Configuración del JScrollPane para la tabla
        JScrollPane scrollPane = new JScrollPane(tablaPedidos);
        scrollPane.setPreferredSize(new Dimension(750, 300));

        // Añadir los paneles al JFrame
        panel.add(panelBusqueda, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER); // Colocar la tabla al centro

        // Añadir el panel al JFrame
        add(panel);

        // Añadir ActionListener a los botones
        btnBuscar.addActionListener(e -> buscarPedido());
        btnCrear.addActionListener(e -> crearPedido());
        btnEditar.addActionListener(e -> editarPedido());
        btnEliminar.addActionListener(e -> eliminarPedido());
    }
     public void clientes() {
        // Inicializa el comboBox con los clientes registrados
        todosClientes = new JComboBox<>();
        for (Cliente cliente : clienteMemory.listarClientes()) {
            todosClientes.addItem(cliente);}
        }

    

    private void buscarPedido() {
        String criterio = txtBuscar.getText();
        // Lógica para buscar un pedido y actualizar la tabla
    }

    private void crearPedido() {
        String id = txtID.getText();
        Cliente cliente = (Cliente) todosClientes.getSelectedItem();
        //ipm=itemsPedidoMemory, mp= metodoDePago
        String ipm = txtIpm.getText();
        String vendedor = txtVendedor.getText();
        String mp = txtMp.getText();
        controlador.crearNuevoPedido(id,cliente,ipm,vendedor, mp);
        areaResultados.setText("Pedido creado.");

    }

    private void editarPedido() {
        int filaSeleccionada = tablaPedidos.getSelectedRow();
        if (filaSeleccionada != -1) {
            // Lógica para editar el pedido seleccionado
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un pedido para editar.");
        }
    }

    private void eliminarPedido() {
        int filaSeleccionada = tablaPedidos.getSelectedRow();
        if (filaSeleccionada != -1) {
            modeloTabla.removeRow(filaSeleccionada);
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un pedido para eliminar.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GestionPedidos::new);
    }
}