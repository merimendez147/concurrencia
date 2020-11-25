/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP6_Punto1;

/**
 *
 * @author maria
 */
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Comedor {
	private Semaphore semGato;// turno del gato
	private Semaphore semPerro; // turno del perro
	private Semaphore mutexCantPlatosOcupados = new Semaphore(1); //exclusion mutua
        private Semaphore mutexCantGatosEsperando = new Semaphore(1, true);
        private Semaphore mutexCantPerrosEsperando = new Semaphore(1, true);

	private final int cantPlatos;
	private int platosUsados;
        private int gatosEsperando, perrosEsperando;

	public Comedor(int platos) {
		cantPlatos = platos;
		platosUsados = 0;
                gatosEsperando=0;
                perrosEsperando=0;
		semGato = new Semaphore(0, true); 
		semPerro = new Semaphore(cantPlatos, true); //arrancan comiendo los perros
	}

	public void esperarTurnoPerro() {
		try {   
                        mutexCantPerrosEsperando.acquire();
                        perrosEsperando++;
                        mutexCantPerrosEsperando.release();
			semPerro.acquire();
		} catch (InterruptedException ex) {
				Logger.getLogger(Comedor.class.getName()).log(Level.SEVERE, null, ex);
			}
	}

	public void esperarTurnoGato() {
		try {
                        mutexCantGatosEsperando.acquire();
                        gatosEsperando++;
                        mutexCantGatosEsperando.release();
			semGato.acquire();
		} catch (InterruptedException ex) {
				Logger.getLogger(Comedor.class.getName()).log(Level.SEVERE, null, ex);
			}
	}

	private void pasarTurnoPerro() {
		System.out.println("Le toca comer a los perros");
		semPerro.release(cantPlatos);
	}

	private void pasarTurnoGato() {
		System.out.println("Le toca comer a los gatos");
		semGato.release(cantPlatos);
	}

	public void entrarGato() {
		try {
			mutexCantPlatosOcupados.acquire();
			if (platosUsados < cantPlatos) {
				System.out.println("El " + Thread.currentThread().getName() + " esta comiendo");
				platosUsados++;
				System.out.println("platos ocupados "+platosUsados);
			} 
			mutexCantPlatosOcupados.release();
		} catch (InterruptedException ex) {
				Logger.getLogger(Comedor.class.getName()).log(Level.SEVERE, null, ex);
			}
	}
	
	public void salirGato(){
		try {
			mutexCantPlatosOcupados.acquire();
			System.out.println("El " + Thread.currentThread().getName() + " dejo de comer");
			platosUsados--;
			System.out.println("platos ocupados "+platosUsados);
			if (platosUsados==0){
				pasarTurnoPerro();
			}
			mutexCantPlatosOcupados.release();
		} catch (InterruptedException ex) {
				Logger.getLogger(Comedor.class.getName()).log(Level.SEVERE, null, ex);
			}
	}

	public void salirPerro(){
		try {
			mutexCantPlatosOcupados.acquire();
			System.out.println("El " + Thread.currentThread().getName() + " dejo de comer");
			platosUsados--;
			System.out.println("platos ocupados "+platosUsados);
			if (platosUsados==0){
				pasarTurnoGato();
			}
			mutexCantPlatosOcupados.release();
		} catch (InterruptedException ex) {
				Logger.getLogger(Comedor.class.getName()).log(Level.SEVERE, null, ex);
			}
	}
	public void entrarPerro() {
		try {
			mutexCantPlatosOcupados.acquire();
			if (platosUsados < cantPlatos) {
				System.out.println("El " + Thread.currentThread().getName() + " esta comiendo");
				platosUsados++;
				System.out.println("platos ocupados "+platosUsados);
			}
			mutexCantPlatosOcupados.release();
		} catch (InterruptedException ex) {
				Logger.getLogger(Comedor.class.getName()).log(Level.SEVERE, null, ex);
			}
	}
}