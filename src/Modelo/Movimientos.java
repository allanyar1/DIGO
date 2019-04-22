/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;

/**
 *
 * @author allan
 */
public class Movimientos {
  
    private String fecha;
    private String hora;
    private String descripcion;
    private int habia;
    private String tipo;
    private int cantidad;
    private int existencia;

    public Movimientos(String fecha, String hora, String descripcion, int habia, String tipo, int cantidad, int existencia) {
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.habia = habia;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.existencia = existencia;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getHabia() {
        return habia;
    }

    public void setHabia(int habia) {
        this.habia = habia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }
    
    
    
}
