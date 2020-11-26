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
public class Main {
public static void main(String[] args) {
        int cantLectores = 20;
        int cantEscritores = 4;
        Libro libro = new Libro(cantLectores, cantEscritores);
        Thread[] lectores = new Thread[cantLectores];
	Thread[] escritores = new Thread[cantEscritores];
        for (int i = 0; i < cantEscritores; i++) {
            escritores[i] = new Thread(new Escritor(libro, "Escritor" +(i + 1)));
        }
        for (int i = 0; i < cantEscritores; i++) {
            escritores[i].start();
        }
	for (int i = 0; i < cantLectores; i++) {
            lectores[i] = new Thread(new Lector(libro, "Lector" +(i + 1)));
        }
        for (int i = 0; i < cantLectores; i++) {
            lectores[i].start();
        }
    }
}