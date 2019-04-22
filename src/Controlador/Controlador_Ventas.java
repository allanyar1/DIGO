package Controlador;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controlador_Ventas {

    Modelo.Venta Ventas;

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

    public String Ventas(int Id, Float Total, java.util.Calendar FechaTemp, String Usuario) {
        /**
         * *
         *
         * Esta función se utiliza para registrar las ventas de un producto en
         * la base de datos
         *
         */

        try {
            PreparedStatement pst;
            Timestamp Fecha = new Timestamp(FechaTemp.getTimeInMillis());
            String sql = "INSERT INTO `ventas`(`id_venta`, `total`, `fecha`, `usuario`) VALUES (?, ?, ?, ?)";
            pst = Coneccion().prepareStatement(sql);
            Ventas = new Modelo.Venta(Id, Total, Fecha, Usuario);
            pst.setInt(1, Ventas.getId());
            pst.setFloat(2, Ventas.getTotal());
            pst.setTimestamp(3, Ventas.getFecha());
            pst.setString(4, Ventas.getUsuario());
            pst.executeUpdate();
            return "Venta realizada exitosamente";
        } catch (SQLException ex) {

            return "Error al realizar la venta";
        }

    }

    public int CalcularId() {
        /**
         * *
         *
         * Esta función se utiliza para verificar el id existente en ventas
         *
         */
        PreparedStatement pst;
        ResultSet rs;
        int Id = 0;
        try {
            String sql = "SELECT MAX(id_venta) FROM ventas";
            pst = Coneccion().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                Id = Integer.parseInt(rs.getString(1));
            }
            return Id;
        } catch (SQLException | NumberFormatException ex) {
            return Id;
        }
    }
}
