package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

public class Controlador_Articulos {

    Controlador_Principal ConPri;
    Modelo.Articulos Articulos;

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

    public String RegistrarProducto(int Id, String Descripcion, String select, float Costo, float Venta, int Departamento) throws SQLException {
        /**
         * *
         *
         * Esta función sirve para registrar un producto dentro la base de datos
         *
         */
        PreparedStatement pst;
        try {
            String sql = "INSERT INTO `articulos`(`id_productos`, `descripcion`, `tipoVenta`, `preCosto`, `preVenta`, `id_departamentos`) VALUES (?,?,?,?,?,?)";
            pst = Coneccion().prepareStatement(sql);
            Articulos = new Modelo.Articulos(Id, Descripcion.toUpperCase(), select, Costo, Venta, Departamento);
            pst.setInt(1, Articulos.getId());
            pst.setString(2, Articulos.getDescripcion());
            pst.setString(3, Articulos.getTipoVenta());
            pst.setFloat(4, Articulos.getPrecioCosto());
            pst.setFloat(5, Articulos.getPrecioVenta());
            pst.setInt(6, Articulos.getDepartamento());
            pst.executeUpdate();
            return "Registro exitoso";
        } catch (SQLException ex) {
            return "Error de registro";
        }
    }

    public float CalcularPrecio(float preCosto, int Ganancia) {
        /**
         * *
         *
         * Esta función sirve calcula el precio de venta de un producto y
         * regresa el valor total
         *
         */
        return (((preCosto * Ganancia) / 100) + preCosto);
    }

    public String EliminarProducto(String tabla) {
        /**
         * *
         *
         * Esta función sirve para eliminar un producto dentro de la del sistema
         *
         */
        try {
            PreparedStatement pst;
            String sql = "DELETE FROM articulos WHERE id_productos=?";
            pst = Coneccion().prepareStatement(sql);
            pst.setString(1, tabla);
            pst.executeUpdate();
            return "Producto eliminado";
        } catch (SQLException ex) {
            return "Error al eliminar producto";
        }
    }

    public String ActualizarProducto(int Id, String Descripcion, String select, float PreCosto, float PreVenta, int Departamento) throws SQLException {
        /**
         * *
         *
         * Esta función sirve para actualizar los cambios hechos en la
         * modificación de productos
         *
         */
        PreparedStatement pst;
        if ((Id + "").equalsIgnoreCase("") || Descripcion.equalsIgnoreCase("") || (PreCosto + "").equalsIgnoreCase("") || (PreVenta + "").equalsIgnoreCase("")) {
            return "Debe de capturar los datos";
        }
        String sql = "UPDATE articulos SET descripcion=?,tipoVenta=?,preCosto=?,preVenta=?,id_departamentos=? WHERE id_productos=?";
        pst = Coneccion().prepareStatement(sql);
        Articulos = new Modelo.Articulos(Id, Descripcion, select, PreCosto, PreVenta, Departamento);
        pst.setInt(6, Articulos.getId());
        pst.setString(1, Articulos.getDescripcion());
        pst.setString(2, Articulos.getTipoVenta());
        pst.setFloat(3, Articulos.getPrecioCosto());
        pst.setFloat(4, Articulos.getPrecioVenta());
        pst.setInt(5, Articulos.getDepartamento());
        pst.executeUpdate();
        return "Modificación exitosa";
    }

    public TableModel RevisarId(int Id) throws SQLException {
        /**
         * *
         *
         * Esta función sirve para buscar el Id de un articulo en la base de
         * datos
         *
         *
         */
        PreparedStatement pst;
        ResultSet ver;
        String sql = "SELECT * FROM articulos WHERE id_productos=?";
        pst = Coneccion().prepareStatement(sql);
        pst.setInt(1, Id);
        ver = pst.executeQuery();
        return DbUtils.resultSetToTableModel(ver);
    }

    public TableModel RevisarDes(String des) throws SQLException {
        /**
         * *
         *
         * Esta función sirve para buscar la descripción de un articulo en la
         * base de datos
         *
         *
         */
        PreparedStatement pst;
        ResultSet ver;
        String sql = "SELECT * FROM articulos WHERE descripcion=?";
        pst = Coneccion().prepareStatement(sql);
        pst.setString(1, des);
        ver = pst.executeQuery();
        return DbUtils.resultSetToTableModel(ver);
    }

    public ResultSet PreVenta(int Id) {
        try {
            PreparedStatement pst;
            ResultSet ver;
            String sql = "SELECT `id_productos`, `descripcion`, `preVenta` FROM `articulos` WHERE id_productos=?";
            pst = Coneccion().prepareStatement(sql);
            pst.setInt(1, Id);
            return pst.executeQuery();
        } catch (SQLException ex) {
            return null;
        }
    }
}
