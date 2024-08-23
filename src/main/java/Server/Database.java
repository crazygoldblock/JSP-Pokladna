package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Core.Objednavka;
import Core.Polozka;
import Core.Stav;

public class Database {
    private Connection connection;
    private PreparedStatement prepUlozitObjednavku;
    private PreparedStatement prepPridatPolozkuObjednavky;
    private PreparedStatement prepPridatPolozku;

    private PreparedStatement prepOdstranitObjednavku;
    private PreparedStatement prepOdstranitPolozku;

    private PreparedStatement prepSelectPolozky;
    private PreparedStatement prepSelectPolozkyObjednavek;

    private PreparedStatement prepUpdateStav;

    private PreparedStatement preGetStavObjednavky;

    private static Database database = new Database();

    private Database() {
        Inicializace();
    }

    public static Database GetInstance() {
        return database;
    }
    public void Inicializace() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurace", "root", "");

            prepUlozitObjednavku = connection.prepareStatement("INSERT INTO objednavky () VALUES ()", PreparedStatement.RETURN_GENERATED_KEYS);
            prepPridatPolozkuObjednavky = connection.prepareStatement("INSERT INTO objednavky_polozky (objednavky_id, polozky_id) VALUES (?, ?)");
            prepPridatPolozku = connection.prepareStatement("INSERT INTO polozky (jmeno, cena, typ) VALUES (?, ?, ?)");

            prepOdstranitObjednavku = connection.prepareStatement("DELETE FROM objednavky WHERE id=?");
            prepOdstranitPolozku = connection.prepareStatement("DELETE FROM polozky WHERE id=?");

            prepSelectPolozky = connection.prepareStatement("SELECT * FROM polozky;");
            prepSelectPolozkyObjednavek = connection.prepareStatement("SELECT objednavky_id, polozky_id FROM objednavky_polozky ORDER BY id ASC;");

            prepUpdateStav = connection.prepareStatement("UPDATE objednavky SET stav = 2 WHERE id = ?");

            preGetStavObjednavky = connection.prepareStatement("SELECT stav FROM objednavky WHERE id = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void Ukoncit() {
        try {
            prepUlozitObjednavku.close();
            prepPridatPolozkuObjednavky.close();
            prepOdstranitObjednavku.close();
            prepPridatPolozku.close();
            prepOdstranitPolozku.close();
            prepSelectPolozky.close();
            prepSelectPolozkyObjednavek.close();
            prepUpdateStav.close();
            connection.close();
        } catch (SQLException e) { }
    }
    public Polozka[] NacistPolozky() {
        List<Polozka> list = new ArrayList<>();
        try (ResultSet result = prepSelectPolozky.executeQuery();) {
            while(result.next()) {
                Polozka polozka = new Polozka(result.getInt(1), result.getString(2), result.getInt(3), result.getInt(4));
                list.add(polozka);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.toArray(new Polozka[0]);
    }
    public void UlozitObjednavku(Objednavka o) {
        try {
            prepUlozitObjednavku.executeUpdate();

            ResultSet result = prepUlozitObjednavku.getGeneratedKeys();
            result.next();

            int id = result.getInt(1);

            result.close();

            for (Polozka polozka : o.GetPolozky()) {
                prepPridatPolozkuObjednavky.setInt(1, id);
                prepPridatPolozkuObjednavky.setInt(2, polozka.GetId());

                prepPridatPolozkuObjednavky.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Objednavka[] NacistObjednavky() {

        HashMap<Integer, Objednavka> objednavky = new HashMap<>();

        HashMap<Integer, Polozka> polozky = new HashMap<>();

        Polozka[] polozkyArray = NacistPolozky();

        for (Polozka polozka : polozkyArray) {
            polozky.put(polozka.GetId(), polozka);
        }

        try (ResultSet result = prepSelectPolozkyObjednavek.executeQuery();) {
            while(result.next()) {
                int objId = result.getInt(1);

                Objednavka obj = objednavky.get(objId);

                Polozka pol = polozky.get(result.getInt(2));

                if (obj == null) {
                    Objednavka nova = new Objednavka(objId, GetStavObjednavky(objId));

                    objednavky.put(objId, nova);

                    nova.PridatPolozku(pol);
                }
                else {
                    obj.PridatPolozku(pol);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Objednavka[] objednavkyArray = new Objednavka[objednavky.size()];

        int i = 0;
        for (Map.Entry<Integer, Objednavka> objednavka : objednavky.entrySet()) {
            objednavkyArray[i] = objednavka.getValue();
            i++;
        }

        return objednavkyArray;
    }
    public Stav GetStavObjednavky(int id) {
        try {
            preGetStavObjednavky.setInt(1, id);

            ResultSet result = preGetStavObjednavky.executeQuery();

            result.next();

            return Stav.GetFromCode(result.getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Stav.GetFromCode(-1);
    }
    public void OdstranitObjednavku(int id) {
        try {
            prepOdstranitObjednavku.setInt(1, id);

            prepOdstranitObjednavku.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void PridatPolozku(Polozka p) {
        try {
            prepPridatPolozku.setString(1, p.GetJmeno());
            prepPridatPolozku.setInt(2, p.GetCena());
            prepPridatPolozku.setInt(3, p.GetTyp());

            prepPridatPolozku.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void OdebratPolozku(int id) {
        try {
            prepOdstranitPolozku.setInt(1, id);

            prepOdstranitPolozku.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void NastavitObjednavkuDokoncenou(int id) {
        try {
            prepUpdateStav.setInt(1, id);

            prepUpdateStav.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Objednavka[] NacistDokonceneObjednavky() {
        Objednavka[] objednavky = NacistObjednavky();

        ArrayList<Objednavka> list = new ArrayList<>();

        for (Objednavka objednavka : objednavky) {
            if (objednavka.GetStav() == Stav.Dokonceno)
                list.add(objednavka);
        }
        return list.toArray(new Objednavka[0]);
    }
    public Objednavka[] NacistPripravovaneObjednavky() {
        Objednavka[] objednavky = NacistObjednavky();

        ArrayList<Objednavka> list = new ArrayList<>();

        for (Objednavka objednavka : objednavky) {
            if (objednavka.GetStav() == Stav.Priprava)
                list.add(objednavka);
        }
        return list.toArray(new Objednavka[0]);
    }
    
}