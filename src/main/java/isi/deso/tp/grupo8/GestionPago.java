package isi.deso.tp.grupo8;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
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
    private Pago pago;
    private GestionPedidos gestionPedidos;

    public GestionPago(Pago pago, GestionPedidos gestionPedidos) {
        this.pago = pago;
        this.gestionPedidos = gestionPedidos;

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
        txtMonto.setText(String.valueOf(pago.getMonto()));
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
        btnGuardarPago.addActionListener(this::guardarPago);

        cambiarMetodoPago(null); // Inicializar campos para el método de pago seleccionado

        setVisible(true);
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

    private void guardarPago(ActionEvent e) {
        String metodoPago = (String) comboMetodoPago.getSelectedItem();
        LocalDate fechaActual = LocalDate.now();

        if ("MP".equals(metodoPago)) {
            pago = new PagoPorMP(0, Double.parseDouble(txtMonto.getText()), fechaActual, txtAlias.getText(), Double.parseDouble(txtMonto.getText()) * 0.05);
        } else if ("Por Transferencia".equals(metodoPago)) {
            pago = new PagoPorTransferencia(0, Double.parseDouble(txtMonto.getText()), fechaActual, txtCbu.getText(), txtCuit.getText(), 0.0);
        }

        // Crear el pedido y asociar el pago
        Pedido pedido = new Pedido();
        pedido.setPago(pago);

        gestionPedidos.guardarPedidoConPago(pedido);
        dispose();
    }
}
