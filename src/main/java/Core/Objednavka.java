package Core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Objednavka implements Serializable {
    private int id;
    private Stav stav;
    private List<Polozka> polozky = new ArrayList<>();  
    
    public Objednavka(int id, Stav stav) {
        this.id = id;
        this.stav = stav;
    }

    public int GetCelkovaCena() {                                                  
        int celkovaCena = 0;
        for (Polozka polozka : polozky) {
            celkovaCena += polozka.GetCena();
        }
        return celkovaCena;
    }
    public void PridatPolozku(Polozka polozka) {                                     
        polozky.add(polozka);
    }
    public int GetPocetPolozek() {
        return polozky.size();
    }
    public int GetId() {
        return id;
    }
    public Stav GetStav() {
        return stav;
    }
    public Polozka[] GetPolozky() {
        return polozky.toArray(new Polozka[0]);
    }
    @Override
    public String toString() {
        String text = "Obj č.: " + id + " - ";
        for (int i = 0; i < polozky.size(); i++) {
            if (i != 0)
                text += ", ";
            text += polozky.get(i).toString();
        }
        text += " - Stav: " + stav;
        return text;
    }
}