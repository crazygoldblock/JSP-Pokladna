package Server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import Core.Objednavka;
import Core.Objednavky;
import Core.Polozka;
import Core.RmiObjednavky;

public class Server extends UnicastRemoteObject implements Objednavky{

    Database database = Database.GetInstance();

    RmiObjednavky pr = null;
    RmiObjednavky pr2 = null;

    protected Server() throws RemoteException {
        super(0);

        database.Inicializace();
    }
    private void Rmi() {
        while (pr == null) {
            try {
                pr = (RmiObjednavky)Naming.lookup("//localhost/Priprava");
            } catch (Exception e) {
                pr = null;
            } 
        }
    
        while (pr2 == null) {
            try {
                pr2 = (RmiObjednavky)Naming.lookup("//localhost/Vydej");
            } catch (Exception e) {
                pr2 = null;
            } 
        }
    }

    @Override
    public Polozka[] NacistPolozky() throws RemoteException {
        return database.NacistPolozky();
    }

    @Override
    public void UlozitObjednavku(Objednavka objednavka) throws RemoteException {
        database.UlozitObjednavku(objednavka);
        pr.PridaniObjednavky(objednavka);
    }

    @Override
    public Objednavka[] NacistObjednavky() throws RemoteException {
        return database.NacistObjednavky();
    }

    @Override
    public void OdstranitObjednavku(int id) throws RemoteException {
       database.OdstranitObjednavku(id);
    }

    @Override
    public void PridatPolozku(Polozka polozka) throws RemoteException {
        database.PridatPolozku(polozka);
    }

    @Override
    public void OdebratPolozku(int id) throws RemoteException {
        database.OdebratPolozku(id);
    }

    @Override
    public Objednavka[] NacistPripravovaneObjednavky() throws RemoteException {
        return database.NacistPripravovaneObjednavky();
    }

    @Override
    public void NastavitObjednavkuDokoncenou(int id) throws RemoteException {
        database.NastavitObjednavkuDokoncenou(id);

        Objednavka[] obj = NacistObjednavky();

        for (Objednavka objednavka : obj) {
            if (objednavka.GetId() == id) {
                pr2.PridaniObjednavky(objednavka);
                return;
            }
        }
        
    }
    @Override
    public Objednavka[] NacistDokonceneObjednavky() throws RemoteException {
        return database.NacistDokonceneObjednavky();
    }
    
    public static void main(String[] args) throws Exception{
        LocateRegistry.createRegistry(1099);

        Server server = new Server();

        server.NacistObjednavky();

        Naming.rebind("//localhost/RmiServer", server);

        server.Rmi();

        System.out.println("Server běží");

        Scanner sc = new Scanner(System.in);

        sc.nextLine();

        sc.close();

        Naming.unbind("RmiServer");

        System.exit(0);
    }
    

    
}
