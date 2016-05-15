package projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * Created by Kuba on 2016-04-09.
 */
/**
 * Klasa charakteryzująca gracza i określająca jego parametry.
 */
public class Gracz implements Runnable {

    /**
     * Konstruktor klasy Gracz
     *
     * @param name     - Okresla imie gracza
     * @param fileName - Okresla scieżka do pliku z ikon gracza.
     */
    public Gracz(String name, String fileName, MapaPanel panel) {
        this.name = name;
        icon = new ImageIcon(fileName);
        hp = 100;
        x = panel.getWidth()/2;
        this.panel = panel;
    }

    /**
     * Funkcja rysująca postać gracza.
     *
     * @param g Kontekst graficzny.
     */
    void draw(Graphics g) {
        g.drawImage(icon.getImage(), x, 750, null);
    }

    /**
     * Funkcja, która zwraca poziom zycia gracza
     */
    int getHP() {
        return hp;
    }

    /**
     * Funkcja, która zwraca imie gracza
     */
    public String getName() {
        return name;
    }
    public int getX() {
        return x;
    }
    private int x;
    private int dx = 10;
    private int xDirection;

    MapaPanel panel;

    private ImageIcon icon;

    private Thread kicker;

    private String name;
    private double score;
    private int hp;

    boolean left;
    boolean right;

    public Gracz(String name) {
        this.name = name;
    }

    private void move() {
        xDirection=0;
        if (left && x>=0) {
            xDirection = -1;
        } else if (right && x+icon.getIconWidth()<panel.getWidth()) {
            xDirection = 1;
        }
        x += xDirection*dx;
    }

    @Override
    public void run() {
        while (kicker == Thread.currentThread()) {
            move();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    public void startLocationUpdateThread(){
        (kicker = new Thread(this)).start();
    }

    public void stopLocationUpdateThread(){
        kicker = null;
    }
}
