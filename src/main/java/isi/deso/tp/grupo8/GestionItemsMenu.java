/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Set;

public class GestionItemsMenu extends JFrame {
    private final ItemsMenuController controlador;
    private JTextField txtID, txtNombre, txtDescripcion, txtPrecio, txtCalorias, txtVolumen, txtAlcohol;
    private JCheckBox chkCeliacos, chkVegetariano;
    private JTextArea areaResultados;
    private JButton btnCrearPlato, btnCrearBebida, btnBuscar, btnModificar, btnEliminar, btnListar;

    public GestionItemsMenu(ItemsMenuController controlador) {
        this.controlador = controlador;

        setTitle("Gestión de Ítems de Menú");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);;
        setLayout(new FlowLayout());

        txtID = new JTextField(5);
        txtNombre = new JTextField(15);
        txtDescripcion = new JTextField(20);
        txtPrecio = new JTextField(10);
        txtCalorias = new JTextField(10);
        txtVolumen = new JTextField(10);
        txtAlcohol = new JTextField(10);
        chkCeliacos = new JCheckBox("Apto Celíacos");
        chkVegetariano = new JCheckBox("Apto Vegetariano");
        areaResultados = new JTextArea(20, 50);
        areaResultados.setEditable(false);

        btnCrearPlato = new JButton("Crear Plato");
        btnCrearBebida = new JButton("Crear Bebida");
        btnBuscar = new JButton("Buscar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnListar = new JButton("Listar");

        // Añadir componentes
        add(new JLabel("ID:"));
        add(txtID);
        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Descripción:"));
        add(txtDescripcion);
        add(new JLabel("Precio:"));
        add(txtPrecio);
        add(new JLabel("Calorías:"));
        add(txtCalorias);
        add(chkCeliacos);
        add(chkVegetariano);
        add(new JLabel("Volumen:"));
        add(txtVolumen);
        add(new JLabel("Alcohol (%):"));
        add(txtAlcohol);
        add(btnCrearPlato);
        add(btnCrearBebida);
        add(btnBuscar);
        add(btnModificar);
        add(btnEliminar);
        add(btnListar);
        add(new JScrollPane(areaResultados));

        // Acciones de los botones
        btnCrearPlato.addActionListener(this::crearPlato);
        btnCrearBebida.addActionListener(this::crearBebida);
        btnBuscar.addActionListener(this::buscarItem);
        btnModificar.addActionListener(this::modificarItem);
        btnEliminar.addActionListener(this::eliminarItem);
        btnListar.addActionListener(e -> listarItems());

        setVisible(true);
    }

    private void crearPlato(ActionEvent e) {
         //Comprobar que esten completos todos los campos necesarios
         if(txtNombre.getText().isEmpty() || txtDescripcion.getText().isEmpty() || txtPrecio.getText().isEmpty() || txtCalorias.getText().isEmpty()){
            areaResultados.setText("Por favor, complete todos los campos");
            return;
        }
        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();
        double precio = Double.parseDouble(txtPrecio.getText());
        double calorias = Double.parseDouble(txtCalorias.getText());
        boolean celiacos = chkCeliacos.isSelected();
        boolean vegetariano = chkVegetariano.isSelected();

        controlador.crearPlato(nombre, descripcion, precio, calorias, celiacos, vegetariano);
        areaResultados.setText("Plato creado.");
    }

    private void crearBebida(ActionEvent e) {
             //Comprobar que esten completos todos los campos necesarios
         if(txtNombre.getText().isEmpty() || txtDescripcion.getText().isEmpty() || txtPrecio.getText().isEmpty() || txtVolumen.getText().isEmpty() || txtAlcohol.getText().isEmpty()){
            areaResultados.setText("Por favor, complete todos los campos");
            return;
        }
        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();
        double precio = Double.parseDouble(txtPrecio.getText());
        double volumen = Double.parseDouble(txtVolumen.getText());
        double alcohol = Double.parseDouble(txtAlcohol.getText());

        controlador.crearBebida(nombre, descripcion, precio, volumen, alcohol);
        areaResultados.setText("Bebida creada.");
    }

