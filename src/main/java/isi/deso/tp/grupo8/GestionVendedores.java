/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Set;

public class GestionVendedores extends JFrame {
    private final VendedorController controlador;
    private JTextField txtNombre, txtDireccion, txtLatitud, txtLongitud, txtID;
    private JTextArea areaResultados;
    private JButton btnCrear, btnBuscar, btnModificar, btnEliminar, btnListar;

    public GestionVendedores(VendedorController controlador) {
        this.controlador = controlador;

        setTitle("Gestión de Vendedores");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        txtID = new JTextField(10);
        txtNombre = new JTextField(15);
        txtDireccion = new JTextField(20);
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
        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Dirección:"));
        add(txtDireccion);
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

        btnCrear.addActionListener(this::crearVendedor);
        btnBuscar.addActionListener(this::buscarVendedor);
        btnModificar.addActionListener(this::modificarVendedor);
        btnEliminar.addActionListener(this::eliminarVendedor);
        btnListar.addActionListener(e -> listarVendedores());

        setVisible(true);
    }

    private void crearVendedor(ActionEvent e) {
        String nombre = txtNombre.getText();
        String direccion = txtDireccion.getText();
        double latitud = Double.parseDouble(txtLatitud.getText());
        double longitud = Double.parseDouble(txtLongitud.getText());
        Coordenada coord = new Coordenada(latitud, longitud);

        controlador.crearNuevoVendedor(nombre, direccion, coord);
        areaResultados.setText("Vendedor creado.");
    }

   private void buscarVendedor(ActionEvent e) {
    String id = txtID.getText(); // Obtener el ID del campo de texto
    Vendedor vendedor = controlador.buscarVendedor(id);

    if (vendedor != null) {
        // Mostrar los datos completos del vendedor
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(vendedor.getId()).append("\n")
          .append("Nombre: ").append(vendedor.getNombre()).append("\n")
          .append("Dirección: ").append(vendedor.getDireccion()).append("\n")
          .append("Coordenadas: (").append(vendedor.getCoor().getLatitud()).append(", ")
          .append(vendedor.getCoor().getLongitud()).append(")\n");

        areaResultados.setText(sb.toString());
    } else {
        areaResultados.setText("Vendedor con ID " + id + " no encontrado.");
    }
}

    private void modificarVendedor(ActionEvent e) {
        String id = txtID.getText();
        String nombre = txtNombre.getText();
        String direccion = txtDireccion.getText();
        double latitud = Double.parseDouble(txtLatitud.getText());
        double longitud = Double.parseDouble(txtLongitud.getText());
        Coordenada coord = new Coordenada(latitud, longitud);

        controlador.modificarVendedor(id, nombre, direccion, coord);
        areaResultados.setText("Vendedor modificado.");
    }

    private void eliminarVendedor(ActionEvent e) {
        String id = txtID.getText();
        controlador.eliminarVendedor(id);
        areaResultados.setText("Vendedor eliminado.");
    }

  private void listarVendedores() {
    Set<Vendedor> vendedores = controlador.obtenerListaVendedores(); // Obtener lista desde el controlador
    if (vendedores.isEmpty()) {
        areaResultados.setText("No hay vendedores registrados.");
    } else {
        StringBuilder sb = new StringBuilder("Vendedores:\n");
        for (Vendedor vendedor : vendedores) {
            sb.append("ID: ").append(vendedor.getId()).append("\n")
              .append("Nombre: ").append(vendedor.getNombre()).append("\n")
              .append("Dirección: ").append(vendedor.getDireccion()).append("\n")
              .append("Coordenadas: (").append(vendedor.getCoor().getLatitud()).append(", ")
              .append(vendedor.getCoor().getLongitud()).append(")\n")
              .append("--------------------------------------\n");
        }
        areaResultados.setText(sb.toString());
    }
}

    public static void main(String[] args) {
        VendedorMemory memory = new VendedorMemory();
        VendedorController controlador = new VendedorController(memory);
        new GestionVendedores(controlador);
    }
}