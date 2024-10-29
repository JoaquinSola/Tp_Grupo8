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
    private JTextField txtBuscar;
    private JButton btnBuscar, btnCrear, btnEditar, btnEliminar;
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;

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

    private void buscarPedido() {
        String criterio = txtBuscar.getText();
        // Lógica para buscar un pedido y actualizar la tabla
    }

    private void crearPedido() {
        // Lógica para crear un nuevo pedido
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
