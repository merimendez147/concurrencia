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
public class Main {

    public static void main(String[] args) {
        int cantDonantes = 20;
        int cantCamillas = 4;
        int cantRevistas = 3;
        Centro centro = new Centro(cantCamillas, cantRevistas);
        Thread[] donantes = new Thread[cantDonantes];
        for (int i = 0; i < cantDonantes; i++) {
            donantes[i] = new Thread(new Donante(i + 1, centro));
        }
        for (int i = 0; i < cantDonantes; i++) {
            donantes[i].start();
        }
    }
}
