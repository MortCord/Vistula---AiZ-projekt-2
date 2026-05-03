package Data;

import Grafy.Graf;
import Grafy.Krawedz;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelGrafu extends JPanel {

    private Graf graf;
    private List<Integer> sciezka = new ArrayList<>();

    public void setGraf(Graf graf) {
        this.graf = graf;
        repaint();
    }

    public void setSciezka(List<Integer> sciezka) {
        this.sciezka = sciezka;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (graf == null) return;

        int n = graf.getV();

        Point[] p = new Point[n];

        int cx = getWidth()/2;
        int cy = getHeight()/2;

        int r = 220;

        for(int i=0;i<n;i++) {

            double ang = 2*Math.PI*i/n;

            int x = cx + (int)(r*Math.cos(ang));
            int y = cy + (int)(r*Math.sin(ang));

            p[i] = new Point(x,y);
        }

        Graphics2D g2 = (Graphics2D) g;

        for(int i=0;i<n;i++) {

            for(Krawedz k : graf.getLista()[i]) {

                int to = k.getTo();

                if(isOnPath(i,to)) {
                    g2.setColor(Color.RED);
                    g2.setStroke(new BasicStroke(4));
                } else {
                    g2.setColor(Color.GRAY);
                    g2.setStroke(new BasicStroke(1));
                }

                g2.drawLine(p[i].x,p[i].y,p[to].x,p[to].y);

                int sx=(p[i].x+p[to].x)/2;
                int sy=(p[i].y+p[to].y)/2;

                g2.setColor(Color.BLACK);
                g2.drawString("" + k.getWaga(), sx, sy);
            }
        }

        for(int i=0;i<n;i++) {

            g2.setColor(Color.CYAN);
            g2.fillOval(p[i].x-18,p[i].y-18,36,36);

            g2.setColor(Color.BLACK);
            g2.drawOval(p[i].x-18,p[i].y-18,36,36);

            g2.drawString("" + i,p[i].x-4,p[i].y+4);
        }
    }

    private boolean isOnPath(int a,int b){

        for(int i=0;i<sciezka.size()-1;i++){

            if(sciezka.get(i)==a && sciezka.get(i+1)==b)
                return true;
        }

        return false;
    }

}
