package Core;

import java.io.Serializable;

public class Polozka implements Serializable {
    private int id = -1;
    private String jmeno = "";
    private int cena = -1;
    private int typ = -1;

    public Polozka (int id, String jmeno, int cena, int typ) {        
        this.id = id;                 
        this.jmeno = jmeno;
        this.cena = cena;
        this.typ = typ;
    }                                          
    public int GetCena() {                                            
        return cena;
    }
    public String GetJmeno() {                                       
        return jmeno;
    }
    public int GetId() {
        return id;
    }
    public int GetTyp() {
        return typ;
    }
    @Override
    public String toString() {
        return jmeno;
    }
    public String Debug() {
        return id + "," + jmeno + "," + cena + "," + typ;
    }
}
