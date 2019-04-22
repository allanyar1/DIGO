
package Controlador;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Controlador_Logotipo {
    Modelo.Logotipo Logo;
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
    public String SubirLogotipo(FileInputStream foto, int longuitud) throws SQLException {
        /**
         * *
         *
         * Esta función sirve para guargar el logotipo de la empresa en la base de datos
         *
         */
        PreparedStatement pst;
        try {
             String sql="UPDATE `logotipo` SET `id_logotipo`=?,`imagenLogo`=?,`longuitud`=? WHERE `id_logotipo`=1 ";
            pst = Coneccion().prepareStatement(sql);
            Logo= new Modelo.Logotipo(foto, longuitud);
            
             pst.setInt(1,1);
             pst.setBinaryStream(2, Logo.getFoto(),Logo.getLonguitud());
             pst.setInt(3,Logo.getLonguitud());
            
            pst.executeUpdate();
            return "Se guardo el logo exitosamente";
        } catch (SQLException ex) {
            return "Error al subir el logo " +ex;
        }
    }
    
}
