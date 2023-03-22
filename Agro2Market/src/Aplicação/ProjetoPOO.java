package Aplicação;

import Classes.*;
import Telas.SplashScreen;
import Telas.MenuInicial;
import javax.swing.JOptionPane;

public class ProjetoPOO {

        
    public static void main(String[] args) {

        SplashScreen in = new SplashScreen();
        in.setVisible(true);
        try {
            for (int i = 0; i < 100; i += 2) {
                Thread.sleep(38); 
                in.loading.setValue(i);

                in.jLabel2.setText("Inicializando . . . " + i + "%");
            }

            in.dispose();
            new MenuInicial().setVisible(true);

        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar.");
        }

    }
}
