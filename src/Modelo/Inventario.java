package Modelo;
public class Inventario 
{
    private int Id;
    private int Existencia;
    private int Minimo;

    public Inventario(int Id, int Existencia, int Minimo) {
        this.Id = Id;
        this.Existencia = Existencia;
        this.Minimo = Minimo;
    }
    public int getId() {
        return Id;
    }
    public void setId(int Id) {
        this.Id = Id;
    }
    public int getExistencia() {
        return Existencia;
    }
    public void setExistencia(int Existencia) {
        this.Existencia = Existencia;
    }
    public int getMinimo() {
        return Minimo;
    }
    public void setMinimo(int Minimo) {
        this.Minimo = Minimo;
    }
}