/**
 * Starting point of program
 * @author Vahe Aleksanyan <a href="mailto:vahe_aleksanyan@edu.aua.am">vahe_aleksanyan@edu.aua.am</a>
 * @author Rafik Gasparyan <a href="mailto:rafik_gasparyan@edu.aua.am">rafik_gasparyan@edu.aua.am</a>
 * @author Arman Hovhannisyan <a href="mailto:arman_hovhannisyan@edu.aua.am">arman_hovhannisyan@edu.aua.am</a>
 * @version 6.0
 * @since 1.0
 */


package am.aua.dama;
import am.aua.dama.ui.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        
//
//        DamaConsole damaConsole = new DamaConsole();
//        damaConsole.play();
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        DamaUI damaUi = new DamaUI();
        damaUi.setVisible(true);
    }
}
