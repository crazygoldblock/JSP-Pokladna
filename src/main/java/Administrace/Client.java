package Administrace;

import javax.swing.JFrame;

public class Client {  
    
    public static void main(String[] args) throws Exception {
        Gui okno = new Gui();
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setVisible(true);
        okno.setSize(500, 320);
        okno.setLocationRelativeTo(null); 
    }
}