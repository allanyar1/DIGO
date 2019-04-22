package Modelo;
public class Usuario {
    private String usuario;
    private String contra;
    private String nombreUsuario;
    private boolean venta;
    private boolean productos;
    private boolean compras;
    private boolean corte;
    private boolean configuracion;

    public Usuario(String usuario, String contra, String nombreUsuario, boolean venta, boolean productos, boolean compras, boolean corte, boolean configuracion) {
        this.usuario = usuario;
        this.contra = contra;
        this.nombreUsuario = nombreUsuario;
        this.venta = venta;
        this.productos = productos;
        this.compras = compras;
        this.corte = corte;
        this.configuracion = configuracion;
    }

    public Usuario(String Usuario, String Password) {
        this.usuario = Usuario;
        this.contra = Password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public boolean isVenta() {
        return venta;
    }

    public void setVenta(boolean venta) {
        this.venta = venta;
    }

    public boolean isProductos() {
        return productos;
    }

    public void setProductos(boolean productos) {
        this.productos = productos;
    }

    public boolean isCompras() {
        return compras;
    }

    public void setCompras(boolean compras) {
        this.compras = compras;
    }

    public boolean isCorte() {
        return corte;
    }

    public void setCorte(boolean corte) {
        this.corte = corte;
    }

    public boolean isConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(boolean configuracion) {
        this.configuracion = configuracion;
    }
    
   
    
}