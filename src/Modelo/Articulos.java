package Modelo;
public class Articulos {
    private int Id;
    private String Descripcion;
    private String TipoVenta;
    private float PrecioCosto;
    private float PrecioVenta;
    private int Departamento;

    public Articulos(int Id, String Descripcion, String TipoVenta, float PrecioCosto, float PrecioVenta, int Departamento) {
        this.Id = Id;
        this.Descripcion = Descripcion;
        this.TipoVenta = TipoVenta;
        this.PrecioCosto = PrecioCosto;
        this.PrecioVenta = PrecioVenta;
        this.Departamento = Departamento;
    }
    public int getId() {
        return Id;
    }
    public void setId(int Id) {
        this.Id = Id;
    }
    public String getDescripcion() {
        return Descripcion;
    }
    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
    public String getTipoVenta() {
        return TipoVenta;
    }
    public void setTipoVenta(String TipoVenta) {
        this.TipoVenta = TipoVenta;
    }
    public float getPrecioCosto() {
        return PrecioCosto;
    }
    public void setPrecioCosto(float PrecioCosto) {
        this.PrecioCosto = PrecioCosto;
    }
    public float getPrecioVenta() {
        return PrecioVenta;
    }
    public void setPrecioVenta(float PrecioVenta) {
        this.PrecioVenta = PrecioVenta;
    }
    public int getDepartamento() {
        return Departamento;
    }
    public void setDepartamento(int Departamento) {
        this.Departamento = Departamento;
    }
}
