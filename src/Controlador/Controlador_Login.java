package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;

public class Controlador_Login {

    Modelo.Usuario Usuario;

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

    public int Login(String Usuario, String Password) {
        /**
         * *
         *
         * Esta función se utiliza para verificar si un usuario y contraseña
         * existen
         *
         */
        this.Usuario = new Modelo.Usuario(Usuario, Password);
        String sql = "SELECT * FROM login WHERE usuario='" + this.Usuario.getUsuario() + "' AND contraseña='" + this.Usuario.getContra() + "'";
        try {
            Statement st = Coneccion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            int cont=0;
            while(rs.next())
                cont++;
            if(cont!=0)
                return 1;
            else
                return -1;
        } catch (SQLException ex) {
            return 0;
        }
    }
    public String RegistrarUsuario(String usuario, String contra, String nombreUsuario, boolean venta, boolean productos, boolean compras, boolean corte, boolean configuracion) throws SQLException {
        /**
         * *
         *
         * Esta función sirve para registrar un producto dentro la base de datos
         *
         */
        PreparedStatement pst;
        try {
            String sql = "INSERT INTO `login`(`usuario`, `contraseña`, `nombreUsuario`, `permisoVenta`, `permisoProductos`, `permisoCompras`, `permisoCorte`, `permisoConfiguracion`) VALUES (?,?,?,?,?,?,?,?)";
            pst = Coneccion().prepareStatement(sql);
            Usuario=new Modelo.Usuario(usuario,contra,nombreUsuario,venta,productos,compras,corte,configuracion);
            System.out.println(usuario+contra+nombreUsuario+venta+productos+compras+corte+configuracion);
            pst.setString(1, Usuario.getUsuario());
            pst.setString(2, Usuario.getContra());
            pst.setString(3, Usuario.getNombreUsuario());
            pst.setBoolean(4,Usuario.isVenta() );
            pst.setBoolean(5, Usuario.isProductos());
            pst.setBoolean(6, Usuario.isCompras());
            pst.setBoolean(7, Usuario.isCorte());
            pst.setBoolean(8, Usuario.isConfiguracion());
            
            pst.executeUpdate();
            return "Registro exitoso";
        } catch (SQLException ex) {
            return "Error de registro";
        }
    }
    public String EliminarUsuario(int fila, JTable tablaUsuario) {
        /**
         * *
         *
         * Esta función se utiliza para eliminar un departamento en la base de
         * datos
         *
         */
        try {
            PreparedStatement pst;
            String sql = "DELETE FROM login WHERE nombreUsuario=?";
            pst = Coneccion().prepareStatement(sql);
            pst.setString(1, tablaUsuario.getValueAt(fila, 0).toString());
            pst.executeUpdate();
            return "Se borro el Usuario exitosamente";
        } catch (SQLException ex) {
            return "Error al eliminar";
        }
    }
    public String ModificarUsuario(String usuario, String contra, String nombreUsuario, boolean venta, boolean productos, boolean compras, boolean corte, boolean configuracion) throws SQLException {
        /**
         * *
         *
         * Esta función sirve para registrar un producto dentro la base de datos
         *
         */
        PreparedStatement pst;
        try {
            String sql = "INSERT INTO `login`(`usuario`, `contraseña`, `nombreUsuario`, `permisoVenta`, `permisoProductos`, `permisoCompras`, `permisoCorte`, `permisoConfiguracion`) VALUES (?,?,?,?,?,?,?,?)";
            pst = Coneccion().prepareStatement(sql);
            Usuario=new Modelo.Usuario(usuario,contra,nombreUsuario,venta,productos,compras,corte,configuracion);
            System.out.println(usuario+contra+nombreUsuario+venta+productos+compras+corte+configuracion);
            pst.setString(1, Usuario.getUsuario());
            pst.setString(2, Usuario.getContra());
            pst.setString(3, Usuario.getNombreUsuario());
            pst.setBoolean(4,Usuario.isVenta() );
            pst.setBoolean(5, Usuario.isProductos());
            pst.setBoolean(6, Usuario.isCompras());
            pst.setBoolean(7, Usuario.isCorte());
            pst.setBoolean(8, Usuario.isConfiguracion());
            
            pst.executeUpdate();
            return "Modificacion exitosa";
        } catch (SQLException ex) {
            return "Error de modi";
        }
    }
    
}
