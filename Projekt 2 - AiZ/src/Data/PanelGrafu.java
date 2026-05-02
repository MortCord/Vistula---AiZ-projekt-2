package Data;

import Grafy.Graf;
import Grafy.Krawedz;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelGrafu extends JPanel {

    private Graf graf;

    private List<Integer> sciezka;

    public void setGraf(Graf graf){
        this.graf = graf;
        repaint();
    }

    public void setSciezka(List<Integer> sciezka) {
        this.sciezka = sciezka;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);

        if(graf == null){
            return;
        }

        int wierzcholki = graf.getWierzcholki();

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        int radius = 180;

        Point[] points = new Point[wierzcholki];

        for(int i = 0; i < wierzcholki; i++){
            double kat = 2 * Math.PI * i / wierzcholki;

            int x = centerX + (int)(radius * Math.cos(kat));
            int y = centerY + (int)(radius * Math.sin(kat));

            points[i] = new Point(x, y);
        }

        for (int i = 0; i < wierzcholki; i++) {

            for (Krawedz krawedz : graf.getListaSasiedztwa()[i]) {

                int to = krawedz.getTo();

                int x1 = points[i].x;
                int y1 = points[i].y;
                int x2 = points[to].x;
                int y2 = points[to].y;

                boolean jestNaSciezce = jestWSciezce(i, to);

                if (jestNaSciezce) {
                    graphics.setColor(Color.RED);
                } else {
                    graphics.setColor(Color.GRAY);
                }

                Graphics2D g2 = (Graphics2D) graphics;
                g2.setStroke(new BasicStroke(jestNaSciezce ? 3 : 1));

                g2.drawLine(x1, y1, x2, y2);

                graphics.setColor(Color.BLACK);

                int srX = (x1 + x2) / 2;
                int srY = (y1 + y2) / 2;

                graphics.drawString(String.valueOf(krawedz.getWaga()), srX, srY);
            }
        }

        for(int i = 0; i < wierzcholki; i++){
            for(Krawedz krawedz : graf.getListaSasiedztwa()[i]){

                int to = krawedz.getTo();

                int x1 = points[i].x;
                int y1 = points[i].y;

                int x2 = points[to].x;
                int y2 = points[to].y;

                graphics.drawLine(x1, y1, x2, y2);

                int srX = (x1 + x2) / 2;
                int srY = (y1 + y2) / 2;

                graphics.drawString(String.valueOf(krawedz.getWaga()), srX, srY);
            }
        }

        for(int i = 0; i < wierzcholki; i++){

            graphics.setColor(Color.CYAN);
            graphics.fillOval(points[i].x - 15, points[i].y - 15, 30, 30);

            graphics.setColor(Color.BLACK);
            graphics.drawOval(points[i].x - 15, points[i].y - 15, 30, 30);

            graphics.drawString(String.valueOf(i), points[i].x - 5, points[i].y + 5);

        }
    }

    private boolean jestWSciezce(int from, int to) {

        if (sciezka == null) return false;

        for (int i = 0; i < sciezka.size() - 1; i++) {

            int a = sciezka.get(i);
            int b = sciezka.get(i + 1);

            if (a == from && b == to) {
                return true;
            }
        }

        return false;
    }
}
