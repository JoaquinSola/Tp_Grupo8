/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GestionClientes extends JFrame {
    private final ClienteController controlador;
    private JTextField txtID, txtCuit, txtEmail, txtDireccion, txtAlias, txtCbu, txtLatitud, txtLongitud;
    private JTextArea areaResultados;
    private JButton btnCrear, btnBuscar, btnModificar, btnEliminar, btnListar;

    public GestionClientes(ClienteController controlador) {
        this.controlador = controlador;

        setTitle("Gestión de Clientes");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);;
        setLayout(new FlowLayout());

        txtID = new JTextField(10);
        txtCuit = new JTextField(15);
        txtEmail = new JTextField(20);
        txtDireccion = new JTextField(20);
        txtAlias = new JTextField(10);
        txtCbu = new JTextField(10);
        txtLatitud = new JTextField(10);
        txtLongitud = new JTextField(10);
        areaResultados = new JTextArea(15, 40);
        areaResultados.setEditable(false);

        btnCrear = new JButton("Crear");
        btnBuscar = new JButton("Buscar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnListar = new JButton("Listar");

        add(new JLabel("ID:"));
        add(txtID);
        add(new JLabel("CUIT:"));
        add(txtCuit);
        add(new JLabel("Email:"));
        add(txtEmail);
        add(new JLabel("Dirección:"));
        add(txtDireccion);
        add(new JLabel("Alias:"));
        add(txtAlias);
        add(new JLabel("CBU:"));
        add(txtCbu);
        add(new JLabel("Latitud:"));
        add(txtLatitud);
        add(new JLabel("Longitud:"));
        add(txtLongitud);
        add(btnCrear);
        add(btnBuscar);
        add(btnModificar);
        add(btnEliminar);
        add(btnListar);
        add(new JScrollPane(areaResultados));

        btnCrear.addActionListener(this::crearCliente);
        btnBuscar.addActionListener(this::buscarCliente);
        btnModificar.addActionListener(this::modificarCliente);
        btnEliminar.addActionListener(this::eliminarCliente);
        btnListar.addActionListener(e -> listarClientes());

        setVisible(true);
    }

    private void crearCliente(ActionEvent e) {
        String cuit = txtCuit.getText();
        String email = txtEmail.getText();
        String direccion = txtDireccion.getText();
        String alias = txtAlias.getText();
        String cbu = txtCbu.getText();
        double latitud = Double.parseDouble(txtLatitud.getText());
        double longitud = Double.parseDouble(txtLongitud.getText());
        Coordenada coordenadas = new Coordenada(latitud, longitud);

        controlador.crearNuevoCliente(cuit, email, direccion, coordenadas, alias, cbu);
        areaResultados.setText("Cliente creado.");
    }

    private void buscarCliente(ActionEvent e) {
        long id = Long.parseLong(txtID.getText());
        Cliente cliente = controlador.buscarCliente(id);

        if (cliente != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("ID: ").append(cliente.getId()).append("\n")
              .append("CUIT: ").append(cliente.getCuit()).append("\n")
              .append("Email: ").append(cliente.getEmail()).append("\n")
              .append("Dirección: ").append(cliente.getDireccion()).append("\n")
              .append("Alias: ").append(cliente.getAlias()).append("\n")
              .append("CBU: ").append(cliente.getCbu()).append("\n")
              .append("Coordenadas: (").append(cliente.getCoor().getLatitud()).append(", ")
              .append(cliente.getCoor().getLongitud()).append(")\n");
            areaResultados.setText(sb.toString());
        } else {
            areaResultados.setText("Cliente con ID " + id + " no encontrado.");
        }
    }

    private void modificarCliente(ActionEvent e) {
       long id = Long.parseLong(txtID.getText());
        String cuit = txtCuit.getText();
        String email = txtEmail.getText();
        String direccion = txtDireccion.getText();
        String alias = txtAlias.getText();
        String cbu = txtCbu.getText();
        double latitud = Double.parseDouble(txtLatitud.getText());
        double longitud = Double.parseDouble(txtLongitud.getText());
        Coordenada coordenadas = new Coordenada(latitud, longitud);

        controlador.modificarCliente(id, cuit, email, direccion, coordenadas, alias, cbu);
        areaResultados.setText("Cliente modificado.");
    }

    private void eliminarCliente(ActionEvent e) {
        long id = Long.parseLong(txtID.getText());
        controlador.eliminarCliente(id);
        areaResultados.setText("Cliente eliminado.");
    }

  private void listarClientes() {
    Set<Cliente> clientes = controlador.obtenerListaClientes(); // Obtener la lista desde el controlador

    if (clientes.isEmpty()) {
        areaResultados.setText("No hay clientes registrados.");
    } else {
        StringBuilder sb = new StringBuilder("Clientes:\n");
        for (Cliente cliente : clientes) {
            sb.append("ID: ").append(cliente.getId()).append("\n")
              .append("CUIT: ").append(cliente.getCuit()).append("\n")
              .append("Email: ").append(cliente.getEmail()).append("\n")
              .append("Dirección: ").append(cliente.getDireccion()).append("\n")
              .append("Alias: ").append(cliente.getAlias()).append("\n")
              .append("CBU: ").append(cliente.getCbu()).append("\n")
              .append("Coordenadas: (").append(cliente.getCoor().getLatitud()).append(", ")
              .append(cliente.getCoor().getLongitud()).append(")\n")
              .append("--------------------------------------\n");
        }
        areaResultados.setText(sb.toString());
    }
}

    public static void main(String[] args) {
        ClienteMemory memory = new ClienteMemory();
        ClienteController controlador = new ClienteController(memory);
        new GestionClientes(controlador);
    }
}