    private void buscarItem(ActionEvent e) {
        int id = Integer.parseInt(txtID.getText());
        ItemMenu item = controlador.buscarItem(id);

        if (item != null) {
            StringBuilder sb = new StringBuilder("Ítems del Menú:\n");
            sb.append("ID: ").append(item.getId()).append("\n")
              .append("Nombre: ").append(item.nombre).append("\n")
              .append("Descripción: ").append(item.getDesc()).append("\n")
              .append("Precio: $").append(item.getPrecio()).append("\n");

            // Verificar si es un Plato o una Bebida
            if (item instanceof Plato) {
                Plato plato = (Plato) item;
                sb.append("Tipo: Plato\n")
                  .append("Calorías: ").append(plato.getCalorias()).append("\n")
                  .append("Apto Celíacos: ").append(plato.getAptoC() ? "Sí" : "No").append("\n")
                  .append("Apto Vegetariano: ").append(plato.getAptoV() ? "Sí" : "No").append("\n");
            } else if (item instanceof Bebida) {
                Bebida bebida = (Bebida) item;
                sb.append("Tipo: Bebida\n")
                  .append("Volumen: ").append(bebida.getVol()).append(" L\n")
                  .append("Alcohol: ").append(bebida.getGradA() > 0 ? "Sí" : "No").append("\n");
            }

            sb.append("--------------------------------------\n");
        } else {
            areaResultados.setText("Ítem no encontrado.");
        }
    }

   private void modificarItem(ActionEvent e) {
    try {
        int id = Integer.parseInt(txtID.getText());
        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();
        double precio = Double.parseDouble(txtPrecio.getText());

        controlador.modificarItem(id, nombre, descripcion, precio);
        areaResultados.setText("Ítem modificado exitosamente.");
    } catch (NumberFormatException ex) {
        areaResultados.setText("Error: Asegúrate de ingresar un ID y precio válidos.");
    } catch (IllegalArgumentException ex) {
        areaResultados.setText(ex.getMessage());
    }
}

    private void eliminarItem(ActionEvent e) {
        int id = Integer.parseInt(txtID.getText());
        controlador.eliminarItem(id);
        areaResultados.setText("Ítem eliminado.");
    }

  private void listarItems() {
    Set<ItemMenu> items = controlador.obtenerListaItems(); // Obtener los ítems desde el controlador

    if (items.isEmpty()) {
        areaResultados.setText("No hay ítems en el menú.");
    } else {
        StringBuilder sb = new StringBuilder("Ítems del Menú:\n");
        for (ItemMenu item : items) {
            sb.append("ID: ").append(item.getId()).append("\n")
              .append("Nombre: ").append(item.nombre).append("\n")
              .append("Descripción: ").append(item.getDesc()).append("\n")
              .append("Precio: $").append(item.getPrecio()).append("\n");

            // Verificar si es un Plato o una Bebida
            if (item instanceof Plato) {
                Plato plato = (Plato) item;
                sb.append("Tipo: Plato\n")
                  .append("Calorías: ").append(plato.getCalorias()).append("\n")
                  .append("Apto Celíacos: ").append(plato.getAptoC() ? "Sí" : "No").append("\n")
                  .append("Apto Vegetariano: ").append(plato.getAptoV() ? "Sí" : "No").append("\n");
            } else if (item instanceof Bebida) {
                Bebida bebida = (Bebida) item;
                sb.append("Tipo: Bebida\n")
                  .append("Volumen: ").append(bebida.getVol()).append(" L\n")
                  .append("Alcohol: ").append(bebida.getGradA() > 0 ? "Sí" : "No").append("\n");
            }

            sb.append("--------------------------------------\n");
        }
        areaResultados.setText(sb.toString());
    }
}

    public static void main(String[] args) {
        ItemsMenuMemory memory = new ItemsMenuMemory();
        ItemsMenuController controlador = new ItemsMenuController(memory);
        new GestionItemsMenu(controlador);
    }
}
