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
    try {
        String nombre = txtNombre.getText();
        String direccion = txtDireccion.getText();

        // Validar que los campos de latitud y longitud no estén vacíos
        if (txtLatitud.getText().isEmpty() || txtLongitud.getText().isEmpty()) {
            areaResultados.setText("Por favor, completa todos los campos.");
            return; // Detener la ejecución si algún campo está vacío
        }

        // Convertir latitud y longitud a double
        double latitud = Double.parseDouble(txtLatitud.getText());
        double longitud = Double.parseDouble(txtLongitud.getText());

        System.out.println("Latitud: " + latitud);
        System.out.println("Longitud: " + longitud);

        // Crear la coordenada con latitud y longitud (sin ID, se asignará automáticamente)
        Coordenada c1 = new Coordenada(longitud, latitud); // ID de coordenada aún no asignado

        // Guardar la coordenada en la base de datos
        CoordenadaDAO coordenadaDAO = new CoordenadaDAO();
        coordenadaDAO.save(c1); // Esto debería generar un ID para la coordenada

        // Crear el vendedor con la coordenada existente o recién creada
        controlador.crearNuevoVendedor(nombre, direccion, c1);
        areaResultados.setText("Vendedor creado: " + nombre);

    } catch (NumberFormatException ex) {
        areaResultados.setText("Por favor, ingresa valores válidos en los campos numéricos.");
        ex.printStackTrace(); // Puedes registrar el error para depuración
    } catch (Exception ex) {
        areaResultados.setText("Ocurrió un error al crear el vendedor: " + ex.getMessage());
        ex.printStackTrace();
    }
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
                  .append("Coordedana id: ").append(vendedor.getCoor().getId()).append("\n")
                  .append("Coordedana Latitud: ").append(vendedor.getCoor().getLatitud()).append("\n")
                  .append("Coordedana Longitud: ").append(vendedor.getCoor().getLongitud()).append("\n");
                  //.append("Coordenadas: (").append((vendedor.getCoor()).getLatitud()).append(", ").append((vendedor.getCoor()).getLongitud()).append(")\n");
                  
                areaResultados.setText(sb.toString());
            } else {
                areaResultados.setText("Vendedor con ID " + id + " no encontrado.");
            }
        } catch (NumberFormatException ex) {
            areaResultados.setText("El ID debe ser un número válido.");
        }
    }

    private boolean modificarVendedor(ActionEvent e) {
        try {
            // Crear un objeto Vendedor con los datos del formulario
            Vendedor vendedor = new Vendedor();
            vendedor.setId(Long.parseLong(txtID.getText())); // id_vendedor
            vendedor.setNombre(txtNombre.getText());
            vendedor.setDireccion(txtDireccion.getText());
    
            // Crear un objeto Coordenada
            Coordenada coord = new Coordenada();
            coord.setLatitud(Double.parseDouble(txtLatitud.getText()));
            coord.setLongitud(Double.parseDouble(txtLongitud.getText()));
            vendedor.setCoordenada(coord); // Asociar la coordenada al vendedor
    
            // Llamar al controlador para modificar el vendedor
            boolean modificado = controlador.modificarVendedor(vendedor);
    
            // Mostrar mensaje en el área de resultados
            if (modificado) {
                areaResultados.setText("Vendedor modificado exitosamente.");
            } else {
                areaResultados.setText("No se pudo modificar el vendedor. Verifica el ID.");
            }
        } catch (NumberFormatException ex) {
            areaResultados.setText("Por favor, ingresa datos válidos en los campos.");
        } catch (Exception ex) {
            areaResultados.setText("Ocurrió un error al modificar el vendedor: " + ex.getMessage());
        }
        return true;
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