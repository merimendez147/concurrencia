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
public class Escritor implements Runnable {

    Libro miLibro;
    String nombre;

    public Escritor(Libro l, String n) {
        miLibro = l;
        nombre = n;
    }

    private void escribirLibro() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Escritor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        miLibro.empezarEscritura(nombre);
        escribirLibro();
        miLibro.terminarEscritura(nombre);
    }
}