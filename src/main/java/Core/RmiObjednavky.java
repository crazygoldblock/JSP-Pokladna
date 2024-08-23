package Core;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiObjednavky extends Remote {
    public void PridaniObjednavky(Objednavka o) throws RemoteException;
}
