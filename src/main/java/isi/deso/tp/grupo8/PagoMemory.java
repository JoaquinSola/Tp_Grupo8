package isi.deso.tp.grupo8;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class PagoMemory implements PagoDAO {
    private Connection connection;

    public PagoMemory() throws SQLException {
        this.connection = ConexionDB.getConnection();
    }


    @Override
    public void crearPago(Pago pago) {
        String sqlPago = "INSERT INTO pago (monto, fecha) VALUES (?, ?)";
        try (PreparedStatement stmtPago = connection.prepareStatement(sqlPago, Statement.RETURN_GENERATED_KEYS)) {
            stmtPago.setDouble(1, pago.getMonto());
            stmtPago.setDate(2, Date.valueOf(pago.getFecha()));
            stmtPago.executeUpdate();

            try (ResultSet generatedKeys = stmtPago.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long idPago = generatedKeys.getLong(1);
                    pago.setId(idPago);

                    if (pago instanceof PagoPorMP) {
                        PagoPorMP pagoMP = (PagoPorMP) pago;
                        String sqlMP = "INSERT INTO pagopormp (monto, fecha, recargo, alias, id_pago) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement stmtMP = connection.prepareStatement(sqlMP)) {
                            stmtMP.setDouble(1, pagoMP.getMonto());
                            stmtMP.setDate(2, Date.valueOf(pagoMP.getFecha()));
                            stmtMP.setDouble(3, pagoMP.getRecargo());
                            stmtMP.setString(4, pagoMP.getAlias());
                            stmtMP.setLong(5, idPago);
                            stmtMP.executeUpdate();
                        }
                    } else if (pago instanceof PagoPorTransferencia) {
                        PagoPorTransferencia pagoTrans = (PagoPorTransferencia) pago;
                        String sqlTrans = "INSERT INTO pagoportransferencia (monto, fecha, recargo, cbu, cuit, id_pago) VALUES (?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement stmtTrans = connection.prepareStatement(sqlTrans)) {
                            stmtTrans.setDouble(1, pagoTrans.getMonto());
                            stmtTrans.setDate(2, Date.valueOf(pagoTrans.getFecha()));
                            stmtTrans.setDouble(3, pagoTrans.getRecargo());
                            stmtTrans.setString(4, pagoTrans.getCbu());
                            stmtTrans.setString(5, pagoTrans.getCuit());
                            stmtTrans.setLong(6, idPago);
                            stmtTrans.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Pago buscarPago(long id) {
        String sqlPago = "SELECT * FROM pago WHERE id_pago = ?";
        try (PreparedStatement stmtPago = connection.prepareStatement(sqlPago)) {
            stmtPago.setLong(1, id);
            try (ResultSet rsPago = stmtPago.executeQuery()) {
                if (rsPago.next()) {
                    double monto = rsPago.getDouble("monto");
                    LocalDate fecha = rsPago.getDate("fecha").toLocalDate();

                    // Check PagoPorMP
                    String sqlMP = "SELECT * FROM pagopormp WHERE id_pago = ?";
                    try (PreparedStatement stmtMP = connection.prepareStatement(sqlMP)) {
                        stmtMP.setLong(1, id);
                        try (ResultSet rsMP = stmtMP.executeQuery()) {
                            if (rsMP.next()) {
                                double recargo = rsMP.getDouble("recargo");
                                String alias = rsMP.getString("alias");
                                return new PagoPorMP(id, monto, fecha, alias, recargo);
                            }
                        }
                    }

                    // Check PagoPorTransferencia
                    String sqlTrans = "SELECT * FROM pagoportransferencia WHERE id_pago = ?";
                    try (PreparedStatement stmtTrans = connection.prepareStatement(sqlTrans)) {
                        stmtTrans.setLong(1, id);
                        try (ResultSet rsTrans = stmtTrans.executeQuery()) {
                            if (rsTrans.next()) {
                                double recargo = rsTrans.getDouble("recargo");
                                String cbu = rsTrans.getString("cbu");
                                String cuit = rsTrans.getString("cuit");
                                return new PagoPorTransferencia(id, monto, fecha, cbu, cuit, recargo);
                            }
                        }
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}