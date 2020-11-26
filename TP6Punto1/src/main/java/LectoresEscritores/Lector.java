/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LectoresEscritores;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elina
 */
public class Lector implements Runnable {

    Libro miLibro;
    String nombre;

    public Lector(Libro l, String n) {
        miLibro = l;
        nombre = n;
    }

    private void leerLibro() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        miLibro.empezarLectura(nombre);
        leerLibro();
        miLibro.terminarLectura(nombre);
    }
}
