
import java.util.*;
//import app.app.*;
import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;

public class aplication {
    public static void main(String[] args) {

        apps app = new apps(); //instancja klasy tworzacej aplikacje

        JFrame frame = new JFrame("app"); // stworzenie okna głównego
        frame.setContentPane( app.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setBounds(100, 100, 500, 500);




    }



}
