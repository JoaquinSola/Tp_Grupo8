package isi.deso.tp.grupo8;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class PedidoMemory implements PedidoDAO {
    private final Connection connection;

    public PedidoMemory() throws SQLException {
        this.connection = ConexionDB.getConnection();
    }

    @Override
    public void crearPedido(Pedido pedido) {
        crearPago(pedido.getPago(), pedido.getId()); // Create and save the payment first
        String sql = "INSERT INTO pedido (id_cliente, id_vendedor, metodo_pago, estado, id_pago) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, pedido.getCliente().getId());
            stmt.setLong(2, pedido.getVendedor().getId());
            stmt.setString(3, pedido.getMetodoDePago());
            stmt.setString(4, pedido.getEstado().toString());
            stmt.setLong(5, pedido.getPago().getId());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pedido.setId(generatedKeys.getLong(1));
                }
            }
            for (ItemPedido itemPedido : pedido.getItemsPedidoMemory().getLista()) {
                ItemMenu itemMenu = itemPedido.getItemPedido();
                agregarItemAPedido(pedido.getId(), itemMenu.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Pedido buscarPedido(long id) {
        String sql = "SELECT * FROM pedido WHERE id_pedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Pedido pedido = new Pedido();
                    pedido.setId(rs.getLong("id_pedido"));
                    pedido.setCliente(new ClienteMemory().buscarCliente(rs.getLong("id_cliente")));
                    pedido.setVendedor(new VendedorMemory().buscarVendedor(rs.getLong("id_vendedor")));
                    pedido.setMetodoDePago(rs.getString("metodo_pago"));
                    pedido.setEstado(EstadoPedido.valueOf(rs.getString("estado")));
                    pedido.setItemsPedidoMemory(obtenerItemsPedido(id));
                    pedido.setPago(buscarPago(id));
                    return pedido;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void actualizarPedido(Pedido pedido) {
        String sql = "UPDATE pedido SET estado = ?, metodo_pago = ? WHERE id_pedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pedido.getEstado().toString());
            stmt.setString(2, pedido.getMetodoDePago());
            stmt.setLong(3, pedido.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarPedido(long id) {
        String sql = "DELETE FROM pedido WHERE id_pedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<Pedido> listarPedidos() {
        Set<Pedido> pedidos = new HashSet<>();
        String sql = "SELECT * FROM pedido";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getLong("id_pedido"));
                pedido.setCliente(new ClienteMemory().buscarCliente(rs.getLong("id_cliente")));
                pedido.setVendedor(new VendedorMemory().buscarVendedor(rs.getLong("id_vendedor")));
                pedido.setMetodoDePago(rs.getString("metodo_pago"));
                pedido.setEstado(EstadoPedido.valueOf(rs.getString("estado")));
                pedido.setItemsPedidoMemory(obtenerItemsPedido(pedido.getId()));
                pedido.setPago(buscarPago(pedido.getId()));
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    private ItemsPedidoMemory obtenerItemsPedido(long idPedido) {
        ItemsPedidoMemory itemsPedidoMemory = new ItemsPedidoMemory();
        String sql = "SELECT * FROM pedido_itemmenu WHERE id_pedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idPedido);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ItemMenu item = new ItemsMenuMemory().buscarItem(rs.getLong("id_itemmenu"));
                    if (item != null) {
                        itemsPedidoMemory.agregarItem(item);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemsPedidoMemory;
    }

    private void agregarItemAPedido(long idPedido, long idItemMenu) {
        String sql = "INSERT INTO pedido_itemmenu (id_pedido, id_itemmenu) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idPedido);
            stmt.setLong(2, idItemMenu);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void crearPago(Pago pago, long idPedido) {
        String sqlPago = "INSERT INTO pago (monto, fecha, tipo_pago) VALUES (?, ?, ?)";
        try (PreparedStatement stmtPago = connection.prepareStatement(sqlPago, Statement.RETURN_GENERATED_KEYS)) {
            stmtPago.setDouble(1, pago.getMonto());
            stmtPago.setDate(2, java.sql.Date.valueOf(pago.getFecha()));
            stmtPago.setString(3, pago.getTipoPago());
            stmtPago.executeUpdate();
            try (ResultSet generatedKeys = stmtPago.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pago.setId(generatedKeys.getLong(1));
                }
            }

            if (pago instanceof PagoPorMP) {
                String sqlMP = "INSERT INTO pagopormp (id_pago, alias, recargo) VALUES (?, ?, ?)";
                try (PreparedStatement stmtMP = connection.prepareStatement(sqlMP)) {
                    stmtMP.setLong(1, pago.getId());
                    stmtMP.setString(2, ((PagoPorMP) pago).getAlias());
                    stmtMP.setDouble(3, ((PagoPorMP) pago).getRecargo());
                    stmtMP.executeUpdate();
                }
            } else if (pago instanceof PagoPorTransferencia) {
                String sqlTransf = "INSERT INTO pagoportransferencia (id_pago, cbu, cuit) VALUES (?, ?, ?)";
                try (PreparedStatement stmtTransf = connection.prepareStatement(sqlTransf)) {
                    stmtTransf.setLong(1, pago.getId());
                    stmtTransf.setString(2, ((PagoPorTransferencia) pago).getCbu());
                    stmtTransf.setString(3, ((PagoPorTransferencia) pago).getCuit());
                    stmtTransf.executeUpdate();
                }
            }

            // Associate the payment with the order
            String sqlPedidoPago = "INSERT INTO pedido_pago (id_pedido, id_pago) VALUES (?, ?)";
            try (PreparedStatement stmtPedidoPago = connection.prepareStatement(sqlPedidoPago)) {
                stmtPedidoPago.setLong(1, idPedido);
                stmtPedidoPago.setLong(2, pago.getId());
                stmtPedidoPago.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Pago buscarPago(long idPedido) {
        String sql = "SELECT p.* FROM pago p JOIN pedido_pago pp ON p.id_pago = pp.id_pago WHERE pp.id_pedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idPedido);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Pago pago;
                    String tipoPago = rs.getString("tipo_pago");
                    if (tipoPago.equals("Mercado Pago")) {
                        pago = new PagoPorMP(rs.getLong("id_pago"), rs.getDouble("monto"), rs.getDate("fecha").toLocalDate(), rs.getString("alias"), rs.getDouble("recargo"));
                    } else if (tipoPago.equals("Transferencia")) {
                        pago = new PagoPorTransferencia(rs.getLong("id_pago"), rs.getDouble("monto"), rs.getDate("fecha").toLocalDate(), rs.getString("cbu"), rs.getString("cuit"), 0.0);
                    } else {
                        throw new IllegalArgumentException("Tipo de pago desconocido: " + tipoPago);
                    }
                    return pago;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
