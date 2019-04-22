package Modelo;
public class Departamento 
{
    private int IdDepartamento;
    private String Departamento;

    public Departamento() {
    }
    public Departamento(int IdDepartamento, String Departamento) {
        this.IdDepartamento = IdDepartamento;
        this.Departamento = Departamento;
    }

    public int getIdDepartamento() {
        return IdDepartamento;
    }

    public void setIdDepartamento(int IdDepartamento) {
        this.IdDepartamento = IdDepartamento;
    }

    public String getDepartamento() {
        return Departamento;
    }

    public void setDepartamento(String Departamento) {
        this.Departamento = Departamento;
    }
    
    public int getDepartamento(String Depa)
    {
        switch (Depa) {
            case "Abarrotes":
                return 1;
            case "Lacteos":
            return 2;
            case "Botanas":
            return 3;
            case "Carnes frias":
            return 4;
            case "Cigarros":
            return 5;
            case "Limpieza":
            return 6;
            case "Verduras":
            return 7;
            case "Frutas":
            return 8;
            case "Cerveza":
            return 9;
            case "Panaderia":
            return 10;
            case "Sodas":
            return 11;
            case "Carniceria":
            return 12;
        }
        return 0;
    }
}
