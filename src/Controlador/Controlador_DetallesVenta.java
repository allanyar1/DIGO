package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Controlador_DetallesVenta 
{
    Modelo.DetallesVenta DetallesVenta;
    public Connection Coneccion() throws SQLException
    {
         /***
         * 
         * Esta función se utiliza para establecer la conexión con la base de datos
         * 
         */
        return DriverManager.getConnection("jdbc:mysql://localhost/DigoV?useSSL=false","root","password");
    }
    public String RegistrarDetalles(int IdVenta, int IdProducto, String Descripcion, int Cantidad, float PreVenta)
    {
         /***
         * 
         * Esta función se utiliza para registrar los detalles de una venta dentro de la base de datos
         * 
         */
        try {
            PreparedStatement pst;
            String sql="INSERT INTO `detalles_venta`(`id_venta`, `id_productos`, `descripcion`, `cantidad`, `preventa`) VALUES (?, ?, ?, ?, ?)";
            pst=Coneccion().prepareStatement(sql);
            DetallesVenta = new Modelo.DetallesVenta(IdVenta, IdProducto, Descripcion, Cantidad, PreVenta);
            pst.setInt(1, DetallesVenta.getIdVenta());
            pst.setInt(2, DetallesVenta.getIdProducto());
            pst.setString(3, DetallesVenta.getDescripcion());
            pst.setInt(4, DetallesVenta.getCantidad());
            pst.setFloat(5, DetallesVenta.getPrecioVenta());
            pst.executeUpdate();
            return "Articulos registrados en venta exitosamente";
        } catch (SQLException ex) {
            return "Error al registrar articulos en venta";
        }
    }
}
