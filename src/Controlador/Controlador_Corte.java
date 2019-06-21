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
    public void corte_borrar(){
        PreparedStatement pst;
        try {
            String sql = "TRUNCATE TABLE corte_turno";
            pst = Coneccion().prepareStatement(sql);
            pst.executeUpdate();
           
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    public void corte_temporal(float monto,float entrada,float salida, float total){
        /**
         * *
         *
         * Esta función se utiliza para registrar el movimiento y tener control
         * de entradas y salidas
         *
         */
        PreparedStatement pst;
        try {
            String sql = "INSERT INTO `corte_temporal`(`Monto_Caja`, `Entradas`, `Salidas`, `Total`) VALUES (?,?,?,?)";
            pst = Coneccion().prepareStatement(sql);
            pst.setFloat(1, monto);
            pst.setFloat(2,entrada );
            pst.setFloat(3,salida );
            pst.setFloat(4,total );
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    public String corte_final(String Usuario, String fecha, String hora,String tipo ,float total){
        /**
         * *
         *
         * Esta función se utiliza para registrar el movimiento y tener control
         * de entradas y salidas
         *
         */
        PreparedStatement pst;
        try {
            String sql = "INSERT INTO `corte_totales`(`Usuario`, `Fecha`, `Hora`, `Tipo`, `Total`) VALUES (?,?,?,?,?)";
            pst = Coneccion().prepareStatement(sql);
            pst.setString(1, Usuario);
            pst.setString(2,fecha);
            pst.setString(3, hora );
            pst.setString(4, tipo );
            pst.setFloat(5, total );
            pst.executeUpdate();
            return "Se registro corte de dia";
        } catch (SQLException ex) {
            return "Error de registro";
        }
    }
     public void CorteTemporalBorrar(){
        PreparedStatement pst;
        try {
            String sql = "TRUNCATE TABLE corte_temporal";
            pst = Coneccion().prepareStatement(sql);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }        
    }   
}