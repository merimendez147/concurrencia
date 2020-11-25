/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP6_Punto1;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maria
 */
public class Perro implements Runnable {
	Comedor comedero;

	
	public Perro(Comedor c){
		comedero=c;

	}
	
	private void comer(){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ex) {
				Logger.getLogger(Perro.class.getName()).log(Level.SEVERE, null, ex);
			}
	}
	
        @Override
	public void run(){
		comedero.esperarTurnoPerro();
		comedero.entrarPerro();
		comer();
		comedero.salirPerro();
		
	}

}

