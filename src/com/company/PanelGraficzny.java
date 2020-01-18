package com.company;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelGraficzny extends JPanel {

    //obiekt do przechowywania grafiki
    BufferedImage plotno;
    Graphics2D g; // (Graphics2D) plotno.getGraphics();
    Timer timerTrasa;
    Timer timerNajkrotszaTrasa;
    int czas = 50;
    boolean faza1 = false;

    public PanelGraficzny(int szer, int wys)
    {
        super();
        ustawRozmiar(new Dimension(szer,wys));

        wyczysc();
        //pack();
    }

    public void ustawRozmiar(Dimension r)
    {
        //przygotowanie płótna
        plotno = new BufferedImage((int)r.getWidth(), (int)r.getHeight(), BufferedImage.TYPE_INT_RGB);
        setPreferredSize(r);
    }

    public void wyczysc()
    {
        //wyrysowanie białego tła
        g = (Graphics2D) plotno.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, plotno.getWidth(), plotno.getHeight());
        setBorder(BorderFactory.createLineBorder(Color.black));
        repaint();
    }

    // // ################### Rysowanie LABIRYNTU ####################

    public void rysujLabirynt(int labirynt[][]){

        int lab[][] = labirynt;
        int wspolX = 0;
        int wspolY = 0;
        int rozmiar = 45;


        ustawRozmiar(new Dimension(rozmiar*lab.length, rozmiar*lab[0].length));
        wyczysc();

        g.setColor(Color.black);

        for(int i = 0; i<lab.length; i++){
            for(int j = 0; j<lab[i].length; j++){
                Rectangle2D rect = new Rectangle2D.Double(wspolX, wspolY, rozmiar, rozmiar);
                if(i == 0 && j == 0){ //ustaw start
                    g.setColor(Color.blue);
                    g.fill(rect);
                    g.setColor(Color.black);
                    g.draw(rect);
                }
                else if(i == lab.length-1 && j == lab[i].length-1){ //ustaw koniec
                    g.setColor(Color.red);
                    g.fill(rect);
                    g.setColor(Color.black);
                    g.draw(rect);
                }
                else if(lab[i][j] == 1){ //ustaw przeszkode
                    g.setColor(Color.black);
                    g.fill(rect);
                    g.draw(rect);
                }
                else
                    g.draw(rect);

                wspolX += rozmiar;
            }
            wspolX = 0;
            wspolY += rozmiar;
        }

        repaint();

    }

    public void rysujKrance(){

        int lab[][] = lab2;
        int wspolX = 0;
        int wspolY = 0;
        int rozmiar = 45;


        g.setColor(Color.black);

        for(int i = 0; i<lab.length; i++){
            for(int j = 0; j<lab[i].length; j++){
                Rectangle2D rect = new Rectangle2D.Double(wspolX, wspolY, rozmiar, rozmiar);
                if(i == 0 && j == 0){ //ustaw start
                    g.setColor(Color.blue);
                    g.fill(rect);
                    g.setColor(Color.black);
                    g.draw(rect);
                }
                else if(i == lab.length-1 && j == lab[i].length-1){ //ustaw koniec
                    g.setColor(Color.red);
                    g.fill(rect);
                    g.setColor(Color.black);
                    g.draw(rect);
                }

                wspolX += rozmiar;
            }
            wspolX = 0;
            wspolY += rozmiar;
        }

        repaint();

    }


    // ################### Rysowanie WSZYSTKICH TRAS ####################

    int wspoltrasy2[][];
    int lab2[][];
    int wspolX;
    int wspolY;
    int rozmiar;
    int ilW;
    public void rysujPrzebiegDane(int [][] wspolTrasy, int [][] lab, int ilW){
        wspoltrasy2 = wspolTrasy;
        lab2 = lab;
        wspolX = 0;
        wspolY = 0;
        rozmiar = 45;
        this.ilW = ilW;
    }

    public void szukaj(int k){

        wspolX = 0;
        wspolY = 0;
        rozmiar = 45;
        for (int i = 0; i < lab2.length; i++) {
            for (int j = 0; j < lab2[i].length; j++) {
                Rectangle2D rect = new Rectangle2D.Double(wspolX, wspolY, rozmiar, rozmiar);
                //for (int p = 0; p < wspoltrasy2.length; p++) {

                if(i == 0 && j == 0){ //ustaw start
                    g.setColor(Color.blue);
                    g.fill(rect);
                    g.setColor(Color.black);
                    g.draw(rect);
                }
                else if(i == lab2.length-1 && j == lab2[i].length-1){ //ustaw koniec
                    g.setColor(Color.red);
                    g.fill(rect);
                    g.setColor(Color.black);
                    g.draw(rect);
                }

                if (i == wspoltrasy2[k][0] && j == wspoltrasy2[k][1]) {
                    g.setColor(Color.yellow);
                    g.fill(rect);
                    g.setColor(Color.black);
                    g.draw(rect);
                    return;
                }


                //}

                wspolX += rozmiar;
            }
            wspolX = 0;
            wspolY += rozmiar;
            //timerNajkrotszaTrasa.start();
        }
    }

    public void rysujPrzebieg()
    {
        //System.out.println(wspoltrasy2.length);
        timerTrasa = new Timer(czas, new ActionListener() {
            int k= 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                szukaj(k);

                if(ilW < k-1){
                    japierdole();
                    timerTrasa.stop();
                }

                rysujKrance();
                repaint();
                //System.out.println(k);
                k++;
            }
        });

        //timerNajkrotszaTrasa.start();
    }

    // ################### Rysowanie najkrótszej trasy ####################

    private void japierdole(){
        timerNajkrotszaTrasa.start();
    }

    int wspoltrasy3[][];
    int lab3[][];
    int wspolX3;
    int wspolY3;
    int rozmiar3;

    public void rysujNajkrotszaDane(int [][] najKrT, int [][] lab){
        wspoltrasy3 = najKrT;
        lab3 = lab;
        wspolX3 = 0;
        wspolY3 = 0;
        rozmiar3 = 45;
    }

    public void rysujNajkrotsza()
    {
        timerNajkrotszaTrasa = new Timer(70, new ActionListener() {
            int k= 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                szukajNajkr(k);

                if(ilW < k-1)
                    timerNajkrotszaTrasa.stop();

                rysujKrance();
                repaint();
                k++;
            }
        });
        //timerNajkrotszaTrasa.start();
    }

    public void szukajNajkr(int k){

        wspolX3 = 0;
        wspolY3 = 0;
        rozmiar3 = 45;
        for (int i = 0; i < lab3.length; i++) {
            for (int j = 0; j < lab3[i].length; j++) {
                Rectangle2D rect = new Rectangle2D.Double(wspolX3, wspolY3, rozmiar3, rozmiar3);
                //for (int p = 0; p < wspoltrasy2.length; p++) {

                if(i == 0 && j == 0){ //ustaw start
                    g.setColor(Color.blue);
                    g.fill(rect);
                    g.setColor(Color.black);
                    g.draw(rect);
                }
                else if(i == lab3.length-1 && j == lab3[i].length-1){ //ustaw koniec
                    g.setColor(Color.red);
                    g.fill(rect);
                    g.setColor(Color.black);
                    g.draw(rect);
                }

                if (i == wspoltrasy3[k][0] && j == wspoltrasy3[k][1]) {
                    g.setColor(Color.green);
                    g.fill(rect);
                    g.setColor(Color.black);
                    g.draw(rect);
                    return;
                }


                //}

                wspolX3 += rozmiar3;
            }
            wspolX3 = 0;
            wspolY3 += rozmiar3;
        }
    }


    //przesłonięta metoda paintComponent z klasy JPanel do rysowania
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //wyrysowanie naszego płótna na panelu
        g2d.drawImage(plotno, 0, 0, this);
    }

}
