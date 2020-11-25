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
public class Main {
public static void main(String[] args) {
    	Comedor comedero = new Comedor(5);
        Thread[] gatos = new Thread[10];
        Thread[] perros = new Thread[10];
        for (int i = 0; i < gatos.length; i++) {
            gatos[i] = new Thread(new Gato(comedero));
            gatos[i].setName("Gato"+i);
        }
        for (int i = 0; i < perros.length; i++) {
            perros[i] = new Thread(new Perro(comedero));
            perros[i].setName("Perro"+i);
        }
        for (int i = 0; i < gatos.length; i++) {
            gatos[i].start();

        }
        for (int i = 0; i < perros.length; i++) {
            perros[i].start();
        }
    }
}