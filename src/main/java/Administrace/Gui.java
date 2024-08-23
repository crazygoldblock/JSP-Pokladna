package Administrace;

import Core.Objednavky;
import Core.Polozka;

import javax.swing.*;
import java.awt.Font; 
import java.awt.event.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Gui extends JFrame {
    private JButton historieObjednavekButton, ukoncitButton, pridatPolozkuButton, odebratPolozkuButton;

    public Gui() throws Exception
    {
        super("Administrace");
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 18));
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 14));
       
        JPanel panel = new JPanel();
        
        historieObjednavekButton = Button("Historie", 40, 20);

        pridatPolozkuButton = Button("Přidat položku", 40, 100);

        odebratPolozkuButton = Button("Odebrat položku", 260, 100);

        ukoncitButton = Button("Ukončit", 145, 180);
        
        add(panel);

        final Objednavky server = (Objednavky)Naming.lookup("//localhost/RmiServer");

        odebratPolozkuButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                OdebratPolozku(server);
            }
        });

        pridatPolozkuButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                PridatPolozku(server);
            }
        });

        historieObjednavekButton.addActionListener(new ActionListener(){               
            public void actionPerformed(ActionEvent e){  
                HistorieObjednavek(server);
            }  
        });  
        ukoncitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                Ukoncit();
            }
        });
    }


    private void OdebratPolozku(Objednavky server) {
        int typ = Okno(new String[]{"Přílohy", "Burgery", "Omáčky", "Nápoje", "Vylepšení"}, "Vyberte typ položky kterou chcete odebrat");
        if (typ == -1)
            return;

        try {
            Polozka[] polozky = server.NacistPolozky();
            ArrayList<Polozka> list = new ArrayList<>();

            for (Polozka polozka : polozky) {
                if (polozka.GetTyp() == typ)
                    list.add(polozka);
            }

            int polozka = Okno(list.toArray(new Polozka[0]), "Vyberte položku kterou odeberete");
            if (polozka == -1) 
                return;

            server.OdebratPolozku(list.get(polozka).GetId());
        }
        catch (RemoteException x) {
            x.printStackTrace();
        }
    }
    private void PridatPolozku(Objednavky server) {
        int typ = Okno(new String[]{"Přílohy", "Burgery", "Omáčky", "Nápoje", "Vylepšení"}, "Vyberte typ položky kterou chcete přidat");

        if (typ == -1) 
            return;

        String jmeno = JOptionPane.showInputDialog(null, "Zadej jméno nové položky");

        String inputCena = JOptionPane.showInputDialog(null, "Zadej cenu nové položky");

        try {
            int cena = Integer.parseInt(inputCena);

            server.PridatPolozku(new Polozka(-1, jmeno, cena, typ));
        }
        catch (Exception x) {
            x.printStackTrace();
        }
    }
    private void HistorieObjednavek(Objednavky server) {
        try {
            Okno(new String[]{"Zavřít"}, server.NacistObjednavky());
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
    }
    private void Ukoncit() {
        System.exit(0);
    }
    private int Okno(Object[] moznosti, Object vyberte) {
        return JOptionPane.showOptionDialog(null, vyberte, "Pokladna", JOptionPane.DEFAULT_OPTION, 
               JOptionPane.PLAIN_MESSAGE, null, moznosti, 0);
    }
    private JButton Button(String title, int x, int y) {
        JButton button = new JButton(title);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setBounds(x, y,190,50);   
        add(button);
        return button;
    }
}
