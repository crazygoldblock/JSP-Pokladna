package Vydej;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Core.Objednavka;
import Core.RmiObjednavky;

public class Rmi extends UnicastRemoteObject implements RmiObjednavky{
    Client cl;
    protected Rmi(Client cl) throws RemoteException {
        super(0);
        this.cl = cl;
    }

    @Override
    public void PridaniObjednavky(Objednavka o) throws RemoteException {
        cl.PridaniObjednavky(o);
    }
    
}
