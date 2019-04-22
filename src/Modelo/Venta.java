package Modelo;

import java.sql.Timestamp;

public class Venta {
    private int Id;
    private float Total;
    private Timestamp Fecha;
    private String Usuario;

    public Venta(int Id, float Total, Timestamp Fecha, String Usuario) {
        this.Id = Id;
        this.Total = Total;
        this.Fecha = Fecha;
        this.Usuario = Usuario;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float Total) {
        this.Total = Total;
    }

    public Timestamp getFecha() {
        return Fecha;
    }

    public void setFecha(Timestamp Fecha) {
        this.Fecha = Fecha;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }
    
}
