package isi.deso.tp.grupo8;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class VendedorMemory implements VendedorDAO {
    private Connection connection;

    public VendedorMemory() throws SQLException {
        this.connection = ConexionDB.getConnection();
    }

    @Override
    public void crearVendedor(Vendedor vendedor) {
        try {
            connection.setAutoCommit(false);

            // Crear la coordenada primero
            Coordenada coordenada = vendedor.getCoor();
            if (coordenada != null && coordenada.getId() == 0) {
                String sqlCoordenada = "INSERT INTO coordenada (latitud, longitud) VALUES (?, ?)";
                try (PreparedStatement stmtCoordenada = connection.prepareStatement(sqlCoordenada, Statement.RETURN_GENERATED_KEYS)) {
                    stmtCoordenada.setDouble(1, coordenada.getLatitud());
                    stmtCoordenada.setDouble(2, coordenada.getLongitud());
                    stmtCoordenada.executeUpdate();
                    try (ResultSet generatedKeys = stmtCoordenada.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            coordenada.setId(generatedKeys.getLong(1));
                        }
                    }
                }
            }

            // Crear el vendedor usando el ID de la coordenada
            String sqlVendedor = "INSERT INTO vendedor (nombre, direccion, id_coordenada) VALUES (?, ?, ?)";
            try (PreparedStatement stmtVendedor = connection.prepareStatement(sqlVendedor, Statement.RETURN_GENERATED_KEYS)) {
                stmtVendedor.setString(1, vendedor.getNombre());
                stmtVendedor.setString(2, vendedor.getDireccion());
                stmtVendedor.setLong(3, vendedor.getCoor().getId());
                stmtVendedor.executeUpdate();
                try (ResultSet generatedKeys = stmtVendedor.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        vendedor.setId(generatedKeys.getLong(1));
                    }
                }
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public Vendedor buscarVendedor(long id) {
        String sql = "SELECT * FROM vendedor WHERE id_vendedor = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Vendedor vendedor = new Vendedor();
                    vendedor.setId(rs.getLong("id_vendedor"));
                    vendedor.setNombre(rs.getString("nombre"));
                    vendedor.setDireccion(rs.getString("direccion"));
                    Coordenada coordenada = new Coordenada();
                    coordenada.setId(rs.getLong("id_coordenada"));
                    vendedor.setCoordenada(coordenada);
                    vendedor.setListaProductos(obtenerListaProductos(id));
                    return vendedor;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean modificarVendedor(Vendedor vendedor) {
        String sql = "UPDATE vendedor SET nombre = ?, direccion = ?, id_coordenada = ? WHERE id_vendedor = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, vendedor.getNombre());
            stmt.setString(2, vendedor.getDireccion());
            stmt.setLong(3, vendedor.getCoor().getId());
            stmt.setLong(4, vendedor.getId());
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void eliminarVendedor(long id) {
        String sql = "DELETE FROM vendedor WHERE id_vendedor = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<Vendedor> listarVendedores() {
        Set<Vendedor> vendedores = new HashSet<>();
        String sql = "SELECT * FROM vendedor";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Vendedor vendedor = new Vendedor();
                vendedor.setId(rs.getLong("id_vendedor"));
                vendedor.setNombre(rs.getString("nombre"));
                vendedor.setDireccion(rs.getString("direccion"));
                Coordenada coordenada = new Coordenada();
                coordenada.setId(rs.getLong("id_coordenada"));
                vendedor.setCoordenada(coordenada);
                vendedor.setListaProductos(obtenerListaProductos(vendedor.getId()));
                vendedores.add(vendedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vendedores;
    }

    @Override
    public void agregarItemMenu(long idVendedor, long idItemMenu) {
        String sql = "INSERT INTO vendedor_itemmenu (id_vendedor, id_itemMenu) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idVendedor);
            stmt.setLong(2, idItemMenu);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<ItemMenu> obtenerListaProductos(long idVendedor) {
        Set<ItemMenu> items = new HashSet<>();
        String sql = "SELECT * FROM vendedor_itemmenu WHERE id_vendedor = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idVendedor);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    long itemId = rs.getLong("id_itemMenu");
                    ItemMenu item = new ItemsMenuMemory().buscarItem(itemId);
                    if (item != null) {
                        items.add(item);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}
