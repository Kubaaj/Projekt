package projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Kuba on 2016-04-10.
 */
public class MenuPanel extends JPanel implements ActionListener{

    public MenuPanel (String img){
        this.img=new ImageIcon(img).getImage();
        Dimension size = new Dimension(this.img.getWidth(null), this.img.getHeight(null));
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
        setSize(size);

        JPanel menu = new JPanel(new GridLayout(2,3));
        JPanel emp1 = new JPanel();
        JPanel emp2 = new JPanel();
        JPanel emp3 = new JPanel();
        JPanel emp4 = new JPanel();
        JPanel but = new JPanel(new GridLayout(6, 1));
        JPanel title = new JPanel();

        add(menu);
        menu.add(emp1);
        menu.add(title);
        menu.add(emp2);
        menu.add(emp3);
        menu.add(but);
        menu.add(emp4);


        JLabel jlabel = new JLabel("Władek Invaders");
        jlabel.setFont(new Font("Verdana",1,20));
        title.add(jlabel);

        JButton b1 = new JButton("       Nowa Gra       ");

        b1.addActionListener(this);
        JButton b2 = new JButton("Najlepsze Wyniki");
        JButton b3 = new JButton("     Zasady Gry     ");
        JButton b4 = new JButton("          Opcje          ");
        JButton b5 = new JButton("        Autorzy        ");
        JButton b6 = new JButton("     Opuść Grę     ");

        but.add(b1);
        but.add(b2);
        but.add(b3);
        but.add(b4);
        but.add(b5);
        but.add(b6);
        b1.setAlignmentY(Component.CENTER_ALIGNMENT);

        //menu.setBackground(Color.yellow);
        menu.setOpaque(false);
        emp1.setOpaque(false);
        emp2.setOpaque(false);
        emp3.setOpaque(false);
        emp4.setOpaque(false);
        title.setOpaque(false);
        try {
            Klient klient = new Klient(InetAddress.getLocalHost(),9876, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
}

    public void paintComponent(Graphics g){
        g.drawImage(img, 0, 0, null);
    }
private Image img;

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane d=new JOptionPane(null);

        String inputValue = JOptionPane.showInputDialog(null, "Podaj imię", "Stwórz gracza", JOptionPane.QUESTION_MESSAGE);
        d.setSize(300, 200);
        d.setVisible(true);

        if(inputValue!=null){
            new Test2(inputValue);
        }
    }
}
