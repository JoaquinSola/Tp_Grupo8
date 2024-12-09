/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;


public class ItemsMenuMemory implements ItemMenuDAO {
    private Connection connection;
    private final Set<ItemMenu> items = new HashSet<>();

    public ItemsMenuMemory() throws SQLException {
        this.connection = ConexionDB.getConnection();
    }
    @Override
    public void crearItem(ItemMenu item) {
            String sql = "INSERT INTO itemMenu (nombre, descripcion, precio) VALUES (?, ?, ?)";
            try(PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                stmt.setString(1,item.getNombre());
                stmt.setString(2,item.getDesc());
                stmt.setDouble(3,item.getPrecio());
                
                // Obtener el ID generado automáticamente para el item
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getLong(1));
                }
            } //Guardar los datos en plato o bebida dependiendo del objeto que se guarde
               /*  if(item.getClass() == Plato.class){
                     String sql = String sql = "INSERT INTO itemMenu (nombre, descripcion, precio) VALUES (?, ?, ?)";
                     
                }*/
            }catch(SQLException e){
                e.printStackTrace();
            }
    }

    @Override
    public ItemMenu buscarItem(long id) {
        return items.stream()
                .filter(item -> item.getId() == id)  // Usar el getter
                .findFirst()
                .orElse(null);
    }

    @Override
    public void actualizarItem(ItemMenu item) {
        eliminarItem(item.getId());  // Usar el getter
        items.add(item);
    }

    @Override
    public void eliminarItem(long id) {
        items.removeIf(item -> item.getId() == id);  // Usar el getter
    }

    @Override
    public Set<ItemMenu> listarItems() {
        return items;
    }
}
