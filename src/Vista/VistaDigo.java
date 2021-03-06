package Vista;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JPasswordField;
import javax.swing.table.DefaultTableModel;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class VistaDigo extends javax.swing.JFrame implements Runnable{
    
    Controlador.Controlador_Principal ConPri = new Controlador.Controlador_Principal();
    Controlador.Controlador_Articulos ConArt = new Controlador.Controlador_Articulos();
    Controlador.Controlador_Inventario ConInv = new Controlador.Controlador_Inventario();
    Controlador.Controlador_Departamento ConDep = new Controlador.Controlador_Departamento();
    Controlador.Controlador_Ventas ConVen = new Controlador.Controlador_Ventas();
    Controlador.Controlador_DetallesVenta ConDet = new Controlador.Controlador_DetallesVenta();
    Controlador.Controlador_Movimientos ConMov= new Controlador.Controlador_Movimientos();
    Controlador.Controlador_Login ConLog= new Controlador.Controlador_Login();
    Controlador.Controlador_Logotipo ConLogo= new Controlador.Controlador_Logotipo();
    Controlador.Controlador_Corte ConCor= new Controlador.Controlador_Corte();
    FileInputStream fis;
    int longitudBytes;
    String hora,minutos,segundos;
    Thread hilo;
    DefaultTableModel modelo;
    int filas=0;
    Connection con= null;
    PreparedStatement pst=null;
    ResultSet rs=null;
    Connection con2= null;
    PreparedStatement pst2=null;
    ResultSet rs2=null;
    int ii=0;
    Boolean ban=false;
    Double Total;
    Double TotalCobro;
    String dolarString;
    double dolar;
    String cajaString;
    double caja;
    float corte=0;
    boolean BanderaUsuario = false;
    boolean BanderaModificarUsuario = false;
    public VistaDigo() throws SQLException{
        initComponents();
        DineroDolar();
        DineroCaja();
       try {
            cargar();
        } catch (IOException ex) {
            Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
        }
        hilo=new Thread(this);
        hilo.start();
        lbFecha.setText(fecha());
        ImageIcon img =new ImageIcon("src\\Imagenes\\mypos.png");
        this.setIconImage(img.getImage());
        grupo.add(radioUnidad);
        grupo.add(radioGranel);
        grupo.add(radioPaquete);
        txtPrecioVenta.setEditable(false);
        panePrincipal.add("Ventas",panelVentas);
        panePrincipal.remove(panelInvBajo);
        panePrincipal.remove(panelProductos);
        panePrincipal.remove(panelDepartamentos);
        panePrincipal.remove(panelCatalogoPro);
        panePrincipal.remove(panelInventario);
        panePrincipal.remove(panelMov);
        panePrincipal.remove(panelCorte);
        panePrincipal.remove(panelConfiguracion);
        paneConfiguracion.remove(panelAdmiUsuarios);
        paneConfiguracion.remove(panelLogotipo);
        paneCorte.remove(panelDia);
        paneCorte.remove(panelCajero);
        btnActualizar.setEnabled(false);
        MostrarTablaProductos();
        MostrarTablaProductos2();
        MostrarTablaReporteInv();
        MostrarTablaInvBajo();
        MostrarTablaMovimientos();
        MostrarTablaDepartamentos();
        MostrarTablaUsuarios();
        MostrarSalida();
        MostrarEntrada();
        MostrarCorteTemporal();
        llenarCombo();
        calcular_corte();
        CarcularCorteFinal();
        //Permisos();
        lbVerificacion.setVisible(false);
        btnSalir.setMnemonic(KeyEvent.VK_END);
        btnVenta.setMnemonic(KeyEvent.VK_F9);
        btnProductos.setMnemonic(KeyEvent.VK_F10);
        btnInventario.setMnemonic(KeyEvent.VK_F11);
        btnMas.setMnemonic(KeyEvent.VK_ADD);
        btnMenos.setMnemonic(KeyEvent.VK_SUBTRACT);
        btnCobrar.setMnemonic(KeyEvent.VK_ENTER);
        btnIdAgregar.setMnemonic(KeyEvent.VK_A);        
//        Vector<Vector<String>> data=new Vector<>();
//        Vector<String> columns=new Vector<String>();
//        DefaultTableModel tableModel=new DefaultTableModel(data, columns);
//        tablaVentas.setModel(tableModel);
        
    }
    public static String fecha(){
        Date fecha =new Date();
        SimpleDateFormat formatofecha=new SimpleDateFormat("dd/MM/YYYY");
        
        return formatofecha.format(fecha);
        
    }
    public Date hora(){
        Calendar calendario= new GregorianCalendar();
        Date horaactual=new Date();
        calendario.setTime(horaactual);
        hora=calendario.get(Calendar.HOUR)>9?""+calendario.get(Calendar.HOUR):"0"+calendario.get(Calendar.HOUR);
        minutos=calendario.get(Calendar.MINUTE)>9?""+calendario.get(Calendar.MINUTE):"0"+calendario.get(Calendar.MINUTE);
        segundos=calendario.get(Calendar.SECOND)>9?""+calendario.get(Calendar.SECOND):"0"+calendario.get(Calendar.SECOND);
        return horaactual;
    }
    /*public void admin(){
       lbUsuario.setText("Administrador");
     //  btnVenta.setEnabled(false);
    }
    public void cajeroV(){
         lbUsuario.setText("Cajero");
        // btnV.setEnabled(false);
    }*/
    
    @Override
    public void run(){
        
        Thread current=Thread.currentThread();
        while(current==hilo){
            hora();
            lbHora.setText(hora+":"+minutos+":"+segundos);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupo = new javax.swing.ButtonGroup();
        btnVenta = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnInventario = new javax.swing.JButton();
        btnCorte = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        lbFoto = new javax.swing.JLabel();
        panePrincipal = new javax.swing.JTabbedPane();
        panelDepartamentos = new javax.swing.JPanel();
        btnNuevoDepartamento = new javax.swing.JButton();
        btnEliminarDepartamento = new javax.swing.JButton();
        txtNuevoDepartamento = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDepartamentos = new javax.swing.JTable();
        lbDepartamentoNuevo = new javax.swing.JLabel();
        panelCatalogoPro = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaReporteProductos = new javax.swing.JTable();
        lbBusquedaFiltrada = new javax.swing.JLabel();
        btnBuscarProducto = new javax.swing.JButton();
        txtBuscarArt = new javax.swing.JTextField();
        panelInvBajo = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaInvBajo = new javax.swing.JTable();
        panelProductos = new javax.swing.JPanel();
        lbIdProducto = new javax.swing.JLabel();
        lbDescripcion = new javax.swing.JLabel();
        lbVentaPor = new javax.swing.JLabel();
        lbPrecioCostoA = new javax.swing.JLabel();
        lbGanancia = new javax.swing.JLabel();
        lbPreVenta = new javax.swing.JLabel();
        lbDepartamento = new javax.swing.JLabel();
        boxInventario = new javax.swing.JCheckBox();
        panelInventarioReg = new javax.swing.JPanel();
        lbExistencia = new javax.swing.JLabel();
        lbMinimo = new javax.swing.JLabel();
        txtHay = new javax.swing.JTextField();
        txtMinimo = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        radioUnidad = new javax.swing.JRadioButton();
        radioGranel = new javax.swing.JRadioButton();
        radioPaquete = new javax.swing.JRadioButton();
        txtPrecioCosto = new javax.swing.JTextField();
        txtPrecioVenta = new javax.swing.JTextField();
        boxDepartamento = new javax.swing.JComboBox<>();
        spinnerGanancia = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        btnGuardarProducto = new javax.swing.JButton();
        btnModProducto = new javax.swing.JButton();
        btnElimProducto = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        lbPorcentaje = new javax.swing.JLabel();
        lbVerificacion = new javax.swing.JLabel();
        panelMov = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaMovientos = new javax.swing.JTable();
        panelVentas = new javax.swing.JPanel();
        lbCodigoVenta = new javax.swing.JLabel();
        txtIdVenta = new javax.swing.JTextField();
        btnIdAgregar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaVentas = new javax.swing.JTable();
        btnCobrar = new javax.swing.JButton();
        lbSignoPesos = new javax.swing.JLabel();
        lbCobroFinal = new javax.swing.JLabel();
        btnEliminarVenta = new javax.swing.JButton();
        btnMas = new javax.swing.JButton();
        btnMenos = new javax.swing.JButton();
        Cb_dolar = new javax.swing.JCheckBox();
        panelConfiguracion = new javax.swing.JPanel();
        paneConfiguracion = new javax.swing.JTabbedPane();
        panelAdmiUsuarios = new javax.swing.JPanel();
        btnEliminarUsuario = new javax.swing.JButton();
        lbUsuarioNuevo = new javax.swing.JLabel();
        lbContraseña = new javax.swing.JLabel();
        btnGuardarUsuario = new javax.swing.JButton();
        lbNombreCompleto = new javax.swing.JLabel();
        btnCancelarUsuario = new javax.swing.JButton();
        txtContraseñaNueva = new javax.swing.JPasswordField();
        txtUsuarioNuevo = new javax.swing.JTextField();
        txtNombreCompleto = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        boxVentas = new javax.swing.JCheckBox();
        boxProductos = new javax.swing.JCheckBox();
        boxCompras = new javax.swing.JCheckBox();
        boxCorte = new javax.swing.JCheckBox();
        boxConfiguracion = new javax.swing.JCheckBox();
        btnModificarUsuario = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablaUsuarios = new javax.swing.JTable();
        panelLogotipo = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnLimpiarImagen = new javax.swing.JButton();
        btnGuardarImagen = new javax.swing.JButton();
        btnCargarImagen2 = new javax.swing.JButton();
        fotoT = new javax.swing.JLabel();
        btnConfiUsuarios = new javax.swing.JButton();
        btnPrecioDolar = new javax.swing.JButton();
        btnLogotipo = new javax.swing.JButton();
        btnImpuestos = new javax.swing.JButton();
        btnDineroCaja = new javax.swing.JButton();
        btnRespaldoBD = new javax.swing.JButton();
        panelCorte = new javax.swing.JPanel();
        btnCorteCajero = new javax.swing.JButton();
        btnCorteDia = new javax.swing.JButton();
        paneCorte = new javax.swing.JTabbedPane();
        panelDia = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tablaCorteTemporal = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lbE1 = new javax.swing.JLabel();
        lbS1 = new javax.swing.JLabel();
        lbTotalCorte1 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        btnDia = new javax.swing.JButton();
        btnReporte = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lbTotalVentas = new javax.swing.JLabel();
        panelCajero = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablaEntradas = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        tablaSalidas = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lbCaja = new javax.swing.JLabel();
        lbE = new javax.swing.JLabel();
        lbS = new javax.swing.JLabel();
        lbTotalCorte = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btnTurno = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbSalidas = new javax.swing.JLabel();
        lbEntradas = new javax.swing.JLabel();
        panelInventario = new javax.swing.JPanel();
        lbInvBusqueda = new javax.swing.JLabel();
        txtIdInventarioB = new javax.swing.JTextField();
        btnBuscaId = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lbDescripcionInv = new javax.swing.JLabel();
        lbPrecioCostoB = new javax.swing.JLabel();
        lbPrecioVentaB = new javax.swing.JLabel();
        lbExistenciaActualInv = new javax.swing.JLabel();
        lbMinimoB = new javax.swing.JLabel();
        txtExistenciaInv = new javax.swing.JTextField();
        txtPrecioVentaInv = new javax.swing.JTextField();
        txtMinimoInv = new javax.swing.JTextField();
        txtPrecioCostoInv = new javax.swing.JTextField();
        lblDes = new javax.swing.JLabel();
        lbAgregarCantidad = new javax.swing.JLabel();
        txtNuevaCantidad = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lblCodigoProducto = new javax.swing.JLabel();
        btnRegistrarInv = new javax.swing.JButton();
        btnEliminarInv = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaReporteInv = new javax.swing.JTable();
        btnRegInventario = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        lbFecha = new javax.swing.JLabel();
        lbHora = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbUsuario = new javax.swing.JLabel();
        btnConfiguracion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema DIGO");

        btnVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconoCarro.png"))); // NOI18N
        btnVenta.setText("[F9] Venta");
        btnVenta.setToolTipText("");
        btnVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentaActionPerformed(evt);
            }
        });
        btnVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnVentaKeyPressed(evt);
            }
        });

        btnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconoAlimentos.png"))); // NOI18N
        btnProductos.setText("[F10] Productos");
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });

        btnInventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconoCompras.png"))); // NOI18N
        btnInventario.setText("[F11] Compras");
        btnInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventarioActionPerformed(evt);
            }
        });

        btnCorte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconoCorte.png"))); // NOI18N
        btnCorte.setText("[F12] Corte");
        btnCorte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCorteActionPerformed(evt);
            }
        });

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconoSalir.png"))); // NOI18N
        btnSalir.setText("[END] Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        lbFoto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbFoto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbFoto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lbFotoKeyPressed(evt);
            }
        });

        btnNuevoDepartamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add.png"))); // NOI18N
        btnNuevoDepartamento.setText("Agregar Departamento");
        btnNuevoDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoDepartamentoActionPerformed(evt);
            }
        });

        btnEliminarDepartamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/elim.png"))); // NOI18N
        btnEliminarDepartamento.setText("Eliminar");
        btnEliminarDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarDepartamentoActionPerformed(evt);
            }
        });

        tablaDepartamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tablaDepartamentos);

        lbDepartamentoNuevo.setText("Ingresa un nuevo departamento");

        javax.swing.GroupLayout panelDepartamentosLayout = new javax.swing.GroupLayout(panelDepartamentos);
        panelDepartamentos.setLayout(panelDepartamentosLayout);
        panelDepartamentosLayout.setHorizontalGroup(
            panelDepartamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDepartamentosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelDepartamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNuevoDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelDepartamentosLayout.createSequentialGroup()
                        .addComponent(btnNuevoDepartamento)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminarDepartamento))
                    .addComponent(lbDepartamentoNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(683, Short.MAX_VALUE))
        );
        panelDepartamentosLayout.setVerticalGroup(
            panelDepartamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDepartamentosLayout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(lbDepartamentoNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNuevoDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelDepartamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevoDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(367, Short.MAX_VALUE))
            .addGroup(panelDepartamentosLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panePrincipal.addTab("Departamentos", panelDepartamentos);

        tablaReporteProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tablaReporteProductos);

        lbBusquedaFiltrada.setText("Busqueda por descripcion");

        btnBuscarProducto.setText("Filtrar");
        btnBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCatalogoProLayout = new javax.swing.GroupLayout(panelCatalogoPro);
        panelCatalogoPro.setLayout(panelCatalogoProLayout);
        panelCatalogoProLayout.setHorizontalGroup(
            panelCatalogoProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCatalogoProLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1325, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelCatalogoProLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbBusquedaFiltrada)
                .addGap(18, 18, 18)
                .addComponent(txtBuscarArt, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBuscarProducto)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelCatalogoProLayout.setVerticalGroup(
            panelCatalogoProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCatalogoProLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(panelCatalogoProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbBusquedaFiltrada)
                    .addComponent(btnBuscarProducto)
                    .addComponent(txtBuscarArt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );

        panePrincipal.addTab("Catalogo De Productos", panelCatalogoPro);

        tablaInvBajo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(tablaInvBajo);

        javax.swing.GroupLayout panelInvBajoLayout = new javax.swing.GroupLayout(panelInvBajo);
        panelInvBajo.setLayout(panelInvBajoLayout);
        panelInvBajoLayout.setHorizontalGroup(
            panelInvBajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInvBajoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1319, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelInvBajoLayout.setVerticalGroup(
            panelInvBajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInvBajoLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(112, Short.MAX_VALUE))
        );

        panePrincipal.addTab("Productos Bajos en Inventarió", panelInvBajo);

        panelProductos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lbIdProducto.setText("Clave de producto");

        lbDescripcion.setText("Descripción ");

        lbVentaPor.setText("Se vende por");

        lbPrecioCostoA.setText("Precio de costo");

        lbGanancia.setText("Ganancia");

        lbPreVenta.setText("Precio de venta");

        lbDepartamento.setText("Departamento");

        boxInventario.setText("Este producto SI utiliza inventario");
        boxInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxInventarioActionPerformed(evt);
            }
        });

        panelInventarioReg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelInventarioReg.setVisible(false);

        lbExistencia.setText("Agregar");

        lbMinimo.setText("Mínimo ");

        txtHay.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHayFocusLost(evt);
            }
        });
        txtHay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHayKeyTyped(evt);
            }
        });

        txtMinimo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMinimoFocusLost(evt);
            }
        });
        txtMinimo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMinimoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout panelInventarioRegLayout = new javax.swing.GroupLayout(panelInventarioReg);
        panelInventarioReg.setLayout(panelInventarioRegLayout);
        panelInventarioRegLayout.setHorizontalGroup(
            panelInventarioRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInventarioRegLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInventarioRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panelInventarioRegLayout.createSequentialGroup()
                        .addComponent(lbExistencia)
                        .addGap(18, 18, 18)
                        .addComponent(txtHay, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelInventarioRegLayout.createSequentialGroup()
                        .addComponent(lbMinimo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        panelInventarioRegLayout.setVerticalGroup(
            panelInventarioRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInventarioRegLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInventarioRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbExistencia)
                    .addComponent(txtHay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInventarioRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbMinimo)
                    .addComponent(txtMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        txtId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdFocusLost(evt);
            }
        });
        txtId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdKeyTyped(evt);
            }
        });

        txtDescripcion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescripcionFocusLost(evt);
            }
        });

        radioUnidad.setText("Por Unidad");

        radioGranel.setText("A Granel ");

        radioPaquete.setText("Paquete");

        txtPrecioCosto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrecioCostoFocusLost(evt);
            }
        });
        txtPrecioCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioCostoKeyTyped(evt);
            }
        });

        spinnerGanancia.setModel(new javax.swing.SpinnerNumberModel(50, 20, 400, 1));

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaProductos.setRequestFocusEnabled(false);
        jScrollPane1.setViewportView(tablaProductos);

        btnGuardarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guar.png"))); // NOI18N
        btnGuardarProducto.setText("Guardar Producto");
        btnGuardarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProductoActionPerformed(evt);
            }
        });

        btnModProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/mod.png"))); // NOI18N
        btnModProducto.setText("Modificar Producto");
        btnModProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModProductoActionPerformed(evt);
            }
        });

        btnElimProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/elim.png"))); // NOI18N
        btnElimProducto.setText("Eliminar Producto");
        btnElimProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnElimProductoActionPerformed(evt);
            }
        });

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/act.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        lbPorcentaje.setText("%");

        lbVerificacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/paloma.png"))); // NOI18N

        javax.swing.GroupLayout panelProductosLayout = new javax.swing.GroupLayout(panelProductos);
        panelProductos.setLayout(panelProductosLayout);
        panelProductosLayout.setHorizontalGroup(
            panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProductosLayout.createSequentialGroup()
                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelInventarioReg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelProductosLayout.createSequentialGroup()
                                .addComponent(btnGuardarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(977, Short.MAX_VALUE))
                    .addGroup(panelProductosLayout.createSequentialGroup()
                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boxInventario)
                            .addGroup(panelProductosLayout.createSequentialGroup()
                                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbIdProducto)
                                    .addComponent(lbDescripcion)
                                    .addComponent(lbVentaPor)
                                    .addComponent(lbGanancia)
                                    .addComponent(lbPreVenta)
                                    .addComponent(lbDepartamento)
                                    .addComponent(lbPrecioCostoA))
                                .addGap(18, 18, 18)
                                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPrecioCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(boxDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelProductosLayout.createSequentialGroup()
                                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtId)
                                            .addComponent(txtDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addComponent(lbVerificacion))
                                    .addGroup(panelProductosLayout.createSequentialGroup()
                                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelProductosLayout.createSequentialGroup()
                                                .addComponent(spinnerGanancia, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(lbPorcentaje))
                                            .addGroup(panelProductosLayout.createSequentialGroup()
                                                .addComponent(radioUnidad)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(radioGranel)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(radioPaquete))
                                    .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 175, Short.MAX_VALUE)
                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProductosLayout.createSequentialGroup()
                                .addComponent(btnModProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(163, 163, 163)
                                .addComponent(btnElimProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(153, 153, 153))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProductosLayout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 795, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45))))))
        );
        panelProductosLayout.setVerticalGroup(
            panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelProductosLayout.createSequentialGroup()
                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbIdProducto)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbVerificacion))
                        .addGap(18, 18, 18)
                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbDescripcion)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbVentaPor)
                            .addComponent(radioUnidad)
                            .addComponent(radioGranel)
                            .addComponent(radioPaquete))
                        .addGap(18, 18, 18)
                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbGanancia)
                            .addComponent(spinnerGanancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbPorcentaje))
                        .addGap(18, 18, 18)
                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPrecioCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbPrecioCostoA))
                        .addGap(18, 18, 18)
                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbPreVenta)
                            .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProductosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbDepartamento)
                            .addComponent(boxDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(boxInventario))
                    .addGroup(panelProductosLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnModProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnElimProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInventarioReg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(88, Short.MAX_VALUE))
        );

        panePrincipal.addTab("Módulo de productos", panelProductos);

        tablaMovientos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tablaMovientos);

        javax.swing.GroupLayout panelMovLayout = new javax.swing.GroupLayout(panelMov);
        panelMov.setLayout(panelMovLayout);
        panelMovLayout.setHorizontalGroup(
            panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMovLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1319, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelMovLayout.setVerticalGroup(
            panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMovLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        panePrincipal.addTab("Movimientos de Inventario", panelMov);

        lbCodigoVenta.setText("Código de Producto");

        txtIdVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdVentaKeyTyped(evt);
            }
        });

        btnIdAgregar.setText("Agregar Producto");
        btnIdAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIdAgregarActionPerformed(evt);
            }
        });

        tablaVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Codigo de producto", "Descripcion", "Cantidad", "Precio de Venta", "Existencia", "Subtotal"
            }
        ));
        jScrollPane4.setViewportView(tablaVentas);

        btnCobrar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnCobrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cobrar.png"))); // NOI18N
        btnCobrar.setText("Cobrar");
        btnCobrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCobrarActionPerformed(evt);
            }
        });

        lbSignoPesos.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lbSignoPesos.setText("$");

        lbCobroFinal.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lbCobroFinal.setText("0.00");

        btnEliminarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/elim.png"))); // NOI18N
        btnEliminarVenta.setText("Eliminar de lista");
        btnEliminarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarVentaActionPerformed(evt);
            }
        });

        btnMas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/plus.png"))); // NOI18N
        btnMas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasActionPerformed(evt);
            }
        });

        btnMenos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/minus.png"))); // NOI18N
        btnMenos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenosActionPerformed(evt);
            }
        });

        Cb_dolar.setText("Dolares");
        Cb_dolar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Cb_dolarItemStateChanged(evt);
            }
        });
        Cb_dolar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                Cb_dolarFocusLost(evt);
            }
        });
        Cb_dolar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cb_dolarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelVentasLayout = new javax.swing.GroupLayout(panelVentas);
        panelVentas.setLayout(panelVentasLayout);
        panelVentasLayout.setHorizontalGroup(
            panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVentasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelVentasLayout.createSequentialGroup()
                        .addGroup(panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelVentasLayout.createSequentialGroup()
                                .addComponent(btnEliminarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVentasLayout.createSequentialGroup()
                                .addGap(0, 537, Short.MAX_VALUE)
                                .addComponent(btnMenos)
                                .addGap(18, 18, 18)
                                .addComponent(btnMas)
                                .addGap(285, 285, 285)))
                        .addGroup(panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelVentasLayout.createSequentialGroup()
                                .addComponent(Cb_dolar)
                                .addGap(150, 150, 150))
                            .addGroup(panelVentasLayout.createSequentialGroup()
                                .addComponent(btnCobrar, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(lbSignoPesos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                                .addComponent(lbCobroFinal))))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1268, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVentasLayout.createSequentialGroup()
                        .addComponent(lbCodigoVenta)
                        .addGap(18, 18, 18)
                        .addComponent(txtIdVenta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnIdAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(57, 57, 57))
        );
        panelVentasLayout.setVerticalGroup(
            panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVentasLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbCodigoVenta)
                    .addComponent(txtIdVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnIdAgregar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelVentasLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(Cb_dolar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCobrar, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbCobroFinal)
                            .addComponent(lbSignoPesos)))
                    .addGroup(panelVentasLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnMas)
                            .addGroup(panelVentasLayout.createSequentialGroup()
                                .addComponent(btnEliminarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(btnMenos)))))
                .addContainerGap(74, Short.MAX_VALUE))
        );

        panePrincipal.addTab("Ventas", panelVentas);

        btnEliminarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconoEliminarUsuario.png"))); // NOI18N
        btnEliminarUsuario.setText("Eliminar Usuario");
        btnEliminarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarUsuarioActionPerformed(evt);
            }
        });

        lbUsuarioNuevo.setText("Usuario");

        lbContraseña.setText("Contraseña");

        btnGuardarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/check.png"))); // NOI18N
        btnGuardarUsuario.setText("Guardar Usuario");
        btnGuardarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarUsuarioActionPerformed(evt);
            }
        });

        lbNombreCompleto.setText("Nombre Completo");

        btnCancelarUsuario.setText("Cancelar");
        btnCancelarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarUsuarioActionPerformed(evt);
            }
        });

        txtUsuarioNuevo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsuarioNuevoFocusLost(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Permisos"));

        boxVentas.setText("Ventas");
        boxVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxVentasActionPerformed(evt);
            }
        });

        boxProductos.setText("Productos");

        boxCompras.setText("Compras");

        boxCorte.setText("Corte de caja");
        boxCorte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxCorteActionPerformed(evt);
            }
        });

        boxConfiguracion.setText("Configuracion");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boxVentas)
                    .addComponent(boxProductos)
                    .addComponent(boxCompras))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boxCorte)
                    .addComponent(boxConfiguracion))
                .addGap(72, 72, 72))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boxVentas)
                    .addComponent(boxCorte))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boxProductos)
                    .addComponent(boxConfiguracion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boxCompras)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        btnModificarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconoNuevoUsuario.png"))); // NOI18N
        btnModificarUsuario.setText("Modificar Usuario");
        btnModificarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarUsuarioActionPerformed(evt);
            }
        });

        tablaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(tablaUsuarios);

        javax.swing.GroupLayout panelAdmiUsuariosLayout = new javax.swing.GroupLayout(panelAdmiUsuarios);
        panelAdmiUsuarios.setLayout(panelAdmiUsuariosLayout);
        panelAdmiUsuariosLayout.setHorizontalGroup(
            panelAdmiUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAdmiUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAdmiUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAdmiUsuariosLayout.createSequentialGroup()
                        .addComponent(btnModificarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelAdmiUsuariosLayout.createSequentialGroup()
                        .addGroup(panelAdmiUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbContraseña)
                            .addComponent(lbUsuarioNuevo))
                        .addGap(41, 41, 41)
                        .addGroup(panelAdmiUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtUsuarioNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtContraseñaNueva, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelAdmiUsuariosLayout.createSequentialGroup()
                        .addComponent(lbNombreCompleto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelAdmiUsuariosLayout.createSequentialGroup()
                        .addComponent(btnGuardarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(521, Short.MAX_VALUE))
        );
        panelAdmiUsuariosLayout.setVerticalGroup(
            panelAdmiUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdmiUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAdmiUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAdmiUsuariosLayout.createSequentialGroup()
                        .addGroup(panelAdmiUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnModificarUsuario)
                            .addComponent(btnEliminarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(panelAdmiUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbUsuarioNuevo)
                            .addComponent(txtUsuarioNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelAdmiUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbContraseña)
                            .addComponent(txtContraseñaNueva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(panelAdmiUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbNombreCompleto)
                            .addComponent(txtNombreCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelAdmiUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGuardarUsuario)
                            .addComponent(btnCancelarUsuario)))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        paneConfiguracion.addTab("Administrar Usuarios", panelAdmiUsuarios);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Subir Logotipo de la Empresa"));

        btnLimpiarImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconoNuevoUsuario.png"))); // NOI18N
        btnLimpiarImagen.setText("Limpiar");
        btnLimpiarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarImagenActionPerformed(evt);
            }
        });

        btnGuardarImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconoNuevoUsuario.png"))); // NOI18N
        btnGuardarImagen.setText("Guardar Imagen");
        btnGuardarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarImagenActionPerformed(evt);
            }
        });

        btnCargarImagen2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconoNuevoUsuario.png"))); // NOI18N
        btnCargarImagen2.setText("Cargar Imagen");
        btnCargarImagen2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarImagen2ActionPerformed(evt);
            }
        });

        fotoT.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(btnCargarImagen2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(fotoT, javax.swing.GroupLayout.PREFERRED_SIZE, 1009, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(fotoT, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpiarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCargarImagen2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelLogotipoLayout = new javax.swing.GroupLayout(panelLogotipo);
        panelLogotipo.setLayout(panelLogotipoLayout);
        panelLogotipoLayout.setHorizontalGroup(
            panelLogotipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLogotipoLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 182, Short.MAX_VALUE))
        );
        panelLogotipoLayout.setVerticalGroup(
            panelLogotipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLogotipoLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(177, Short.MAX_VALUE))
        );

        paneConfiguracion.addTab("Cargar Logotipo", panelLogotipo);

        btnConfiUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconoUsuario (1).png"))); // NOI18N
        btnConfiUsuarios.setToolTipText("Administrar usuarios");
        btnConfiUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfiUsuariosActionPerformed(evt);
            }
        });

        btnPrecioDolar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconoDolar.png"))); // NOI18N
        btnPrecioDolar.setToolTipText("Precio del Dolar");
        btnPrecioDolar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrecioDolarActionPerformed(evt);
            }
        });

        btnLogotipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconoSubirLogo (1).png"))); // NOI18N
        btnLogotipo.setToolTipText("Cargar Logotipo");
        btnLogotipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogotipoActionPerformed(evt);
            }
        });

        btnImpuestos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconoImpuestos.png"))); // NOI18N
        btnImpuestos.setToolTipText("Administrar Impuestos");
        btnImpuestos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImpuestosActionPerformed(evt);
            }
        });

        btnDineroCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconoDineroCaja.png"))); // NOI18N
        btnDineroCaja.setToolTipText("Efectivo Inicial en Caja");
        btnDineroCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDineroCajaActionPerformed(evt);
            }
        });

        btnRespaldoBD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconoRespaldo.png"))); // NOI18N
        btnRespaldoBD.setToolTipText("Respaldo de Base de Datos");
        btnRespaldoBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRespaldoBDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelConfiguracionLayout = new javax.swing.GroupLayout(panelConfiguracion);
        panelConfiguracion.setLayout(panelConfiguracionLayout);
        panelConfiguracionLayout.setHorizontalGroup(
            panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfiguracionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(paneConfiguracion)
                    .addGroup(panelConfiguracionLayout.createSequentialGroup()
                        .addComponent(btnConfiUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPrecioDolar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLogotipo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnImpuestos, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDineroCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnRespaldoBD, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelConfiguracionLayout.setVerticalGroup(
            panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfiguracionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnConfiUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPrecioDolar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogotipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnImpuestos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDineroCaja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRespaldoBD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paneConfiguracion)
                .addContainerGap())
        );

        panePrincipal.addTab("Configuracion", panelConfiguracion);

        btnCorteCajero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/rojo.png"))); // NOI18N
        btnCorteCajero.setText("Hacer corte de caja de cajero");
        btnCorteCajero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCorteCajeroActionPerformed(evt);
            }
        });

        btnCorteDia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/azul.png"))); // NOI18N
        btnCorteDia.setText("Hacer corte de caja del dia");
        btnCorteDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCorteDiaActionPerformed(evt);
            }
        });

        tablaCorteTemporal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane11.setViewportView(tablaCorteTemporal);

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel17.setText("Entradas");

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel18.setText("(-)Salidas");

        jLabel20.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel20.setText("-----------------------");

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel21.setText("$");

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel22.setText("$");

        lbE1.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        lbE1.setForeground(new java.awt.Color(0, 153, 0));
        lbE1.setText("0.00");

        lbS1.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        lbS1.setForeground(new java.awt.Color(204, 0, 0));
        lbS1.setText("0.00");

        lbTotalCorte1.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        lbTotalCorte1.setText("0.00");

        jLabel24.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel24.setText("$");

        jLabel25.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel25.setText("Corte de Dia");

        btnDia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/corte_turno.png"))); // NOI18N
        btnDia.setText("Realizar corte");
        btnDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiaActionPerformed(evt);
            }
        });

        btnReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/excelicono.jpg"))); // NOI18N
        btnReporte.setText("Exportar a Excel");
        btnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel26.setText("Corte total");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel25))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addGap(66, 66, 66))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel18)
                                        .addGap(45, 45, 45)))
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel21)
                                        .addGap(18, 18, 18)
                                        .addComponent(lbE1))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel22)
                                        .addGap(18, 18, 18)
                                        .addComponent(lbS1))))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(99, 99, 99)
                                .addComponent(jLabel20))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(jLabel24)
                        .addGap(18, 18, 18)
                        .addComponent(lbTotalCorte1)))
                .addContainerGap(225, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDia, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addGap(88, 88, 88)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel21)
                    .addComponent(lbE1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel22)
                    .addComponent(lbS1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTotalCorte1)
                    .addComponent(jLabel24)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDia)
                    .addComponent(btnReporte))
                .addGap(59, 59, 59))
        );

        jLabel27.setText("Total:");

        lbTotalVentas.setText("$0.00");

        javax.swing.GroupLayout panelDiaLayout = new javax.swing.GroupLayout(panelDia);
        panelDia.setLayout(panelDiaLayout);
        panelDiaLayout.setHorizontalGroup(
            panelDiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDiaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelDiaLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbTotalVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(440, Short.MAX_VALUE))
        );
        panelDiaLayout.setVerticalGroup(
            panelDiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDiaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelDiaLayout.createSequentialGroup()
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panelDiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(lbTotalVentas))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        paneCorte.addTab("Corte de caja-Dia", panelDia);

        tablaEntradas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane9.setViewportView(tablaEntradas);

        tablaSalidas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane10.setViewportView(tablaSalidas);

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel8.setText("Fondo de caja");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel9.setText("Entradas");

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel10.setText("(-)Salidas");

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel11.setText("-----------------------");

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel12.setText("$");

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel13.setText("$");

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel14.setText("$");

        lbCaja.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        lbCaja.setText("0.00");

        lbE.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        lbE.setForeground(new java.awt.Color(0, 153, 0));
        lbE.setText("0.00");

        lbS.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        lbS.setForeground(new java.awt.Color(204, 0, 0));
        lbS.setText("0.00");

        lbTotalCorte.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        lbTotalCorte.setText("0.00");

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel19.setText("$");

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel15.setText("Corte de turno");

        btnTurno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/corte_turno.png"))); // NOI18N
        btnTurno.setText("Realizar corte");
        btnTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTurnoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel15))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(27, 27, 27)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addGap(18, 18, 18)
                                        .addComponent(lbE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(18, 18, 18)
                                        .addComponent(lbS))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addGap(18, 18, 18)
                                        .addComponent(lbCaja))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(99, 99, 99)
                                .addComponent(jLabel11))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(jLabel19)
                                .addGap(18, 18, 18)
                                .addComponent(lbTotalCorte)))))
                .addContainerGap(225, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addGap(50, 50, 50)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel14)
                    .addComponent(lbCaja))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12)
                    .addComponent(lbE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel13)
                    .addComponent(lbS))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTotalCorte)
                    .addComponent(jLabel19))
                .addGap(66, 66, 66)
                .addComponent(btnTurno)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jLabel2.setText("Salidas:");

        jLabel3.setText("Entradas");

        lbSalidas.setText("$0.00");

        lbEntradas.setText("$0.00");

        javax.swing.GroupLayout panelCajeroLayout = new javax.swing.GroupLayout(panelCajero);
        panelCajero.setLayout(panelCajeroLayout);
        panelCajeroLayout.setHorizontalGroup(
            panelCajeroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCajeroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCajeroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelCajeroLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbSalidas, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(panelCajeroLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbEntradas, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(440, Short.MAX_VALUE))
        );
        panelCajeroLayout.setVerticalGroup(
            panelCajeroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCajeroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCajeroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelCajeroLayout.createSequentialGroup()
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCajeroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lbSalidas))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCajeroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbEntradas)
                            .addComponent(jLabel3))))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        paneCorte.addTab("Corte de caja-Turno", panelCajero);

        javax.swing.GroupLayout panelCorteLayout = new javax.swing.GroupLayout(panelCorte);
        panelCorte.setLayout(panelCorteLayout);
        panelCorteLayout.setHorizontalGroup(
            panelCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCorteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(paneCorte)
                    .addGroup(panelCorteLayout.createSequentialGroup()
                        .addComponent(btnCorteCajero)
                        .addGap(18, 18, 18)
                        .addComponent(btnCorteDia, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelCorteLayout.setVerticalGroup(
            panelCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCorteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCorteCajero, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCorteDia, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paneCorte, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        panePrincipal.addTab("Corte de caja", panelCorte);

        lbInvBusqueda.setText("Codigo o Descripcion");

        txtIdInventarioB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdInventarioBKeyTyped(evt);
            }
        });

        btnBuscaId.setText("Buscar");
        btnBuscaId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaIdActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lbDescripcionInv.setText("Descripción");

        lbPrecioCostoB.setText("Precio de costo");

        lbPrecioVentaB.setText("Precio de venta");

        lbExistenciaActualInv.setText("Existencia");

        lbMinimoB.setText("Mínimo");

        txtExistenciaInv.setEditable(false);
        txtExistenciaInv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtExistenciaInvKeyTyped(evt);
            }
        });

        txtPrecioVentaInv.setEditable(false);
        txtPrecioVentaInv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioVentaInvKeyTyped(evt);
            }
        });

        txtMinimoInv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMinimoInvFocusLost(evt);
            }
        });
        txtMinimoInv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMinimoInvKeyTyped(evt);
            }
        });

        txtPrecioCostoInv.setEditable(false);
        txtPrecioCostoInv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioCostoInvKeyTyped(evt);
            }
        });

        lblDes.setText("Descripción del producto");

        lbAgregarCantidad.setText("Cantidad");

        txtNuevaCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuevaCantidadKeyTyped(evt);
            }
        });

        jLabel1.setText("Codigo de producto");

        lblCodigoProducto.setText("Id ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbDescripcionInv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbPrecioCostoB, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPrecioCostoInv)
                            .addComponent(lblDes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(lblCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(lbPrecioVentaB, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                            .addGap(18, 18, 18)
                            .addComponent(txtPrecioVentaInv, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lbAgregarCantidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbMinimoB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbExistenciaActualInv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtExistenciaInv, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtMinimoInv, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNuevaCantidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblCodigoProducto))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbDescripcionInv)
                    .addComponent(lblDes))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbPrecioCostoB)
                    .addComponent(txtPrecioCostoInv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbPrecioVentaB)
                    .addComponent(txtPrecioVentaInv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbExistenciaActualInv)
                    .addComponent(txtExistenciaInv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbMinimoB)
                    .addComponent(txtMinimoInv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbAgregarCantidad)
                    .addComponent(txtNuevaCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        btnRegistrarInv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add.png"))); // NOI18N
        btnRegistrarInv.setText("Agregar Inventario");
        btnRegistrarInv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarInvActionPerformed(evt);
            }
        });

        btnEliminarInv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/elim.png"))); // NOI18N
        btnEliminarInv.setText("Eliminar Inventario");
        btnEliminarInv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarInvActionPerformed(evt);
            }
        });

        tablaReporteInv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(tablaReporteInv);

        btnRegInventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guar.png"))); // NOI18N
        btnRegInventario.setText("Guardar Inventario");
        btnRegInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegInventarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelInventarioLayout = new javax.swing.GroupLayout(panelInventario);
        panelInventario.setLayout(panelInventarioLayout);
        panelInventarioLayout.setHorizontalGroup(
            panelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInventarioLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(panelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInventarioLayout.createSequentialGroup()
                        .addComponent(lbInvBusqueda)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtIdInventarioB, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscaId))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegInventario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 309, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInventarioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRegistrarInv, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(144, 144, 144)
                .addComponent(btnEliminarInv, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
        );
        panelInventarioLayout.setVerticalGroup(
            panelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInventarioLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(panelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbInvBusqueda)
                    .addComponent(txtIdInventarioB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscaId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegistrarInv)
                    .addComponent(btnEliminarInv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 97, Short.MAX_VALUE))
        );

        panePrincipal.addTab("Módulo  de Compras", panelInventario);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lbFecha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbFecha.setText("DD/MM/YYYY");

        lbHora.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbHora.setText("00:00:00");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Usuario:");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/calendario.png"))); // NOI18N

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/hora.png"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/usuario.png"))); // NOI18N

        lbUsuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbUsuario.setText("TipoUsuario");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbFecha)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbHora))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbUsuario)))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6)
                        .addComponent(jLabel4)
                        .addComponent(lbHora, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(lbFecha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(lbUsuario)))
                .addGap(23, 23, 23))
        );

        btnConfiguracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconoConfiguracion.png"))); // NOI18N
        btnConfiguracion.setText("[F13] Configuracion");
        btnConfiguracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfiguracionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panePrincipal)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnProductos)
                                .addGap(18, 18, 18)
                                .addComponent(btnInventario)
                                .addGap(18, 18, 18)
                                .addComponent(btnCorte, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnConfiguracion)
                                .addGap(18, 18, 18)
                                .addComponent(btnSalir))
                            .addComponent(lbFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 1009, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lbFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnConfiguracion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnCorte, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(panePrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarioActionPerformed
         panePrincipal.remove(panelProductos);  
         panePrincipal.remove(panelDepartamentos);   
         panePrincipal.remove(panelVentas);  
         panePrincipal.remove(panelCatalogoPro); 
         panePrincipal.remove(panelConfiguracion);
         panePrincipal.remove(panelCorte);
         panePrincipal.add("Modulo de Inventario",panelInventario);
         panePrincipal.add("Productos Bajo en Inventario",panelInvBajo);
         panePrincipal.add("Movimientos de Inventario",panelMov);
        
    }//GEN-LAST:event_btnInventarioActionPerformed

    private void btnCorteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCorteActionPerformed
        panePrincipal.remove(panelVentas);  
         panePrincipal.remove(panelInvBajo);
         panePrincipal.remove(panelInventario);
         panePrincipal.remove(panelMov);
         panePrincipal.remove(panelConfiguracion);
         panePrincipal.remove(panelProductos);
         panePrincipal.remove(panelDepartamentos);
         panePrincipal.remove(panelCatalogoPro);
         panePrincipal.add("Corte de caja",panelCorte);
    }//GEN-LAST:event_btnCorteActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
         panePrincipal.remove(panelVentas);  
         panePrincipal.remove(panelInvBajo);
         panePrincipal.remove(panelInventario);
         panePrincipal.remove(panelMov);
         panePrincipal.remove(panelConfiguracion);
         panePrincipal.add("Modulo de productos",panelProductos);
         panePrincipal.add("Departamentos",panelDepartamentos);
         panePrincipal.add("Catalogo de productos",panelCatalogoPro);
         panePrincipal.remove(panelCorte);
        
         
    }//GEN-LAST:event_btnProductosActionPerformed

    private void btnVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentaActionPerformed
        panePrincipal.add("Ventas",panelVentas);
        panePrincipal.remove(panelMov);
        panePrincipal.remove(panelInventario);
        panePrincipal.remove(panelInvBajo);
        panePrincipal.remove(panelProductos);
        panePrincipal.remove(panelDepartamentos);
        panePrincipal.remove(panelCatalogoPro);
        panePrincipal.remove(panelConfiguracion);
        panePrincipal.remove(panelCorte);
    }//GEN-LAST:event_btnVentaActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
      //Este evento sirve para salir del sistema 
       int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro se salir del sistema?","Confirmar salida",JOptionPane.YES_NO_OPTION);
       VistaLogin vista = new VistaLogin();
       if(resp==0){
           
           vista.setVisible(true);
           this.setVisible(false);
       }
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnVentaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVentaKeyPressed

    private void btnBuscaIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaIdActionPerformed

        try {                                           
            
            try {
                //trae los datos de la tabla a los campos
                tablaReporteInv.setModel(ConInv.BuscarId(Integer.parseInt(txtIdInventarioB.getText())));
            }catch(NumberFormatException ex){
                try {
                    tablaReporteInv.setModel(ConInv.BuscarDescripcion(txtIdInventarioB.getText().toUpperCase()));
                } catch (SQLException ex1) {
                    Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }catch (SQLException ex) {
                Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(tablaReporteInv.getRowCount() == 0){
                JOptionPane.showMessageDialog(null,"Ese producto no existe ");
                txtIdInventarioB.setText("");
            }
            else
            {
                //Se llevan los datos de la tabla hacia los espacios
                lblCodigoProducto.setText(tablaReporteInv.getValueAt(0,0).toString());
                lblDes.setText(tablaReporteInv.getValueAt(0,1).toString());
                txtPrecioCostoInv.setText(tablaReporteInv.getValueAt(0,2).toString());
                txtPrecioVentaInv.setText(tablaReporteInv.getValueAt(0,3).toString());
                txtExistenciaInv.setText(tablaReporteInv.getValueAt(0,4).toString());
                txtMinimoInv.setText(tablaReporteInv.getValueAt(0,5).toString());
                txtIdInventarioB.setEditable(false);
                txtPrecioCostoInv.setEditable(false);
                txtPrecioVenta.setEditable(false);
                btnBuscaId.setEnabled(false);
            }
            //Se refrescan las tablas para ver los cambios hechos
            MostrarTablaReporteInv();
            MostrarTablaInvBajo();
            
            
            
        }catch(SQLException ex){
            Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
        } 
          
        
      
    }//GEN-LAST:event_btnBuscaIdActionPerformed

    private void btnBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProductoActionPerformed
       //Esta funcion sirve para hacer buscar un registro en la base de datos y vizualizarlo
        try {
            MostrarTablaProductos2();
        } catch (SQLException ex) {
            Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarProductoActionPerformed

    private void btnElimProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnElimProductoActionPerformed
      //Esta funcion sirva para eliminar un producto
        JOptionPane.showConfirmDialog(this, "¿Desea eliminar este producto?");
        int fila = tablaProductos.getSelectedRow();
        String Id = tablaProductos.getValueAt(fila,0).toString();
        borrarInv(Id);
        if(fila>=0){
            try {
                //Se manda a a llamar al controlador para ejecutar el query y eliminar el producto
                JOptionPane.showMessageDialog(null, ConArt.EliminarProducto(Id));
                //Se refrescan las tablas para ver los cambios 
                MostrarTablaProductos();
                MostrarTablaProductos2();
                
            } catch (SQLException ex) {
                Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null,"No selecciono ninguna fila");
        }
    }//GEN-LAST:event_btnElimProductoActionPerformed

    private void btnGuardarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProductoActionPerformed
//    }//GEN-LAST:event_btnGuardarProductoActionPerformed
        String descripcion=txtDescripcion.getText();
        String tipo="Entrada";
        int habia=0;
        int cantidad=0;
        int existencia=habia+cantidad;
        String horas="";
        String fecha="";
        Date date = new Date();
        DateFormat h = new SimpleDateFormat("HH:mm");     
        DateFormat f = new SimpleDateFormat("yyyy/MM/dd");       
        fecha=f.format(date);
        horas=h.format(date);
        //Se manda a llamar la funcion que verifica si los espacios estan vacios
        validarReg();
        try {
            int Id, Existencia, Minimo, Departamento = 0;
            String Descripcion, TipoVenta = null;
            float Costo, Venta;
            Id=Integer.parseInt(txtId.getText());
            Descripcion = txtDescripcion.getText(); 
            if(radioUnidad.isSelected())
                TipoVenta = radioUnidad.getText();
            if(radioGranel.isSelected())
                TipoVenta = radioGranel.getText();
            if(radioPaquete.isSelected())
                TipoVenta = radioPaquete.getText();
            Costo = Float.parseFloat(txtPrecioCosto.getText());
            Venta = Float.parseFloat(txtPrecioVenta.getText());
            ResultSet result = ConDep.Departamento((String) boxDepartamento.getSelectedItem());
            while(result.next())
                {
                    Departamento=result.getInt(1);
                }
            //Se manda a llamar al controlador para hacer un registro de productos
          
            if(!boxInventario.isSelected()){
               JOptionPane.showMessageDialog(null,ConArt.RegistrarProducto(Id, Descripcion, TipoVenta, Costo, Venta, Departamento));
               ConInv.RegistrarInventario(Id, 0, 0);
            }else{
                Existencia=Integer.parseInt(txtHay.getText());
                Minimo=Integer.parseInt(txtMinimo.getText());
                JOptionPane.showMessageDialog(null,ConArt.RegistrarProducto(Id, Descripcion, TipoVenta, Costo, Venta, Departamento));
                JOptionPane.showMessageDialog(null, ConInv.RegistrarInventario(Id, Existencia, Minimo));
            }
            
            
//            if(boxInventario.isSelected())
//            {
//                Existencia=Integer.parseInt(txtHay.getText());
//                Minimo=Integer.parseInt(txtMinimo.getText());
//                regInventario(Id, Existencia, Minimo);
//                //Se manda a llamar al controlador de inventarios para hacer una compra
//                ConMov.registrarMovimientos(fecha,hora,descripcion,habia,tipo,cantidad,existencia);
//            }
            
            //Se refrescan las tablas para ver los cambios 
            MostrarTablaProductos();
            MostrarTablaProductos2();
            MostrarTablaMovimientos();
            MostrarTablaReporteInv();
            MostrarTablaInvBajo();
            //Esta funcion sirve para limpiar los espacios de un registro
            limpiarReg();
            lbVerificacion.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
            
        }catch(NumberFormatException e){
        }
//        if(!boxInventario.isSelected()){
//               int Id2=Integer.parseInt(txtId.getText());
//               int  Existencia2=1;
//               int  Minimo2=1;
//            try {
//                regInventarioVacio(Id2,Existencia2,Minimo2);
//            } catch (SQLException ex) {
//                Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
//            }
//                
//            }
    }
    private void txtPrecioCostoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrecioCostoFocusLost
       //Este funcion calcula el precio de venta al calcular la ganancia y el precio de costo
        float pre = Float.parseFloat(txtPrecioCosto.getText());
        int gan = Integer.parseInt(spinnerGanancia.getValue().toString());
        if(pre>0)
            txtPrecioVenta.setText(ConArt.CalcularPrecio(pre, gan)+"");
        else
            txtPrecioCosto.setText("");

    }//GEN-LAST:event_txtPrecioCostoFocusLost

    private void boxInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxInventarioActionPerformed
       //Esta funcion hace visible el panel al querer agregar productos en un registro
        if(boxInventario.isSelected()){
            panelInventarioReg.setVisible(true);
        }else{
            panelInventarioReg.setVisible(false);
        }
    }//GEN-LAST:event_boxInventarioActionPerformed

    private void btnRegistrarInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarInvActionPerformed
        //Esta funcion arrastra los datos de la tabla de inventarios para realizar una venta 
        int fila=tablaReporteInv.getSelectedRow();
        if(fila>=0){
            lblCodigoProducto.setText(tablaReporteInv.getValueAt(fila,0).toString());
            lblDes.setText(tablaReporteInv.getValueAt(fila,1).toString());
            txtPrecioCostoInv.setText(tablaReporteInv.getValueAt(fila,2).toString());
            txtPrecioVentaInv.setText(tablaReporteInv.getValueAt(fila,3).toString());
            txtExistenciaInv.setText(tablaReporteInv.getValueAt(fila,4).toString());
            txtMinimoInv.setText(tablaReporteInv.getValueAt(fila,5).toString());
            txtIdInventarioB.setEditable(false);
            txtPrecioCostoInv.setEditable(false);
            txtPrecioVentaInv.setEditable(false);
            txtExistenciaInv.setEditable(false);
            txtMinimoInv.setEditable(true);
            btnBuscaId.setEnabled(false);
        }else{
            JOptionPane.showMessageDialog(null,"No selecciono ninguna fila");
        }
    }//GEN-LAST:event_btnRegistrarInvActionPerformed

    private void btnRegInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegInventarioActionPerformed
       //Este evento funciona para realizar compras dentro del sistema
        String descripcion=lblDes.getText();
        String tipo="Salida";
        int habia=Integer.parseInt(txtExistenciaInv.getText());
        int cantidad=Integer.parseInt(txtNuevaCantidad.getText());
        if(cantidad>0){
            int existencia=habia+cantidad;
            String horas="";
            String fecha="";
            Date date = new Date();
            DateFormat h = new SimpleDateFormat("HH:mm");     
            DateFormat f = new SimpleDateFormat("yyyy/MM/dd");       
            fecha=f.format(date);
            horas=h.format(date);
            float cortet=Float.parseFloat(txtPrecioCostoInv.getText())*Float.parseFloat(txtNuevaCantidad.getText());
            String usuario=lbUsuario.getText();
            if("".equals(lblCodigoProducto.getText())){
                JOptionPane.showMessageDialog(null,"No ingresaste un Id o seleccionaste un inventario");
            }else{
                try {
                    //regMovimientoEntrada();
                    ConMov.registrarMovimientos(fecha,horas,descripcion,habia,tipo,cantidad,existencia);
                    ConCor.regCorte(usuario,fecha,horas,tipo,cortet);
                    //Este metodo sirve para hacer la modificacion de las compras
                    regInvTabla();
                    MostrarTablaMovimientos();
                    MostrarSalida();
                    MostrarEntrada();
                    calcular_corte();
                } catch (SQLException ex) {
                    Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else{
          JOptionPane.showMessageDialog(null,"No se permite compras menores a un elemento");
          txtNuevaCantidad.setText("");
        }
    }//GEN-LAST:event_btnRegInventarioActionPerformed

    private void btnEliminarInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarInvActionPerformed
        //Esta funcion sirve para eliminar la tupla seleccionada dentro de la tabla de inventarios
        int fila=tablaProductos.getSelectedRow();
        JOptionPane.showMessageDialog(null, ConInv.BorrarInventario(fila, tablaProductos.getValueAt(fila,0).toString()));
    }//GEN-LAST:event_btnEliminarInvActionPerformed

    private void btnModProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModProductoActionPerformed
        // Trae los datos seleccionados de la tabla
        btnActualizar.setEnabled(true);
         int fila=tablaProductos.getSelectedRow();
        if(fila>=0){
            txtId.setText(tablaProductos.getValueAt(fila,0).toString());
            txtDescripcion.setText(tablaProductos.getValueAt(fila,1).toString());
            if(radioUnidad.getText() == null ? tablaProductos.getValueAt(fila,2).toString() == null : radioUnidad.getText().equals(tablaProductos.getValueAt(fila,2).toString())){
                radioUnidad.setSelected(true);
            }
            if(radioGranel.getText() == null ? tablaProductos.getValueAt(fila,2).toString() == null : radioGranel.getText().equals(tablaProductos.getValueAt(fila,2).toString())){
                radioGranel.setSelected(true);
            }
            if(radioPaquete.getText() == null ? tablaProductos.getValueAt(fila,2).toString() == null : radioPaquete.getText().equals(tablaProductos.getValueAt(fila,2).toString())){
                radioPaquete.setSelected(true);
            }
            txtPrecioCosto.setText(tablaProductos.getValueAt(fila,3).toString());
            txtPrecioVenta.setText(tablaProductos.getValueAt(fila,4).toString());
            txtId.setEnabled(false);
            btnGuardarProducto.setEnabled(false);
            boxInventario.setEnabled(false);
        }else{
            JOptionPane.showMessageDialog(null,"No selecciono ninguna fila");
        }
    }//GEN-LAST:event_btnModProductoActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        try {
            // Esta funcion sirve para actualizar los cambios hechos en los productos
            String select = null;
            int Departamento=0;
            if(radioUnidad.isSelected()){
                select=radioUnidad.getText();
            }
            if(radioGranel.isSelected()){
                select=radioGranel.getText();
            }
            if(radioPaquete.isSelected()){
                select=radioPaquete.getText();
            }
            ResultSet result = ConDep.Departamento((String) boxDepartamento.getSelectedItem());
            while(result.next())
                {
                    Departamento=result.getInt(1);
                }
            JOptionPane.showMessageDialog(null, ConArt.ActualizarProducto(Integer.parseInt(txtId.getText()), txtDescripcion.getText(), select, Float.parseFloat(txtPrecioCosto.getText()), Float.parseFloat(txtPrecioVenta.getText()), Departamento));
            MostrarTablaProductos();
            txtId.setEnabled(true);
            txtId.setText("");
            txtDescripcion.setText("");
            txtPrecioCosto.setText("");
            txtPrecioVenta.setText("");
            boxDepartamento.setSelectedItem("-Sin Departamento-");
            btnActualizar.setEnabled(false);
            btnGuardarProducto.setEnabled(true);
            boxInventario.setEnabled(true);
            txtId.setEnabled(true);
        } catch (SQLException ex) {
            Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
              JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnNuevoDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoDepartamentoActionPerformed
        //Aqui se agrega el departamento tanto en la tabla como en el comboBox
        if(txtNuevoDepartamento.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Debe de ingresar el nombre del nuevo departamento");
        }else{
            try {
                agregarDepartamento();
                llenarCombo();
                txtNuevoDepartamento.setText("");
            } catch (SQLException ex) {
                Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }//GEN-LAST:event_btnNuevoDepartamentoActionPerformed

    private void btnEliminarDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarDepartamentoActionPerformed
        //Aqui se elimina un departamento seleccionado
        int fila=tablaDepartamentos.getSelectedRow();
        if(fila>=0){
            try {
                JOptionPane.showMessageDialog(null, ConDep.EliminarDepartamento(fila, tablaDepartamentos));
                MostrarTablaDepartamentos();
            } catch (SQLException ex) {
                Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
            }
            }else{
            JOptionPane.showMessageDialog(null,"No selecciono ninguna fila");
        }
        
    }//GEN-LAST:event_btnEliminarDepartamentoActionPerformed

    private void txtIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdFocusLost
        // Este es el desenfoque del Id de productos que verifica que existe el ID
        try {
            int Id=Integer.parseInt(txtId.getText());
            tablaProductos.setModel(ConArt.RevisarId(Id));
            if(tablaProductos.getRowCount() !=0){
                JOptionPane.showMessageDialog(null,"Ese código ya existe");
                txtId.setText("");
                lbVerificacion.setVisible(false);
            }else{
                if(ValidarPositivos(Id))
                    txtId.setText("");
                else
                    lbVerificacion.setVisible(true);
            }
            MostrarTablaProductos();
//            if(ValidarPositivos(Id)){
//                txtId.setText("");
//            }
        } catch (SQLException ex) {
            Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
        } catch(NumberFormatException ex){
        }
    }//GEN-LAST:event_txtIdFocusLost

    private void lbFotoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lbFotoKeyPressed
        
    }//GEN-LAST:event_lbFotoKeyPressed

    private void btnIdAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIdAgregarActionPerformed
       //Esta tabla sirve para agragar productos a la tabla de ventas 
        try {
            MostrarTablaVentas(Integer.parseInt(txtIdVenta.getText()));
            if(Integer.parseInt(tablaVentas.getValueAt(ii-1, 4)+"")==0)
            {
                lbCobroFinal.setText(ConPri.Restar(Float.parseFloat(lbCobroFinal.getText()), Float.parseFloat((String) tablaVentas.getValueAt(ii-1, 3)))+"0");
                Object[] ObVacio = null;
                DefaultTableModel modelodel = (DefaultTableModel) tablaVentas.getModel();
                modelodel.removeRow(ii-1);
                ii--;
                if(modelodel.getRowCount()<17)
                    modelodel.addRow(ObVacio);
                JOptionPane.showMessageDialog(null, "Este articulo no tiene inventario", "Articulo sin inventario", JOptionPane.WARNING_MESSAGE);
            }
            txtIdVenta.setText("");
            
        } catch (SQLException ex) {
            Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
        }catch(NullPointerException ex)
        {
            JOptionPane.showMessageDialog(null, "Este articulo no existe", "Articulo inexistente", JOptionPane.WARNING_MESSAGE);
            txtIdVenta.setText("");
        }catch(NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(null, "Ingrese articulo", "No se ingreso articulo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnIdAgregarActionPerformed

    private void txtDescripcionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescripcionFocusLost
        //Este evento es para validar que la descripcion no exista
        try {
            
            String des=txtDescripcion.getText();
            tablaProductos.setModel(ConArt.RevisarDes(des));
            if(tablaProductos.getRowCount() !=0){
                JOptionPane.showMessageDialog(null,"Esa articulo ya existe");
                txtDescripcion.setText("");
               
            }else{
            }
            MostrarTablaProductos();
        } catch (SQLException ex) {
            Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtDescripcionFocusLost

    private void btnCobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCobrarActionPerformed
       //Esta evento sirve para realizar el cobro de un producto
        String usuario=lbUsuario.getText();
        String descripcion="";
        int habia=0;
        String tipo="Entrada", respuesta;
        int cantidad=0;
        int existencia=0;
        String horas="";
        String fecha2="";
        Date date = new Date();
        DateFormat h = new SimpleDateFormat("HH:mm");     
        DateFormat f = new SimpleDateFormat("yyyy/MM/dd");       
        fecha2=f.format(date);
        horas=h.format(date);
        Float tot;
        respuesta=JOptionPane.showInputDialog(null, "Por favor, pague: "+lbCobroFinal.getText()+" pesos", "Cobro", JOptionPane.INFORMATION_MESSAGE);
        if(respuesta!=null)
        {
            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.UP);
            try {
                Float pago = Float.parseFloat(respuesta);
                if(pago<Float.parseFloat(lbCobroFinal.getText()))
                    btnCobrarActionPerformed(null);
                else
                {
                    Double cambio = pago-Double.parseDouble(lbCobroFinal.getText());
                    if(Cb_dolar.isSelected())
                        cambio=cambio*dolar;
                    JOptionPane.showMessageDialog(null, "Su cambio es de: $"+df.format(cambio)+"0 pesos", "Cobro", JOptionPane.INFORMATION_MESSAGE);

                    int Id=ConVen.CalcularId();
                    java.util.Calendar fecha = new java.util.GregorianCalendar();
                    tot=Float.parseFloat(lbCobroFinal.getText());
                    if(Cb_dolar.isSelected())
                        tot=tot*Float.parseFloat(dolar+"");
                    JOptionPane.showMessageDialog(null, ConVen.Ventas(Id+1, tot, fecha, lbUsuario.getText()));

                    for (int cont = 0; cont < tablaVentas.getRowCount(); cont++) 
                    {
                        if(tablaVentas.getValueAt(cont, 0)!= null)
                        {
                            ConDet.RegistrarDetalles(Id+1, Integer.parseInt(tablaVentas.getValueAt(cont, 0)+""), tablaVentas.getValueAt(cont, 1)+"", Integer.parseInt(tablaVentas.getValueAt(cont, 2)+""), Float.parseFloat(tablaVentas.getValueAt(cont, 3)+""));
                            for (int i = 0; i < Integer.parseInt(tablaVentas.getValueAt(ii-1, 2)+""); i++) {
                            ConInv.DisminuirInventario(Integer.parseInt((String) tablaVentas.getValueAt(cont, 0)));
                            }
                            //regMovimientoSalida();
                            descripcion=tablaVentas.getValueAt(cont, 1)+"";
                            habia=Integer.parseInt(tablaVentas.getValueAt(cont, 4)+"");
                            cantidad=Integer.parseInt(tablaVentas.getValueAt(cont, 2)+"");
                            existencia=habia-cantidad;
                            corte=tot;
                            ConMov.registrarMovimientos(fecha2,horas,descripcion,habia,tipo,cantidad,existencia);
                           // ConCor.regCorte(usuario,fecha2,hora,tipo,corte);
                        }
                        else
                            break;
                    }
                    txtIdVenta.setText(""); 
                    MostrarTablaProductos();
                    MostrarTablaProductos2();
                    MostrarTablaMovimientos();
                    MostrarTablaReporteInv();
                    MostrarTablaInvBajo();
                    MostrarSalida();
                    MostrarEntrada();
                    tablaVentas.setModel(new javax.swing.table.DefaultTableModel(
                    new Object [][] {
                        {null, null, null, null,null},
                        {null, null, null, null,null},
                        {null, null, null, null,null},
                        {null, null, null, null,null},
                        {null, null, null, null,null},
                        {null, null, null, null,null},
                        {null, null, null, null,null},
                        {null, null, null, null,null},
                        {null, null, null, null,null},
                        {null, null, null, null,null},
                        {null, null, null, null,null},
                        {null, null, null, null,null},
                        {null, null, null, null,null},
                        {null, null, null, null,null},
                        {null, null, null, null,null},
                        {null, null, null, null,null},
                        {null, null, null, null,null},


                    },
                    new String [] {
                        "Codigo de producto", "Descripcion", "Cantidad", "Precio de Venta", "Existencia", "Subtotal"
                    }));
                    ii=0;
                    ConCor.regCorte(usuario,fecha2,horas,tipo,corte);
                    Cb_dolar.setSelected(false);
                    ban=false;
                    lbCobroFinal.setText("0.00");
                }
            } catch (SQLException ex) {
                Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
            } catch( NumberFormatException ex)
            {
                btnCobrarActionPerformed(null);
            }
        }
        try {
            MostrarEntrada();
            calcular_corte();
        } catch (SQLException ex) {
            Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCobrarActionPerformed

    private void btnEliminarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarVentaActionPerformed
        // Esta funcion sirve para eliminar un producto dentro de las ventas
        int Fila = tablaVentas.getSelectedRow();
        if (Fila >= 0) {
            lbCobroFinal.setText(ConPri.Restar(Float.parseFloat(lbCobroFinal.getText()), Float.parseFloat((String) tablaVentas.getValueAt(Fila, 5)))+"0");
            Object[] ObVacio = null;
            DefaultTableModel modelodel = (DefaultTableModel) tablaVentas.getModel();
            modelodel.removeRow(Fila);
            ii--;
            if (modelodel.getRowCount()<17)
                modelodel.addRow(ObVacio);
        }else
            JOptionPane.showMessageDialog(null, "No se selecciono fila");
    }//GEN-LAST:event_btnEliminarVentaActionPerformed

    private void txtIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdKeyTyped
        // Validacion de solo numeros
        ValidarLetras(evt);
        //ValidarPositivos(Integer.parseInt(txtId.getText()));
    }//GEN-LAST:event_txtIdKeyTyped

    private void txtPrecioCostoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioCostoKeyTyped
        // Validar solo numero
        ValidarLetras(evt);
    }//GEN-LAST:event_txtPrecioCostoKeyTyped

    private void txtIdInventarioBKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdInventarioBKeyTyped
       //Validar solo numeros
     
    }//GEN-LAST:event_txtIdInventarioBKeyTyped

    private void txtNuevaCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevaCantidadKeyTyped
        // Validar solo numeros
        ValidarLetras(evt);
    }//GEN-LAST:event_txtNuevaCantidadKeyTyped

    private void txtMinimoInvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMinimoInvKeyTyped
        // Validar solo numeros
        ValidarLetras(evt);
    }//GEN-LAST:event_txtMinimoInvKeyTyped

    private void txtExistenciaInvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExistenciaInvKeyTyped
        // Validar solo numeros
        ValidarLetras(evt);
    }//GEN-LAST:event_txtExistenciaInvKeyTyped

    private void txtPrecioCostoInvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioCostoInvKeyTyped
        // Validar solo numeros
        ValidarLetras(evt);
    }//GEN-LAST:event_txtPrecioCostoInvKeyTyped

    private void txtPrecioVentaInvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioVentaInvKeyTyped
        // Validar solo numeros
        ValidarLetras(evt);
    }//GEN-LAST:event_txtPrecioVentaInvKeyTyped

    private void txtIdVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdVentaKeyTyped
        // Validar solo numeros 
        ValidarLetras(evt);
    }//GEN-LAST:event_txtIdVentaKeyTyped

    private void btnMasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasActionPerformed
        // TODO add your handling code here:
        if(!(ii==0))
            if((Integer.parseInt(tablaVentas.getValueAt(ii-1, 4)+""))>(Integer.parseInt(tablaVentas.getValueAt(ii-1, 2)+"")))
            {
                int cantidad = Integer.parseInt(tablaVentas.getValueAt(ii-1, 2)+"")+1;
                tablaVentas.setValueAt(cantidad, ii-1, 2);
                tablaVentas.setValueAt(cantidad*Float.parseFloat(tablaVentas.getValueAt(ii-1, 3)+"")+"0", ii-1, 5);
                lbCobroFinal.setText((ConPri.SumarProducto(Float.parseFloat(lbCobroFinal.getText()), Float.parseFloat(tablaVentas.getValueAt(ii-1, 3)+"")))+"0");
            }
    }//GEN-LAST:event_btnMasActionPerformed

    private void btnMenosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenosActionPerformed
        // TODO add your handling code here:
        if(!(ii==0))
        {
            tablaVentas.getValueAt(ii-1, 2);
            if(Integer.parseInt((tablaVentas.getValueAt(ii-1, 2))+"")>1)
            {
                int cantidad = Integer.parseInt(tablaVentas.getValueAt(ii-1, 2)+"")-1;
                tablaVentas.setValueAt(cantidad, ii-1, 2);
                tablaVentas.setValueAt(cantidad*Float.parseFloat(tablaVentas.getValueAt(ii-1, 3)+"")+"0", ii-1, 5);
                lbCobroFinal.setText((ConPri.RestarProducto(Float.parseFloat(lbCobroFinal.getText()), Float.parseFloat(tablaVentas.getValueAt(ii-1, 3)+"")))+"0");
            }
        }
    }//GEN-LAST:event_btnMenosActionPerformed

    private void btnConfiguracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfiguracionActionPerformed
         panePrincipal.remove(panelProductos);  
         panePrincipal.remove(panelDepartamentos);   
         panePrincipal.remove(panelVentas);  
         panePrincipal.remove(panelCatalogoPro); 
         panePrincipal.remove(panelInventario);
         panePrincipal.remove(panelMov);
         panePrincipal.remove(panelInvBajo);  
         panePrincipal.remove(panelCorte);
         panePrincipal.add("Configuracion",panelConfiguracion);
         
    }//GEN-LAST:event_btnConfiguracionActionPerformed

    private void btnRespaldoBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRespaldoBDActionPerformed
       
        int r = JOptionPane.showConfirmDialog(null, "¿Está seguro se realizar un respaldo de la Base de Datos actual?");
        if(r==0){
           int resp;
        JFileChooser RealizarBackupMySQL = new JFileChooser();
        resp=RealizarBackupMySQL.showSaveDialog(this);//JFileChooser de nombre RealizarBackupMySQL
        if (resp==JFileChooser.APPROVE_OPTION) {//Si el usuario presiona aceptar; se genera el Backup
        try{
        Runtime runtime = Runtime.getRuntime();
        File backupFile = new File(String.valueOf(RealizarBackupMySQL.getSelectedFile().toString())+".sql");
        InputStreamReader irs;
        BufferedReader br;
            try (FileWriter fw = new FileWriter(backupFile)) {
                Process child = runtime.exec("C:\\Bitnami\\wampstack-7.1.26-0\\mysql\\bin\\mysqldump --opt --password=password --user=root --databases DigoV");
                irs = new InputStreamReader(child.getInputStream());
                br = new BufferedReader(irs);
                String line;
                while( (line=br.readLine()) != null ) {
                    fw.write(line + "\n");
                }   
            }
        irs.close();
        br.close();

        JOptionPane.showMessageDialog(null, "Archivo generado correctamente.","Verificar",JOptionPane. INFORMATION_MESSAGE);
        }catch(IOException ex){
        JOptionPane.showMessageDialog(null, "Error no se genero el archivo por el siguiente motivo:"+ex.getMessage(), "Verificar",JOptionPane.ERROR_MESSAGE);
        }
        //JOptionPane.showMessageDialog(null, "Archivogenerado","Verificar",JOptionPane.INFORMAT ION_MESSAGE);
        } else if (resp==JFileChooser.CANCEL_OPTION) {
        JOptionPane.showMessageDialog(null,"Ha sido cancelada la generación del Backup.");
        }
        }
        
    }//GEN-LAST:event_btnRespaldoBDActionPerformed

    private void btnConfiUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfiUsuariosActionPerformed
        paneConfiguracion.add("Administrar Usuarios",panelAdmiUsuarios);
        paneConfiguracion.remove(panelLogotipo);
        
    }//GEN-LAST:event_btnConfiUsuariosActionPerformed

    private void btnImpuestosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImpuestosActionPerformed
        paneConfiguracion.remove(panelAdmiUsuarios);
        paneConfiguracion.remove(panelLogotipo);
    }//GEN-LAST:event_btnImpuestosActionPerformed

    private void btnGuardarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarUsuarioActionPerformed
        // Esta funcion guarda los datos de un nuevo cajero
        JPasswordField pf = new JPasswordField();
        int resp=JOptionPane.showConfirmDialog(null, pf, "Ingrese contraseña de administrador",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resp == JOptionPane.OK_OPTION) {
            try {
                String password = new String(pf.getPassword());
                ResultSet result = ConPri.VerificarAdmin();
                String pass="";
                while(result.next())
                {
                    pass=result.getString(1);
                }
                if(password.equals(pass)){
                    String usuario=txtUsuarioNuevo.getText();
                    String contra=txtContraseñaNueva.getText();
                    String nombreUsuario=txtNombreCompleto.getText();
                    boolean venta=boxVentas.isSelected();
                    boolean productos=boxProductos.isSelected();
                    boolean compras=boxCompras.isSelected();
                    boolean cortet=boxCorte.isSelected();
                    boolean configuracion=boxConfiguracion.isSelected();        
                    if(txtUsuarioNuevo.getText().equals("")|| txtContraseñaNueva.getText().equals("") || txtNombreCompleto.getText().equals("")){
                        JOptionPane.showMessageDialog(null,"Debe de llenar todos los espacios");
                    }
                    else{
                        if(!BanderaModificarUsuario)
                            JOptionPane.showMessageDialog(null,ConLog.RegistrarUsuario(usuario, contra, nombreUsuario, venta, productos, compras, cortet, configuracion));
                        else{
                            JOptionPane.showMessageDialog(null,ConLog.ModificarUsuario(usuario, contra, nombreUsuario, venta, productos, compras, cortet, configuracion));
                            txtUsuarioNuevo.setEditable(true);
                        }
                        MostrarTablaUsuarios();
                        txtUsuarioNuevo.setText("");
                        txtContraseñaNueva.setText("");
                        txtNombreCompleto.setText("");
                        boxVentas.setSelected(false);
                        boxProductos.setSelected(false);
                        boxCompras.setSelected(false);
                        boxCorte.setSelected(false);
                        boxConfiguracion.setSelected(false);
                        BanderaUsuario = false;
                        BanderaModificarUsuario = false;
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Contraseña incorrecta");
                }
            } catch (SQLException ex) {
            }
        }
    }//GEN-LAST:event_btnGuardarUsuarioActionPerformed

    private void boxVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxVentasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxVentasActionPerformed

    private void btnEliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarUsuarioActionPerformed
        
        int fila=tablaUsuarios.getSelectedRow();
            if(fila>=0){
            try {
                int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el usuario?");
                if(resp==0){
                JOptionPane.showMessageDialog(null, ConLog.EliminarUsuario(tablaUsuarios.getValueAt(fila, 1).toString()));
                MostrarTablaUsuarios();
                }
            } catch (SQLException ex) {
                Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
            }
            }else{
            JOptionPane.showMessageDialog(null,"No selecciono ninguna fila");
        }
    }//GEN-LAST:event_btnEliminarUsuarioActionPerformed

    private void btnLogotipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogotipoActionPerformed
        paneConfiguracion.add("Cargar logotipo",panelLogotipo);
        paneConfiguracion.remove(panelAdmiUsuarios);
    }//GEN-LAST:event_btnLogotipoActionPerformed

    private void btnCargarImagen2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarImagen2ActionPerformed
        fotoT.setIcon(null);
        JFileChooser j=new JFileChooser();
        j.setFileSelectionMode(JFileChooser.FILES_ONLY);//solo archivos y no carpetas
        int estado=j.showOpenDialog(null);
        if(estado== JFileChooser.APPROVE_OPTION){
            try{
                fis=new FileInputStream(j.getSelectedFile());
                //necesitamos saber la cantidad de bytes
                longitudBytes=(int)j.getSelectedFile().length();
                try {
                    Image icono=ImageIO.read(j.getSelectedFile()).getScaledInstance
                    (fotoT.getWidth(),fotoT.getHeight(),Image.SCALE_DEFAULT);
                    fotoT.setIcon(new ImageIcon(icono));
                    fotoT.updateUI();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "imagen: "+ex);
                }
            }catch(FileNotFoundException ex){
            }
        }  
    }//GEN-LAST:event_btnCargarImagen2ActionPerformed

    private void btnGuardarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarImagenActionPerformed
        try {
            // TODO add your handling code here:
            JOptionPane.showMessageDialog(null,ConLogo.SubirLogotipo(fis, longitudBytes));
            fotoT.setIcon(null);
        } catch (SQLException ex) {
            Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            cargar();
        } catch (IOException ex) {
            Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }//GEN-LAST:event_btnGuardarImagenActionPerformed

    private void btnLimpiarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarImagenActionPerformed
        fotoT.setIcon(null);
    }//GEN-LAST:event_btnLimpiarImagenActionPerformed

    private void btnCancelarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarUsuarioActionPerformed
        BanderaUsuario=false;
        BanderaModificarUsuario=false;
        txtUsuarioNuevo.setEditable(true);
        txtUsuarioNuevo.setText("");
        txtContraseñaNueva.setText("");
        txtNombreCompleto.setText("");
        boxVentas.setSelected(false);
        boxProductos.setSelected(false);
        boxCompras.setSelected(false);
        boxCorte.setSelected(false);
        boxConfiguracion.setSelected(false);
    }//GEN-LAST:event_btnCancelarUsuarioActionPerformed

    private void btnPrecioDolarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrecioDolarActionPerformed
        // TODO add your handling code here:
        DineroDolar();
    }//GEN-LAST:event_btnPrecioDolarActionPerformed

    private void btnDineroCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDineroCajaActionPerformed
        // TODO add your handling code here:
        DineroCaja();
    }//GEN-LAST:event_btnDineroCajaActionPerformed

    private void btnCorteCajeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCorteCajeroActionPerformed
        paneCorte.remove(panelDia);
        paneCorte.add("Corte de caja-Turno",panelCajero);
    }//GEN-LAST:event_btnCorteCajeroActionPerformed

    private void btnCorteDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCorteDiaActionPerformed
        JPasswordField pf = new JPasswordField();
        int resp=JOptionPane.showConfirmDialog(null, pf, "Ingrese contraseña de administrador",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resp == JOptionPane.OK_OPTION) {
            try {
                String password = new String(pf.getPassword());
                ResultSet result = ConPri.VerificarAdmin();
                String pass="";
                while(result.next())
                {
                    pass=result.getString(1);
                }
                if(password.equals(pass)){
                 paneCorte.remove(panelCajero);
                 paneCorte.add("Corte de caja-Dia",panelDia);  
                }  
                else{
                  JOptionPane.showMessageDialog(null,"Contraseña incorrecta");
                }      
            } catch (SQLException ex) {
            }
        }
    }//GEN-LAST:event_btnCorteDiaActionPerformed

    private void btnTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTurnoActionPerformed
        float monto=Float.parseFloat(lbCaja.getText());
        float entrada=Float.parseFloat(lbE.getText());
        float salida=Float.parseFloat(lbS.getText());
        float total=Float.parseFloat(lbTotalCorte.getText());
        ConCor.corte_temporal(monto,entrada,salida,total);
        ConCor.corte_borrar();
        try {
            MostrarSalida();
            MostrarEntrada();
            MostrarCorteTemporal();
        } catch (SQLException ex) {
            Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
        }
        lbCaja.setText("0.00");
        lbE.setText("0.00");
        lbS.setText("0.00");
        lbTotalCorte.setText("0.00");
        lbSalidas.setText("0.00");
        lbEntradas.setText("0.00");
        
        
    }//GEN-LAST:event_btnTurnoActionPerformed

    private void btnDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiaActionPerformed
        // TODO add your handling code here:
        String horas="";
        String fecha="";
        Date date = new Date();
        DateFormat h = new SimpleDateFormat("HH:mm");     
        DateFormat f = new SimpleDateFormat("yyyy/MM/dd");       
        fecha=f.format(date);
        horas=h.format(date);
        String tipo;
        float total=Float.parseFloat(lbTotalCorte1.getText());
        if(total>0){
            tipo="Ganancia";
        }else{
            tipo="Perdida";
        }
         JOptionPane.showMessageDialog(null, ConCor.corte_final(lbUsuario.getText(), fecha, horas, tipo, total));
         ConCor.CorteTemporalBorrar();
        try {
            MostrarCorteTemporal();
            CarcularCorteFinal();
        } catch (SQLException ex) {
            Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDiaActionPerformed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
            // TODO add your handling code here:
        try {
            VistaCorte Corte =new VistaCorte();
            Corte.setVisible(true);
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_btnReporteActionPerformed

    private void Cb_dolarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cb_dolarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Cb_dolarActionPerformed

    private void Cb_dolarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Cb_dolarFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_Cb_dolarFocusLost

    private void Cb_dolarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Cb_dolarItemStateChanged
        // TODO add your handling code here:
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.UP);
        if(!ban){
            TotalCobro=Double.parseDouble(lbCobroFinal.getText());
            ban=true;
        }
        if(Cb_dolar.isSelected()){
            Double TotalDolar = TotalCobro/dolar;
            lbCobroFinal.setText(df.format(TotalDolar));
        }
        else{
            lbCobroFinal.setText(TotalCobro+"0");
        }
    }//GEN-LAST:event_Cb_dolarItemStateChanged

    private void txtHayFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHayFocusLost
        // TODO add your handling code here:
        try{
            if(ValidarPositivos(Integer.parseInt(txtHay.getText())))
                txtHay.setText("");
        }catch(NumberFormatException ex){
        }
    }//GEN-LAST:event_txtHayFocusLost

    private void txtMinimoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMinimoFocusLost
        // TODO add your handling code here:
        try{
            if(ValidarPositivos(Integer.parseInt(txtMinimo.getText())))
                txtMinimo.setText("");
        }catch(NumberFormatException ex){
        }
    }//GEN-LAST:event_txtMinimoFocusLost

    private void txtHayKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHayKeyTyped
        // TODO add your handling code here:
        ValidarLetras(evt);
    }//GEN-LAST:event_txtHayKeyTyped

    private void txtMinimoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMinimoKeyTyped
        // TODO add your handling code here:
        ValidarLetras(evt);
    }//GEN-LAST:event_txtMinimoKeyTyped

    private void txtMinimoInvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMinimoInvFocusLost
        // TODO add your handling code here:
        try{
            if(ValidarPositivos(Integer.parseInt(txtMinimoInv.getText())))
                txtMinimoInv.setText("");
        }catch(NumberFormatException ex){
        }
    }//GEN-LAST:event_txtMinimoInvFocusLost

    private void btnModificarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarUsuarioActionPerformed
        // TODO add your handling code here:
        txtUsuarioNuevo.setEditable(false);
        BanderaUsuario = true;
        BanderaModificarUsuario = true;
        int fila = tablaUsuarios.getSelectedRow();
        if(fila >= 0){
            try {
                txtUsuarioNuevo.setText(tablaUsuarios.getValueAt(fila, 1).toString());
                txtNombreCompleto.setText(tablaUsuarios.getValueAt(fila, 2).toString());
                ResultSet result = ConPri.Permisos(txtUsuarioNuevo.getText());
                while(result.next()){
                    boxVentas.setSelected(result.getBoolean(1));
                    boxProductos.setSelected(result.getBoolean(2));
                    boxCompras.setSelected(result.getBoolean(3));
                    boxCorte.setSelected(result.getBoolean(4));
                    boxConfiguracion.setSelected(result.getBoolean(5));
                }
            } catch (SQLException ex) {
            }
        }
        else
            JOptionPane.showMessageDialog(null, "No se selecciono ningun usuario");
    }//GEN-LAST:event_btnModificarUsuarioActionPerformed

    private void boxCorteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxCorteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxCorteActionPerformed

    private void txtUsuarioNuevoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsuarioNuevoFocusLost
            // TODO add your handling code here:
            if(!BanderaUsuario)
                try{
                    ResultSet result = ConPri.BuscarUsuario(txtUsuarioNuevo.getText());
                    String usuario = null;
                    while(result.next()){
                        usuario = result.getString(1);
                    }
                    if(usuario != null)
                    {
                        System.out.println(usuario);
                        JOptionPane.showMessageDialog(null, "Ese nombre de usuario ya existe");
                        txtUsuarioNuevo.setText("");
                    }
                }catch (SQLException ex) {
                }
    }//GEN-LAST:event_txtUsuarioNuevoFocusLost

    private void llenarCombo(){
       //Esta funcion sirve para llenar el componente de departamentos con los registros de la base de datos
        try{
            boxDepartamento.removeAllItems();
          
            ResultSet result=ConDep.Llenar();
            while(result.next())
            {
                boxDepartamento.addItem(result.getString(2));
              
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    public void agregarDepartamento() throws SQLException{
        int Ultimo=tablaDepartamentos.getRowCount();
        System.out.println(Ultimo);
        JOptionPane.showMessageDialog(null, ConDep.RegistrarDepartamento(Ultimo, txtNuevoDepartamento.getText()));
        MostrarTablaDepartamentos();
    }
    private void MostrarTablaProductos() throws SQLException{
        //Esta funcion sirve para traer los datos de la base de datos a la tabla
        tablaProductos.setModel(ConPri.MostrarTablaProductos());
    }
    private void MostrarTablaProductos2() throws SQLException{
        //Esta funcion sirve para traer los datos de la base de datos a la tabla
        tablaReporteProductos.setModel(ConPri.MostrarTabla2(txtBuscarArt));
    }
    private void MostrarTablaReporteInv() throws SQLException{
        //Esta funcion sirve para traer los datos de la base de datos a la tabla
        tablaReporteInv.setModel(ConPri.MostrarTablaReporteInv());
    }
    private void MostrarTablaInvBajo() throws SQLException{
        //Esta funcion sirve para traer los datos de la base de datos a la tabla
        tablaInvBajo.setModel(ConPri.MostrarTablaInvBajo());
    }
    private void MostrarTablaDepartamentos() throws SQLException{
        //Esta funcion sirve para traer los datos de la base de datos a la tabla
        tablaDepartamentos.setModel(ConPri.MostrarTablaDepartamentos());
    }
    private void MostrarTablaMovimientos() throws SQLException{
        //Esta funcion sirve para traer los datos de la base de datos a la tabla
        tablaMovientos.setModel(ConPri.MostrarTablaMov());
    }
    private void MostrarSalida() throws SQLException{
        //Esta funcion sirve para traer los datos de la base de datos a la tabla
        tablaEntradas.setModel(ConPri.MostrarSalida());
    }
    private void MostrarEntrada() throws SQLException{
        //Esta funcion sirve para traer los datos de la base de datos a la tabla
        tablaSalidas.setModel(ConPri.MostrarEntrada());
    }
    private void MostrarCorteTemporal() throws SQLException{
        //Esta funcion sirve para traer los datos de la base de datos a la tabla
        tablaCorteTemporal.setModel(ConPri.MostrarTemporal());
    }
    private void MostrarTablaUsuarios() throws SQLException{
        //Esta funcion sirve para traer los datos de la base de datos a la tabla
        tablaUsuarios.setModel(ConPri.MostrarTablaUsuarios());
    }
    private void MostrarTablaVentas(int Id) throws SQLException{
        //Esta funcion sirve para traer los datos de la base de datos a la tabla
        DefaultTableModel modeloinsert = (DefaultTableModel) tablaVentas.getModel();
        Vector <String> Fila=ConPri.MostrarTablaVentas(Id);
        modeloinsert.insertRow(ii, Fila);
        
        lbCobroFinal.setText(""+ConPri.Total(Float.parseFloat(lbCobroFinal.getText()), Float.parseFloat(Fila.elementAt(3)))+"0");
//        float x=ConPri.Total(Float.parseFloat(lbCobroFinal.getText()), Float.parseFloat(Fila.elementAt(3)));
//        float y=Float.parseFloat(Fila.elementAt(2));
//        float z= x*y;
//        System.out.println("CANTIDAD "+y);
//        System.out.println("PRECIO "+x);
//        System.out.println("Total "+z);
//        String a= Float.toString(z);
//        lbCobroFinal.setText(a);
        ii++;
        if(ii<18)
            modeloinsert.removeRow(17);
    }
    private void limpiarReg(){
        //Se limpia los espacios de el registro de productos
            txtId.setText("");
            txtDescripcion.setText("");
            txtPrecioCosto.setText("");
            txtPrecioVenta.setText("");
            boxDepartamento.setSelectedItem("- Sin Departamento -");
            txtHay.setText("");
            txtMinimo.setText("");
            grupo.clearSelection();
            boxInventario.setSelected(false);
            panelInventarioReg.setVisible(false);
    }
    
    private void validarReg(){
        //Valida que los datos del registro de productos no esten vacios
        if(boxInventario.isSelected()){
            if(txtId.getText().equals("") || txtDescripcion.getText().equals("")||txtPrecioCosto.getText().equals("")|| txtPrecioVenta.getText().equals("")|| txtHay.getText().equals("")|| txtMinimo.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Debe de capturar los datos");
                lbVerificacion.setVisible(false);
            }
        }else{
            if(txtId.getText().equals("") || txtDescripcion.getText().equals("")||txtPrecioCosto.getText().equals("")|| txtPrecioVenta.getText().equals(""))
            JOptionPane.showMessageDialog(null,"Debe de capturar los datos");
            lbVerificacion.setVisible(false);
        }
       if(radioUnidad.isSelected()==false && radioGranel.isSelected()==false && radioPaquete.isSelected()==false){
            JOptionPane.showMessageDialog(null,"Debe de seleccionar el tipo de venta");
       }
    }
    public void nombre(String Usuario) throws SQLException
    {
        lbUsuario.setText(Usuario);
    }
    public void borrarInv(String Id){
        int fila=tablaProductos.getSelectedRow();
        JOptionPane.showMessageDialog(null, ConInv.BorrarInventario(fila, Id));
    }
    public void regInvTabla() throws SQLException{
        //En esta funcion sirve para registrar una venta de productos
        int id=Integer.parseInt(lblCodigoProducto.getText());
        int Totallizacion=(Integer.parseInt(txtExistenciaInv.getText()))+(Integer.parseInt(txtNuevaCantidad.getText()));
        int minimo=Integer.parseInt(txtMinimoInv.getText());
        JOptionPane.showMessageDialog(null, ConInv.ModificarInventario(id,Totallizacion, minimo));
        MostrarTablaReporteInv();
        MostrarTablaInvBajo();
        lblCodigoProducto.setText("");
        txtIdInventarioB.setText("");
        txtPrecioCostoInv.setText("");
        txtPrecioVentaInv.setText("");
        lblDes.setText("Descripcion del Producto");
        txtExistenciaInv.setText("");
        txtMinimoInv.setText("");
        txtNuevaCantidad.setText("");
        btnBuscaId.setEnabled(true);
        txtIdInventarioB.setEditable(true);
        txtPrecioCostoInv.setEditable(true);
        txtPrecioVentaInv.setEditable(true);
        txtExistenciaInv.setEditable(true);
        txtMinimoInv.setEditable(true);
    }
    public void regInventario(int Id, int Existencia, int Minimo) throws SQLException{
        JOptionPane.showMessageDialog(null, ConInv.RegistrarInventario(Id, Existencia, Minimo));
        MostrarTablaReporteInv();
        MostrarTablaInvBajo();
        
    }
    public void regInventarioVacio(int Id2, int Existencia2, int Minimo2) throws SQLException{
        JOptionPane.showMessageDialog(null, ConInv.RegistrarInventario(Id2, Existencia2, Minimo2));
        MostrarTablaReporteInv();
        MostrarTablaInvBajo();
        
    }
    public void regMovimientoEntrada(){
        int fila=tablaVentas.getSelectedRow();
        String resultado="";
        Date date = new Date();
        DateFormat horas = new SimpleDateFormat("HH:mm");     
        DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");       
        String f=fecha.format(date);
        String h=horas.format(date);
        try{
            String sql="INSERT INTO `movimientos`(`fecha`, `hora`, `descripcion`, `habia`, `tipo`, `cantidad`, `existencia`) VALUES(?,?,?,?,?,?,?)";
            con =DriverManager.getConnection("jdbc:mysql://localhost/DigoV?SSL=FALSE","root","password");
            pst=con.prepareStatement(sql);
            pst.setString(1,f);
            pst.setString(2,h);
            pst.setString(3,lblDes.getText());
            pst.setString(4,txtExistenciaInv.getText());
            pst.setString(5,"Entrada");
            pst.setString(6,txtNuevaCantidad.getText());
            int aux1=Integer.parseInt(txtNuevaCantidad.getText());
            int aux2=Integer.parseInt(txtExistenciaInv.getText());
            int res=aux1+aux2;
            resultado=Integer.toString(res);
          
            pst.setString(7,resultado);
            

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Se registro el movimiento");
             MostrarTablaMovimientos();

        }catch(MySQLIntegrityConstraintViolationException ex){
            JOptionPane.showMessageDialog(null,"El ID ya existe ingrese otro");

        }
        catch(HeadlessException | SQLException e){
           JOptionPane.showMessageDialog(null,""+e);

        }
    }
    public void regMovimientoSalida(){
        int fila=tablaVentas.getSelectedRow();
        String resultado="";
        Date date = new Date();
        DateFormat horas = new SimpleDateFormat("HH:mm");     
        DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");       
        String f=fecha.format(date);
        String h=horas.format(date);
        try{
            String sql="INSERT INTO `movimientos`(`fecha`, `hora`, `descripcion`, `habia`, `tipo`, `cantidad`, `existencia`) VALUES (?,?,?,?,?,?,?)";
            con2 =DriverManager.getConnection("jdbc:mysql://localhost/DigoV?SSL=FALSE","root","password");
            pst2=con2.prepareStatement(sql);
            pst2.setString(1,f);
            pst2.setString(2,h);
            pst2.setString(3,tablaVentas.getValueAt(fila,1).toString());
            pst2.setString(4,"1");
            pst2.setString(5,"Salida");
            pst2.setString(6,"1");
//            int aux1=Integer.parseInt(txtNuevoHay.getText());
//            int aux2=Integer.parseInt(txtHayInv.getText());
//            int res=aux1+aux2;
//            resultado=Integer.toString(res);
//          
            pst2.setString(7,"1");
            

            pst2.executeUpdate();
            JOptionPane.showMessageDialog(null,"Se registro el movimiento");
             MostrarTablaMovimientos();

        }catch(MySQLIntegrityConstraintViolationException ex){
            JOptionPane.showMessageDialog(null,"El ID ya existe ingrese otro");

        }
        catch(HeadlessException | SQLException e){
           JOptionPane.showMessageDialog(null,""+e);

        }
    }
    public void Permisos() throws SQLException
    {
        ResultSet result= ConPri.Permisos(lbUsuario.getText());
        while(result.next()){
            btnVenta.setEnabled(result.getBoolean(1));
            btnProductos.setEnabled(result.getBoolean(2));
            btnInventario.setEnabled(result.getBoolean(3));
            btnCorte.setEnabled(true);
            //btnCorteDia.setEnabled(rs.getBoolean(4));
            btnConfiguracion.setEnabled(result.getBoolean(5));
        }
    }
    private void cargar() throws IOException{
        BufferedImage img=null;
        ResultSet result=ConPri.visualizarLogo();
        try {
            if(result.next()){
                Blob blob= result.getBlob(2);
                byte[] data=blob.getBytes(1,(int)blob.length());
                try{
                    img=ImageIO.read(new ByteArrayInputStream(data));
                }catch(IOException ex){
                }
                
            }  
        } catch (SQLException ex) {
            Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
        }
        ImageIcon icono=new ImageIcon(img);
        lbFoto.setIcon(icono);
    }
    private void calcular_corte(){
        float acum=0;
        float acum2=0;
        float t,e,s,c;
        for (int cont = 0; cont < tablaEntradas.getRowCount(); cont++) {
            acum=acum+Float.parseFloat(tablaEntradas.getValueAt(cont, 3)+"");
        }
        lbSalidas.setText(acum+"");
        
         for (int cont2 = 0; cont2 < tablaSalidas.getRowCount(); cont2++) {
            acum2=acum2+Float.parseFloat(tablaSalidas.getValueAt(cont2, 3)+"");
        }
        lbEntradas.setText(acum2+"");
        lbCaja.setText(cajaString);
        lbE.setText(lbEntradas.getText());
        lbS.setText(lbSalidas.getText());
        e=Float.parseFloat(lbE.getText());
        s=Float.parseFloat(lbS.getText());
        c=Float.parseFloat(lbCaja.getText());
        lbTotalCorte.setText((c+e-s)+"");
        
    }
    private void CarcularCorteFinal(){
        float acum=0;
        float acum2=0;
        float acum3=0;
        float t,e,s,c;
        for (int cont = 0; cont < tablaCorteTemporal.getRowCount(); cont++) {
            acum=acum+Float.parseFloat(tablaCorteTemporal.getValueAt(cont, 3)+"");
        }
        for (int cont2 = 0; cont2 < tablaCorteTemporal.getRowCount(); cont2++) {
            acum2=acum2+Float.parseFloat(tablaCorteTemporal.getValueAt(cont2, 1)+"");
        }
        for (int cont3 = 0; cont3 < tablaCorteTemporal.getRowCount(); cont3++) {
            acum3=acum3+Float.parseFloat(tablaCorteTemporal.getValueAt(cont3, 2)+"");
        }
        
        lbTotalVentas.setText(acum+"");
        
        
       
        lbE1.setText(acum2+"");
        lbS1.setText(acum3+"");
        e=Float.parseFloat(lbE1.getText());
        s=Float.parseFloat(lbS1.getText());
        c=Float.parseFloat(lbCaja.getText());
        lbTotalCorte1.setText((c+e-s)+"");
    }
    public void ExportarExcel(JTable t) throws IOException{
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String ruta = chooser.getSelectedFile().toString().concat(".xls");
            try {
                File archivoXLS = new File(ruta);
                if (archivoXLS.exists()) {
                    archivoXLS.delete();
                }
                archivoXLS.createNewFile();
                Workbook libro = new HSSFWorkbook();
                try (FileOutputStream archivo = new FileOutputStream(archivoXLS)) {
                    Sheet hoja = libro.createSheet("Mi hoja de trabajo 1");
                    hoja.setDisplayGridlines(false);
                    for (int f = 0; f < t.getRowCount(); f++) {
                        Row fila = hoja.createRow(f);
                        for (int c = 0; c < t.getColumnCount(); c++) {
                            Cell celda = fila.createCell(c);
                            if (f == 0) {
                                celda.setCellValue(t.getColumnName(c));
                            }
                        }
                    }
                    int filaInicio = 1;
                    for (int f = 0; f < t.getRowCount(); f++) {
                        Row fila = hoja.createRow(filaInicio);
                        filaInicio++;
                        for (int c = 0; c < t.getColumnCount(); c++) {
                            Cell celda = fila.createCell(c);
                            if (t.getValueAt(f, c) instanceof Double) {
                                celda.setCellValue(Double.parseDouble(t.getValueAt(f, c).toString()));
                            } else if (t.getValueAt(f, c) instanceof Float) {
                                celda.setCellValue(Float.parseFloat((String) t.getValueAt(f, c)));
                            } else {
                                celda.setCellValue(String.valueOf(t.getValueAt(f, c)));
                            }
                        }
                    }
                    libro.write(archivo);
                }
                Desktop.getDesktop().open(archivoXLS);
            } catch (IOException | NumberFormatException e) {
            }
        }
    }
    public boolean ValidarPositivos(int num)
    {
        if(num <= 0){
            JOptionPane.showMessageDialog(null,"No se permite ingresar numeros negativos","Ventana Error Datos",JOptionPane.ERROR_MESSAGE);
            return true;
        }
        else
            return false;
    }
    public boolean ValidarPositivos(Float num)
    {
        if(num <= 0){
            JOptionPane.showMessageDialog(null,"No se permite ingresar numeros negativos","Ventana Error Datos",JOptionPane.ERROR_MESSAGE);
            return true;
        }
        else
            return false;
    }
    public boolean ValidarPositivos(Double num)
    {
        if(num <= 0){
            JOptionPane.showMessageDialog(null,"No se permite ingresar numeros negativos","Ventana Error Datos",JOptionPane.ERROR_MESSAGE);
            return true;
        }
        else
            return false;
    }
    public boolean ValidarLetras(java.awt.event.KeyEvent evt)
    {
        int k=(int)evt.getKeyChar();
        if (k >= 97 && k <= 122 || k>=65 && k<=90 || k==241 || k==209){
            evt.setKeyChar((char)KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(null,"No se permite ingresar letras","Ventana Error Datos",JOptionPane.ERROR_MESSAGE);
            return true;
        }
        else
            return false;
    }
    private void DineroDolar()
    {
        try{
            dolarString = JOptionPane.showInputDialog(null, "¿Cual es el valor del dolar?", "Tipo de cambio", JOptionPane.PLAIN_MESSAGE);
            if(!ValidarPositivos(Double.parseDouble(dolarString)))
                dolar=Double.parseDouble(dolarString);
            else
                DineroDolar();
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Escriba numeros", "Error numerico", JOptionPane.ERROR_MESSAGE);
            DineroDolar();
        }
    }
    private void DineroCaja()
    {
        try{
            cajaString = JOptionPane.showInputDialog(null, "¿Cuanto dinero hay en caja?", "Fondo de caja", JOptionPane.PLAIN_MESSAGE);
            if(!ValidarPositivos(Double.parseDouble(cajaString)))
                caja=Double.parseDouble(cajaString);
            else
                DineroCaja();
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Escriba numeros", "Error numerico", JOptionPane.ERROR_MESSAGE);
            DineroCaja();
        }
    }
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaDigo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new VistaDigo().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(VistaDigo.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox Cb_dolar;
    private javax.swing.JCheckBox boxCompras;
    private javax.swing.JCheckBox boxConfiguracion;
    private javax.swing.JCheckBox boxCorte;
    private javax.swing.JComboBox<String> boxDepartamento;
    private javax.swing.JCheckBox boxInventario;
    private javax.swing.JCheckBox boxProductos;
    private javax.swing.JCheckBox boxVentas;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscaId;
    private javax.swing.JButton btnBuscarProducto;
    private javax.swing.JButton btnCancelarUsuario;
    private javax.swing.JButton btnCargarImagen2;
    private javax.swing.JButton btnCobrar;
    private javax.swing.JButton btnConfiUsuarios;
    private javax.swing.JButton btnConfiguracion;
    private javax.swing.JButton btnCorte;
    private javax.swing.JButton btnCorteCajero;
    private javax.swing.JButton btnCorteDia;
    private javax.swing.JButton btnDia;
    private javax.swing.JButton btnDineroCaja;
    private javax.swing.JButton btnElimProducto;
    private javax.swing.JButton btnEliminarDepartamento;
    private javax.swing.JButton btnEliminarInv;
    private javax.swing.JButton btnEliminarUsuario;
    private javax.swing.JButton btnEliminarVenta;
    private javax.swing.JButton btnGuardarImagen;
    private javax.swing.JButton btnGuardarProducto;
    private javax.swing.JButton btnGuardarUsuario;
    private javax.swing.JButton btnIdAgregar;
    private javax.swing.JButton btnImpuestos;
    private javax.swing.JButton btnInventario;
    private javax.swing.JButton btnLimpiarImagen;
    private javax.swing.JButton btnLogotipo;
    private javax.swing.JButton btnMas;
    private javax.swing.JButton btnMenos;
    private javax.swing.JButton btnModProducto;
    private javax.swing.JButton btnModificarUsuario;
    private javax.swing.JButton btnNuevoDepartamento;
    private javax.swing.JButton btnPrecioDolar;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnRegInventario;
    private javax.swing.JButton btnRegistrarInv;
    private javax.swing.JButton btnReporte;
    private javax.swing.JButton btnRespaldoBD;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnTurno;
    private javax.swing.JButton btnVenta;
    private javax.swing.JLabel fotoT;
    private javax.swing.ButtonGroup grupo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lbAgregarCantidad;
    private javax.swing.JLabel lbBusquedaFiltrada;
    private javax.swing.JLabel lbCaja;
    private javax.swing.JLabel lbCobroFinal;
    private javax.swing.JLabel lbCodigoVenta;
    private javax.swing.JLabel lbContraseña;
    private javax.swing.JLabel lbDepartamento;
    private javax.swing.JLabel lbDepartamentoNuevo;
    private javax.swing.JLabel lbDescripcion;
    private javax.swing.JLabel lbDescripcionInv;
    private javax.swing.JLabel lbE;
    private javax.swing.JLabel lbE1;
    private javax.swing.JLabel lbEntradas;
    private javax.swing.JLabel lbExistencia;
    private javax.swing.JLabel lbExistenciaActualInv;
    private javax.swing.JLabel lbFecha;
    private javax.swing.JLabel lbFoto;
    private javax.swing.JLabel lbGanancia;
    private javax.swing.JLabel lbHora;
    private javax.swing.JLabel lbIdProducto;
    private javax.swing.JLabel lbInvBusqueda;
    private javax.swing.JLabel lbMinimo;
    private javax.swing.JLabel lbMinimoB;
    private javax.swing.JLabel lbNombreCompleto;
    private javax.swing.JLabel lbPorcentaje;
    private javax.swing.JLabel lbPreVenta;
    private javax.swing.JLabel lbPrecioCostoA;
    private javax.swing.JLabel lbPrecioCostoB;
    private javax.swing.JLabel lbPrecioVentaB;
    private javax.swing.JLabel lbS;
    private javax.swing.JLabel lbS1;
    private javax.swing.JLabel lbSalidas;
    private javax.swing.JLabel lbSignoPesos;
    private javax.swing.JLabel lbTotalCorte;
    private javax.swing.JLabel lbTotalCorte1;
    private javax.swing.JLabel lbTotalVentas;
    private javax.swing.JLabel lbUsuario;
    private javax.swing.JLabel lbUsuarioNuevo;
    private javax.swing.JLabel lbVentaPor;
    private javax.swing.JLabel lbVerificacion;
    private javax.swing.JLabel lblCodigoProducto;
    private javax.swing.JLabel lblDes;
    private javax.swing.JTabbedPane paneConfiguracion;
    private javax.swing.JTabbedPane paneCorte;
    private javax.swing.JTabbedPane panePrincipal;
    private javax.swing.JPanel panelAdmiUsuarios;
    private javax.swing.JPanel panelCajero;
    private javax.swing.JPanel panelCatalogoPro;
    private javax.swing.JPanel panelConfiguracion;
    private javax.swing.JPanel panelCorte;
    private javax.swing.JPanel panelDepartamentos;
    private javax.swing.JPanel panelDia;
    private javax.swing.JPanel panelInvBajo;
    private javax.swing.JPanel panelInventario;
    private javax.swing.JPanel panelInventarioReg;
    private javax.swing.JPanel panelLogotipo;
    private javax.swing.JPanel panelMov;
    private javax.swing.JPanel panelProductos;
    private javax.swing.JPanel panelVentas;
    private javax.swing.JRadioButton radioGranel;
    private javax.swing.JRadioButton radioPaquete;
    private javax.swing.JRadioButton radioUnidad;
    private javax.swing.JSpinner spinnerGanancia;
    private javax.swing.JTable tablaCorteTemporal;
    private javax.swing.JTable tablaDepartamentos;
    private javax.swing.JTable tablaEntradas;
    private javax.swing.JTable tablaInvBajo;
    private javax.swing.JTable tablaMovientos;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTable tablaReporteInv;
    private javax.swing.JTable tablaReporteProductos;
    private javax.swing.JTable tablaSalidas;
    private javax.swing.JTable tablaUsuarios;
    private javax.swing.JTable tablaVentas;
    private javax.swing.JTextField txtBuscarArt;
    private javax.swing.JPasswordField txtContraseñaNueva;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtExistenciaInv;
    private javax.swing.JTextField txtHay;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIdInventarioB;
    private javax.swing.JTextField txtIdVenta;
    private javax.swing.JTextField txtMinimo;
    private javax.swing.JTextField txtMinimoInv;
    private javax.swing.JTextField txtNombreCompleto;
    private javax.swing.JTextField txtNuevaCantidad;
    private javax.swing.JTextField txtNuevoDepartamento;
    private javax.swing.JTextField txtPrecioCosto;
    private javax.swing.JTextField txtPrecioCostoInv;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JTextField txtPrecioVentaInv;
    private javax.swing.JTextField txtUsuarioNuevo;
    // End of variables declaration//GEN-END:variables
}