package Controlador;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

public class Controlador_Principal {

    Modelo.Articulos Articulos;
    Modelo.Usuario Usuario;
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
    public String Nombre(String Usuario) throws SQLException
    {
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT nombreUsuario FROM `login` WHERE `usuario`=?";
        pst = Coneccion().prepareStatement(sql);
        pst.setString(1, Usuario);
        rs = pst.executeQuery();
        String nom=rs.getString(1);
        System.out.println(nom);
        return nom;
    }
    public TableModel MostrarTablaProductos() throws SQLException {
        /**
         * *
         *
         * Esta función se utiliza para establecer una conexion entre la base de
         * datos y la tabla de productos y traer los datos para hacerlos
         * visibles en el sistema
         *
         */
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT id_productos AS 'Clave de producto', descripcion AS Descripcion, tipoVenta AS 'Tipo de Venta', preCosto AS 'Precio de costo', preVenta AS 'Precio de venta', departamento AS Departamento FROM articulos INNER JOIN departamentos ON articulos.id_departamentos=departamentos.id_departamentos ORDER BY id_productos";
        pst = Coneccion().prepareStatement(sql);
        rs = pst.executeQuery();
        return DbUtils.resultSetToTableModel(rs);
    }

    public TableModel MostrarTabla2(JTextField txtBuscarArt) throws SQLException {
        /**
         * *
         *
         * Esta función se utiliza para establecer una conexión entre la base de
         * datos y la tabla de consulta de productos por descripción
         *
         */
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        if (txtBuscarArt.getText() == "") {
            sql = "SELECT *FROM articulos ";
        } else {
            sql = "SELECT id_productos AS 'Clave de producto', descripcion AS Descripcion, tipoVenta AS 'Tipo de Venta', preCosto AS 'Precio de costo', preVenta AS 'Precio de venta', id_departamentos AS Departamento FROM articulos WHERE descripcion='" + txtBuscarArt.getText().toUpperCase() + "'";
        }
        pst = Coneccion().prepareStatement(sql);
        rs = pst.executeQuery();
        return DbUtils.resultSetToTableModel(rs);
    }

    public TableModel MostrarTablaReporteInv() throws SQLException {
        /**
         * *
         *
         * Esta función se utiliza para establecer una conexión entre la base de
         * datos y la tabla de Inventarios
         *
         */
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT articulos.id_productos AS 'Codigo de producto', descripcion AS 'Descripcion', preCosto AS 'Precio de costo', preVenta AS 'Precio de venta', existencia AS 'Existencia', minimoInv AS 'Minimo de Inventario' FROM inventario_productos INNER JOIN articulos ON inventario_productos.id_productos=articulos.id_productos";
        pst = Coneccion().prepareStatement(sql);
        rs = pst.executeQuery();
        return DbUtils.resultSetToTableModel(rs);
    }

    public TableModel MostrarTablaInvBajo() throws SQLException {
        /**
         *
         *
         * Esta función se utiliza para establecer una conexion entre la base de
         * datos y la tabla de Productos bajos de inventario
         *
         */
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT articulos.descripcion AS 'Descripcion', inventario_productos.id_productos AS 'Codigo de Producto', inventario_productos.existencia AS 'Existencia',inventario_productos.minimoInv AS 'Minimo' FROM inventario_productos INNER JOIN articulos ON inventario_productos.id_productos= articulos.id_productos WHERE minimoInv>=existencia";
        pst = Coneccion().prepareStatement(sql);
        rs = pst.executeQuery();
        return DbUtils.resultSetToTableModel(rs);
    }

    public TableModel MostrarTablaDepartamentos() throws SQLException {
        /**
         * *
         *
         * Esta función se utiliza para establecer una conexion entre la base de
         * datos y la tabla de departamentos
         *
         */
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT departamentos.id_departamentos AS 'Codigo de departamento', departamentos.departamento AS 'Departamento' FROM departamentos ";
        pst = Coneccion().prepareStatement(sql);
        rs = pst.executeQuery();
        return DbUtils.resultSetToTableModel(rs);
    }

    public TableModel MostrarTablaMov() throws SQLException {
        /**
         * *
         *
         * Esta función se utiliza para establecer una conexión entre la base de
         * datos y la tabla de movimientos de entrada y salida
         *
         */
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT movimientos.id_movimiento AS 'Numero de Movimiento',movimientos.fecha AS 'Fecha',movimientos.hora AS 'Hora',movimientos.descripcion AS 'Descripcion',movimientos.habia AS 'Habia',movimientos.tipo AS 'Tipo de moviento',movimientos.cantidad AS 'Cantidad',movimientos.existencia AS 'Existencia actual' FROM movimientos ";
        pst = Coneccion().prepareStatement(sql);
        rs = pst.executeQuery();
        return DbUtils.resultSetToTableModel(rs);
    }
    public TableModel MostrarSalida() throws SQLException {
        /**
         * *
         *
         * Esta función se utiliza para establecer una conexión entre la base de
         * datos y la tabla de movimientos de entrada y salida
         *
         */
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT Usuario,Fecha,Hora,Total FROM `corte_turno` WHERE Tipo='Salida'";
        pst = Coneccion().prepareStatement(sql);
        rs = pst.executeQuery();
        return DbUtils.resultSetToTableModel(rs);
    }
    public TableModel MostrarEntrada() throws SQLException {
        /**
         * *
         *
         * Esta función se utiliza para establecer una conexión entre la base de
         * datos y la tabla de movimientos de entrada y salida
         *
         */
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT Usuario,Fecha,Hora,Total FROM `corte_turno` WHERE Tipo='Entrada'";
        pst = Coneccion().prepareStatement(sql);
        rs = pst.executeQuery();
        return DbUtils.resultSetToTableModel(rs);
    }
    public TableModel MostrarTemporal() throws SQLException {
        /**
         * *
         *
         * Esta función se utiliza para establecer una conexión entre la base de
         * datos y la tabla de movimientos de entrada y salida
         *
         */
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT Monto_Caja,Entradas,Salidas,Total from corte_temporal";
        pst = Coneccion().prepareStatement(sql);
        rs = pst.executeQuery();
        return DbUtils.resultSetToTableModel(rs);
    }

