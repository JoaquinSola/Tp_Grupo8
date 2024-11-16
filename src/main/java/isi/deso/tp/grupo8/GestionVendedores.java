package isi.deso.tp.grupo8;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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

    CoordenadaDAO coordenadaDAO = new CoordenadaDAO();
    
    // 1. Buscar si ya existe la coordenada con estas latitud y longitud
    Coordenada coordenada = coordenadaDAO.findByLatLong(latitud, longitud);

    if (coordenada == null) {
        // 2. Si no existe, crear la coordenada
        coordenada = new Coordenada(latitud, longitud, 0); // ID inicializado en 0
        coordenadaDAO.save(coordenada); // Este método asignará un ID desde la DB
        
        if (coordenada.getId() == 0) { // Validación para evitar errores en la asignación del ID
            areaResultados.setText("Error al guardar la coordenada. No se generó un ID válido.");
            return;
        }
    }

    // 3. Crear el vendedor con la coordenada existente o recién creada
    controlador.crearNuevoVendedor(nombre, direccion, coordenada);
    areaResultados.setText("Vendedor creado con ID Coordenada: " + coordenada.getId());
}

    private void buscarVendedor(ActionEvent e) {
        try {
            long id = Long.parseLong(txtID.getText()); // Obtener el ID como long
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
        } catch (NumberFormatException ex) {
            areaResultados.setText("El ID debe ser un número válido.");
        }
    }

    private void modificarVendedor(ActionEvent e) {
        try {
            long id = Long.parseLong(txtID.getText()); // Obtener el ID como long
            String nombre = txtNombre.getText();
            String direccion = txtDireccion.getText();
            double latitud = Double.parseDouble(txtLatitud.getText());
            double longitud = Double.parseDouble(txtLongitud.getText());
            Coordenada coord = new Coordenada(latitud, longitud,1);

            controlador.modificarVendedor(id, nombre, direccion, coord);
            areaResultados.setText("Vendedor modificado.");
        } catch (NumberFormatException ex) {
            areaResultados.setText("El ID debe ser un número válido.");
        }
    }

    private void eliminarVendedor(ActionEvent e) {
        try {
            long id = Long.parseLong(txtID.getText()); // Obtener el ID como long
            controlador.eliminarVendedor(id);
            areaResultados.setText("Vendedor eliminado.");
        } catch (NumberFormatException ex) {
            areaResultados.setText("El ID debe ser un número válido.");
        }
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

public static void main(String[] args) throws SQLException {
        VendedorMemory memory = new VendedorMemory();
        VendedorController controlador = new VendedorController(memory);
        new GestionVendedores(controlador);
    }
}