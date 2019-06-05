package Modelo;
public class Corte {
    
    private String usuario;
    private String fecha;
    private String hora;
    private String tipo;
    private float total;

    public Corte(String usuario, String fecha, String hora, String tipo, float total) {
       
        this.usuario = usuario;
        this.fecha = fecha;
        this.hora = hora;
        this.tipo = tipo;
        this.total = total;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
    
}
