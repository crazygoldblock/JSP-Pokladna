package Vydej;

import Core.Objednavka;
import Core.Objednavky;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Client extends JFrame {
    static JPanel mainPanel = null; 
    static ArrayList<Objednavka> objednavky = new ArrayList<>();
    static Objednavky server = null;
    public Client() throws Exception {
        setTitle("Výdej");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setVisible(true);

        server = (Objednavky)Naming.lookup("//localhost/RmiServer");

        Objednavka[] obj = server.NacistDokonceneObjednavky();

        for (Objednavka objednavka : obj) {
            objednavky.add(objednavka);
        }

        VypsatObjednavky();

        //LocateRegistry.createRegistry(1099);
    }
    public synchronized void VypsatObjednavky() {
        if (mainPanel != null) 
            remove(mainPanel);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        for (int i = 0; i < objednavky.size(); i++) {
            JButton button = new JButton("Hotovo");
            mainPanel.add(button, constraints);
            constraints.gridy++;

            final int[] arr = new int[1];
            arr[0] = i;

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    OdstranitObjednavku(arr[0]);
                }
            });
        }

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;

        for (Objednavka objednavka : objednavky) {
            JTextField textField = new JTextField(objednavka.toString());
            mainPanel.add(textField, constraints);
            constraints.gridy++;
        }

        add(mainPanel, BorderLayout.WEST);
        pack();
    }
    public void OdstranitObjednavku(int index) {
        try {
            server.OdstranitObjednavku(objednavky.get(index).GetId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        objednavky.remove(index);
        VypsatObjednavky();
    }
    public void PridaniObjednavky(Objednavka o) throws RemoteException {
        objednavky.add(o);
        VypsatObjednavky();
    }

    public static void main(String[] args) throws Exception {
        Client cl = new Client();

        Rmi rm = new Rmi(cl);

        Naming.rebind("//localhost/Vydej", rm);
    }
    
}