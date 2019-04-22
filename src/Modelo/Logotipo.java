
package Modelo;

import java.io.FileInputStream;


public class Logotipo {
   
    private FileInputStream foto;
    private int longuitud;
    
    public Logotipo(FileInputStream foto, int longuitud) {
        this.foto = foto;
        this.longuitud = longuitud;
    }

    public FileInputStream getFoto() {
        return foto;
    }

    public void setFoto(FileInputStream foto) {
        this.foto = foto;
    }

    public int getLonguitud() {
        return longuitud;
    }

    public void setLonguitud(int longuitud) {
        this.longuitud = longuitud;
    }
    
    
    
    
}
