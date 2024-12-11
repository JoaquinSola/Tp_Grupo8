package isi.deso.tp.grupo8;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GestionPago extends JFrame {
    private JTextField txtMonto;
    private JTextField txtAlias, txtCbu, txtCuit;
    private JButton btnGuardarPago;
    private JComboBox<String> comboMetodoPago;
    private Pedido pedido;

    public GestionPago(Pedido pedido) {
        this.pedido = pedido;

        setTitle("Gestión de Pago");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        txtMonto = new JTextField(10);
        txtAlias = new JTextField(10);
        txtCbu = new JTextField(10);
        txtCuit = new JTextField(10);
        btnGuardarPago = new JButton("Guardar Pago");
        comboMetodoPago = new JComboBox<>(new String[]{"MP", "Por Transferencia"});

        add(new JLabel("Monto:"));
        txtMonto.setText(String.valueOf(calcularMontoTotal()));
        txtMonto.setEditable(false);
        add(txtMonto);

        add(new JLabel("Método de Pago:"));
        add(comboMetodoPago);

        add(new JLabel("Alias:"));
        add(txtAlias);

        add(new JLabel("CBU:"));
        add(txtCbu);

        add(new JLabel("CUIT:"));
        add(txtCuit);

        add(btnGuardarPago);

        comboMetodoPago.addActionListener(this::cambiarMetodoPago);
        btnGuardarPago.addActionListener(e -> {
            try {
                guardarPago(e);
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Handle the exception appropriately
            }
        });

        cambiarMetodoPago(null); // Inicializar campos para el método de pago seleccionado

        setVisible(true);
    }

    private double calcularMontoTotal() {
        return pedido.getItemsPedidoMemory().getLista().stream()
                .mapToDouble(itemPedido -> itemPedido.getItemPedido().getPrecio())
                .sum();
    }

    private void cambiarMetodoPago(ActionEvent e) {
        String metodoPago = (String) comboMetodoPago.getSelectedItem();
        if ("MP".equals(metodoPago)) {
            txtAlias.setVisible(true);
            txtCbu.setVisible(false);
            txtCuit.setVisible(false);
        } else if ("Por Transferencia".equals(metodoPago)) {
            txtAlias.setVisible(false);
            txtCbu.setVisible(true);
            txtCuit.setVisible(true);
        }
    }

    private void guardarPago(ActionEvent e) throws SQLException {
        String metodoPago = (String) comboMetodoPago.getSelectedItem();
        LocalDate fechaActual = LocalDate.now();

        double monto = Double.parseDouble(txtMonto.getText());
        Pago pago;

        if ("MP".equals(metodoPago)) {
            pago = new PagoPorMP(0, monto, fechaActual, txtAlias.getText(), monto * 0.05);
        } else if ("Por Transferencia".equals(metodoPago)) {
            pago = new PagoPorTransferencia(0, monto, fechaActual, txtCbu.getText(), txtCuit.getText(), 0.0);
        } else {
            throw new IllegalArgumentException("Método de pago desconocido: " + metodoPago);
        }

        // Save the payment in the database
        PagoDAO pagoDAO = new PagoMemory();
        pagoDAO.crearPago(pago);

        // Associate the payment with the order
        if (pedido != null) {
            pedido.setPago(pago);
            pedido.setMetodoDePago(metodoPago);
            // Save the order with the payment
            PedidoDAO pedidoDAO = new PedidoMemory();
            pedidoDAO.crearPedido(pedido);
        } else {
            // Handle the case where there is no current order
            System.err.println("No current order found.");
        }
        dispose();
    }
}
