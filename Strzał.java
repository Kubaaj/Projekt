package projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 * Created by Kuba on 2016-05-14.
 */
public class Strzał implements Runnable {

    public Strzał(String fileName, MapaPanel panel) {
        icon = new ImageIcon(fileName);
        this.panel = panel;
        }

    public boolean spacja=false;



    void draw(Graphics g) {g.drawImage(icon.getImage(), xs+45, y, null);}

    private ImageIcon icon;
    MapaPanel panel;
    public int xs;
    public int y=480;
    private int dy = 10;
    private int yDirection;
    private int moc;

    private Thread kicker;

    private void move() {
            if(spacja) {
            xs=panel.gracz.getX();
            yDirection = -1;
            y += yDirection * dy;
        }
        if(y<480 && y>-20) {
            spacja = false;
            yDirection = -1;
            y += yDirection * dy;
        }

        if (y<-20) {y=480;
        }
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
