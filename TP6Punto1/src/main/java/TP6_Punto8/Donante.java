/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP6_Punto8;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maria
 */
public class Donante implements Runnable {

    private int id;
    private Centro centro;
    private Random random;

    public Donante(int id, Centro centro) {
        this.id = id;
        this.centro = centro;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {

            centro.entrar(id);
            int tiempo = random.nextInt(7) + 4;
            Thread.sleep(tiempo * 1000);
            centro.salir(id);
        } catch (InterruptedException ex) {
            Logger.getLogger(Donante.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
