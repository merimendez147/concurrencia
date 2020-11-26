/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP6_Punto1;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maria
 */
public class Gato implements Runnable {

    Comedor comedero;
    private Random random;

    public Gato(Comedor c) {
        comedero = c;

    }

    private void comer() {
        try {
            int tiempo = random.nextInt(7) + 4;
            Thread.sleep(tiempo * 1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Gato.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        comedero.esperarTurnoGato();
        comedero.entrarGato();
        comer();
        comedero.salirGato();
    }

}
