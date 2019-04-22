package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

public class Controlador_Inventario {

    Controlador_Principal ConPri = new Controlador_Principal();
    Modelo.Inventario Inventarios;

    public Connection Coneccion() throws SQLException {
        /**
         * *
         *
         * Esta función se utiliza para establecer la conexión con la base de
         * datos
         *
         */
        return DriverManager.getConnection("jdbc:mysql://localhost/DigoV?useSSL=false", "root", "password");
    }

    public String RegistrarInventario(int Id, int Existencia, int Minimo) {
        /**
         * *
         *
         * Esta función se utiliza para registrar una compra de productos dentro
         * de la base de datos
         *
         */
        PreparedStatement pst;
        try {
            String sql = "INSERT INTO `inventario_productos`(`id_productos`, `existencia`, `minimoInv`) VALUES (?,?,?)";
            pst = Coneccion().prepareStatement(sql);
            Inventarios = new Modelo.Inventario(Id, Existencia, Minimo);
            pst.setInt(1, Inventarios.getId());
            pst.setInt(2, Inventarios.getExistencia());
            pst.setInt(3, Inventarios.getMinimo());
            pst.executeUpdate();
            return "Se agrego exitosamente al inventario";
        } catch (SQLException ex) {
            return "Fallo en el registro del inventario";
        }
    }

    public String BorrarInventario(int fila, String Id) {
        /**
         * *
         *
         * Esta función se utiliza para eliminar un producto con compras hechas
         *
         */
        try {
            PreparedStatement pst;
            String sql = "DELETE FROM inventario_productos WHERE id_productos=?";
            pst = Coneccion().prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(Id));
            pst.executeUpdate();
            return "Eliminacion de inventario exitosa";
        } catch (SQLException ex) {
            return "Error al eliminar inventario";
        }
    }

    public TableModel BuscarId(int id) throws SQLException {
        /**
         * *
         *
         * Esta función se utiliza para saber si un código existe dentro de los
         * registros de la base de datos
         *
         */
        ResultSet ver;
        PreparedStatement pst;
        if ((id + "").equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Ingrese un Id");
        } else {
            String sql = "SELECT articulos.id_productos AS 'Codigo de producto', articulos.descripcion AS 'Descripcion', articulos.preCosto AS 'Precio de costo', articulos.preVenta AS 'Precio de venta', inventario_productos.existencia AS 'Existencia', inventario_productos.minimoInv FROM articulos INNER JOIN inventario_productos ON articulos.id_productos=inventario_productos.id_productos WHERE articulos.id_productos=?";
            pst = Coneccion().prepareStatement(sql);
            pst.setInt(1, id);
            ver = pst.executeQuery();
            return DbUtils.resultSetToTableModel(ver);
        }
        return null;
    }

    public String ModificarInventario(int Id, int Total, int minimo) {
        /**
         * *
         *
         * Esta función se utiliza para modificar los cambios hechos en el
         * inventario
         *
         */
        try {
            PreparedStatement pst;
            String sql = "UPDATE `inventario_productos` SET `existencia`=?,`minimoInv`=? WHERE id_productos=?";
            pst = Coneccion().prepareStatement(sql);
            pst.setInt(3, Id);
            pst.setInt(1, Total);
            pst.setInt(2, minimo);
            pst.executeUpdate();
            return "Se agrego al inventario";
        } catch (SQLException ex) {
            return "Error al agregar al inventario";
        }
    }

    public void DisminuirInventario(int Id) throws SQLException {
        /**
         * *
         *
         * Esta función se utiliza para disminuir el inventario al momento de
         * hacer una venta
         *
         */
        PreparedStatement pst;
        String sql = "UPDATE inventario_productos SET existencia=? WHERE id_productos=?";
        pst = Coneccion().prepareStatement(sql);
        pst.setInt(2, Id);
        pst.setInt(1, ConsultarInventario(Id) - 1);
        pst.executeUpdate();

    }

    public int ConsultarInventario(int Id) throws SQLException {
        PreparedStatement pst;
        ResultSet rs;
        int Existencia = 0;
        String sql = "SELECT existencia FROM inventario_productos WHERE id_productos=?";
        pst = Coneccion().prepareStatement(sql);
        pst.setInt(1, Id);
        rs = pst.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString(1));
            Existencia = Integer.parseInt(rs.getString(1));
        }
        return Existencia;
    }

    public TableModel BuscarDescripcion(String nombre) throws SQLException {
        ResultSet ver;
        PreparedStatement pst;
        String sql = "SELECT articulos.id_productos AS 'Codigo de producto', articulos.descripcion AS 'Descripcion', articulos.preCosto AS 'Precio de costo', articulos.preVenta AS 'Precio de venta', inventario_productos.existencia AS 'Existencia', inventario_productos.minimoInv FROM articulos INNER JOIN inventario_productos ON articulos.id_productos=inventario_productos.id_productos WHERE articulos.descripcion=?";
        pst = Coneccion().prepareStatement(sql);
        pst.setString(1, nombre);
        ver = pst.executeQuery();
        return DbUtils.resultSetToTableModel(ver);
    }
}
