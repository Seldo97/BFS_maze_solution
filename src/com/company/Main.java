package com.company;
import java.awt.EventQueue;

public class Main {

    public static void main(String[] args) {

/*        Labirynt []labirynt = new Labirynt[4];

       for(int i = 1; i < 2; i++){
           labirynt[i] = new Labirynt("labirynt"+i+".txt");
           labirynt[i].wyswietlLabirynt();
       }*/


        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MojeOkno();
            }
        });

    }
}
