package Data;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Wykres extends JPanel {

    private int[] xValues;
    private long[] yValues;
    private String title = "";

    private Map<String, Long> mapData;

    // TRYB 1: analiza (V/E)
    public void setData(int[] xValues, long[] yValues, String title) {
        this.xValues = xValues;
        this.yValues = yValues;
        this.title = title;
        this.mapData = null;
        repaint();
    }

    // TRYB 2: porównanie algorytmów
    public void setData(Map<String, Long> data) {
        this.mapData = data;
        this.xValues = null;
        this.yValues = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = getWidth();
        int h = getHeight();
        int m = 50;

        g.drawLine(m, h - m, w - m, h - m);
        g.drawLine(m, m, m, h - m);

        g.drawString(title, w / 2 - 60, 20);

        if (xValues != null && yValues != null) {

            long max = 1;
            for (long v : yValues) {
                max = Math.max(max, v);
            }

            int step = (w - 2 * m) / xValues.length;

            for (int i = 0; i < xValues.length; i++) {

                int barH = (int)((double)yValues[i] / max * (h - 2 * m));

                int x = m + i * step + 10;
                int y = h - m - barH;

                g.setColor(Color.BLUE);


                int px = x + (step - 20) / 2;
                int py = y;

                g.fillOval(px - 4, py - 4, 8, 8);

                if (i > 0) {

                    int prevBarH = (int)((double)yValues[i - 1] / max * (h - 2 * m));
                    int prevX = m + (i - 1) * step + 10 + (step - 20) / 2;
                    int prevY = h - m - prevBarH;

                    g.drawLine(prevX, prevY, px, py);
                }

                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(xValues[i]), x, h - m + 20);
                g.drawString(yValues[i] + " ns", x, y - 5);
            }
        }

        if (mapData != null) {

            long max = 1;
            for (long v : mapData.values()) {
                max = Math.max(max, v);
            }

            int i = 0;
            int step = (w - 2 * m) / mapData.size();

            for (String name : mapData.keySet()) {

                long value = mapData.get(name);

                int barH = (int)((double)value / max * (h - 2 * m));

                int x = m + i * step + 10;
                int y = h - m - barH;

                g.setColor(Color.RED);
                g.setColor(Color.RED);


                int px = x + (step - 20) / 2;
                int py = y;

                g.fillOval(px - 4, py - 4, 8, 8);

                if (i > 0) {

                    long prevVal = (long) mapData.values().toArray()[i - 1];
                    int prevBarH = (int)((double)prevVal / max * (h - 2 * m));

                    int prevX = m + (i - 1) * step + 10 + (step - 20) / 2;
                    int prevY = h - m - prevBarH;

                    g.drawLine(prevX, prevY, px, py);
                }

                g.setColor(Color.BLACK);
                g.drawString(name, x, h - m + 20);
                g.drawString(value + " ns", x, y - 5);

                i++;
            }
        }
    }
}