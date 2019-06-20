package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

public class Controlador_Departamento {

    Modelo.Departamento Departamento = new Modelo.Departamento();

    public Connection Coneccion() throws SQLException {
        /**
         * *
         *
         * Esta funcion se utiliza para establecer la conexión con la base de
         * datos
         *
         */
        return DriverManager.getConnection("jdbc:mysql://localhost/DigoV?useSSL=false", "root", "password");
    }

    public ResultSet Departamento(String Depa) throws SQLException {
        //return Departamento.getDepartamento(Depa);
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT `id_departamentos` FROM `departamentos` WHERE `departamento`=?";
        pst = Coneccion().prepareStatement(sql);
        pst.setString(1, Depa);
        rs = pst.executeQuery();
        return rs;
    }

    public String EliminarDepartamento(int fila, JTable tablaDepartamentos) {
        /**
         * *
         *
         * Esta función se utiliza para eliminar un departamento en la base de
         * datos
         *
         */
        try {
            PreparedStatement pst;
            String sql = "DELETE FROM departamentos WHERE id_departamentos=?";
            pst = Coneccion().prepareStatement(sql);
            pst.setString(1, tablaDepartamentos.getValueAt(fila, 0).toString());
            pst.executeUpdate();
            return "Se borro el departamento exitosamente";
        } catch (SQLException ex) {
            return "Error al eliminar";
        }
    }

    public ResultSet Llenar() throws ClassNotFoundException, SQLException {
        /**
         * *
         *
         * Esta función se utiliza para llenar los combo box de el modulo de
         * departamentos
         *
         */
        Class.forName("com.mysql.jdbc.Driver");
        Statement st = Coneccion().createStatement();
        return st.executeQuery("SELECT * FROM departamentos");
    }

    public String RegistrarDepartamento(int IdDepartamento, String NuevoDepartamento) {
        /**
         * *
         *
         * Esta función se utiliza para registrar un nuevo departamento en la
         * base de datos
         *
         */
        try {
            PreparedStatement pst;
            String sql = "INSERT INTO `departamentos`(`id_departamentos`,`departamento`) VALUES (?,?)";
            pst = Coneccion().prepareStatement(sql);
            Departamento = new Modelo.Departamento((IdDepartamento) + 1, NuevoDepartamento);
            pst.setInt(1, Departamento.getIdDepartamento());
            pst.setString(2, Departamento.getDepartamento());
            pst.executeUpdate();
            return "Departamento creado exitosamente";
        } catch (SQLException ex) {
            return "Error al crear departamento";
        }
    }
}
