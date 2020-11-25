/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP6_Punto8;

/**
 *
 * @author maria
 */
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Centro {

    private final int cantCamillas;//4
    private int camillasOcupadas;//contador de camillas en uso
    private final Lock lock;
    private final int cantidadRevistas;
    private int revistasOcupadas;
    private final Condition esperandoCamilla;
    private final Condition esperandoRevista;

    public Centro(int cantC, int cantR) {
        cantCamillas = cantC;
        camillasOcupadas = 0;
        cantidadRevistas = cantR;
        revistasOcupadas = 0;
        lock = new ReentrantLock(true);
        esperandoCamilla = lock.newCondition();
        esperandoRevista = lock.newCondition();

    }

    public void entrar(int id) {
        lock.lock();
        try {
            while (camillasOcupadas >= cantCamillas) {
                System.out.println("El donante " + id + " tiene que esperar la camilla");
                esperarTele(id);
                esperarRevista(id);
            }
            camillasOcupadas++;
            System.out.println("El donante " + id + " consigue una camilla para donar sangre");

        } finally {
            lock.unlock();
        }
    }

    public void esperarTele(int id) {
        lock.lock();
        try {
            while (revistasOcupadas >= cantidadRevistas && camillasOcupadas >= cantCamillas) {
                //si no hay revistas y (no es su turno o estan todas las camas ocupadas)
                System.out.println("La persona " + id + " no puede tomar una revista y se pone a ver el noticiero");
                esperandoRevista.await();//pone a esperar a la persona viendo el noticiero ya que aun no puede tomar una revista
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Centro.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lock.unlock();
        }
    }

    public void esperarRevista(int id) {
        lock.lock();
        try {
            if (camillasOcupadas >= cantCamillas) {
                revistasOcupadas++;
                System.out.println("El donante " + id + " logra tomar una revista y se pone a leer");
                while (camillasOcupadas >= cantCamillas) {
                    esperandoCamilla.await();
                }
                revistasOcupadas--;
                System.out.println("El donante " + id + " devuelve la revista");
                esperandoRevista.signalAll();
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(Centro.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lock.unlock();
        }
    }

    public void salir(int id) {
        lock.lock();
        try {
            camillasOcupadas--;
            System.out.println("El donante " + id + " termina de donar sangre y se va");
            esperandoCamilla.signalAll();
            esperandoRevista.signalAll();
        } finally {
            lock.unlock();
        }
    }

}
