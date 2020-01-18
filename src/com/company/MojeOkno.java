package com.company;
import java.awt.*;
import java.awt.Insets;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MojeOkno extends JFrame //implements ActionListener, MouseListener, MouseMotionListener
{

    PanelGraficzny panel;

    //##

    int lab = 8; // !!! wybór labiryntu do wyświetlenia od 1-4 !!!

    //##

    public MojeOkno(){

        //wywolanie konstruktora klasy nadrzednej (JFrame)
        super("Olek, Cekan labirynt v1.3");
        //ustawienie standardowej akcji po naciśnięciu przycisku zamkniecia
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(740, 600);
        //setPreferredSize(new Dimension(600, 600));
        panel = new PanelGraficzny(600, 600);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        pack();
        //wysrodkowanie okna na ekranie
        setLocationRelativeTo(null);
        //wyswietlenie naszej ramki
        setVisible(true);

        wczytajLabirynty();

    }

    public void wczytajLabirynty(){

        Labirynt []labirynt = new Labirynt[9];

       for(int i = 0; i < 9; i++)
           labirynt[i] = new Labirynt("labirynt"+(i+1)+".txt");


        System.out.println("====================================");
        labirynt[lab-1].wyswietlLabirynt();
        labirynt[lab-1].wyswietlWspolPrzeszkod();
        System.out.println("====================================");

        panel.rysujLabirynt(labirynt[lab-1].getLabirynt());
        pack();

        Labirynt labKopia = new Labirynt("labirynt"+(lab)+".txt");
        Labirynt labKopia2 = new Labirynt("labirynt"+(lab)+".txt");
        Labirynt labKopia3 = new Labirynt("labirynt"+(lab)+".txt");



       int[][] mat =
                {
                        {0, 0, 0, 0},
                        {0, 1, 1, 1},
                        {0, 0, 0, 1},
                        {0, 0, 0, 0}

                };

        // #########################################################
        // ################# PRZESZUKIWANIE ŚLEPE #################
        // #########################################################
        System.out.println("### PRZESZUKIWANIE ŚLEPE ###");
        BFS bfs = new BFS(labirynt[lab-1].getM(), labirynt[lab-1].getN()); // stworz obiekt z rozmiarem labiryntu
        bfs.szukajDrogi(labirynt[lab-1].getLabirynt(), 0, 0, labirynt[lab-1].getM()-1, labirynt[lab-1].getN()-1); // odpal BFSa

        panel.rysujPrzebiegDane(bfs.getWspolTrasy(), labirynt[lab-1].getLabirynt(), bfs.getIloscKrokowCalkowita()); // wprowadz dane przebytych wierzcholkow
        panel.rysujNajkrotszaDane(bfs.getNajKrTrasa(), labirynt[lab-1].getLabirynt());

        panel.rysujNajkrotsza();
        panel.rysujPrzebieg(); // narysuj je według przebiegu algorytmu
        panel.timerTrasa.start(); // odpalenie rysowania
        System.out.println();
        // #########################################################
    }


}
