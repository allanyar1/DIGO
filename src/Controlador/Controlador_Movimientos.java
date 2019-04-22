/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Controlador_Movimientos {

    Modelo.Movimientos movimientos;

    public Connection Coneccion() throws SQLException {
        /**
         * *
         *
         * Esta función se utiliza para establecer la conexion con la base de
         * datos
         *
         */
        return DriverManager.getConnection("jdbc:mysql://localhost/DigoV?useSSL=false", "root", "password");
    }

    public String registrarMovimientos(String fecha, String hora, String descripcion, int habia, String tipo, int cantidad, int existencia) {
        /**
         * *
         *
         * Esta función se utiliza para registrar el movimiento y tener control
         * de entradas y salidas
         *
         */
        PreparedStatement pst;

        try {
            String sql = "INSERT INTO `movimientos`(`fecha`, `hora`, `descripcion`, `habia`, `tipo`, `cantidad`, `existencia`) VALUES (?,?,?,?,?,?,?)";
            pst = Coneccion().prepareStatement(sql);
            movimientos = new Modelo.Movimientos(fecha, hora, descripcion.toUpperCase(), habia, tipo, cantidad, existencia);
            pst.setString(1, movimientos.getFecha());
            pst.setString(2, movimientos.getHora());
            pst.setString(3, movimientos.getDescripcion());
            pst.setInt(4, movimientos.getHabia());
            pst.setString(5, movimientos.getTipo());
            pst.setInt(6, movimientos.getCantidad());
            pst.setInt(7, movimientos.getExistencia());
            pst.executeUpdate();
            return "Movimiento registrado";
        } catch (SQLException ex) {
            return "Error al registrar el movimiento";
        }
    }
}
