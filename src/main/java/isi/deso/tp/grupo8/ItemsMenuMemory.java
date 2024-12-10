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
            String sqlcat = "INSERT INTO categoria (descripcion,tipo_item) VALUES (?,?)";
            String sql = "INSERT INTO itemMenu (nombre, descripcion, precio, id_categoria) VALUES (?, ?, ?, ?)";
            try(PreparedStatement stmt3 = connection.prepareStatement(sqlcat, Statement.RETURN_GENERATED_KEYS)){
                stmt3.setString(1,item.getDesc());
                stmt3.setObject(2,item.getCategoria().getTipo());
                stmt3.executeUpdate();
                
                // Obtener el ID generado automáticamente para el item
            try (ResultSet generatedKeys = stmt3.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.getCategoria().setID(generatedKeys.getLong(1));
                }
            }
                
            }catch(SQLException e){
                e.printStackTrace();
            }
            try(PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                stmt.setString(1,item.getNombre());
                stmt.setString(2,item.getDesc());
                stmt.setDouble(3,item.getPrecio());
                stmt.setDouble(4,item.getCategoria().getId());
                stmt.executeUpdate();
                
                // Obtener el ID generado automáticamente para el item
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getLong(1));
                }
            }
            
            //Guardar los datos en plato o bebida dependiendo del tipo de objeto
               if(item.esComida()){
                Plato itemComida = (Plato) item;
                String sql2 = "INSERT INTO plato (nombre, descripcion, precio, id_categoria, calorias, aptoCeliaco, aptoVegetariano, peso, id_itemMenu) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try(PreparedStatement stmt2 = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS)){
                stmt2.setString(1,itemComida.getNombre());
                stmt2.setString(2,itemComida.getDesc());
                stmt2.setDouble(3,itemComida.getPrecio());
                stmt2.setDouble(4,itemComida.getCategoria().getId());
                stmt2.setDouble(5,itemComida.getCalorias());
                stmt2.setBoolean(6, itemComida.getAptoC());
                stmt2.setObject(7, itemComida.getAptoV()); //<----------- ver esto
                stmt2.setDouble(8, itemComida.getPeso());
                stmt2.setLong(9, itemComida.getId());
                stmt2.executeUpdate();
                }
              }else{
                if(item.esBebida()){
                Bebida itemBebida = (Bebida) item;
                String sql2 = "INSERT INTO bebida (nombre, descripcion, precio, id_categoria, graduacionAlcoholica, volumen, id_itemMenu) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try(PreparedStatement stmt2 = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS)){
                stmt2.setString(1,itemBebida.getNombre());
                stmt2.setString(2,itemBebida.getDesc());
                stmt2.setDouble(3,itemBebida.getPrecio());
                stmt2.setDouble(4,itemBebida.getCategoria().getId());
                stmt2.setDouble(5,itemBebida.getGradA());
                stmt2.setDouble(6, itemBebida.getVol());
                stmt2.setLong(7, itemBebida.getId());
                stmt2.executeUpdate();
                }
              }
               }
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
