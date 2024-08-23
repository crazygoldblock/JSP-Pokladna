package Core;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Objednavky extends Remote {  
    public Polozka[] NacistPolozky() throws RemoteException;  
    public void UlozitObjednavku(Objednavka objednavka) throws RemoteException;
    public Objednavka[] NacistObjednavky() throws RemoteException;
    public void OdstranitObjednavku(int id) throws RemoteException;
    public void PridatPolozku(Polozka polozka) throws RemoteException;
    public void OdebratPolozku(int id) throws RemoteException;
    public Objednavka[] NacistPripravovaneObjednavky() throws RemoteException;
    public Objednavka[] NacistDokonceneObjednavky() throws RemoteException;
    public void NastavitObjednavkuDokoncenou(int id) throws RemoteException;
}  