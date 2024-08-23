package Core;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Utils {

    public static Objednavky rm;

    public static Polozka[] GetPrilohy() {
        Polozka[] polozky = GetPolozky();
        ArrayList<Polozka> pol = new ArrayList<>();

        for (Polozka polozka : polozky) {
            if (polozka.GetTyp() == 0)
                pol.add(polozka);
        }

        return pol.toArray(new Polozka[0]);
    }
    public static Polozka[] GetBurgery() {
        Polozka[] polozky = GetPolozky();
        ArrayList<Polozka> pol = new ArrayList<>();

        for (Polozka polozka : polozky) {
            if (polozka.GetTyp() == 1)
                pol.add(polozka);
        }

        return pol.toArray(new Polozka[0]);

        
    }
    public static Polozka[] GetOmacky() {
        Polozka[] polozky = GetPolozky();
        ArrayList<Polozka> pol = new ArrayList<>();

        for (Polozka polozka : polozky) {
            if (polozka.GetTyp() == 2)
                pol.add(polozka);
        }

        return pol.toArray(new Polozka[0]);
    }
    public static Polozka[] GetNapoje() {
        Polozka[] polozky = GetPolozky();
        ArrayList<Polozka> pol = new ArrayList<>();

        for (Polozka polozka : polozky) {
            if (polozka.GetTyp() == 3)
                pol.add(polozka);
        }

        return pol.toArray(new Polozka[0]);
    }
    public static Polozka[] GetPolozky() {

        if (rm == null) {
            try {
                rm = (Objednavky)Naming.lookup("//localhost/RmiServer");
            } catch (MalformedURLException | RemoteException | NotBoundException e) { }
        }

        try {
            return rm.NacistPolozky();
        } catch (RemoteException e) { }
        return null;
    }
    public void SaveOrder(String order) {
        String[] polozky = order.split(",");
        Polozka[] pol = GetPolozky();

        Objednavka obj = new Objednavka(0, Stav.Priprava);

        for (String str : polozky) {
            for (Polozka polozka : pol) {
                if (str.equals(polozka.toString())) {
                    obj.PridatPolozku(polozka);
                } 
            }
        }
        try {
            rm.UlozitObjednavku(obj);
        } catch (RemoteException e) { }
    }
}
