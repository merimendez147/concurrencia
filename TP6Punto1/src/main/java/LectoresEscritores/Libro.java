/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LectoresEscritores;

/**
 *
 * @author elina
 */
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Libro {

    private final ReentrantLock lock;
    private final Condition escritoresEsperando;
    private final Condition lectoresEsperando;
    private int cantLectoresLleyendo, cantEscritoresEsperando, cantEscritoresEscribiendo, cantLectoresEsperando;
    int cantLectores, cantEscritores;
    boolean escritorEscribiendo;

    public Libro(int cantL, int cantE) {
        lock = new ReentrantLock();
        escritoresEsperando = lock.newCondition();
        lectoresEsperando = lock.newCondition();
        cantLectores = cantL;
        cantEscritores = cantE;
        cantLectoresLleyendo = 0;
        cantEscritoresEsperando = 0;
        cantLectoresEsperando = 0;
        cantEscritoresEscribiendo = 0;
        escritorEscribiendo = false;
    }

    public void empezarLectura(String nombre) {
        lock.lock();
        try {
            cantLectoresEsperando++;
            while (escritorEscribiendo || cantEscritoresEsperando > 0) {
                lectoresEsperando.await();
            }
            cantLectoresEsperando--;
            cantLectoresLleyendo++;
            System.out.println("El " + nombre + " comenzo a leer");

        } catch (InterruptedException ex) {
            Logger.getLogger(Libro.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            lock.unlock();
        }
    }

    public void terminarLectura(String nombre) {
        lock.lock();
        try {
            System.out.println("El " + nombre + " dejo de leer");
            cantLectoresLleyendo--;
            if (cantLectoresLleyendo == 0) {
                escritoresEsperando.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public void empezarEscritura(String nombre) {
        lock.lock();
        try {
            cantEscritoresEsperando++;
            while (escritorEscribiendo || cantLectoresLleyendo > 0) {
                escritoresEsperando.await();
            }
            cantEscritoresEsperando--;
            escritorEscribiendo = true;
            cantEscritoresEscribiendo++;
            System.out.println("El " + nombre + " esta escribiendo");
        } catch (InterruptedException ex) {
            Logger.getLogger(Libro.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lock.unlock();
        }
    }

    public void terminarEscritura(String nombre) {
        lock.lock();
        try {
            System.out.println("El " + nombre + " dejo de escribir");
            cantEscritoresEscribiendo--;
            escritorEscribiendo = false;
            System.out.println("escritores escribiendo" + cantEscritoresEscribiendo);
            System.out.println("escritores esperando" + cantEscritoresEsperando);
            if (cantEscritoresEsperando > 0) {
                escritoresEsperando.signal();
            } else {
                lectoresEsperando.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

}
