package Modelo;
public class DetallesVenta 
{
    private int IdVenta;
    private int IdProducto;
    private String Descripcion;
    private int Cantidad;
    private Float PrecioVenta;

    public DetallesVenta(int IdVenta, int IdProductos, String Descripcion, int Cantidad, Float PrecioVenta) {
        this.IdVenta = IdVenta;
        this.IdProducto = IdProductos;
        this.Descripcion = Descripcion;
        this.Cantidad = Cantidad;
        this.PrecioVenta = PrecioVenta;
    }

    public int getIdVenta() {
        return IdVenta;
    }

    public void setIdVenta(int IdVenta) {
        this.IdVenta = IdVenta;
    }

    public int getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(int IdProducto) {
        this.IdProducto = IdProducto;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public Float getPrecioVenta() {
        return PrecioVenta;
    }

    public void setPrecioVenta(Float PrecioVenta) {
        this.PrecioVenta = PrecioVenta;
    }
    
}
