/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP6_Punto1_Locks;

/**
 *
 * @author maria
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Comedor {

    private boolean turnoGato;// turno del gato
    private boolean turnoPerro; // turno del perro
    private final ReentrantLock lock;
    private final Condition gatosEsperandoPlato;
    private final Condition perrosEsperandoPlato;
    private final int cantPlatos;
    private int platosUsados;
    private int gatosEsperando, perrosEsperando;

    public Comedor(int platos) {
        cantPlatos = platos;
        platosUsados = 0;
        gatosEsperando = 0;
        perrosEsperando = 0;
        lock = new ReentrantLock(true);
        gatosEsperandoPlato = lock.newCondition();
        perrosEsperandoPlato = lock.newCondition();
        turnoGato = false;
        turnoPerro = true; //arrancan comiendo losf perros
    }

   
    private void pasarTurnoPerro() {
        lock.lock();
        try {
            if (gatosEsperando > cantPlatos) {
                System.out.println("Siguen entrando gatos");
                gatosEsperandoPlato.signal();
            } else {
                System.out.println("Le toca comer a los perros");
                turnoPerro = true;
                turnoGato = false;
                perrosEsperandoPlato.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    private void pasarTurnoGato() {
        lock.lock();
        try {
            if (perrosEsperando > cantPlatos) {
                System.out.println("Siguen entrando perros");
                perrosEsperandoPlato.signal();
            } else {
                System.out.println("Le toca comer a los gatos");
                turnoPerro = false;
                turnoGato = true;
                gatosEsperandoPlato.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    public void entrarGato() {
        lock.lock();
        try {
            gatosEsperando++;
            while (platosUsados > cantPlatos || !turnoGato) {
                gatosEsperandoPlato.await();
            }
            System.out.println("El " + Thread.currentThread().getName() + " esta comiendo");
            gatosEsperando--;
            platosUsados++;
            System.out.println("platos ocupados " + platosUsados);

        } catch (InterruptedException ex) {
            Logger.getLogger(Comedor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lock.unlock();
        }
    }

    public void salirGato() {
        lock.lock();
        try {
            System.out.println("El " + Thread.currentThread().getName() + " dejo de comer");
            platosUsados--;
            System.out.println("platos ocupados " + platosUsados);
            if (platosUsados == 0) {
                pasarTurnoPerro();
            }
        } finally {
            lock.unlock();
        }
    }

    public void salirPerro() {
        lock.lock();
        try {
            System.out.println("El " + Thread.currentThread().getName() + " dejo de comer");
            platosUsados--;
            System.out.println("platos ocupados " + platosUsados);
            if (platosUsados == 0) {
                pasarTurnoGato();
            }
        } finally {
            lock.unlock();
        }
    }

    public void entrarPerro() {
        lock.lock();
        try {
            perrosEsperando++;
            while (platosUsados > cantPlatos || !turnoPerro) {
                perrosEsperandoPlato.await();
            }
            System.out.println("El " + Thread.currentThread().getName() + " esta comiendo");
            perrosEsperando--;
            platosUsados++;
            System.out.println("platos ocupados " + platosUsados);
        } catch (InterruptedException ex) {
            Logger.getLogger(Comedor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lock.unlock();
        }
    }
}