    public Vector<String> MostrarTablaVentas(int Id) throws SQLException {
        /**
         * *
         *
         * Esta función se utiliza para establecer una conexión entre la base de
         * datos y la tabla de Ventas
         *
         */
        PreparedStatement pst;
        ResultSet rs;
        Vector<String> newRow = new Vector<>();
        int numero = 1;
        String sql = "SELECT articulos.id_productos AS 'Codigo de producto', articulos.descripcion AS 'Descripcion', articulos.preVenta AS 'Precio de venta',inventario_productos.existencia AS 'Existenccia'FROM articulos INNER JOIN inventario_productos ON articulos.id_productos = inventario_productos.id_productos WHERE articulos.id_productos=?";
        pst = Coneccion().prepareStatement(sql);
        pst.setInt(1, Id);
        rs = pst.executeQuery();
        while (rs.next()) {
            newRow.add(rs.getString(1));
            newRow.add(rs.getString(2));
            newRow.add(numero + "");
            newRow.add(rs.getString(3)+"0");
            newRow.add(rs.getString(4));
            newRow.add(rs.getString(3)+"0");
        }
        return newRow;
        //return DbUtils.resultSetToTableModel(rs);
    }

    public float Total(float Total, float Precio) {

        return Total + Precio;
    }

    public float Multiplicar(float Total, float Precio) {
        return Total * Precio;
    }

    public float Restar(float Total, float Menos) {
        return Total - Menos;
    }
    public float SumarProducto(float Total, float Precio)
    {
        return Total + Precio;
    }
    public float RestarProducto(float Total, float Precio)
    {
        return Total - Precio;
    }
    
    public TableModel MostrarTablaUsuarios() throws SQLException {
        /**
         * *
         *
         * Esta función se utiliza para establecer una conexion entre la base de
         * datos y la tabla de productos y traer los datos para hacerlos
         * visibles en el sistema
         *
         */
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT nombreUsuario AS 'Usuarios' FROM login";
        pst = Coneccion().prepareStatement(sql);
        rs = pst.executeQuery();
        return DbUtils.resultSetToTableModel(rs);
    }
    public ResultSet Permisos(String Usuario) throws SQLException{
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT `permisoVenta`, `permisoProductos`, `permisoCompras`, `permisoCorte`, `permisoConfiguracion` FROM `login` WHERE `usuario`=?";
        pst = Coneccion().prepareStatement(sql);
        pst.setString(1, Usuario);
        rs = pst.executeQuery();
        return rs;
    }
    public BufferedImage cargarLogotipo() throws IOException {
        PreparedStatement pst;
        ResultSet rs;
        String sql="SELECT  *FROM `logotipo` ";
        ImageIcon ii=null;
        InputStream is=null;
        Blob blob;
        byte[] data;
        BufferedImage img=null;
       
        try {
            pst=(PreparedStatement) Coneccion().createStatement();
             rs=pst.executeQuery(sql);
             if(rs.next()){
                 blob= rs.getBlob(2);
                 data=blob.getBytes(1,(int)blob.length());
                 try{
                     img=ImageIO.read(new ByteArrayInputStream(data));
                 }catch(Exception ex){
                     System.out.println(ex);  
                 }
                 ImageIcon icono=new ImageIcon(img);
                 
//                 is=rs.getBinaryStream(2);
//                 BufferedImage bi= ImageIO.read(is);
//                 ii=new ImageIcon(bi);
             }
        } catch (SQLException ex) {
            Logger.getLogger(Controlador_Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
           
     
        return  img;
        
    }
    public ResultSet visualizarLogo(){
        ResultSet rs=null;
        try {
            PreparedStatement ps=Coneccion().prepareStatement("SELECT  *FROM `logotipo` WHERE id_logotipo=1");
            rs=ps.executeQuery();
            System.out.println(rs);
        } catch (SQLException ex) {
            Logger.getLogger(Controlador_Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    public ResultSet CorteDia() throws SQLException
    {
        PreparedStatement pst=null;
        ResultSet rs;
        String sql = "SELECT contraseña FROM login WHERE usuario='Admin'";
        pst = Coneccion().prepareStatement(sql);
        rs = pst.executeQuery();
        return rs;
    }
   
}

