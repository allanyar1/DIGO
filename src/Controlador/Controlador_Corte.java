package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Controlador_Corte {
    Modelo.Corte corte;
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
    public void regCorte(String usuario, String fecha, String hora, String tipo, float total){
        /**
         * *
         *
         * Esta función se utiliza para registrar el movimiento y tener control
         * de entradas y salidas
         *
         */
        PreparedStatement pst;

        try {
            String sql = "INSERT INTO `corte_turno`( `Usuario`, `Fecha`, `Hora`, `Tipo`, `Total`) VALUES (?,?,?,?,?)";
            pst = Coneccion().prepareStatement(sql);
            corte = new Modelo.Corte(usuario, fecha, hora, tipo, total);
           
            pst.setString(1, corte.getUsuario());
            pst.setString(2, corte.getFecha());
            pst.setString(3, corte.getHora() );
            pst.setString(4, corte.getTipo());
            pst.setFloat(5, corte.getTotal()); 
            pst.executeUpdate();
            System.out.println("si se registro ");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
        
    }

